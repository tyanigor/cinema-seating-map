package net.rest.cinemaseatingmap.dao;

import net.rest.cinemaseatingmap.model.Seat;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Реализация DAO для места
 */
@Repository("seatDAO")
public class SeatDAOImpl extends AbstractDAO<Integer, Seat> implements SeatDAO {

    /**
     * Получение места по первичному ключу
     *
     * @param id - первичный ключ
     * @return - модель места
     */
    @Override
    public Seat findSeatById(int id) {
        return getByKey(id);
    }

    /**
     * Сохранение места
     *
     * @param seat - модель места
     */
    @Override
    public void persistSeat(Seat seat) {
        persist(seat);
    }

    /**
     * Удаление места
     *
     * @param seat - модель места
     */
    @Override
    public void deleteSeat(Seat seat) {
        delete(seat);
    }

    /**
     * Получение списка мест
     *
     * @return - список моделей мест
     */
    @Override
    public List<Seat> listSeats() {
        return list();
    }

    /**
     * Поиск мест по первичному ключу ряда
     *
     * @param id - первичный ключ ряда
     * @return - список мест
     */
    @Override
    public List<Seat> findSeatsByRowId(int id) {
        return (List<Seat>) createEntityCriteria()
            .createCriteria("row")
                .add(Restrictions.idEq(id))
            .list();
    }
}
