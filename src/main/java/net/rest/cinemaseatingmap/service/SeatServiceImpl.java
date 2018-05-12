package net.rest.cinemaseatingmap.service;

import net.rest.cinemaseatingmap.dao.SeatDAO;
import net.rest.cinemaseatingmap.model.Row;
import net.rest.cinemaseatingmap.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация сервиса для работы с местами
 */
@Service("seatService")
@Transactional
public class SeatServiceImpl implements SeatService {

    /**
     * Для работы через DAO с местами
     */
    @Autowired
    private SeatDAO seatDAO;

    /**
     * Сервис для работы рядами
     */
    @Autowired
    private RowService rowService;

    /**
     * Поиск места по первичному ключу
     *
     * @param id - первичный ключ
     * @return - модель данных места
     */
    @Override
    public Seat findSeatById(int id) {
        return seatDAO.findSeatById(id);
    }

    /**
     * Сохранение места
     *
     * @param seat - модель данных места
     */
    @Override
    public void saveSeat(Seat seat) {
        seatDAO.persistSeat(seat);
    }

    /**
     * Обновление места
     *
     * @param seat - модель данных места
     */
    @Override
    public void updateSeat(Seat seat) {
        Seat entity = seatDAO.findSeatById(seat.getId());

        if (entity != null) {
            entity.setNumber(seat.getNumber());
        }
    }

    /**
     * Удаление места
     *
     * @param seat - модель данных места
     */
    @Override
    public void deleteSeat(Seat seat) {
        Seat entity = seatDAO.findSeatById(seat.getId());

        if (entity != null) {
            seatDAO.deleteSeat(entity);
        }
    }

    /**
     * Получение всего списка места
     *
     * @return - список моделей данных мест
     */
    @Override
    public List<Seat> listSeats() {
        return seatDAO.listSeats();
    }

    /**
     * Поиск мест относящихся к ряду
     *
     * @param id - первичный ключ ряда
     * @return - список моделей данных ряда
     */
    @Override
    public List<Seat> findSeatsByRowId(int id) {
        return seatDAO.findSeatsByRowId(id);
    }

    /**
     * Создание места ассоциированного с рядом
     *
     * @param seat - место
     * @param id - первичный ключ ряда
     */
    public void saveSeatByRowId(Seat seat, int id) {
        Row row = rowService.findRowById(id);

        if (row != null) {
            seat.setRow(row);
            saveSeat(seat);
        }
    }

    /**
     * Создание списка мест ассоциированного с рядом
     *
     * @param seats - список мест
     * @param id - первичный ключ ряда
     */
    public void saveSeatsByRowId(List<Seat> seats, int id) {
        for (Seat seat : seats) {
            saveSeatByRowId(seat, id);
        }
    }
}
