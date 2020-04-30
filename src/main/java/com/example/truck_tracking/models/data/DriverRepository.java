package com.example.truck_tracking.models.data;

import com.example.truck_tracking.models.Driver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kipngetich
 */
@Repository
public interface DriverRepository extends CrudRepository<Driver,Integer> {
    Driver findByUsername(String username);
}
