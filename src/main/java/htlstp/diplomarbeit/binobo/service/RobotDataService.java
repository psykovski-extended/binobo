package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.robo.RobotData;

import java.util.List;

public interface RobotDataService {
    RobotData findTopByOrderByIdAsc();
    void delete(RobotData robotData);
    RobotData save(RobotData robotData);
    List<RobotData> findAll();
}
