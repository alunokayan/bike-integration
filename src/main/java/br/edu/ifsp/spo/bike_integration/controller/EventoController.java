package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1")
@Tag(name = "Evento", description = "Controller para operações relacionadas a eventos.")
public class EventoController {

}
