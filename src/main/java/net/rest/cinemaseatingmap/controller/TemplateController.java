package net.rest.cinemaseatingmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Контроллер отдающий шаблоны для AngularJS
 */
@Controller
public class TemplateController {

    /**
     * Точка входа
     *
     * @return - шаблон jsp
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage() {
        return "index";
    }

    /**
     * Часть отвечающая за форму логина
     *
     * @return - шаблон jsp
     */
    @RequestMapping(value = "/template/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    /**
     * Часть отвечающая за форму регистрации
     *
     * @return - шаблон jsp
     */
    @RequestMapping(value = "/template/registration", method = RequestMethod.GET)
    public String registrationPage() {
        return "registration";
    }

    /**
     * Часть отвечающая за форму формирование списка сеансов
     *
     * @return - шаблон jsp
     */
    @RequestMapping(value = "/template/sessions", method = RequestMethod.GET)
    public String sessionsPage() {
        return "sessions";
    }

    /**
     * Часть отвечающая за форму отображение сеанса
     *
     * @return - шаблон jsp
     */
    @RequestMapping(value = "/template/session", method = RequestMethod.GET)
    public String sessionPage() {
        return "session";
    }
}
