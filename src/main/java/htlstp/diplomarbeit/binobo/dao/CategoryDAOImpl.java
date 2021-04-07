package htlstp.diplomarbeit.binobo.dao;

import htlstp.diplomarbeit.binobo.model.Category;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Category> findAll() {
        Session session = sessionFactory.openSession();

        // Create Criteria
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Category> criteria = builder.createQuery(Category.class);

        // Specify criteria root
        criteria.from(Category.class);

        // Execute query
        List<Category> categories = session.createQuery(criteria).getResultList();

        // close the session
        session.close();

        return categories;
    }

    @Override
    public Category findById(Long id) {
        // Open a session
        Session session = sessionFactory.openSession();

        // retrieve category by id
        Category category = session.get(Category.class,id);

        Hibernate.initialize(category.getPosts());

        // Close the session
        session.close();

        return category;
    }

    @Override
    public void save(Category category) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin transaction
        session.beginTransaction();

        // Save the post
        session.save(category);

        // commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    @Override
    public void delete(Category category) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin transaction
        session.beginTransaction();

        // Save the post
        session.delete(category);

        // commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }
}
