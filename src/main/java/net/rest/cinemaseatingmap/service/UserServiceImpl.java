package net.rest.cinemaseatingmap.service;

import net.rest.cinemaseatingmap.dao.UserDAO;
import net.rest.cinemaseatingmap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса для работы с пользователями
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    /**
     * Для работы через DAO с пользователями
     */
    @Autowired
    UserDAO userDAO;

    /**
     * Поиск пользователя по первичному ключу
     *
     * @param id - первичный ключ
     * @return - модель данных пользователя
     */
    @Override
    public User findUserById(int id) {
        return userDAO.findUserById(id);
    }

    /**
     * Поиск пользователя по логину
     *
     * @param username - логин пользователя
     * @return - модель данных пользователя
     */
    @Override
    public User findUserByUsername(String username) {
        return userDAO.findUserByUsername(username);
    }

    /**
     * Сохранение пользователя
     *
     * @param user - модель данных пользователя
     */
    @Override
    public void saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDAO.persistUser(user);
    }

    /**
     * Возвращает аутентифицированного пользователя
     *
     * @return - пользователь
     */
    public User getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findUserByUsername(authentication.getName());
    }
}
