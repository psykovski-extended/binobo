package htlstp.diplomarbeit.binobo.repositories;

import htlstp.diplomarbeit.binobo.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    List<Role> findAll();
}
