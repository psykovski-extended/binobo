package htlstp.diplomarbeit.binobo.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @NotNull
    private String username;
    @Column
    @NotNull
    @Size(min = 8, max = 64)
    private String title;
    @Column(length = 65535)
    @NotNull
    @Size(min = 20, max = 65535)
    @Lob
    private String content;
    @Column(length = 65535)
    @NotNull
    @Size(min = 20, max = 65535)
    @Lob
    private String renderedContent;
    @Column
    @NotNull
    private LocalDateTime createdOn = LocalDateTime.now();
    @Column
    private LocalDateTime updatedOn;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "post", cascade = {CascadeType.ALL})
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = {CascadeType.ALL})
    private List<Vote> votes;
    @Column
    private Long marks = 0L;

    public Post(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getRenderedContent() {
        return renderedContent;
    }

    public void setRenderedContent(String renderedContent) {
        this.renderedContent = renderedContent;
    }

    public Long getMarks() {
        return marks;
    }

    public void setMarks(Long marks) {
        this.marks = marks;
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
}
