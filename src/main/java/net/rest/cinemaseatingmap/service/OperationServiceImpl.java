package net.rest.cinemaseatingmap.service;

import net.rest.cinemaseatingmap.dao.OperationDAO;
import net.rest.cinemaseatingmap.model.Operation;
import net.rest.cinemaseatingmap.model.Seat;
import net.rest.cinemaseatingmap.model.Session;
import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сервиса для работы с операциями
 */
@Service("operationService")
@Transactional
public class OperationServiceImpl implements OperationService {

    /**
     * Для работы через DAO с операциями
     */
    @Autowired
    private OperationDAO operationDAO;

    /**
     * Сервис для работы с сеансами
     */
    @Autowired
    private SessionService sessionService;

    /**
     * Сервис для работы с местами
     */
    @Autowired
    private SeatService seatService;

    /**
     * Сервис для работы с пользователями
     */
    @Autowired
    private UserService userService;

    /**
     * Поиск операции по первичному ключу
     *
     * @param id - первичный ключ
     * @return - модель данных операции
     */
    @Override
    public Operation findOperationById(int id) {
        return operationDAO.findOperationById(id);
    }

    /**
     * Сохранение операции
     *
     * @param operation - модель данных операции
     */
    @Override
    public void saveOperation(Operation operation) {
        operation.setUser(userService.getAuthenticationUser());
        operationDAO.persistOperation(operation);
    }

    /**
     * Обновление модели данных операции
     *
     * @param operation - модель данных операции
     */
    @Override
    public void updateOperation(Operation operation) {
        Operation entity = operationDAO.findOperationById(operation.getId());

        if (entity != null) {
            entity.setType(operation.getType());
        }
    }

    /**
     * Удаление модели данных операции
     *
     * @param operation - модель данных операции
     */
    @Override
    public void deleteOperation(Operation operation) {
        Operation entity = operationDAO.findOperationById(operation.getId());

        if (entity != null) {
            operationDAO.deleteOperation(entity);
        }
    }

    /**
     * Получение всего списка операций
     *
     * @return - список моделей данных операций
     */
    @Override
    public List<Operation> listOperations() {
        return operationDAO.listOperations();
    }

    /**
     * Поиск операций относящихся к сеанса
     *
     * @param id - первичный ключ сеанса
     * @return - список операций
     */
    @Override
    public List<Operation> findOperationsBySessionId(int id) {
        return operationDAO.findOperationsBySessionId(id);
    }

    /**
     * Сохранение операции по сеансу и месту
     *
     * @param operation - операция
     * @param sessionId - первичный ключ сеанса
     * @param seatId - первичный ключ места
     */
    @Override
    public void saveOperationBySessionIdAndSeatId(Operation operation, int sessionId, int seatId) {
        Session session = sessionService.findSessionById(sessionId);
        Seat seat = seatService.findSeatById(seatId);

        if (session != null && seat != null) {
            operation.setSession(session);
            operation.setSeat(seat);
            saveOperation(operation);
        }
    }

    /**
     * Находит последнюю операцию ассоциированную с сеансом и местом
     *
     * @param sessionId - первичный ключ сеанса
     * @param seatId - первичный ключ места
     * @return - последняя операция
     */
    public Operation findLastOperationBySessionIdAndSeatId(int sessionId, int seatId) {
        return operationDAO.findLastOperationBySessionIdAndSeatId(sessionId, seatId);
    }

    /**
     * Сохранение список операций по сеансу и месту
     *
     * @param operations - список операций
     * @param id - первичный ключ сеанса
     */
    @Override
    public void saveOperationsBySessionId(List<Operation> operations, int id) {
        for (Operation operation : operations) {
            /*
             * Получим последнюю операцию по ассоциированному сеансу и месту
             */
            Operation lastOperation = findLastOperationBySessionIdAndSeatId(id, operation.getSeat().getId());

            /*
             * Если последняя операция по месту равна добавляемой операции
             */
            if (lastOperation != null && lastOperation.getType() == operation.getType()
                    || lastOperation == null && operation.getType() == Operation.OperationType.FREE) {
                throw new IllegalArgumentException("Место уже "
                        + (operation.getType() == Operation.OperationType.FREE ? "свободно" : "занято"));
            }

            /*
             * Если операция снятия брони, но бронировал место другой пользователь
             */
            if (lastOperation != null && lastOperation.getType() == Operation.OperationType.RESERVE
                    && operation.getType() == Operation.OperationType.FREE
                    && lastOperation.getUser().getId() != userService.getAuthenticationUser().getId()) {
                throw new IllegalAddException("Вы не можете снять бронь чужого пользователя");
            }
        }
        /*
         * Если бронируются последние места, то только по два мест рядом
         */
        if (!checkOperationsForLastRows(operations)) {
            throw new IllegalAddException("Последние места бронируются/снимается бронь только рядом по два места");
        }

        /*
         * Если ошибок не было, сохраним операции
         */
        for (Operation operation : operations) {
            saveOperationBySessionIdAndSeatId(operation, id, operation.getSeat().getId());
        }
    }

    /**
     * Делает проверку операций для последних рядов,
     * если бронируются места в последних рядах, нужно учесть, что они
     * бронируются только по 2 места рядом друг с другом
     *
     * @param operations - список операций для проверки
     * @return  - булевый результат
     */
    protected boolean checkOperationsForLastRows(List<Operation> operations) {
        /*
         * Сделаем проверку, что местам находятся друг рядом с другом
         */
        List<Operation> operationsForLastRow = filterReserveOperationsForLastRows(operations);
        int operationsCountInLastRows = 0; // Количество опеций для последних рядов

        for(Operation operation1 : operationsForLastRow) {
            boolean seat1HasReservedSeat2 = false; // Идентификатор место1 имеет рядом забронированное место2
            operationsCountInLastRows++;

            /*
             * Если слева или справа от бронируемого места есть бронируемое место
             * и эти места находятся в одном ряду и операции одинакового типа (бронировать или снять бронь
             * можно только с двух сразу)
             */
            for(Operation operation2 : operationsForLastRow) {
                if ((operation1.getSeat().getNumber() == operation2.getSeat().getNumber() - 1
                        || operation1.getSeat().getNumber() == operation2.getSeat().getNumber() + 1)
                        && operation1.getSeat().getRow().getNumber() == operation2.getSeat().getRow().getNumber()
                        && operation1.getType() == operation2.getType()) {
                    seat1HasReservedSeat2 = true;
                    break;
                }
            }

            if (!seat1HasReservedSeat2) { // Если рядом с местом нет ни одного забронированного места
                return false;
            }
        }

        /*
         * Операций в последних рядах должно быть четное число
         * так как по бронировать можно только по 2 места (для случая если будет
         * забронировано нечетное количество мест рядом друг с другом)
         */
        if (operationsCountInLastRows % 2 != 0) {
            return false;
        }

        return true;
    }

    /**
     * Фильтрует операции только для последних рядов
     *
     * @param operations - список входных операций
     * @return  - отфильтрованный список операций
     */
    protected List<Operation> filterReserveOperationsForLastRows(List<Operation> operations) {
        List<Operation> result = new ArrayList<>();

        for(Operation operation : operations) {
            Seat seat = seatService.findSeatById(operation.getSeat().getId());

            /*
             * Проверим, если операция предназначена для место в последнем ряду
             * то добавим в результат данную операцию
             */
            if (seat != null && seat.getRow().isLast()) {
                operation.setSeat(seat);
                result.add(operation);
            }
        }

        return result;
    }
}
