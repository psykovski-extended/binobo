package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.RobotData;

public interface RobotDataService {
    RobotData findTopByOrderByIdDesc();
}
