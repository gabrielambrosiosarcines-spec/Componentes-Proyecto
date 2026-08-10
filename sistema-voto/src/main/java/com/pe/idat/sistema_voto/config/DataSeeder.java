package com.pe.idat.sistema_voto.config;

import com.pe.idat.sistema_voto.entity.Usuario;
import com.pe.idat.sistema_voto.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Al arrancar la aplicación, crea un usuario administrador por defecto
 * si todavía no existe ninguno. Esto es necesario porque /usuarios/**
 * requiere ROLE_ADMIN: sin este seeder no habría forma de crear el
 * primer admin (nadie podría autenticarse para crearlo).
 *
 * Credenciales por defecto: admin / admin123
 * Cámbialas apenas puedas iniciar sesión (POST /usuarios/cambiar-password).
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
public void run(String... args) {

    boolean existeAdmin =
            usuarioRepository.findByUsername("admin").isPresent();

    if (!existeAdmin) {

        Usuario admin = new Usuario();

        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRol("ADMIN");
        admin.setEstado(true);

        usuarioRepository.save(admin);

        System.out.println("========================================================");
        System.out.println(" Usuario admin creado -> username: admin / password: admin123");
        System.out.println(" Cambia esta contraseña apenas puedas.");
        System.out.println("========================================================");
        }
    }
}
