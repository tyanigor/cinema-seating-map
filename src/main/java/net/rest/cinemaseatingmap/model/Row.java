package net.rest.cinemaseatingmap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Модель описывающая сущность "ряд"
 */
@Entity
@Table(name = "t_row")
public class Row {

    /**
     * Идентификатор ряда
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "row_id")
    private int id;

    /**
     * Номер ряда
     */
    @Column(name = "row_number", nullable = false)
    private int number;

    /**
     * Идентификатор, который позволяет определить последний ряд или нет
     */
    @Column(name = "is_last_row", nullable = false)
    private boolean last = false;

    /**
     * Места относящиеся к ряду
     */
    @JsonIgnore
    @OneToMany(mappedBy = "row", cascade = CascadeType.ALL)
    private List<Seat> seats;

    /**
     * Зал в котром находятся места
     */
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "hall_id")
    private Hall hall;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}
