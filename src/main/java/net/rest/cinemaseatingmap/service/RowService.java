package net.rest.cinemaseatingmap.service;

import net.rest.cinemaseatingmap.model.Row;

import java.util.List;

public interface RowService {

    Row findRowById(int id);

    void saveRow(Row row);

    void updateRow(Row row);

    void deleteRow(Row row);

    List<Row> listRows();

    List<Row> findRowsByHallId(int id);

    void saveRowByHallId(Row row, int id);

    List<Row> findRowsBySessionId(int id);
}
