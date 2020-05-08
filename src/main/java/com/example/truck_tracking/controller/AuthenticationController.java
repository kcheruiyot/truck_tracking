//package com.example.truck_tracking.controller;
//
//import com.example.truck_tracking.models.Driver;
//import com.example.truck_tracking.models.Shipment;
//import com.example.truck_tracking.models.Supervisor;
//import com.example.truck_tracking.models.Token;
//import com.example.truck_tracking.models.data.DriverRepository;
//import com.example.truck_tracking.models.data.ShipmentRepository;
//import com.example.truck_tracking.models.data.SupervisorRepository;
//import com.example.truck_tracking.models.data.TokenRepository;
//import com.example.truck_tracking.models.dto.LoginFormDTO;
//import com.example.truck_tracking.models.dto.SignupFormDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
///**
// * Created by Kipngetich
// */
//@Controller
//public class AuthenticationController {
//    @Autowired
//    private DriverRepository driverRepository;
//    @Autowired
//    private SupervisorRepository supervisorRepository;
//    @Autowired
//    private TokenRepository tokenRepository;
//    @Autowired
//    private ShipmentRepository shipmentRepository;
//
//    private static final String driverSessionKey = "driver";
//    private static final String supervisorSessionKey = "supervisor";
//
//    public Driver getDriverFromSession(HttpSession session) {
//        Integer driverId = (Integer) session.getAttribute(driverSessionKey);
//        if (driverId == null) {
//            return null;
//        }
//
//        Optional<Driver> user = driverRepository.findById(driverId);
//
//        if (user.isEmpty()) {
//            return null;
//        }
//        return user.get();
//    }
//
//
//    public Supervisor getSupervisorFromSession(HttpSession session) {
//        Integer supervisorId = (Integer) session.getAttribute(supervisorSessionKey);
//        if (supervisorId == null) {
//            return null;
//        }
//
//        Optional<Supervisor> user = supervisorRepository.findById(supervisorId);
//
//        if (user.isEmpty()) {
//            return null;
//        }
//
//        return user.get();
//    }
//
//
//
//    private static void setDriverInSession(HttpSession session, Driver driver) {
//        session.setAttribute(driverSessionKey, driver.getId());
//    }
//    private static void setSupervisorInSession(HttpSession session, Supervisor supervisor) {
//        session.setAttribute(supervisorSessionKey, supervisor.getId());
//    }
//
//    @GetMapping("/signup")
//    public String displayRegistrationForm(Model model) {
//        model.addAttribute(new SignupFormDTO());
//        model.addAttribute("title", "Signup");
//        model.addAttribute("appName","Shipper's Scheduler");
//        model.addAttribute("company","Daily Shippers");
//        return "signup";
//    }
//
//
//    @PostMapping("/signup")
//    public String processRegistrationForm(@ModelAttribute @Valid SignupFormDTO signupFormDTO,
//                                          Errors errors, HttpServletRequest request,
//                                          Model model) {
//
//        if (errors.hasErrors()) {
//            model.addAttribute("title", "Signup");
//            model.addAttribute("appName","Shipper's Scheduler");
//            model.addAttribute("company","Daily Shippers");
//            return "signup";
//        }
//
//        Driver existingDriver = driverRepository.findByUsername(signupFormDTO.getUsername());
//        Supervisor existingSupervisor = supervisorRepository.findByUsername(signupFormDTO.getUsername());
//
//        if (existingDriver != null || existingSupervisor != null) {
//            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
//            model.addAttribute("title", "Signup");
//            model.addAttribute("appName","Shipper's Scheduler");
//            model.addAttribute("company","Daily Shippers");
//            return "signup";
//        }
//
//        String newToken = signupFormDTO.getToken();
//        Optional<Token> savedToken = Optional.ofNullable(tokenRepository.findByToken(newToken));
//
//        if(!savedToken.isPresent()){
//            errors.rejectValue("token", "token.doesnotexists", "The token does not exist");
//            model.addAttribute("title", "Signup");
//            model.addAttribute("appName","Shipper's Scheduler");
//            model.addAttribute("company","Daily Shippers");
//            return "signup";
//            }
//        else {
//            Token token = savedToken.get();
//            if(!signupFormDTO.getFirstName().equals(token.getFirstName())
//                    || !signupFormDTO.getLastName().equals(token.getLastName())
//                    || signupFormDTO.isSupervisor()!=token.isSupervisor()){
//                errors.rejectValue("token", "token.doesnotexists", "The token does not exist");
//                model.addAttribute("title", "Signup");
//                model.addAttribute("appName","Shipper's Scheduler");
//                model.addAttribute("company","Daily Shippers");
//                return "signup";
//            }
//
//        }
//
//        String password = signupFormDTO.getPassword();
//        String verifyPassword = signupFormDTO.getVerifyPassword();
//        if (!password.equals(verifyPassword)) {
//            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
//            model.addAttribute("title", "Signup");
//            model.addAttribute("appName","Shipper's Scheduler");
//            model.addAttribute("company","Daily Shippers");
//            return "signup";
//        }
//
//        boolean isSupervisor = signupFormDTO.isSupervisor();
//        if(isSupervisor){
//            Supervisor newSupervisor = new Supervisor(signupFormDTO.getUsername(), signupFormDTO.getPassword(),signupFormDTO.getFirstName(),signupFormDTO.getLastName(),signupFormDTO.getEmail(),signupFormDTO.getToken());
//            supervisorRepository.save(newSupervisor);
//            setSupervisorInSession(request.getSession(), newSupervisor);
//        }
//        else{
//            Driver newDriver = new Driver(signupFormDTO.getUsername(), signupFormDTO.getPassword(),signupFormDTO.getFirstName(),signupFormDTO.getLastName(),signupFormDTO.getEmail(),signupFormDTO.getToken());
//            driverRepository.save(newDriver);
//            setDriverInSession(request.getSession(), newDriver);
//        }
//
//
//        return "redirect:";
//    }
//    @GetMapping("")
//    public String displayLoginForm(Model model) {
//        model.addAttribute(new LoginFormDTO());
//        model.addAttribute("appName","Shipper's Scheduler");
//        model.addAttribute("company","Daily Shippers");
//        model.addAttribute("title","Truck Company");
//        model.addAttribute("shipments",shipmentRepository.findAll());
//        return "index";
//    }
//
//
//
//
//    @PostMapping("")
//    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
//                                   Errors errors, HttpServletRequest request,
//                                   Model model) {
//
//        if (errors.hasErrors()) {
//            model.addAttribute("appName","Shipper's Scheduler");
//            model.addAttribute("company","Daily Shippers");
//            model.addAttribute("title","Truck Company");
//            return "index";
//        }
//
//        boolean isSupervisor = loginFormDTO.isSupervisor();
//
//        if(isSupervisor){
//            Supervisor theSupervisor = supervisorRepository.findByUsername(loginFormDTO.getUsername());
//
//            if (theSupervisor == null) {
//                errors.rejectValue("username", "user.invalid", "The given username does not exist");
//                model.addAttribute("appName","Shipper's Scheduler");
//                model.addAttribute("company","Daily Shippers");
//                model.addAttribute("title","Truck Company");
//                return "index";
//            }
//
//            String password = loginFormDTO.getPassword();
//
//            if (!theSupervisor.isMatchingPassword(password)) {
//                errors.rejectValue("password", "password.invalid", "Invalid password");
//                model.addAttribute("appName","Shipper's Scheduler");
//                model.addAttribute("company","Daily Shippers");
//                model.addAttribute("title","Truck Company");
//                return "index";
//            }
//
//            setSupervisorInSession(request.getSession(), theSupervisor);
//            model.addAttribute("appName","Shipper's Scheduler");
//            model.addAttribute("company","Daily Shippers");
//            model.addAttribute("title","Truck Company");
//            model.addAttribute("shipments",shipmentRepository.findAll());
//            return "supervisor/index";
//
//        }
//
//
//
//
//
//        else{
//            Driver theDriver = driverRepository.findByUsername(loginFormDTO.getUsername());
//
//            if (theDriver == null) {
//                errors.rejectValue("username", "user.invalid", "The given username does not exist");
//                model.addAttribute("appName","Shipper's Scheduler");
//                model.addAttribute("company","Daily Shippers");
//                model.addAttribute("title","Truck Company");
//                return "index";
//            }
//
//            String password = loginFormDTO.getPassword();
//
//            if (!theDriver.isMatchingPassword(password)) {
//                errors.rejectValue("password", "password.invalid", "Invalid password");
//                model.addAttribute("appName","Shipper's Scheduler");
//                model.addAttribute("company","Daily Shippers");
//                model.addAttribute("title","Truck Company");
//                return "index";
//            }
//
//            setDriverInSession(request.getSession(), theDriver);
//           String today = LocalDate.now().toString();
//            String shipmentDate;
//            List<Shipment> shipments = new ArrayList<>();
//
//
//            for(Shipment shipment:shipmentRepository.findAll()){
//               if(!shipment.getDriver().equals(theDriver)){
//                   shipmentDate = shipment.getDate();
//                   if(shipmentDate.equals(today)){
//                       shipments.add(shipment);
//                   }
//                }
//            }
//
//            model.addAttribute("appName","Shipper's Scheduler");
//            model.addAttribute("company","Daily Shippers");
//            model.addAttribute("title","Truck Company");
//            model.addAttribute("name",theDriver.getLastName());
//            model.addAttribute("today", LocalDate.now());
//
//            Optional<Shipment> optionalShipment = Optional.ofNullable(shipmentRepository.findByDateAndDriver(LocalDate.now().toString(), theDriver));
//            if(optionalShipment.isPresent()){
//                model.addAttribute("myShipment",optionalShipment.get());
//            }
//            model.addAttribute("shipments",shipments);
//            return "driver/index";
//        }
//    }
//
//
//
//
//
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request){
//        request.getSession().invalidate();
//        return "redirect:";
//    }
//
//
//}
