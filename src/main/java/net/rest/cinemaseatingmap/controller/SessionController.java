package net.rest.cinemaseatingmap.controller;

import net.rest.cinemaseatingmap.model.Session;
import net.rest.cinemaseatingmap.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RESTful контроллер для работы с сеансами
 */
@RestController
@RequestMapping("/api/v1/")
public class SessionController {

    /**
     * Сервис для работы с сеансами
     */
    @Autowired
    SessionService sessionService;

    /**
     * Получение списка сеансов
     *
     * @return - список сеансов и/или статус операции
     */
    @RequestMapping(value = { "/sessions" }, method = RequestMethod.GET)
    public ResponseEntity<List<Session>> listSessions() {
        List<Session> result = sessionService.listSessions();

        return new ResponseEntity<List<Session>>(result, HttpStatus.OK);
    }

    /**
     * Получение сеанса по первичному ключу
     *
     * @param id - первичный ключ сеанса
     * @return - сеанс и/или статус операции
     */
    @RequestMapping(value = { "/sessions/{id}" }, method = RequestMethod.GET)
    public ResponseEntity<Session> findSessionById(@PathVariable int id) {
        Session result = sessionService.findSessionById(id);

        return new ResponseEntity<Session>(result, HttpStatus.OK);
    }

    /**
     * Обновление сеанса
     *
     * @param session - сеанс
     * @return - статус операции
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = { "/halls/{id}/sessions" }, method = RequestMethod.PUT)
    public ResponseEntity<Void> updateSessionByHallId(@RequestBody Session session, @PathVariable int id) {
        sessionService.updateSessionByHallId(session, id);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * Удаление сеанса
     *
     * @param session - сеанс
     * @return - статус операции
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = { "/sessions" }, method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteHall(@RequestBody Session session) {
        sessionService.deleteSession(session);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * Создание сеанса и ассоциация его с залом
     *
     * @param session - сеанс
     * @param id - первичный ключ зала
     * @return - статус операции
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = { "/halls/{id}/sessions" }, method = RequestMethod.POST)
    public ResponseEntity<Void> saveSessionByHallId(@RequestBody Session session, @PathVariable int id) {
        sessionService.saveSessionByHallId(session, id);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
