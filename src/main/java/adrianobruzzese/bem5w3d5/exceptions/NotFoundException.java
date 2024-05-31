package adrianobruzzese.bem5w3d5.exceptions;

public class NotFoundException extends RuntimeException {

    // Costruttore per creare un'eccezione con un ID non trovato
    public NotFoundException(int id) {
        super("Elemento con id " + id + " non trovato!");
    }

    // Costruttore per creare un'eccezione con un messaggio personalizzato
    public NotFoundException(String message) {
        super(message);
    }
}
