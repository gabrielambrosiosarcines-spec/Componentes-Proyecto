package com.pe.idat.sistema_voto.controller;

import com.pe.idat.sistema_voto.dto.CambiarPasswordRequest;
import com.pe.idat.sistema_voto.entity.Usuario;
import com.pe.idat.sistema_voto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> listar() {
        return service.listar();
    }

    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return service.guardar(usuario);
    }
    @GetMapping("/{id}")
    public Usuario buscar(
            @PathVariable Integer id){

        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public Usuario actualizar(
            @PathVariable Integer id,
            @RequestBody Usuario usuario){

        return service.actualizar(
                id, usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Integer id){

        service.eliminar(id);
    }

    // Abierto a cualquier usuario autenticado (ver SecurityConfig): no requiere ROLE_ADMIN,
    // porque cada quien debe poder cambiar su propia contraseña.
    @PostMapping("/cambiar-password")
    public ResponseEntity<?> cambiarPassword(
            @RequestBody CambiarPasswordRequest request,
            Authentication authentication) {

        boolean actualizado = service.cambiarPassword(
                authentication.getName(),
                request.getPasswordActual(),
                request.getPasswordNuevo());

        if (!actualizado) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("La contraseña actual no es correcta");
        }

        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }
}
