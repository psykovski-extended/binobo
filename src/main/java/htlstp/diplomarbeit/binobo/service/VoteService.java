package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Comment;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.model.Vote;

import java.util.List;

public interface VoteService {

    List<Vote> findAllByUser(User user);
    List<Vote> findAllByPost(Post post);
    List<Vote> findAllByComment(Comment comment);
    void deleteAllByPost(Post post);
    void deleteAllByUser(User user);
    void deleteAllByComment(Comment comment);
    int countAllByPost(Post post);
    int countAllByUser(User user);
    int countAllByComment(Comment comment);
    int getVoteCountByPost(Post post);
    int getVoteCountByComment(Comment comment);
    List<Integer> getAllVoteCountByPosts(List<Post> posts);
    List<Integer> getAllVoteCountByComments(List<Comment> comments);
    Vote findByUserAndPost(User user, Post post);
    Vote findByUserAndComment(User user, Comment comment);

    void save(Vote vote);
    void delete(Vote vote);

    void saveAll(List<Vote> votes);
}
