package com.example.truck_tracking.models;

import javax.persistence.Entity;

/**
 * Created by Kipngetich
 */
@Entity
public class Supervisor extends AbstractEntity {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
