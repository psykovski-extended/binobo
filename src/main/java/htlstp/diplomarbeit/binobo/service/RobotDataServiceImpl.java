package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.robo.RobotData;
import htlstp.diplomarbeit.binobo.repositories.RobotDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class RobotDataServiceImpl implements RobotDataService {

    @Autowired
    RobotDataRepository roboRepository;

    @Override
    public RobotData findTopByOrderByIdAsc() {
        return roboRepository.findTopByOrderByIdAsc();
    }

    @Override
    public void delete(RobotData robotData) {
        roboRepository.delete(robotData);
    }

    @Override
    public RobotData save(RobotData robotData) {
        return roboRepository.save(robotData);
    }

    @Override
    public List<RobotData> findAll() {
        return roboRepository.findAll();
    }
}
