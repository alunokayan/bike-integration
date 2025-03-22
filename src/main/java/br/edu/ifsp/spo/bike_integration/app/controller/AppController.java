package br.edu.ifsp.spo.bike_integration.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifsp.spo.bike_integration.annotation.BearerToken;
import br.edu.ifsp.spo.bike_integration.annotation.Role;
import br.edu.ifsp.spo.bike_integration.hardcode.RoleType;

@Controller
@RequestMapping("/app")
public class AppController {

    @Role(RoleType.ADMIN)
    @BearerToken
    @RequestMapping({ "/", "/home", "/index" })
    public String home() {
        return "home.html";
    }

    @Role(RoleType.ADMIN)
    @BearerToken
    @RequestMapping("/login")
    public String login(boolean expired) {
        return "login.html";
    }

    @Role(RoleType.ADMIN)
    @BearerToken
    @RequestMapping("/evento")
    public String evento() {
        return "evento.html";
    }

}
