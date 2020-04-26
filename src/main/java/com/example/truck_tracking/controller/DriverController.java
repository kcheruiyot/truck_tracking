package com.example.truck_tracking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

/**
 * Created by Kipngetich
 */
@Controller
@RequestMapping("driver")
public class DriverController {

    @GetMapping
    public String index(Model model){
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        model.addAttribute("name","Cheruiyot");
        model.addAttribute("today", LocalDate.now());
        return "driver/index";
    }
}
