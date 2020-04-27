package com.example.truck_tracking.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * Created by Kipngetich
 */
@Entity
public class Token {
    @Id
    @GeneratedValue
    private int id;

   @Size(min = 3,max = 30,message = "First name should be 3-30 characters")
    private String firstName;
    @Size(min = 3,max = 30,message = "Last name should be 3-30 characters")
    private String lastName;

    @Size(min = 3,max = 10,message = "Token should be 3-10 characters")
    private String token;

    public Token(@Size(min = 3, max = 30, message = "First name should be 3-30 characters") String firstName, @Size(min = 3, max = 30, message = "Last name should be 3-30 characters") String lastName, @Size(min = 3, max = 10, message = "Token should be 3-10 characters") String token) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
