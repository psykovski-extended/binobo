package htlstp.diplomarbeit.binobo.dto;

import htlstp.diplomarbeit.binobo.service.validation.FieldMatch;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@FieldMatch(first = "password", second = "password_verify", message = "The passwords must match!")
public class RegisterRequest {

    @NotNull(message = "A username is required!")
    @NotEmpty(message = "A username is required!")
    private String username;
    @NotNull(message = "No password is not a good idea!")
    @NotEmpty(message = "No password is not a good idea!")
    private String password;
    @NotNull(message = "You need to validate your password!")
    @NotEmpty(message = "You need to validate your password!")
    private String password_verify;
    @NotNull(message = "A valid email is required, but dont worry! We wont send any spam-emails!")
    @NotEmpty(message = "A valid email is required, but dont worry! We wont send any spam-emails!")
    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "Email must be valid! Please try again with an correct email address!")
    private String email;
    private String firstname;
    private String lastname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword_verify() {
        return password_verify;
    }

    public void setPassword_verify(String password_verify) {
        this.password_verify = password_verify;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
