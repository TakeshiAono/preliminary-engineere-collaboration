package com.api.EngineerCollabo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    
    @GetMapping("/chat")
    public String chatPage(){
        return "chat"; 
    }
}
