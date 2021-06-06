package htlstp.diplomarbeit.binobo.dto;

import htlstp.diplomarbeit.binobo.service.validation.FieldMatch;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "password_verify", message = "The Passwords must Match!")
public class RegisterRequest {

    @NotNull(message = "May not be null!")
    @NotEmpty(message = "May not be null!")
    private String username;
    @NotNull(message = "May not be null!")
    @NotEmpty(message = "May not be null!")
    private String password;
    @NotNull(message = "May not be null!")
    @NotEmpty(message = "May not be null!")
    private String password_verify;
    @NotNull(message = "May not be null!")
    @NotEmpty(message = "May not be null!")
    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "Email pattern must be valid! Please try again with correct Email!")
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
