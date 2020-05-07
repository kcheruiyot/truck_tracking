package com.example.truck_tracking.controller;

import com.example.truck_tracking.models.Driver;
import com.example.truck_tracking.models.Shipment;
import com.example.truck_tracking.models.Token;
import com.example.truck_tracking.models.data.DriverRepository;
import com.example.truck_tracking.models.data.ShipmentRepository;
import com.example.truck_tracking.models.data.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
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
    private DriverRepository driverRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping
    public String index(Model model){
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        model.addAttribute("name","Cheruiyot");
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("shipments",shipmentRepository.findAll());
        return "supervisor/index";
    }
    @GetMapping("add")
    public String displayAddAssignmentsForm(Model model){
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        model.addAttribute("name","Cheruiyot");
        model.addAttribute(new Shipment());
       model.addAttribute("drivers",driverRepository.findAll());

        return "supervisor/add";
    }
    @PostMapping("add")
    public String processAddAssignmentForm(@RequestParam String username, @RequestParam String source, @RequestParam String destination, @RequestParam String description, @RequestParam String recipient, Model model){


        Optional<Driver> optionalDriver= Optional.ofNullable(driverRepository.findByUsername(username));
       if(optionalDriver.isPresent()){
           Driver currentDriver = optionalDriver.get();
           Shipment shipment = new Shipment(currentDriver,source,destination,description, recipient);

           Iterable<Shipment> shipments = shipmentRepository.findAll();
           for(Shipment shipment1: shipments){
               if(shipment1.getDriver().equals(currentDriver)&& (shipment.getDate().equals(LocalDate.now().toString()))){
                   model.addAttribute("appName","Shipper's Scheduler");
                   model.addAttribute("company","Daily Shippers");
                   model.addAttribute("title","Truck Company");
                   model.addAttribute("name","Cheruiyot");
                   model.addAttribute(new Shipment());
                   model.addAttribute("drivers",driverRepository.findAll());
                   model.addAttribute("errorMsg","Driver already assigned today");
                   return "supervisor/add";
               }
           }
           currentDriver.addShipment(shipment);
           shipmentRepository.save(shipment);

           model.addAttribute("appName","Shipper's Scheduler");
           model.addAttribute("company","Daily Shippers");
           model.addAttribute("title","Truck Company");
           model.addAttribute("name","Cheruiyot");
           model.addAttribute("today", LocalDate.now());
           model.addAttribute("currentDriver",shipment.getDriver());
           model.addAttribute("shipments",shipmentRepository.findAll());
           return "redirect:";
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
    public String processTokenForm( @ModelAttribute Token token,Errors errors,Model model){

        Optional<Token> optionalToken = Optional.ofNullable(tokenRepository.findByToken(token.getToken()));
       if(optionalToken.isPresent()){
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
        return "redirect:";
    }
}
