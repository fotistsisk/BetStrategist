package com.fotistsiskakis.betstrategist.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {
    @RequestMapping( "/hello" )
    public String echo() {
        return "Hello World!";
    }
}