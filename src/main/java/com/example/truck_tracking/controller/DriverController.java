//package com.example.truck_tracking.controller;
//
//import com.example.truck_tracking.models.data.ShipmentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
///**
// * Created by Kipngetich
// */
//@Controller
//public class DriverController {
//@Autowired
//private ShipmentRepository shipmentRepository;
//    @GetMapping("driver")
//    public String index(Model model){
//        model.addAttribute("appName","Shipper's Scheduler");
//        model.addAttribute("company","Daily Shippers");
//        model.addAttribute("title","Truck Company");
//        model.addAttribute("shipments",shipmentRepository.findAll());
//        return "driver/index";
//    }
//}
