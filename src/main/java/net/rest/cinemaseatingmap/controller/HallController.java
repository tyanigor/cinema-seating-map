package net.rest.cinemaseatingmap.controller;

import net.rest.cinemaseatingmap.model.Hall;
import net.rest.cinemaseatingmap.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RESTful контроллер для работы с залами
 */
@RestController
@RequestMapping("/api/v1/")
public class HallController {

    /**
     * Сервис для работы с залами
     */
    @Autowired
    HallService hallService;

    /**
     * Получение зала по первичному ключу
     *
     * @param id - первичный ключ
     * @return - зал
     */
    @RequestMapping(value = { "/halls/{id}" }, method = RequestMethod.GET)
    public ResponseEntity<Hall> findHallById(@PathVariable int id) {
        Hall result = hallService.findHallById(id);

        return new ResponseEntity<Hall>(result, HttpStatus.OK);
    }

    /**
     * Получение списка залов
     *
     * @return - список залов
     */
    @RequestMapping(value = { "/halls" }, method = RequestMethod.GET)
    public ResponseEntity<List<Hall>> listHalls() {
        List<Hall> result = hallService.listHalls();

        return new ResponseEntity<List<Hall>>(result, HttpStatus.OK);
    }

    /**
     * Создание зала
     *
     * @param hall - зал
     * @return - статус операции
     */
    @RequestMapping(value = { "/halls" }, method = RequestMethod.POST)
    public ResponseEntity<Void> createHall(@RequestBody Hall hall) {
        hallService.saveHall(hall);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * Обновление зала
     *
     * @param hall - зал
     * @return - статус операции
     */
    @RequestMapping(value = { "/halls" }, method = RequestMethod.PUT)
    public ResponseEntity<Void> updateHall(@RequestBody Hall hall) {
        hallService.updateHall(hall);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * Удаление зала
     *
     * @param hall - зал
     * @return - статус операции
     */
    @RequestMapping(value = { "/halls" }, method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteHall(@RequestBody Hall hall) {
        hallService.deleteHall(hall);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
