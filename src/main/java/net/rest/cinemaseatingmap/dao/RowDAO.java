package net.rest.cinemaseatingmap.dao;

import net.rest.cinemaseatingmap.model.Row;

import java.util.List;

public interface RowDAO {

    Row findRowById(int id);

    void persistRow(Row row);

    void deleteRow(Row row);

    List<Row> listRows();

    List<Row> findRowsByHallId(int id);

    List<Row> findRowsBySessionId(int id);
}
