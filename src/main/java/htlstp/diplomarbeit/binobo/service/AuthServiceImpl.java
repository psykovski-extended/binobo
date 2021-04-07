package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.dao.UserDAO;
import htlstp.diplomarbeit.binobo.dto.RegisterRequest;
import htlstp.diplomarbeit.binobo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserDAO userDAO;

    @Override
    public void signUp(RegisterRequest registerRequest) {
        User user = new User();

        user.setUserName(registerRequest.getUsername());

        user.setPassword(encodePassword(registerRequest.getPassword()));

        user.setEmail(registerRequest.getEmail());

        user.setFirstName(registerRequest.getFirstname());

        user.setLastName(registerRequest.getLastname());

        userDAO.save(user);
    }

    @Override
    public String encodePassword(String password) {
        return null;
    }
}
