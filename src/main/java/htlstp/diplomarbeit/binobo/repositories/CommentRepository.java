package htlstp.diplomarbeit.binobo.repositories;

import htlstp.diplomarbeit.binobo.model.Comment;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByUser(User user);
    void deleteAllByUser(User user);
}
