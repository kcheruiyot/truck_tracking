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
public class Driver extends AbstractEntity {
    @NotNull
    private String username;

    @OneToMany
    @JoinColumn
    List<Shipment> shipments = new ArrayList<>();
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
    public Driver() {}

    public Driver(@NotNull String username, @NotNull String password, @NotNull @Size(min = 3, max = 20, message = "Name must be 3-20 characters") String firstName, @Size(min = 3, max = 20, message = "Name must be 3-20 characters") String lastName, @NotNull @Email String email, @NotNull String token) {
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
    @Override
    public String toString() {
        return firstName +" "+lastName;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Driver driver = (Driver) o;
        return this.getId() == driver.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUsername());
    }

    public void addShipment(Shipment shipment){
        shipments.add(shipment);
}
    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }
}
