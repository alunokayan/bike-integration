
package br.edu.ifsp.spo.bike_integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.bike_integration.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/v1/email")
@Tag(name = "Email", description = "Email Controller, com envio de emails")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping("/sendAnyMessage")
	public ResponseEntity<Void> sendEmail(@RequestParam String message, @RequestParam String to)
			throws MessagingException {
		emailService.sendAnyMessageEmail(message, to);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}