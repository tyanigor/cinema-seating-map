package net.rest.cinemaseatingmap.controller;

import net.rest.cinemaseatingmap.dto.Error;
import net.rest.cinemaseatingmap.model.Operation;
import net.rest.cinemaseatingmap.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * RESTful контроллер для работы с операциями
 */
@RestController
@RequestMapping("/api/v1/")
public class OperationController {

    /**
     * Сервис для работы с операциями
     */
    @Autowired
    private OperationService operationService;

    /**
     * Получение списка операций по сеансу
     *
     * @param id - первичный ключ сеанса
     * @return - список сеансов
     */
    @RequestMapping(value = { "/sessions/{id}/operations" }, method = RequestMethod.GET)
    public ResponseEntity<List<Operation>> findOperationsBySessionId(@PathVariable int id) {
        List<Operation> result = operationService.findOperationsBySessionId(id);

        return new ResponseEntity<List<Operation>>(result, HttpStatus.OK);
    }

    /**
     * Создание список операций ассоциированного с сеансом и местом
     *
     * @param operations - список операций
     * @param id - сеанс
     * @return - статус операции
     */
    @RequestMapping(value = { "/sessions/{id}/operations" }, method = RequestMethod.POST)
    public ResponseEntity<Void> saveOperationsBySessionIdAndSeatId(@RequestBody List<Operation> operations, @PathVariable int id) {
        operationService.saveOperationsBySessionId(operations, id);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * В случае возникновения ошибки выводит результат
     *
     * @param exc - Исключение
     * @return - описание ошибки
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView errorHandler(Exception exc) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=utf-8");

        return new Error(exc.getMessage(), exc.getClass().getSimpleName()).asModelAndView();
    }
}
