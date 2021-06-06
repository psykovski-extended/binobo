package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findById(Long id);
}
