package com.pe.idat.sistema_voto.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Punto único donde se traducen las excepciones de toda la API a respuestas
 * HTTP consistentes: { timestamp, status, error, message, path }.
 * Evita que un error inesperado devuelva un 500 con stacktrace crudo.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 404 — id que no existe (buscar/actualizar/eliminar sobre un recurso inexistente)
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Object> manejarNoEncontrado(
            RecursoNoEncontradoException ex, WebRequest request) {
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    // 400 — datos de entrada inválidos (ids inexistentes referenciados, argumentos ilegales, etc.)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> manejarArgumentoInvalido(
            IllegalArgumentException ex, WebRequest request) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    // 409 — conflicto de estado (ej. votante que ya participó en la elección)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> manejarEstadoInvalido(
            IllegalStateException ex, WebRequest request) {
        return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    // 401 — credenciales incorrectas en login (por si no se captura antes en el controller)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> manejarCredencialesInvalidas(
            BadCredentialsException ex, WebRequest request) {
        return construirRespuesta(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos", request);
    }

    // 403 — autenticado pero sin el rol/permiso necesario
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> manejarAccesoDenegado(
            AccessDeniedException ex, WebRequest request) {
        return construirRespuesta(HttpStatus.FORBIDDEN, "No tienes permisos para esta acción", request);
    }

    // 500 — cualquier otro error no anticipado: no se expone el detalle interno ni el stacktrace.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> manejarErrorGeneral(
            Exception ex, WebRequest request) {
        log.error("Error no controlado", ex);
        return construirRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno. Intenta nuevamente más tarde.",
                request);
    }

    // 400 — JSON mal formado en el body de la petición
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, "El cuerpo de la petición no es JSON válido", request);
    }

    // 400 — fallos de @Valid (queda listo para cuando se agreguen validaciones con @NotBlank, etc.)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> body = cuerpoBase(HttpStatus.BAD_REQUEST, "Datos inválidos", request);
        body.put("errores", errores);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    private ResponseEntity<Object> construirRespuesta(
            HttpStatus status, String mensaje, WebRequest request) {
        return ResponseEntity.status(status).body(cuerpoBase(status, mensaje, request));
    }

    private Map<String, Object> cuerpoBase(HttpStatus status, String mensaje, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", mensaje);
        body.put("path", request.getDescription(false).replace("uri=", ""));
        return body;
    }
}
