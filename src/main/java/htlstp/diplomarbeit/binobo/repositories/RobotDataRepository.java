package htlstp.diplomarbeit.binobo.repositories;

import htlstp.diplomarbeit.binobo.model.robo.RobotData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RobotDataRepository extends JpaRepository<RobotData, Long> {
    RobotData findTopByOrderByIdAsc();
    List<RobotData> findAll();
}
