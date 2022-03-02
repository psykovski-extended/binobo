package htlstp.diplomarbeit.binobo.repositories;

import htlstp.diplomarbeit.binobo.model.API_Key;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface API_KEY_Repository extends CrudRepository<API_Key, Long> {
    API_Key findByToken(String token);
}
