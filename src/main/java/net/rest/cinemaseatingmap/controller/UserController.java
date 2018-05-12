package net.rest.cinemaseatingmap.controller;

import net.rest.cinemaseatingmap.model.User;
import net.rest.cinemaseatingmap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * RESTful контроллер для работы с пользователями
 */
@RestController
public class UserController {

    /**
     * Сервис для работы с пользователями
     */
    @Autowired
    UserService userService;

    /**
     * Регистрация нового пользователя
     *
     * @param user - новый пользователь
     * @return - статус результата
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        if (userService.findUserByUsername(user.getUsername()) != null) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        userService.saveUser(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * Получение пользователя по username
     *
     * @param username - логин пользователя
     * @return - пользователь и статус
     */
    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        User result = userService.findUserByUsername(username);

        if (result != null) {
            result.setPassword("");
        }

        return new ResponseEntity<User>(result, HttpStatus.OK);
    }

    /**
     * Возвращает текущего пользователя
     *
     * @return - текущий пользователь
     */
    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    public ResponseEntity<User> currentUser() {
        User result = userService.getAuthenticationUser();

        return new ResponseEntity<User>(result, HttpStatus.OK);
    }
}
