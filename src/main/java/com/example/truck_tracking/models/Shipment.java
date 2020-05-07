package com.example.truck_tracking.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Kipngetich
 */
@Entity
public class Shipment {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Driver driver;
    private String source;
    private String destination;
    private String description;
    private String recipient;
    private final String date = LocalDate.now().toString();

    public int getId() {
        return id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Shipment() {
    }

    public String getDate() {
        return date;
    }

    public Shipment(Driver driver, String source, String destination, String description, String recipient) {
        this.driver = driver;
        this.source = source;
        this.destination = destination;
        this.description = description;
        this.recipient = recipient;
    }
}
