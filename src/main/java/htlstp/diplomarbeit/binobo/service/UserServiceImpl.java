package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.dto.RegisterRequest;
import htlstp.diplomarbeit.binobo.model.ConfirmationToken;
import htlstp.diplomarbeit.binobo.model.Role;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.repositories.RoleRepository;
import htlstp.diplomarbeit.binobo.repositories.TokenRepository;
import htlstp.diplomarbeit.binobo.repositories.UserRepository;
import htlstp.diplomarbeit.binobo.service.validation.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

}
