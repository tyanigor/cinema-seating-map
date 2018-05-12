package net.rest.cinemaseatingmap.controller;

import net.rest.cinemaseatingmap.model.Row;
import net.rest.cinemaseatingmap.service.RowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RESTful контроллер для работы с рядами
 */
@RestController
@RequestMapping("/api/v1/")
public class RowController {

    /**
     * Сервис для работы с рядами
     */
    @Autowired
    RowService rowService;

    /**
     * Получение ряда по первичному ключу
     *
     * @param id - первичный ключ
     * @return - ряд и/или статус операции
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = { "/rows/{id}" }, method = RequestMethod.GET)
    public ResponseEntity<Row> findRowById(@PathVariable int id) {
        Row result = rowService.findRowById(id);

        return new ResponseEntity<Row>(result, HttpStatus.OK);
    }

    /**
     * Обновление ряда
     *
     * @param row - ряд
     * @return - статус операции
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = { "/rows" }, method = RequestMethod.PUT)
    public ResponseEntity<Void> updateRow(@RequestBody Row row) {
        rowService.updateRow(row);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * Удаление ряда
     *
     * @param row - ряд
     * @return - статус операции
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = { "/rows" }, method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteRow(@RequestBody Row row) {
        rowService.deleteRow(row);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * Получение списка рядов ассоциированных с залом
     *
     * @param id - первичный ключ зала
     * @return - список рядов и/или статус операции
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = { "/halls/{id}/rows" }, method = RequestMethod.GET)
    public ResponseEntity<List<Row>> findRowsByHallId(@PathVariable int id) {
        List<Row> result = rowService.findRowsByHallId(id);

        return new ResponseEntity<List<Row>>(result, HttpStatus.OK);
    }

    /**
     * Создание ряда ассоциированного с залом
     *
     * @param row - ряд
     * @param id - первичный ключ зала
     * @return - статус операции
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = { "/halls/{id}/rows" }, method = RequestMethod.POST)
    public ResponseEntity<Void> saveRowByHallId(@RequestBody Row row, @PathVariable int id) {
        rowService.saveRowByHallId(row, id);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * Получение списка рядов по ассоциированной сессии
     *
     * @param id - первичный ключ сессии
     * @return - список рядов и/или статус операции
     */
    @RequestMapping(value = { "/sessions/{id}/rows" }, method = RequestMethod.GET)
    public ResponseEntity<List<Row>> findRowsBySessionId(@PathVariable int id) {
        List<Row> result = rowService.findRowsBySessionId(id);

        return new ResponseEntity<List<Row>>(result, HttpStatus.OK);
    }
}
