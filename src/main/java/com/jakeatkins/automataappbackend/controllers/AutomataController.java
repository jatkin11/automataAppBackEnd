package com.jakeatkins.automataappbackend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jakeatkins.automataappbackend.services.AutomataService;

@RestController
@RequestMapping("/api/automata")
@CrossOrigin(origins = {"http://localhost:5173","https://automata.help"})
public class AutomataController {
    
    private final AutomataService automataService;

    public AutomataController(AutomataService automataService) {
        this.automataService = automataService;
    }


}
