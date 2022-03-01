package htlstp.diplomarbeit.binobo.repositories;

import htlstp.diplomarbeit.binobo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    void deleteByUsername(String username);
}
