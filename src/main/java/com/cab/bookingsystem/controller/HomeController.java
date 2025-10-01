package com.cab.bookingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@RequestParam(value = "response", required = false) String response, Model model) {
        model.addAttribute("response", response); // makes ${response} available to index.html
        return "index"; // templates/index.html
    }
}
