package com.example.truck_tracking.models.data;

import com.example.truck_tracking.models.Driver;
import com.example.truck_tracking.models.Shipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * Created by Kipngetich
 */
@Repository
public interface ShipmentRepository extends CrudRepository<Shipment,Integer> {
    Shipment findByDateAndDriver(String date, Driver driver);
}
