package com.mycompany.service;

import com.mycompany.entities.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Brian
 */
@Local
public interface UserServiceLocal
{
    List<User> getUsers();

    void createUser(final User parameter);
}
