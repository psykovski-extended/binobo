package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Comment;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.model.Vote;

import java.util.List;

public interface CommentService {
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByUser(User user);
    Comment findById(Long id);
    void deleteAllByUser(User user);
    void deleteAllByPost(Post post);
    void delete(Comment comment);
    void saveComment(Comment comment);
    void deleteComment(Comment comment);
    void incrementMarks(Comment comment);
    void decrementMarks(Comment comment);

    void removeFromVotes(Comment comment, Vote vote);
}
