package com.mycompany.service;

import com.mycompany.entities.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian
 */
@Stateless
public class UserService implements UserServiceLocal
{    
    @PersistenceContext(unitName = "com.mycompany_SemanticValidation-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Resource
    private javax.transaction.UserTransaction utx;
    
    /**
     * Returns all the {@link User} objects from the database
     * @return the list of users
     */
    @Override
    public List<User> getUsers()
    {
        Query query = em.createQuery("SELECT e FROM User e");
        return query.getResultList();
    }

    @Override
    public User getUser(final long id)
    {
        return em.find(User.class, id);
    }
    
    @Override
    public void createUser(final User user)
    {
        em.persist(user);
    }
}
