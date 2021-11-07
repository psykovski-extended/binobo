package htlstp.diplomarbeit.binobo.repositories;

import htlstp.diplomarbeit.binobo.model.DataAccessToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DATRepository extends CrudRepository<DataAccessToken, Long> {
    Optional<DataAccessToken> findByToken(String token);
    List<DataAccessToken> findAll();
}
