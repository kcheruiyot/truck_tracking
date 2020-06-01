package com.example.truck_tracking.controller;

import com.example.truck_tracking.models.*;
import com.example.truck_tracking.models.data.ShipmentRepository;
import com.example.truck_tracking.models.data.TokenRepository;
import com.example.truck_tracking.models.data.UserRepository;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kipngetich
 */
@Controller
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    private static final String userSessionKey = "user";


    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }


    @GetMapping("/signup")
    public String displaySignupForm(Model model) {
        model.addAttribute(new SignupFormDTO());
        model.addAttribute("title", "Signup");
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignupForm(@ModelAttribute @Valid SignupFormDTO signupFormDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Signup");
            model.addAttribute("appName","Shipper's Scheduler");
            model.addAttribute("company","Daily Shippers");
            return "signup";
        }

        User existingUser = userRepository.findByUsername(signupFormDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "Signup");
            model.addAttribute("appName","Shipper's Scheduler");
            model.addAttribute("company","Daily Shippers");
            return "signup";
        }

        String newToken = signupFormDTO.getToken();
        Optional<Token> savedToken = Optional.ofNullable(tokenRepository.findByToken(newToken));

        if(!savedToken.isPresent()){
            errors.rejectValue("token", "token.doesnotexists", "The token does not exist");
            model.addAttribute("title", "Signup");
            model.addAttribute("appName","Shipper's Scheduler");
            model.addAttribute("company","Daily Shippers");
            return "signup";
        }
        else {
            Token token = savedToken.get();
            if(!signupFormDTO.getFirstName().equals(token.getFirstName())
                    || !signupFormDTO.getLastName().equals(token.getLastName())
                    || signupFormDTO.isSupervisor()!=token.isSupervisor()){
                errors.rejectValue("token", "token.doesnotexists", "The token does not exist");
                model.addAttribute("title", "Signup");
                model.addAttribute("appName","Shipper's Scheduler");
                model.addAttribute("company","Daily Shippers");
                return "signup";
            }

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

        User newUser = new User(signupFormDTO.getUsername(), signupFormDTO.getPassword(),signupFormDTO.getFirstName(),signupFormDTO.getLastName(),signupFormDTO.getEmail(),signupFormDTO.isSupervisor());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);
       // model.addAttribute(new LoginFormDTO());
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        if(signupFormDTO.isSupervisor()){
            model.addAttribute("shipments",shipmentRepository.findAll());
            return "supervisor/index";
        }else{

            String today = LocalDate.now().toString();
            String shipmentDate;
            List<Shipment> shipments = new ArrayList<>();
            for(Shipment shipment:shipmentRepository.findAll()){
                if(!shipment.getUser().equals(newUser)){
                    shipmentDate = shipment.getDate();
                    if(shipmentDate.equals(today)){
                        shipments.add(shipment);
                    }
                }
            }


            model.addAttribute("name",newUser.getLastName());
            model.addAttribute("today", LocalDate.now());

            Optional<Shipment> optionalShipment = Optional.ofNullable(shipmentRepository.findByDateAndUser(LocalDate.now().toString(), newUser));
            if(optionalShipment.isPresent()){
                model.addAttribute("myShipment",optionalShipment.get());
            }
            model.addAttribute("shipments",shipments);
            return "driver/index";
        }

    }
    @GetMapping("/login")
    public String displayLoginForm(Model model) {
        model.addAttribute(new LoginFormDTO());
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
                                   Errors errors, HttpServletRequest request,
                                   Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("appName","Shipper's Scheduler");
            model.addAttribute("company","Daily Shippers");
            model.addAttribute("title","Truck Company");
            return "login";
        }


        User user = userRepository.findByUsername(loginFormDTO.getUsername());
        if (user == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            model.addAttribute("appName","Shipper's Scheduler");
            model.addAttribute("company","Daily Shippers");
            model.addAttribute("title","Truck Company");
            return "login";
        }

        String password = loginFormDTO.getPassword();

        if(!user.isMatchingPassword(password)){
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("appName","Shipper's Scheduler");
            model.addAttribute("company","Daily Shippers");
            model.addAttribute("title","Truck Company");
            return "login";
        }
        setUserInSession(request.getSession(), user);
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        model.addAttribute("name",user.getLastName());

        if(user.isSupervisor()) {
            Iterable<Shipment>  allShipments = shipmentRepository.findAll();
            List<Shipment> shipments = new ArrayList<>();
            for(Shipment shipment: allShipments){
                if(shipment.getDate().equals(LocalDate.now().toString())){
                    shipments.add(shipment);
                }
            }
            model.addAttribute("shipments",shipments);
            return "supervisor/index";
        }
        else {
            String today = LocalDate.now().toString();
            String shipmentDate;
            List<Shipment> shipments = new ArrayList<>();
            for(Shipment shipment:shipmentRepository.findAll()){
                if(!shipment.getUser().equals(user)){
                    shipmentDate = shipment.getDate();
                    if(shipmentDate.equals(today)){
                        shipments.add(shipment);
                    }
                }
            }


            model.addAttribute("name",user.getLastName());
            model.addAttribute("today", LocalDate.now());

            Optional<Shipment> optionalShipment = Optional.ofNullable(shipmentRepository.findByDateAndUser(LocalDate.now().toString(), user));
            if(optionalShipment.isPresent()){
                model.addAttribute("myShipment",optionalShipment.get());
            }
            model.addAttribute("shipments",shipments);
            return "driver/index";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/login";
    }
}
