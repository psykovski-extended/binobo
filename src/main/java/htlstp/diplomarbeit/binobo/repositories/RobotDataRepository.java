package htlstp.diplomarbeit.binobo.repositories;

import htlstp.diplomarbeit.binobo.model.DataAccessToken;
import htlstp.diplomarbeit.binobo.model.robo.RobotData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RobotDataRepository extends JpaRepository<RobotData, Long> {
    RobotData findTopByOrderByIdAsc();
    List<RobotData> findAll();
    List<RobotData> findAllByDataAccessToken(DataAccessToken dataAccessToken);
    Optional<RobotData> findTopByDataAccessToken(DataAccessToken dataAccessToken);
    void deleteAllByDataAccessToken(DataAccessToken dataAccessToken);
}
