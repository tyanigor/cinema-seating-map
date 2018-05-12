package net.rest.cinemaseatingmap.service;

import net.rest.cinemaseatingmap.model.Seat;

import java.util.List;

public interface SeatService {

    Seat findSeatById(int id);

    void saveSeat(Seat seat);

    void updateSeat(Seat seat);

    void deleteSeat(Seat seat);

    List<Seat> listSeats();

    List<Seat> findSeatsByRowId(int id);

    void saveSeatByRowId(Seat seat, int id);

    void saveSeatsByRowId(List<Seat> seats, int id);
}
