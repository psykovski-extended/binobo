package htlstp.diplomarbeit.binobo.dao;

import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class PostDAOImpl implements PostDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Post> findAll() {
        // Opan a session
        Session session = sessionFactory.openSession();

        // Create Criteria
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Post> criteria = builder.createQuery(Post.class);

        // Specify criteria root
        criteria.from(Post.class);

        // Execute query
        List<Post> posts = session.createQuery(criteria).getResultList();

        // close the session
        session.close();

        return posts;
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
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin transaction
        session.beginTransaction();

        // Save the post
        session.saveOrUpdate(post);

        // commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    @Override
    public void deletePost(Post post) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin transaction
        session.beginTransaction();

        // Delete the post
        session.delete(post);

        // commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    @Override
    public Post findById(Long postId) {
        Session session = sessionFactory.openSession();
        Post post = session.get(Post.class,postId);
        session.close();
        return post;
    }
}
