package net.rest.cinemaseatingmap.dto;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Класс описывающий ошибку
 */
public class Error {

    /**
     * Ошибка
     */
    private String message;

    /**
     * Тип ошибки
     */
    private String type;

    public ModelAndView asModelAndView() {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        ModelAndView modelAndView = new ModelAndView(jsonView);

        modelAndView.addObject("error", true);
        modelAndView.addObject("type", type);
        modelAndView.addObject("message", message);

        return modelAndView;
    }

    public Error(String message, String type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
