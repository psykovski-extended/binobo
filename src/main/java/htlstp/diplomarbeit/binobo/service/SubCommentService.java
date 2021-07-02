package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Comment;
import htlstp.diplomarbeit.binobo.model.SubComment;
import htlstp.diplomarbeit.binobo.model.User;

import java.util.List;

public interface SubCommentService {
    List<SubComment> findAllByComment(Comment comment);
    List<SubComment> findAllByUser(User user);
    void saveComment(SubComment comment);
    void deleteComment(SubComment comment);
    void deleteAllByComment(Comment comment);


}
