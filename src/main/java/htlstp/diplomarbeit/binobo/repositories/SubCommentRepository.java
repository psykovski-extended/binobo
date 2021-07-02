package htlstp.diplomarbeit.binobo.repositories;

import htlstp.diplomarbeit.binobo.model.Comment;
import htlstp.diplomarbeit.binobo.model.SubComment;
import htlstp.diplomarbeit.binobo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCommentRepository extends CrudRepository<SubComment, Long> {
    List<SubComment> findAllByComment(Comment comment);
    List<SubComment> findAllByUser(User user);
    void deleteAllByComment(Comment comment);
}
