package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;

import java.util.List;

public interface PostService {
    List<Post> findAll();
    List<Post> findByUser(User user);
    Post findByTitle(String title);
    void savePost(Post post);
    void deletePost(Post post);
}
