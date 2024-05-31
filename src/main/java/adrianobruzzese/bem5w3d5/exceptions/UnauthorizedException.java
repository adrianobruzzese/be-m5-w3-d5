package adrianobruzzese.bem5w3d5.exceptions;

public class UnauthorizedException extends RuntimeException {

    // Costruttore per creare un'eccezione con un messaggio personalizzato
    public UnauthorizedException(String message) {
        super(message);
    }
}
