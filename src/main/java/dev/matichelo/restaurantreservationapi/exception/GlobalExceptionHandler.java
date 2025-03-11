package dev.matichelo.restaurantreservationapi.exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

// ControllerAdvice es una anotación de marcador que permite a un controlador de Spring ser compartido por múltiples controladores.
// Es una especialización de la anotación @Component, lo que significa que un controlador anotado con él se detectará durante el escaneo de componentes automático de Spring.
@ControllerAdvice
public class GlobalExceptionHandler {

    //  @ExceptionHandler es una anotación que se utiliza para manejar excepciones específicas y enviar respuestas de
    //  error al client., (ResourceNotFoundException.class) es la excepción que se manejará y (ex) es la excepción
    //  que se manejará, . class es un método que devuelve la clase de un objeto.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                        WebRequest request){
        // ResourceNotFoundException es una clase que extiende de RuntimeException y se utiliza para manejar excepciones de recursos no encontrados.
        // WebRequest es una interfaz que proporciona métodos para acceder a la solicitud HTTP y a los metadatos de la solicitud.
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(BadRequestException ex, WebRequest request){
        // request.getDescription(false) es un método que devuelve una descripción de la solicitud y se pone false para que no se incluyan los detalles de la solicitud.
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
