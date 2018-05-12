package net.rest.cinemaseatingmap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Модель описывающая сущность "сеанс"
 */
@Entity
@Table(name = "t_session")
public class Session {

    /**
     * Идентификатор сеанса
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "session_id")
    private int id;

    /**
     * Дата и время начала сеанса
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = false)
    private Date start;

    /**
     * Дата и время окончания сеанса (Фильм может начинатся в один день в 23-30 и заканчиватся на следующий день в 01-30 поэтому две даты)
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time", nullable = false)
    private Date end;

    /**
     * Фильм
     */
    @Column(name = "movie", nullable = false)
    String movie;

    /**
     * Зал в котором проводится сеанс
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "hall_id")
    private Hall hall;

    /**
     * Операции по сеансу
     */
    @JsonIgnore
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<Operation> operations;

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
