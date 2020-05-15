package com.example.truck_tracking.controller;

import com.example.truck_tracking.models.Shipment;
import com.example.truck_tracking.models.Token;
import com.example.truck_tracking.models.User;
import com.example.truck_tracking.models.data.ShipmentRepository;
import com.example.truck_tracking.models.data.TokenRepository;
import com.example.truck_tracking.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kipngetich
 */
@Controller
@RequestMapping("supervisor")
public class SupervisorController {

    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

//    @GetMapping
//    public String index(Model model){
//        model.addAttribute("appName","Shipper's Scheduler");
//        model.addAttribute("company","Daily Shippers");
//        model.addAttribute("title","Truck Company");
//        model.addAttribute("name","Cheruiyot");
//        model.addAttribute("today", LocalDate.now());
//        model.addAttribute("shipments",shipmentRepository.findAll());
//        return "supervisor/index";
//    }
    @GetMapping("add")
    public String displayAddAssignmentsForm(Model model){
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        model.addAttribute("name","Cheruiyot");
        model.addAttribute(new Shipment());
        Iterable<User> users = userRepository.findAll();
        List<User> drivers = new ArrayList<>();
        for(User user:users){
            if(!user.isSupervisor()){
                drivers.add(user);
            }
        }
       model.addAttribute("drivers",drivers);

        return "supervisor/add";
    }
    @PostMapping("add")
    public String processAddAssignmentForm(@RequestParam String username, @RequestParam String source, @RequestParam String destination, @RequestParam String description, @RequestParam String recipient, Model model){


        Optional<User> optionalUser= Optional.ofNullable(userRepository.findByUsername(username));
       if(optionalUser.isPresent()){
           User currentUser = optionalUser.get();
           Shipment shipment = new Shipment(currentUser,source,destination,description, recipient);

           Iterable<Shipment> shipments = shipmentRepository.findAll();
           for(Shipment shipment1: shipments){
               if(shipment1.getUser().equals(currentUser)&& (shipment1.getDate().equals(LocalDate.now().toString()))){
                   model.addAttribute("appName","Shipper's Scheduler");
                   model.addAttribute("company","Daily Shippers");
                   model.addAttribute("title","Truck Company");
                   model.addAttribute("name","Cheruiyot");
                   model.addAttribute(new Shipment());
                   Iterable<User> users = userRepository.findAll();
                   List<User> drivers = new ArrayList<>();
                   for(User user:users){
                       if(!user.isSupervisor()){
                           drivers.add(user);
                       }
                   }
                   model.addAttribute("drivers",drivers);
                   model.addAttribute("errorMsg","Driver already assigned today");
                   return "supervisor/add";
               }
           }
           currentUser.addShipment(shipment);
           shipmentRepository.save(shipment);

           model.addAttribute("appName","Shipper's Scheduler");
           model.addAttribute("company","Daily Shippers");
           model.addAttribute("title","Truck Company");
           model.addAttribute("name","Cheruiyot");
           model.addAttribute("today", LocalDate.now());
           model.addAttribute("currentDriver",shipment.getUser());
           model.addAttribute("shipments",shipmentRepository.findAll());
           return "supervisor/index";
       }else {
           return "supervisor/add";
       }


    }
    @GetMapping("addToken")
    public String displayAddTokenForm(Model model){
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        model.addAttribute("name","Cheruiyot");
        model.addAttribute(new Token());
        return "supervisor/addToken";
    }


    @PostMapping("addToken")
    public String processTokenForm(@ModelAttribute @Valid Token token, Errors errors, Model model){

        Optional<Token> optionalToken = Optional.ofNullable(tokenRepository.findByToken(token.getToken()));
       if(optionalToken.isPresent() || errors.hasErrors()){
           model.addAttribute("appName","Shipper's Scheduler");
           model.addAttribute("company","Daily Shippers");
           model.addAttribute("title","Truck Company");
           model.addAttribute("name","Cheruiyot");
          errors.rejectValue("token", "token.invalid", "Token already exists");
           model.addAttribute(new Token());
           return "supervisor/addToken";
       }
        //Token newToken = new Token(firstName,lastName,theToken,isSupervisor);
       tokenRepository.save(token);
        model.addAttribute("shipments",shipmentRepository.findAll());
        return "supervisor/index";
    }
}
