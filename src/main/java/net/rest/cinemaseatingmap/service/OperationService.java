package net.rest.cinemaseatingmap.service;

import net.rest.cinemaseatingmap.model.Operation;

import java.util.List;

public interface OperationService {

    Operation findOperationById(int id);

    void saveOperation(Operation operation);

    void updateOperation(Operation operation);

    void deleteOperation(Operation operation);

    List<Operation> listOperations();

    List<Operation> findOperationsBySessionId(int id);

    void saveOperationBySessionIdAndSeatId(Operation operation, int sessionId, int seatId);

    void saveOperationsBySessionId(List<Operation> operation, int id);

    Operation findLastOperationBySessionIdAndSeatId(int sessionId, int seatId);
}
