package htlstp.diplomarbeit.binobo.repositories;

import htlstp.diplomarbeit.binobo.model.Comment;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.model.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {
    List<Vote> findAllByUser(User user);
    List<Vote> findAllByPost(Post post);
    List<Vote> findAllByComment(Comment comment);
    Vote findByUserAndPost(User user, Post post);
    Vote findByUserAndComment(User user, Comment comment);
    void deleteAllByPost(Post post);
    void deleteAllByUser(User user);
    void deleteAllByComment(Comment comment);
    int countAllByPost(Post post);
    int countAllByUser(User user);
    int countAllByComment(Comment comment);
}
