package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.robo.RobotData;
import htlstp.diplomarbeit.binobo.repositories.RobotDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class RobotDataServiceImpl implements RobotDataService {

    RobotDataRepository roboRepository;

    @Autowired
    public RobotDataServiceImpl(RobotDataRepository robotDataRepository){
        this.roboRepository = robotDataRepository;
    }

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

    @Override
    @Async
    public CompletableFuture<RobotData> checkIfExpired() {
        RobotData earliest = findTopByOrderByIdAsc();
        LocalDateTime createdOn = earliest.getUploadedOn();
        LocalDateTime expiresOn = LocalDateTime.of(createdOn.getYear(), createdOn.getMonth(), createdOn.getDayOfMonth(), createdOn.getHour(), createdOn.getMinute() + 5, createdOn.getSecond());

        LocalDateTime tempDateTime = LocalDateTime.from( createdOn );

        long years = tempDateTime.until( expiresOn, ChronoUnit.YEARS );
        tempDateTime = tempDateTime.plusYears( years );

        long months = tempDateTime.until( expiresOn, ChronoUnit.MONTHS );
        tempDateTime = tempDateTime.plusMonths( months );

        long days = tempDateTime.until( expiresOn, ChronoUnit.DAYS );
        tempDateTime = tempDateTime.plusDays( days );

        long hours = tempDateTime.until( expiresOn, ChronoUnit.HOURS );
        tempDateTime = tempDateTime.plusHours( hours );

        long minutes = tempDateTime.until( expiresOn, ChronoUnit.MINUTES );
        tempDateTime = tempDateTime.plusMinutes( minutes );

        long seconds = tempDateTime.until( expiresOn, ChronoUnit.SECONDS );
        tempDateTime = tempDateTime.plusSeconds(seconds);

        earliest.setExpired(tempDateTime.getMinute() >= 5 || tempDateTime.getHour() >= 1);
        save(earliest);

        return CompletableFuture.completedFuture(earliest);
    }
}
