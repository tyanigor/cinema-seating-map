package net.rest.cinemaseatingmap.service;

import net.rest.cinemaseatingmap.dao.SessionDAO;
import net.rest.cinemaseatingmap.model.Hall;
import net.rest.cinemaseatingmap.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Реализация сервиса для работы с сеансами
 */
@Service("sessionService")
@Transactional
public class SessionServiceImpl implements SessionService {

    /**
     * Для работы через DAO с сеансами
     */
    @Autowired
    private SessionDAO sessionDao;

    /**
     * Сервис для работы с залами
     */
    @Autowired
    private HallService hallService;

    /**
     * Поиск сеанса по первичному ключу
     *
     * @param id - первичный ключ сеанса
     * @return - модель данных сеанса
     */
    @Override
    public Session findSessionById(int id) {
        return sessionDao.findSessionById(id);
    }

    /**
     * Сохранение сеанса
     *
     * @param session - модель данных сеанса
     */
    @Override
    public void saveSession(Session session) {
        if (checkSessionsDates(session.getStart(), session.getEnd())) {
            sessionDao.persistSession(session);
        }
    }

    /**
     * Обновление сеанса
     *
     * @param session - модель данных сеанса
     */
    @Override
    public void updateSession(Session session) {
        Session entity = sessionDao.findSessionById(session.getId());
        if (entity != null && checkSessionsDates(session.getStart(), session.getEnd())) {
            entity.setStart(session.getStart());
            entity.setEnd(session.getEnd());
            entity.setMovie(session.getMovie());
        }
    }

    /**
     * Удаление сеанса
     *
     * @param session - модель данных сеанса
     */
    @Override
    public void deleteSession(Session session) {
        Session entity = sessionDao.findSessionById(session.getId());

        if (entity != null) {
            sessionDao.deleteSession(entity);
        }
    }

    /**
     * Получение списка всех сеансов
     *
     * @return - список моделей данных сеансов
     */
    @Override
    public List<Session> listSessions() {
        return sessionDao.listSessions();
    }

    /**
     * Сохранение сеанса с указанием зала
     *
     * @param session - сеанс
     * @param id - первичный ключ зала
     */
    @Override
    public void saveSessionByHallId(Session session, int id) {
        Hall hall = hallService.findHallById(id);

        if (hall != null) {
            session.setHall(hall);
            saveSession(session);
        }
    }

    /**
     * Обновление сеанса с изменением ассоциированного зала
     *
     * @param session - сеанс
     * @param id - первичный ключ зала
     */
    @Override
    public void updateSessionByHallId(Session session, int id) {
        Hall hall = hallService.findHallById(id);

        if (hall != null && checkSessionsDates(session.getStart(), session.getEnd())) {
            session.setHall(hall);
            updateSession(session);
        }
    }

    /**
     * Производит проверку дат, первая дата должна быть меньше второй
     * при этом даты и время не должны пересекаться с текущими сеансами
     *
     * @param startDate - дата и время начала
     * @param endDate - дата и время окончания
     * @return - булевый результат
     */
    protected boolean checkSessionsDates(Date startDate, Date endDate) {
        if (startDate.after(endDate)) { // дата начала должна быть меньше даты конца
            return false;
        }

        // Проверим даты по существующим сеансам, чтобы не было пересечений
        return sessionDao.findSessionByStartDateAndEndDate(startDate, endDate).size() == 0;
    }
}
