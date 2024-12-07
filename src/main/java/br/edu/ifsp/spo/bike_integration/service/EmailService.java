package br.edu.ifsp.spo.bike_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.bike_integration.model.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envia um e-mail com o token de confirmação para o usuário.
     */
    public void sendTokenEmail(Usuario usuario) throws MessagingException {
        String subject = "Token de Confirmação - Bike Integration";
        String htmlContent = buildTokenEmailContent(usuario);
        sendEmail(usuario.getEmail(), subject, htmlContent);
    }

    /**
     * Envia uma mensagem personalizada para o destinatário.
     */
    public void sendAnyMessageEmail(String message, String to) throws MessagingException {
        String subject = "Mensagem de Bike Integration";
        String htmlContent = buildCustomEmailContent(message);
        sendEmail(to, subject, htmlContent);
    }

    /**
     * Constrói o conteúdo do e-mail de token de confirmação.
     */
    private String buildTokenEmailContent(Usuario usuario) {
        return "<!DOCTYPE html>" +
                "<html lang='pt-br'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>" +
                "</head>" +
                "<body style='background-color: #f8f9fa; padding: 20px;'>" +
                "<div class='container' style='max-width: 600px; background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); padding: 20px;'>" +
                "<h1 style='font-size: 24px; color: #007bff;'>Olá " + usuario.getNome() + ",</h1>" +
                "<p>Obrigado por se registrar no <strong>Bike Integration</strong>.</p>" +
                "<p>Seu token de confirmação é:</p>" +
                "<div style='text-align: center; margin: 20px 0;'>" +
                "<span style='font-size: 20px; font-weight: bold; color: #28a745;'>" + usuario.getLastToken() + "</span>" +
                "</div>" +
                "<p>Por favor, use este token para completar seu registro.</p>" +
                "<p style='margin-top: 30px; font-size: 12px; color: #6c757d;'>Atenciosamente,<br>Equipe Bike Integration</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    /**
     * Constrói o conteúdo de um e-mail personalizado.
     */
    private String buildCustomEmailContent(String message) {
        return "<!DOCTYPE html>" +
                "<html lang='pt-br'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>" +
                "</head>" +
                "<body style='background-color: #f8f9fa; padding: 20px;'>" +
                "<div class='container' style='max-width: 600px; background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); padding: 20px;'>" +
                "<h1 style='font-size: 24px; color: #007bff;'>Olá,</h1>" +
                "<p>" + message + "</p>" +
                "<p style='margin-top: 30px; font-size: 12px; color: #6c757d;'>Atenciosamente,<br>Equipe Bike Integration</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    /**
     * Método genérico para enviar um e-mail.
     */
    private void sendEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }
}