package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.dao.PostDAO;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostDAO postDAO;

    @Override
    public List<Post> findAll() {
        return postDAO.findAll();
    }

    @Override
    public List<Post> findByUser(User user) {
        return null;
    }

    @Override
    public Post findByTitle(String title) {
        return null;
    }

    @Override
    public void savePost(Post post) {
        postDAO.savePost(post);
    }

    @Override
    public void deletePost(Post post) {
        postDAO.deletePost(post);
    }
}
