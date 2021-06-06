package htlstp.diplomarbeit.binobo.repositories;

import htlstp.diplomarbeit.binobo.model.ConfirmationToken;
import htlstp.diplomarbeit.binobo.service.UserServiceImpl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<ConfirmationToken, Long> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
