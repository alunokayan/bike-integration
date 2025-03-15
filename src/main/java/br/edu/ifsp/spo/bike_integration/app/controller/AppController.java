package br.edu.ifsp.spo.bike_integration.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class AppController {

    @RequestMapping({ "/", "/home", "/index" })
    public String home() {
        return "home.html";
    }

    @RequestMapping("/login")
    public String login(boolean expired) {
        return "login.html";
    }

    @RequestMapping("/evento")
    public String evento() {
        return "evento.html";
    }

}
