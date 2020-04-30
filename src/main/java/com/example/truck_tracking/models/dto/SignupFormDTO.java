package com.example.truck_tracking.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Kipngetich
 */
public class SignupFormDTO extends LoginFormDTO{
    private String verifyPassword;

    @NotNull
    @Size(min = 3,max = 20,message = "Name must be 3-20 characters")
    private String firstName;

    @NotNull
    @Size(min = 3,max = 20,message = "Name must be 3-20 characters")
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String token;
    private boolean isSupervisor;

    public boolean isSupervisor() {
        return isSupervisor;
    }

    public void setSupervisor(boolean supervisor) {
        isSupervisor = supervisor;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
