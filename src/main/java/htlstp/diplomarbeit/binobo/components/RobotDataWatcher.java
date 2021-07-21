package htlstp.diplomarbeit.binobo.components;

import htlstp.diplomarbeit.binobo.model.robo.RobotData;
import htlstp.diplomarbeit.binobo.repositories.RobotDataRepository;
import htlstp.diplomarbeit.binobo.service.RobotDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class RobotDataWatcher implements CommandLineRunner {

    private final RobotDataRepository robotDataRepository;
    private final RobotDataService robotDataService;
    private static final Logger logger = LoggerFactory.getLogger(RobotDataWatcher.class);

    @Autowired
    public RobotDataWatcher(RobotDataRepository robotDataRepository, RobotDataService robotDataService) {
        this.robotDataRepository = robotDataRepository;
        this.robotDataService = robotDataService;
    }

    @Override
    public void run(String... args) throws Exception {
        while(true) {
            CompletableFuture<RobotData> robotData = robotDataService.checkIfExpired();

            CompletableFuture.allOf(robotData).join();

            if(robotData.get().isExpired()){
                robotDataRepository.delete(robotData.get());
                logger.info("Data expired --> Therefore it got deleted!");
            }

            Thread.sleep(1000L);
        }
    }
}
