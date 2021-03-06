package com.example.truck_tracking.models.data;

import com.example.truck_tracking.models.Shipment;
import com.example.truck_tracking.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kipngetich
 */
@Repository
public interface ShipmentRepository extends CrudRepository<Shipment,Integer> {
    Shipment findByDateAndUser(String date, User user);
}
