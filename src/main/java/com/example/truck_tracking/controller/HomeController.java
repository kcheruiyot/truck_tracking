package com.example.truck_tracking.controller;

import com.example.truck_tracking.models.dto.LoginFormDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Kipngetich
 */
@Controller
public class HomeController {

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("appName","Drivers' Scheduler");
        model.addAttribute("company","Daily Shippers");
        model.addAttribute("title","Truck Company");
        return "index";
    }
}
