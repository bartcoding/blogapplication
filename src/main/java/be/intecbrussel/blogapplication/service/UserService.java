package be.intecbrussel.blogapplication.service;

import be.intecbrussel.blogapplication.model.User;
import org.springframework.stereotype.Service;


public interface UserService extends CrudService<User, Long> {
    User findById(Long id);
    User save(User user);
}
