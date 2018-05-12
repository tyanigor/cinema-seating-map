package net.rest.cinemaseatingmap.dao;

import net.rest.cinemaseatingmap.model.User;

public interface UserDAO {

    User findUserById(int id);

    User findUserByUsername(String username);

    void persistUser(User user);
}
