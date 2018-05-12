package net.rest.cinemaseatingmap.service;

import net.rest.cinemaseatingmap.dao.HallDAO;
import net.rest.cinemaseatingmap.model.Hall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация сервиса для работы с залами
 */
@Service("hallService")
@Transactional
public class HallServiceImpl implements HallService {

    /**
     * Для работы через DAO с залами
     */
    @Autowired
    private HallDAO hallDAO;

    /**
     * Получение зала по первичному ключу
     *
     * @param id - первичный ключ
     * @return - модель данных зала
     */
    @Override
    public Hall findHallById(int id) {
        return hallDAO.findHallById(id);
    }

    /**
     * Сохранение зала
     *
     * @param hall - модель данных зала
     */
    @Override
    public void saveHall(Hall hall) {
        hallDAO.persistHall(hall);
    }

    /**
     * Обновление модели данных зала
     *
     * @param hall - модель данных зала
     */
    @Override
    public void updateHall(Hall hall) {
        Hall entity = hallDAO.findHallById(hall.getId());

        if (entity != null) {
            entity.setNumber(hall.getNumber());
        }
    }

    /**
     * Удаление зала
     *
     * @param hall - модель данных зала
     */
    @Override
    public void deleteHall(Hall hall) {
        Hall entity = hallDAO.findHallById(hall.getId());

        if (entity != null) {
            hallDAO.deleteHall(entity);
        }
    }

    /**
     * Получение списка моделей данных зала
     *
     * @return - список моделей данных зала
     */
    @Override
    public List<Hall> listHalls() {
        return hallDAO.listHalls();
    }
}
