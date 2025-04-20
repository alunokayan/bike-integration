package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.annotation.XAccessKey;
import br.edu.ifsp.spo.bike_integration.annotation.Role;
import br.edu.ifsp.spo.bike_integration.hardcode.RoleType;
import br.edu.ifsp.spo.bike_integration.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("v1/email")
@Tag(name = "Email", description = "Controller para operações relacionadas ao envio de e-mail.")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@Role(RoleType.ADMIN)
	@XAccessKey
	@PostMapping(path = "/mensagem", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Envia uma mensagem de e-mail para o destinatário informado.")
	@ResponseStatus(HttpStatus.OK)
	public void sendEmail(@RequestParam String message, @RequestParam String to) throws MessagingException {
		emailService.sendAnyMessageEmail(message, to);
	}
}