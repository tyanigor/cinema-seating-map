package net.rest.cinemaseatingmap.dao;

import net.rest.cinemaseatingmap.model.Session;

import java.util.Date;
import java.util.List;

public interface SessionDAO {

    Session findSessionById(int id);

    void persistSession(Session session);

    void deleteSession(Session session);

    List<Session> listSessions();

    List<Session> findSessionByStartDateAndEndDate(Date startDate, Date endDate);
}
