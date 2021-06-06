package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Comment;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;

import java.util.List;

public interface CommentService {
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByUser(User user);
    void deleteAllByUser(User user);
    void delete(Comment comment);
}
