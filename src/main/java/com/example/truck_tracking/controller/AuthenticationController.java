package com.example.truck_tracking.controller;

import com.example.truck_tracking.models.Driver;
import com.example.truck_tracking.models.data.DriverRepository;
import com.example.truck_tracking.models.dto.LoginFormDTO;
import com.example.truck_tracking.models.dto.SignupFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by Kipngetich
 */
@Controller
public class AuthenticationController {
    @Autowired
    private DriverRepository driverRepository;

    private static final String driverSessionKey = "driver";

    public Driver getDriverFromSession(HttpSession session) {
        Integer driverId = (Integer) session.getAttribute(driverSessionKey);
        if (driverId == null) {
            return null;
        }

        Optional<Driver> user = driverRepository.findById(driverId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    private static void setDriverInSession(HttpSession session, Driver driver) {
        session.setAttribute(driverSessionKey, driver.getId());
    }

    @GetMapping("/signup")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new SignupFormDTO());
        model.addAttribute("title", "Signup");
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        return "signup";
    }


    @PostMapping("/signup")
    public String processRegistrationForm(@ModelAttribute @Valid SignupFormDTO signupFormDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Signup");
            model.addAttribute("appName","Shipper's Scheduler");
            model.addAttribute("company","Daily Shippers");
            return "signup";
        }

        Driver existingDriver = driverRepository.findByUsername(signupFormDTO.getUsername());

        if (existingDriver != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "Signup");
            model.addAttribute("appName","Shipper's Scheduler");
            model.addAttribute("company","Daily Shippers");
            return "signup";
        }

        String password = signupFormDTO.getPassword();
        String verifyPassword = signupFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Signup");
            model.addAttribute("appName","Shipper's Scheduler");
            model.addAttribute("company","Daily Shippers");
            return "signup";
        }

        Driver newDriver = new Driver(signupFormDTO.getUsername(), signupFormDTO.getPassword(),signupFormDTO.getFirstName(),signupFormDTO.getLastName(),signupFormDTO.getEmail(),signupFormDTO.getToken());
       driverRepository.save(newDriver);
        setDriverInSession(request.getSession(), newDriver);

        return "redirect:";
    }
    @GetMapping("")
    public String displayLoginForm(Model model) {
        model.addAttribute(new LoginFormDTO());
       // model.addAttribute("title", "Log In");
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        return "index";
    }




    @PostMapping("")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
                                   Errors errors, HttpServletRequest request,
                                   Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("appName","Shipper's Scheduler");
            model.addAttribute("company","Daily Shippers");
            model.addAttribute("title","Truck Company");
            return "index";
        }

        Driver theDriver = driverRepository.findByUsername(loginFormDTO.getUsername());

        if (theDriver == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            model.addAttribute("appName","Shipper's Scheduler");
            model.addAttribute("company","Daily Shippers");
            model.addAttribute("title","Truck Company");
            return "index";
        }

        String password = loginFormDTO.getPassword();

        if (!theDriver.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("appName","Shipper's Scheduler");
            model.addAttribute("company","Daily Shippers");
            model.addAttribute("title","Truck Company");
            return "index";
        }

        setDriverInSession(request.getSession(), theDriver);
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        return "driver/index";
    }





    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:";
    }


}
