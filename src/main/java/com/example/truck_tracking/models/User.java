package com.example.truck_tracking.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Kipngetich
 */
@Entity
public class User extends AbstractEntity{
    @NotNull
    private String username;

    @OneToMany
    @JoinColumn
    List<Shipment> shipments = new ArrayList<>();
    private String pwHash;

    private String firstName;

    private String lastName;

    private String email;

    private boolean isSupervisor;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {}

    public User(@NotNull String username, @NotNull String password, @NotNull @Size(min = 3, max = 20, message = "Name must be 3-20 characters") String firstName, @Size(min = 3, max = 20, message = "Name must be 3-20 characters") String lastName, @NotNull @Email String email,boolean isSupervisor) {
        this.username = username;
        this.pwHash = encoder.encode(password);;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isSupervisor = isSupervisor;
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

    public boolean isSupervisor() {
        return isSupervisor;
    }

    public String getEmail() {
        return email;
    }


    public List<Shipment> getShipments() {
        return shipments;
    }
    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }
    @Override
    public String toString() {
        return firstName +" "+lastName;
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUsername());
    }

    public void addShipment(Shipment shipment){
        shipments.add(shipment);
    }

}
