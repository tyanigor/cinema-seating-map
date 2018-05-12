package net.rest.cinemaseatingmap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Модель описывающая сущность "операция"
 */
@Entity
@Table(name = "t_operation")
public class Operation {

    /**
     * Идентификатор операции
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "operation_id")
    private int id;

    /**
     * Тип операции
     */
    @Column(name = "operation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type = OperationType.FREE;

    /**
     * Сеанс, к которому относится операция
     */
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "session_id")
    private Session session;

    /**
     * Место, к которому относится операция
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    /**
     * Пользователь добавевший операцию
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Типы операций
     */
    public enum OperationType {
        FREE,       // Освобождение
        RESERVE     // Бронирование
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
