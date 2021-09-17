package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Comment;
import htlstp.diplomarbeit.binobo.model.SubComment;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.repositories.SubCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubCommentServiceImpl implements SubCommentService{

    private final SubCommentRepository subCommentRepository;

    @Autowired
    public SubCommentServiceImpl(SubCommentRepository subCommentRepository){
        this.subCommentRepository = subCommentRepository;
    }

    @Override
    public List<SubComment> findAllByComment(Comment comment) {
        return subCommentRepository.findAllByComment(comment);
    }

    @Override
    public List<SubComment> findAllByUser(User user) {
        return subCommentRepository.findAllByUser(user);
    }

    @Override
    public void saveComment(SubComment comment) {
        subCommentRepository.save(comment);
    }

    @Override
    public void deleteComment(SubComment comment) {
        subCommentRepository.delete(comment);
    }

    @Override
    public void deleteAllByComment(Comment comment) {
        subCommentRepository.deleteAllByComment(comment);
    }
}
