package net.rest.cinemaseatingmap.dao;

import net.rest.cinemaseatingmap.model.Operation;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Реализация DAO для операции
 */
@Repository("operationDAO")
public class OperationDAOImpl extends AbstractDAO<Integer, Operation> implements OperationDAO {

    /**
     * Получение операции по первичному ключу
     *
     * @param id - первичный ключ
     * @return - модель операции
     */
    @Override
    public Operation findOperationById(int id) {
        return getByKey(id);
    }

    /**
     * Сохранение операции
     *
     * @param operation - модель операции
     */
    @Override
    public void persistOperation(Operation operation) {
        persist(operation);
    }

    /**
     * Удаление операции
     *
     * @param operation - модель операции
     */
    @Override
    public void deleteOperation(Operation operation) {
        delete(operation);
    }

    /**
     * Получение списка операций
     *
     * @return - список моделей операций
     */
    @Override
    public List<Operation> listOperations() {
        return list();
    }

    /**
     * Возвращает список моделей операций относящихся к сеансу
     *
     * @param id - первичный ключ сеанса
     * @return - список моделей операций
     */
    @Override
    public List<Operation> findOperationsBySessionId(int id) {
        return (List<Operation>) createEntityCriteria()
            .createCriteria("session")
                .add(Restrictions.idEq(id))
            .list();
    }

    /**
     * Находит последнюю операцию ассоциированную с сеансом и местом
     *
     * @param sessionId - первичный ключ сеанса
     * @param seatId - первичный ключ места
     * @return - последняя операция
     */
    public Operation findLastOperationBySessionIdAndSeatId(int sessionId, int seatId) {
        return (Operation) createEntityCriteria()
                .add(Restrictions.and(
                    Restrictions.eq("session.id", sessionId),
                    Restrictions.eq("seat.id", seatId)
                ))
                .addOrder(Order.desc("id"))
                .setMaxResults(1)
                .uniqueResult();
    }
}
