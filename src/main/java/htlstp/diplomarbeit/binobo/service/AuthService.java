package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.dto.RegisterRequest;

public interface AuthService {
    void signUp(RegisterRequest registerRequest);
    String encodePassword(String password);
}
