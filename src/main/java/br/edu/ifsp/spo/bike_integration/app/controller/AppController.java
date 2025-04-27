package br.edu.ifsp.spo.bike_integration.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.edu.ifsp.spo.bike_integration.annotation.BearerToken;
import br.edu.ifsp.spo.bike_integration.annotation.Role;
import br.edu.ifsp.spo.bike_integration.exception.BikeIntegrationCustomException;
import br.edu.ifsp.spo.bike_integration.exception.CryptoException;
import br.edu.ifsp.spo.bike_integration.hardcode.RoleType;
import br.edu.ifsp.spo.bike_integration.jwt.service.JwtService;
import br.edu.ifsp.spo.bike_integration.model.Usuario;
import br.edu.ifsp.spo.bike_integration.service.SessaoService;
import br.edu.ifsp.spo.bike_integration.service.UsuarioService;
import br.edu.ifsp.spo.bike_integration.util.CryptoUtils;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/app")
public class AppController {

    @Value("${decryption.key}")
    private String decryptionKey;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private JwtService jwtService;

    @RequestMapping({ "/", "/home" })
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

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> doLogin(HttpServletResponse response, String username, String password)
            throws BikeIntegrationCustomException, CryptoException {
        Usuario usuario = usuarioService.loadUsuarioByNomeUsuario(username);
        if (usuario == null) {
            throw new BikeIntegrationCustomException("Usuário não encontrado");
        }

        String token = jwtService.create(usuario, password);
        sessaoService.create(usuario, token);

        return new ResponseEntity<>(
                Map.of(
                        "token", CryptoUtils.encryptWithHexKey(token, decryptionKey),
                        "userId", String.valueOf(usuario.getId()),
                        "username", usuario.getNomeUsuario()),
                HttpStatus.OK);
    }

    @Role(RoleType.ADMIN)
    @BearerToken
    @RequestMapping(path = "/isUsuarioValido", method = RequestMethod.GET)
    public ResponseEntity<String> isUsuarioValido() {
        return new ResponseEntity<>("Usuário válido", HttpStatus.NO_CONTENT);
    }

}
