package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.DataAccessToken;
import htlstp.diplomarbeit.binobo.model.robo.RobotData;
import htlstp.diplomarbeit.binobo.repositories.DATRepository;
import htlstp.diplomarbeit.binobo.repositories.RobotDataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class RobotDataServiceImpl implements RobotDataService {

    private final RobotDataRepository roboRepository;
    private final DATRepository datRepository;

    @Autowired
    public RobotDataServiceImpl(RobotDataRepository robotDataRepository, DATRepository datRepository){
        this.roboRepository = robotDataRepository;
        this.datRepository = datRepository;
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
    public List<RobotData> findAllByDataAccessToken(String token) throws NullPointerException{
        DataAccessToken dat = findDATByToken(token);
        List<RobotData> robotDataList = roboRepository.findAllByDataAccessToken(dat);

        if(robotDataList == null) throw new NullPointerException("Database is empty, please make sure you are connected!");

        return robotDataList;
    }

    @Override
    @Async
    public CompletableFuture<RobotData> checkIfExpired() {
        RobotData earliest = findTopByOrderByIdAsc();
        try {
            double uploadedOn = earliest.getUploadedOn() / 1000.0;
            earliest.setExpired(System.currentTimeMillis() / 1000.0 - uploadedOn >= 300);
            save(earliest);

            return CompletableFuture.completedFuture(earliest);
        } catch (Exception e){
            return CompletableFuture.completedFuture(new RobotData());
        }
    }

    @Override
    public RobotData findTopByDataAccessToken(String token) throws NullPointerException, DataAccessResourceFailureException{
        DataAccessToken dataAccessToken = findDATByToken(token);
        RobotData rb = roboRepository.findTopByDataAccessToken(dataAccessToken).orElse(null);
        if(rb == null) throw new DataAccessResourceFailureException("Database is empty, please make sure you are connected!");
        return rb;
    }

    @Override
    public DataAccessToken saveDataAccessToken(DataAccessToken dat) {
        return datRepository.save(dat);
    }

    @Override
    public void deleteAllByDataAccessToken(String token) throws NullPointerException{
        DataAccessToken dat = datRepository.findByToken(token).orElse(null);
        if(dat == null) throw new NullPointerException("Token is invalid! Access denied!");
        roboRepository.deleteAllByDataAccessToken(dat);
    }

    @Override
    public void deleteAllMatching(List<RobotData> robotData) {
        roboRepository.deleteAll(robotData);
    }

    @Override
    public void saveAll(Iterable<RobotData> robotData) {
        roboRepository.saveAll(robotData);
    }

    @Override
    public DataAccessToken findDATByToken(String token) throws NullPointerException {
        DataAccessToken dat = datRepository.findByToken(token).orElse(null);
        if(dat == null) throw new NullPointerException("Token is invalid! Access denied!");
        return dat;
    }

}
