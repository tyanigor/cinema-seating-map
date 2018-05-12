package net.rest.cinemaseatingmap.service;

import net.rest.cinemaseatingmap.dao.RowDAO;
import net.rest.cinemaseatingmap.model.Hall;
import net.rest.cinemaseatingmap.model.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация сервиса для работы с рядами
 */
@Service("rowService")
@Transactional
public class RowServiceImpl implements RowService {

    /**
     * Для работы через DAO с рядами
     */
    @Autowired
    private RowDAO rowDAO;

    /**
     * Сервис для работы с залами
     */
    @Autowired
    private HallService hallService;

    /**
     * Поиск ряда по первичному ключу
     *
     * @param id - первичный ключ ряда
     * @return - модель данных ряда
     */
    @Override
    public Row findRowById(int id) {
        return rowDAO.findRowById(id);
    }

    /**
     * Сохранение ряда
     *
     * @param row - модель данных ряда
     */
    @Override
    public void saveRow(Row row) {
        rowDAO.persistRow(row);
    }

    /**
     * Обновление ряда
     *
     * @param row - модель данных ряда
     */
    @Override
    public void updateRow(Row row) {
        Row entity = rowDAO.findRowById(row.getId());

        if (entity != null) {
            // Обновлять значение Last не нужно, т.к. оно автоматически изменяется при добавлении
            // entity.setLast(row.isLast());
            entity.setNumber(row.getNumber());
        }
    }

    /**
     * Удаление ряда
     *
     * @param row - модель данных ряда
     */
    @Override
    public void deleteRow(Row row) {
        Row entity = rowDAO.findRowById(row.getId());

        if (entity != null) {
            rowDAO.deleteRow(entity);
        }
    }

    /**
     * Получение списка всех рядов
     *
     * @return - список моделей данных рядов
     */
    @Override
    public List<Row> listRows() {
        return rowDAO.listRows();
    }

    /**
     * Получение рядов по залу
     *
     * @param id - первичный ключ зала
     * @return - список моделей данных рядов
     */
    @Override
    public List<Row> findRowsByHallId(int id) {
        return rowDAO.findRowsByHallId(id);
    }

    /**
     * Сохранение ряда относящегося к залу
     *
     * @param row - ряд
     * @param id - первичный ключ зала
     */
    @Override
    public void saveRowByHallId(Row row, int id) {
        Hall hall = hallService.findHallById(id);

        if (hall != null) {
            row.setHall(hall);
            row.setLast(true);
            saveRow(row);

            /*
             * Изменим идентификатор last, так как после
             * добавления еще одного ряда он стал последним и плюс два перед ним
             */
            if (hall.getRows().size() > 1) {
                Row oldLastRow = hall.getRows().get(hall.getRows().size() - 2);
                oldLastRow.setLast(false);
                saveRow(oldLastRow);
            }
        }
    }

    /**
     * Поиск рядов по сеансу
     *
     * @param id - первичный ключ сеанса
     * @return - список рядов
     */
    @Override
    public List<Row> findRowsBySessionId(int id) {
        return rowDAO.findRowsBySessionId(id);
    }
}
