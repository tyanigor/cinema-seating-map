package net.rest.cinemaseatingmap.dao;

import net.rest.cinemaseatingmap.model.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Реализация DAO для сеанса
 */
@Repository("sessionDAO")
public class SessionDAOImpl extends AbstractDAO<Integer, Session> implements SessionDAO {

    /**
     * Получение сеанса по первичному ключу
     *
     * @param id - первичный ключ
     * @return - модель сеанса
     */
    @Override
    public Session findSessionById(int id) {
        return getByKey(id);
    }

    /**
     * Сохранение сеанса
     *
     * @param session - модель сеанса
     */
    @Override
    public void persistSession(Session session) {
        persist(session);
    }

    /**
     * Удаление сеанса
     *
     * @param session - модель сеанса
     */
    @Override
    public void deleteSession(Session session) {
        delete(session);
    }

    /**
     * Получение списка сеансов
     *
     * @return - список моделей сеансов
     */
    @Override
    public List<Session> listSessions() {
        return list();
    }

    /**
     * По заданному началу сеанса и консу сеанса ищет все
     * сеансы входящие в данный промежуток
     *
     * @param startDate - дата начала сеанса
     * @param endDate - дата конца сенса
     * @return - сеансы которые входят в данное условие
     */
    @Override
    public List<Session> findSessionByStartDateAndEndDate(Date startDate, Date endDate) {
        return (List<Session>) createEntityCriteria()
                .add(Restrictions.or(Restrictions.between("start", startDate, endDate),
                                        Restrictions.between("end", startDate, endDate)))
                .list();
    }
}
