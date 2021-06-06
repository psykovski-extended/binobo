package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Comment;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public void deleteAllByUser(User user) {
        commentRepository.deleteAllByUser(user);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
