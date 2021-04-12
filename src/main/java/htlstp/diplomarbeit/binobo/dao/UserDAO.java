package htlstp.diplomarbeit.binobo.dao;

import htlstp.diplomarbeit.binobo.model.User;

public interface UserDAO {
    void save(User user);
    void delete(User user);
    boolean doesUserExist(User user);
    User findById(Long id);
}
