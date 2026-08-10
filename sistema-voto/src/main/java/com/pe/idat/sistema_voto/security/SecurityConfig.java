package com.pe.idat.sistema_voto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    // Orígenes del frontend permitidos (ver app.cors.allowed-origins en application.yaml).
    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        List<String> origenes = Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(origen -> !origen.isBlank())
                .toList();

        configuration.setAllowedOrigins(origenes);
        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Público
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Cualquier usuario autenticado puede cambiar su propia contraseña
                        // y emitir su voto: van antes que las reglas generales de abajo.
                        .requestMatchers("/usuarios/cambiar-password").authenticated()
                        .requestMatchers(HttpMethod.POST, "/votos/emitir").authenticated()

                        // Gestión de usuarios y auditoría: solo administradores.
                        .requestMatchers("/usuarios/**", "/auditoria/**").hasRole("ADMIN")

                        // Padrón de votantes: contiene datos personales (DNI, correo) —
                        // solo administradores lo listan/editan.
                        .requestMatchers("/votantes/**").hasRole("ADMIN")

                        // Registro de participación: si un votante pudiera tocar esta tabla
                        // directamente, podría borrar su propio registro y volver a votar.
                        // Solo administradores acceden al CRUD genérico.
                        .requestMatchers("/registro-participacion/**").hasRole("ADMIN")

                        // Elecciones y candidatos: cualquier autenticado los puede leer
                        // (los necesita para votar), pero solo ADMIN los crea/edita/borra.
                        .requestMatchers(HttpMethod.POST, "/elecciones/**", "/candidatos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/elecciones/**", "/candidatos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/elecciones/**", "/candidatos/**").hasRole("ADMIN")

                        // Votos: el CRUD genérico (crear/editar/borrar un voto "a mano")
                        // queda para administradores; los votantes usan /votos/emitir.
                        .requestMatchers(HttpMethod.POST, "/votos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/votos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/votos/**").hasRole("ADMIN")

                        // Cualquier otra ruta: basta con estar autenticado.
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
