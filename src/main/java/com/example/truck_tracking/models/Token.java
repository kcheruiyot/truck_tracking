package com.example.truck_tracking.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Kipngetich
 */
@Entity
public class Token {
    @Id
    @GeneratedValue
    private int id;

   @NotNull
   @Size(min = 3,max = 30,message = "First name should be 3-30 characters")
    private String firstName;

    @NotNull
    @Size(min = 3,max = 30,message = "Last name should be 3-30 characters")
    private String lastName;

    @NotNull
    @Size(min = 3,max = 10,message = "Token should be 3-10 characters")
    private String token;

    private boolean isSupervisor;

    public Token() {
    }

    public Token(@Size(min = 3, max = 30, message = "First name should be 3-30 characters") String firstName, @Size(min = 3, max = 30, message = "Last name should be 3-30 characters") String lastName, @Size(min = 3, max = 10, message = "Token should be 3-10 characters") String token, boolean isSupervisor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        this.isSupervisor = isSupervisor;
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

    public boolean isSupervisor() {
        return isSupervisor;
    }

    public void setSupervisor(boolean supervisor) {
        isSupervisor = supervisor;
    }
}
