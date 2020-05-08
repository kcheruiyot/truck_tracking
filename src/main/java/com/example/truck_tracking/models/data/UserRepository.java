package com.example.truck_tracking.models.data;

import com.example.truck_tracking.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kipngetich
 */
@Repository
public interface UserRepository  extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    User findByFirstNameAndLastName(String firstName, String lastName);
}
