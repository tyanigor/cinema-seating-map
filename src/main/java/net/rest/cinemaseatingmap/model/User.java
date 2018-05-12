package net.rest.cinemaseatingmap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Модель описывающая сущность "пользователь"
 */
@Entity
@Table(name="t_user")
public class User {

    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    /**
     * Пароль
     */
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Логин
     */
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /**
     * Роль пользователя
     */
    @Column(name = "role", nullable = false)
    private Role role = Role.USER;

    /**
     * Перечисление ролей пользователей
     */
    public enum Role {
        ADMIN("ROLE_ADMIN"),

        USER("ROLE_USER");

        private final String text;

        Role(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
