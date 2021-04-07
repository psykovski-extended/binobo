package htlstp.diplomarbeit.binobo.dao;

import htlstp.diplomarbeit.binobo.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
