package net.rest.cinemaseatingmap.dao;

import net.rest.cinemaseatingmap.model.Hall;

import java.util.List;

public interface HallDAO {

    Hall findHallById(int id);

    void persistHall(Hall hall);

    void deleteHall(Hall hall);

    List<Hall> listHalls();
}
