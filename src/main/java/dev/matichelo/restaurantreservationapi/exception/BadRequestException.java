package dev.matichelo.restaurantreservationapi.exception;

public class BadRequestException extends RuntimeException {

    // Es un método de la clase RuntimeException que no recibe parámetros y lo envía al constructor de la clase padre
    public BadRequestException() {
        super();
    }

    // Es un método de la clase RuntimeException que recibe un mensaje y lo envía al constructor de la clase padre
    public BadRequestException(String message) {
        // super es una palabra clave en Java que se utiliza para acceder a los métodos de la clase padre o a los constructores de la clase padre.
        // en este caso lo utilizamos para enviar el mensaje al constructor de la clase padre y la clase padre es RuntimeException
        super(message);
    }
}

