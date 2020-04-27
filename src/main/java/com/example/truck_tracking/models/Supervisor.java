package com.example.truck_tracking.models;

import javax.persistence.Entity;

/**
 * Created by Kipngetich
 */
@Entity
public class Supervisor extends AbstractPersonnel{
    public Supervisor(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }
}
