package net.rest.cinemaseatingmap.controller;

import net.rest.cinemaseatingmap.model.Seat;
import net.rest.cinemaseatingmap.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RESTful контроллер для работы с местами
 */
@RestController
@RequestMapping("/api/v1/")
public class SeatController {

    /**
     * Сервис для работы с местами
     */
    @Autowired
    SeatService seatService;

    /**
     * Получение списка мест ассоциированных с рядом
     *
     * @param id - первичный ключ ряда
     * @return - список мест и/или статус операции
     */
    @RequestMapping(value = { "/rows/{id}/seats" }, method = RequestMethod.GET)
    public ResponseEntity<List<Seat>> findSeatsByRowId(@PathVariable int id) {
        List<Seat> result = seatService.findSeatsByRowId(id);

        return new ResponseEntity<List<Seat>>(result, HttpStatus.OK);
    }

    /**
     * Создание места ассоциированного с рядом
     *
     * @param seats - место
     * @param id - первичный ключ ряда
     * @return - статус операции
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = { "/rows/{id}/seats" }, method = RequestMethod.POST)
    public ResponseEntity<Void> saveSeatsByRowId(@RequestBody List<Seat> seats, @PathVariable int id) {
        seatService.saveSeatsByRowId(seats, id);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
