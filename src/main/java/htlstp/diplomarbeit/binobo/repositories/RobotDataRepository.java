package htlstp.diplomarbeit.binobo.repositories;

import htlstp.diplomarbeit.binobo.model.RobotData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RobotDataRepository extends JpaRepository<RobotData, Long> {
    RobotData findTopByOrderByIdDesc();
}
