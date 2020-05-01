package com.example.truck_tracking.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Kipngetich
 */
@Entity
public class Supervisor extends AbstractEntity {

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    @NotNull
    @Size(min = 3,max = 20,message = "Name must be 3-20 characters")
    private String firstName;

    @Size(min = 3,max = 20,message = "Name must be 3-20 characters")
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String token;


    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public Supervisor() {}

    public Supervisor(@NotNull String username, @NotNull String password, @NotNull @Size(min = 3, max = 20, message = "Name must be 3-20 characters") String firstName, @Size(min = 3, max = 20, message = "Name must be 3-20 characters") String lastName, @NotNull @Email String email, @NotNull String token) {
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }
}
