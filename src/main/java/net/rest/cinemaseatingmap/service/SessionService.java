package net.rest.cinemaseatingmap.service;

import net.rest.cinemaseatingmap.model.Session;

import java.util.List;

public interface SessionService {

    Session findSessionById(int id);

    void saveSession(Session session);

    void updateSession(Session session);

    void deleteSession(Session session);

    List<Session> listSessions();

    void saveSessionByHallId(Session session, int id);

    void updateSessionByHallId(Session session, int id);
}
