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

/**
 * This class runs asynchronous to the main thread in an endless loop, where it checks every minute, if saved RobotData
 * is older than 5 minutes, if yes, they get deleted, so that the database will not become too big.
 */
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

    /**
     * Endlessly retrieves oldest data in database and checks the timestamp
     * @param args optional arguments - not needed in this case
     * @throws Exception throws an Exception if stuck or interrupted
     */
    @Override
    public void run(String...args) throws Exception {
        while(true) {
            try {
                CompletableFuture<RobotData> robotData = robotDataService.checkIfExpired();

                CompletableFuture.allOf(robotData).join();

                if(robotData.get().isExpired()){
                    robotDataRepository.delete(robotData.get());
                    logger.info("Data expired --> Therefore it got deleted!");
                }

                Thread.sleep(1000L);
            } catch (Exception e){
                logger.warn("Error occurred: " + e.getMessage());
            }
        }
    }
}
