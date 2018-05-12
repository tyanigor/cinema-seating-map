package net.rest.cinemaseatingmap.dao;

import net.rest.cinemaseatingmap.model.Seat;

import java.util.List;

public interface SeatDAO {

    Seat findSeatById(int id);

    void persistSeat(Seat seat);

    void deleteSeat(Seat seat);

    List<Seat> listSeats();

    List<Seat> findSeatsByRowId(int id);
}
