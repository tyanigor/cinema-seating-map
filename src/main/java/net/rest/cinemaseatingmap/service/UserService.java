package net.rest.cinemaseatingmap.service;

import net.rest.cinemaseatingmap.model.User;

public interface UserService {

    User findUserById(int id);

    User findUserByUsername(String username);

    void saveUser(User user);

    public User getAuthenticationUser();
}
