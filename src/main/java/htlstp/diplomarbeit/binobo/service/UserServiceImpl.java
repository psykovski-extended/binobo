package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.dto.RegisterRequest;
import htlstp.diplomarbeit.binobo.model.*;
import htlstp.diplomarbeit.binobo.repositories.API_KEY_Repository;
import htlstp.diplomarbeit.binobo.repositories.RoleRepository;
import htlstp.diplomarbeit.binobo.repositories.TokenRepository;
import htlstp.diplomarbeit.binobo.repositories.UserRepository;
import htlstp.diplomarbeit.binobo.service.validation.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private API_KEY_Repository api_key_repository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null)throw new UsernameNotFoundException("User not found: " + username);
        return user;
    }

    @Override
    public void register(RegisterRequest regReq) throws UserAlreadyExistException {
        User ae_un = findByUsername(regReq.getUsername());
        User ae_email = findByEmail(regReq.getEmail());

        if(ae_email != null || ae_un != null)throw new UserAlreadyExistException("This username and/or email is already used by another user!");

        User user = new User();
        user.setUsername(regReq.getUsername());
        user.setEmail(regReq.getEmail());
        user.setFirstName(regReq.getFirstname());
        user.setLastName(regReq.getLastname());

        Role role = roleRepository.findById(1L).orElse(null);
        user.setRole(role);

        user.setPassword(passwordEncoder.encode(regReq.getPassword()));

        userRepository.save(user);
    }

    @Override
    public List<User> findAll(){
        return (List<User>)userRepository.findAll();
    }

    @Override
    public User findById(Long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    @Override
    public void delete(User userToDelete) {
        userRepository.delete(userToDelete);
    }

    @Override
    public void setRole(User user, Role role) {
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public User getUser(String verificationToken) {
        return tokenRepository.findByConfirmationToken(verificationToken).getUser();
    }

    @Override
    public ConfirmationToken getVerificationToken(String confirmationToken) {
        return tokenRepository.findByConfirmationToken(confirmationToken);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        ConfirmationToken myToken = new ConfirmationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public void deleteToken(ConfirmationToken token) {
        tokenRepository.delete(token);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void saveAPIKey(API_Key key) {
        api_key_repository.save(key);
    }

    @Override
    public void deleteAPIKey(API_Key key) {
        api_key_repository.delete(key);
    }

    @Override
    public void updateAPIKeyForUser(User user) {
        API_Key key = new API_Key();
        user.setApi_key(key);
        userRepository.save(user);
        api_key_repository.save(key);
    }

    @Override
    public API_Key findAPIKeyByToken(String token) {
        return api_key_repository.findByToken(token);
    }

    @Override
    public void generateNewTokenForUser(User user) {
        API_Key key = new API_Key();
        this.saveAPIKey(key);
        user.setApi_key(key);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeFromVotes(User user, Vote vote) {
        user.getVotes().remove(vote);
        save(user);
    }

}
