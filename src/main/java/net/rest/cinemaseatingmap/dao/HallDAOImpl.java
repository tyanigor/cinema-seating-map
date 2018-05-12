package net.rest.cinemaseatingmap.dao;

import net.rest.cinemaseatingmap.model.Hall;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Реализация DAO для зала
 */
@Repository("hallDAO")
public class HallDAOImpl extends AbstractDAO<Integer, Hall> implements HallDAO {

    /**
     * Получение зала по первичному ключу
     *
     * @param id - первичный ключ
     * @return - модель зала
     */
    @Override
    public Hall findHallById(int id) {
        return getByKey(id);
    }

    /**
     * Сохранение зала
     *
     * @param hall - модель зала
     */
    @Override
    public void persistHall(Hall hall) {
        persist(hall);
    }

    /**
     * Удаление зала
     *
     * @param hall - модель зала
     */
    @Override
    public void deleteHall(Hall hall) {
        delete(hall);
    }

    /**
     * Получение списка залов
     *
     * @return - список моделей залов
     */
    @Override
    public List<Hall> listHalls() {
        return list();
    }
}
