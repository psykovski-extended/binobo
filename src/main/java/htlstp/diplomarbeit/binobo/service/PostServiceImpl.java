package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findByUser(User user) {
        return postRepository.findAllByUser(user);
    }

    @Override
    public Post findByTitle(String title) {
        Iterable<Post> posts = postRepository.findAll();
        for (Post p: posts) if(p.getTitle().equals(title))return p;
        return null;
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId).orElse(new Post());
    }

    @Override
    public void deleteAllByUser(User user) {
        postRepository.deleteAllByUser(user);
    }
}
