package com.example.truck_tracking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kipngetich
 */
@Controller
public class HomeController {
    @RequestMapping("")
    public String index(Model model){
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        return "index";
    }
    @GetMapping("signup")
    public String signup(Model model){
        model.addAttribute("appName","Shipper's Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        return "signup";
    }
}
