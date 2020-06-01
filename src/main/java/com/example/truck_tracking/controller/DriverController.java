package com.example.truck_tracking.controller;

import com.example.truck_tracking.models.Shipment;
import com.example.truck_tracking.models.User;
import com.example.truck_tracking.models.data.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kipngetich
 */

@Controller
@RequestMapping("driver")
public class DriverController {
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private AuthenticationController authenticationController;
    @GetMapping("index")
    public String index(Model model, HttpServletRequest request){


        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Iterable <Shipment> allShipments = shipmentRepository.findAll();
        List<Shipment> shipments = new ArrayList<>();
        for(Shipment shipment:allShipments){
            if(!user.getUsername().equals(shipment.getUser().getUsername()) && shipment.getDate().equals(LocalDate.now().toString())){
                shipments.add(shipment);
            }
        }
        Optional<Shipment> optionalShipment = Optional.ofNullable(shipmentRepository.findByDateAndUser(LocalDate.now().toString(), user));
        if(optionalShipment.isPresent()){
            model.addAttribute("myShipment",optionalShipment.get());
        }
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        model.addAttribute("shipments",shipments);
        model.addAttribute("name",user.getLastName());
        return "driver/index";
    }
    @GetMapping("shipment")
public String shipment(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
    Iterable <Shipment> allShipments = shipmentRepository.findAll();
        List<Shipment> shipments = new ArrayList<>();
        for(Shipment shipment:allShipments){
            if(user.getUsername().equals(shipment.getUser().getUsername())){
                shipments.add(shipment);
            }
        }
        model.addAttribute("shipments",shipments);
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
    return "driver/shipment";
}
}
