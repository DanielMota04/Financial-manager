package br.com.danielmota.controllers;

import br.com.danielmota.data.dto.LoginDTO;
import br.com.danielmota.data.dto.RegisterDTO;
import br.com.danielmota.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        authorizationService.register(dto);
        return ResponseEntity.ok("Usuário registrado com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        String token = authorizationService.login(dto);
        return ResponseEntity.ok(token);
    }
}
