package com.example.truck_tracking.controller;

import com.example.truck_tracking.models.Shipment;
import com.example.truck_tracking.models.User;
import com.example.truck_tracking.models.data.ShipmentRepository;
import com.example.truck_tracking.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    private UserRepository userRepository;
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
        model.addAttribute("appName","Drivers' Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        model.addAttribute("shipments",shipments);
        model.addAttribute("name",user.getLastName());
        return "driver/index";
    }


    @PostMapping("index")
    public String toggleSwap(Model model, HttpServletRequest request){


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
            if(optionalShipment.get().isSwappable()){
                optionalShipment.get().setSwappable(false);
            }else {
                optionalShipment.get().setSwappable(true);

            }
            shipmentRepository.save(optionalShipment.get());
        }
        model.addAttribute("appName","Drivers' Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        model.addAttribute("shipments",shipments);
        model.addAttribute("name",user.getLastName());
        return "driver/index";
    }



    @GetMapping("swap")
    public String displaySwap(Model model){
        model.addAttribute("appName","Drivers' Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        return "driver/swap";
    }

    @PostMapping("swap")
    public String swapShipment(@RequestParam String username,Model model, HttpServletRequest request){


        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        User user2 = userRepository.findByUsername(username);
        Shipment shipment1 = shipmentRepository.findByDateAndUser(LocalDate.now().toString(),user);
        Shipment shipment2 = shipmentRepository.findByDateAndUser(LocalDate.now().toString(),user2);
        user.addShipment(shipment2);
        user2.addShipment(shipment1);
        shipment1.setUser(user2);
        shipment2.setUser(user);
        userRepository.save(user);
        userRepository.save(user2);

        int id1 = shipment1.getId();
        int id2 = shipment2.getId();

        model.addAttribute("appName","Drivers' Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        //model.addAttribute("name",user.getLastName());
        return "driver/swap";
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
        model.addAttribute("appName","Drivers' Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
    return "driver/shipment";
}
}
