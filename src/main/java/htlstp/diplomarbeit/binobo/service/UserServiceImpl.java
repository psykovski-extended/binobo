package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.dto.RegisterRequest;
import htlstp.diplomarbeit.binobo.model.Role;
import htlstp.diplomarbeit.binobo.model.User;
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

//        FIXME I dont know if this works
        Role role = new Role();
        role.setName("ROLE_USER");
        role.setId(1L);
        user.setRole(role);

        user.setPassword(passwordEncoder.encode(regReq.getPassword()));

        userRepository.save(user);
    }

    @Override
    public List<User> findAll(){
        return (List<User>)userRepository.findAll();
    }

}
