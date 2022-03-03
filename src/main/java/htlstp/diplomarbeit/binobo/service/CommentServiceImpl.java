package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Comment;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.model.Vote;
import htlstp.diplomarbeit.binobo.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> findAllByPost(Post post) {
        return commentRepository.findAllByPost(post);
    }

    @Override
    public List<Comment> findAllByUser(User user) {
        return commentRepository.findAllByUser(user);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteAllByUser(User user) {
        commentRepository.deleteAllByUser(user);
    }

    @Override
    public void deleteAllByPost(Post post) {
        commentRepository.deleteAllByPost(post);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void incrementMarks(Comment comment) throws NullPointerException {
        if(comment == null)throw new NullPointerException("Post may not be null");
        comment.setMarks(comment.getMarks() + 1);
        commentRepository.save(comment);
    }

    @Override
    public void decrementMarks(Comment comment) throws NullPointerException {
        if(comment == null)throw new NullPointerException("Post may not be null");
        comment.setMarks(comment.getMarks() - 1);
        commentRepository.save(comment);
    }

    @Override
    public void removeFromVotes(Comment comment, Vote vote) {
        comment.getVotes().remove(vote);
        saveComment(comment);
    }
}
