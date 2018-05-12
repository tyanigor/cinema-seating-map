package net.rest.cinemaseatingmap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Модель описывающая сущность "место"
 */
@Entity
@Table(name = "t_seat")
public class Seat {

    /**
     * Идентификатор места
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seat_id")
    private int id;

    /**
     * Номер места
     */
    @Column(name = "seat_number", nullable = false)
    private int number;

    /**
     * Ряд к которому относится место
     */
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "row_id")
    private Row row;

    /**
     * Операции производимые над местом
     */
    @JsonIgnore
    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<Operation> operations;

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

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
