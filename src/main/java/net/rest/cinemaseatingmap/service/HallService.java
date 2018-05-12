package net.rest.cinemaseatingmap.service;

import net.rest.cinemaseatingmap.model.Hall;

import java.util.List;

public interface HallService {

    Hall findHallById(int id);

    void saveHall(Hall hall);

    void updateHall(Hall hall);

    void deleteHall(Hall hall);

    List<Hall> listHalls();
}
