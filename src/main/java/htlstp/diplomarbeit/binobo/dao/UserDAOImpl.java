package htlstp.diplomarbeit.binobo.dao;

import htlstp.diplomarbeit.binobo.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

// FIXME in progress!

@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(User user) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin transaction
        session.beginTransaction();

        // Save the post
        session.save(user);

        // commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    @Override
    public void delete(User user) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin transaction
        session.beginTransaction();

        // Save the post
        session.delete(user);

        // commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    /**
     *
     * @param user
     * @return
     */
    // TODO check if this is working!!
    @Override
    public boolean doesUserExist(User user) {
        // Open a session
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        criteria.from(User.class);
        List<User> users = session.createQuery(criteria).getResultList();

        boolean userExists = false;

        for(User u : users){
//            if(u.getEmail().equals(user.getEmail()) && u.getUserName())
        }

        return false;
    }

    @Override
    public User findById(Long id) {
        Session session = sessionFactory.openSession();

        User user = session.get(User.class, id);

        session.close();

        return user;
    }
}
