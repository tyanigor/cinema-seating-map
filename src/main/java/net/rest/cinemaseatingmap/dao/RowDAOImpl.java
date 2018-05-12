package net.rest.cinemaseatingmap.dao;

import net.rest.cinemaseatingmap.model.Row;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Реализация DAO для ряда
 */
@Repository("rowDAO")
public class RowDAOImpl extends AbstractDAO<Integer, Row> implements RowDAO {

    /**
     * Получение ряда по первичному ключу
     *
     * @param id - первичный ключ
     * @return - модель ряда
     */
    @Override
    public Row findRowById(int id) {
        return getByKey(id);
    }

    /**
     * Сохранение ряда
     *
     * @param row - модель ряда
     */
    @Override
    public void persistRow(Row row) {
        persist(row);
    }

    /**
     * Удаление ряда
     *
     * @param row - модель ряда
     */
    @Override
    public void deleteRow(Row row) {
        delete(row);
    }

    /**
     * Получение списка рядов
     *
     * @return - список моделей ряда
     */
    @Override
    public List<Row> listRows() {
        return list();
    }

    /**
     * Получение списка рядов относящихся к залу
     *
     * @param id - первичный ключ зала
     * @return - список моделей рядов
     */
    @Override
    public List<Row> findRowsByHallId(int id) {
        return (List<Row>) createEntityCriteria()
            .createCriteria("hall")
                .add(Restrictions.idEq(id))
            .list();
    }

    /**
     * Получение списка рядов относящихся к сеансу
     *
     * @param id - первичный ключ сеанса
     * @return - список моделей рядов
     */
    @Override
    public List<Row> findRowsBySessionId(int id) {
        return (List<Row>) createEntityCriteria()
            .createCriteria("hall")
                .createCriteria("sessions")
                    .add(Restrictions.idEq(id))
            .list();
    }
}
