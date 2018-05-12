package net.rest.cinemaseatingmap.dao;

import net.rest.cinemaseatingmap.model.Operation;

import java.util.List;

public interface OperationDAO {

    Operation findOperationById(int id);

    void persistOperation(Operation operation);

    void deleteOperation(Operation operation);

    List<Operation> listOperations();

    List<Operation> findOperationsBySessionId(int id);

    Operation findLastOperationBySessionIdAndSeatId(int sessionId, int seatId);
}
