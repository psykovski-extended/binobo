package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.RobotData;
import htlstp.diplomarbeit.binobo.repositories.RobotDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RobotDataServiceImpl implements RobotDataService {

    @Autowired
    RobotDataRepository roboRepository;

    @Override
    public RobotData findTopByOrderByIdDesc() {
        return roboRepository.findTopByOrderByIdDesc();
    }
}
