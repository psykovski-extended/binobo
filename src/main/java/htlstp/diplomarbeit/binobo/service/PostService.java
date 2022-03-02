package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.model.Vote;

import java.util.List;

public interface PostService {
    List<Post> findAll();
    List<Post> findByUser(User user);
    Post findByTitle(String title);
    void savePost(Post post);
    void deletePost(Post post);
    Post findById(Long postId);
    void deleteAllByUser(User user);
    void incrementMarks(Post post);
    void decrementMarks(Post post);

    void removeFromVotes(Post post, Vote vote);
}
