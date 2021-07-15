package htlstp.diplomarbeit.binobo.components;

import htlstp.diplomarbeit.binobo.repositories.RobotDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RobotDataWatcher implements CommandLineRunner {

    private RobotDataRepository robotDataRepository;

    @Autowired
    public RobotDataWatcher(RobotDataRepository robotDataRepository) {
        this.robotDataRepository = robotDataRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // while true
        // ....retrieve latest robotData entry, check date, if older than 5 mins --> delete
    }
}
