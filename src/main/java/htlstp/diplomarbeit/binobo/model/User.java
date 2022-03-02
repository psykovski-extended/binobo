package htlstp.diplomarbeit.binobo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    @NotEmpty
    @NotNull
    private String username;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column(unique = true, nullable = false)
    @NotEmpty
    @NotNull
    private String email;
    @Column(nullable = false)
    @NotEmpty
    @NotNull
    private String password;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<SubComment> subComments;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Vote> votes;
    @Column(nullable = false)
    private boolean activated = false;
    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToOne
    @JoinColumn(name = "dataAccessToken_id")
    private DataAccessToken dataAccessToken;
    @OneToOne
    @JoinColumn(name = "api_key_id")
    private API_Key api_key;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Bookmark> bookmarks;

    public User(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        return grantedAuthorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activated;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public List<SubComment> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<SubComment> subComments) {
        this.subComments = subComments;
    }

    public DataAccessToken getDataAccessToken() {
        return dataAccessToken;
    }

    public void setDataAccessToken(DataAccessToken dataAccessToken) {
        this.dataAccessToken = dataAccessToken;
    }

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
    }

    public API_Key getApi_key() {
        return api_key;
    }

    public void setApi_key(API_Key api_key) {
        this.api_key = api_key;
    }
}
