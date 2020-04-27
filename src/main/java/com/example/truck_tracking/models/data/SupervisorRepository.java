package com.example.truck_tracking.models.data;

import com.example.truck_tracking.models.Supervisor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kipngetich
 */
@Repository
public interface SupervisorRepository extends CrudRepository<Supervisor,Integer> {
}
