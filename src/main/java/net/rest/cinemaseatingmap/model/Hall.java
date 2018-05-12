package net.rest.cinemaseatingmap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Модель описывающая сущность "зал"
 */
@Entity
@Table(name = "t_hall")
public class Hall {

    /**
     * Идентификатор зала
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hall_id")
    private int id;

    /**
     * Номер зала
     */
    @Column(name = "hall_number", nullable = false)
    private int number;

    /**
     * Ряды содержащиеся в зале
     */
    @JsonIgnore
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<Row> rows;

    /**
     * Сеансы относящиеся к залу
     */
    @JsonIgnore
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<Session> sessions;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
