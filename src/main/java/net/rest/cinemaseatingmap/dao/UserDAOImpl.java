package net.rest.cinemaseatingmap.dao;

import net.rest.cinemaseatingmap.model.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userDAO")
public class UserDAOImpl extends AbstractDAO<Integer, User> implements UserDAO {

    /**
     * Поиск пользователя по первичному ключу
     *
     * @param id - первичный ключ
     * @return - пользователь
     */
    @Override
    public User findUserById(int id) {
        return getByKey(id);
    }

    /**
     * Поиск пользователя по логину
     *
     * @param username - логин
     * @return - пользователь
     */
    @Override
    public User findUserByUsername(String username) {
        return (User) createEntityCriteria()
                .add(Restrictions.eq("username", username))
                .uniqueResult();
    }

    /**
     * Сохранение пользователя
     *
     * @param user - пользователь
     */
    @Override
    public void persistUser(User user) {
        persist(user);
    }
}
