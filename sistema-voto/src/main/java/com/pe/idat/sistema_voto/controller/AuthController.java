package com.pe.idat.sistema_voto.controller;

import com.pe.idat.sistema_voto.dto.LoginRequest;
import com.pe.idat.sistema_voto.dto.LoginResponse;
import com.pe.idat.sistema_voto.entity.Usuario;
import com.pe.idat.sistema_voto.repository.UsuarioRepository;
import com.pe.idat.sistema_voto.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario o contraseña incorrectos");
        }

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow();

        String token = jwtUtil.generarToken(
                usuario.getUsername(), usuario.getRol());

        return ResponseEntity.ok(
                new LoginResponse(token, usuario.getUsername(), usuario.getRol()));
    }
}
