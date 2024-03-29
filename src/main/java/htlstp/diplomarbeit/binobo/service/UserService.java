package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.dto.RegisterRequest;
import htlstp.diplomarbeit.binobo.model.*;
import htlstp.diplomarbeit.binobo.service.validation.UserAlreadyExistException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    User findByEmail(String email);
    void register(RegisterRequest regReq) throws UserAlreadyExistException;
    List<User> findAll();
    User findById(Long user_id);
    void delete(User userToDelete);
    void setRole(User user, Role role);
    User getUser(String verificationToken);
    ConfirmationToken getVerificationToken(String confirmationToken);
    void createVerificationToken(User user, String token);
    void deleteToken(ConfirmationToken token);
    void save(User user);
    void deleteByUsername(String username);
    void saveAPIKey(API_Key key);
    void deleteAPIKey(API_Key key);
    void updateAPIKeyForUser(User user);
    API_Key findAPIKeyByToken(String token);
    void generateNewTokenForUser(User user);

    void removeFromVotes(User user, Vote vote);
}
