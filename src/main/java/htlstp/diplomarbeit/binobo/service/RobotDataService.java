package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.DataAccessToken;
import htlstp.diplomarbeit.binobo.model.robo.RobotData;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RobotDataService {
    RobotData findTopByOrderByIdAsc();
    void delete(RobotData robotData);
    RobotData save(RobotData robotData);
    List<RobotData> findAll();
    List<RobotData> findAllByDataAccessToken(String token);
    CompletableFuture<RobotData> checkIfExpired();
    DataAccessToken findDATByToken(String token);
    RobotData findTopByDataAccessToken(String token);
    DataAccessToken saveDataAccessToken(DataAccessToken dat);
    void deleteAllByDataAccessToken(String token);
    void deleteAllMatching(List<RobotData> robotData);
    void saveAll(Iterable<RobotData> robotData);
    HashMap<String, List<RobotData>> getAllSortedByToken();
}
