package adrianobruzzese.bem5w3d5.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException {
    private final List<ObjectError> errorsList;

    // Costruttore per creare un'eccezione con un messaggio personalizzato
    public BadRequestException(String message) {
        super(message);
        this.errorsList = null;
    }

    // Costruttore per creare un'eccezione con una lista di errori di validazione
    public BadRequestException(List<ObjectError> errorsList) {
        super("Errore! " +
                "Se vuoi salvare un UTENTE: Tutti i campi sono obbligatori; Username, nome e cognome devono avere tra i 3 e i 30 caratteri, e il ruolo può essere ORGANIZZATORE o PARTECIPANTE" +
                " Se vuoi salvare un EVENTO: Tutti i campi sono obbligatori; Titolo e descrizione devono avere tra i 3 e i 30 caratteri, e la capienza deve essere di almeno 10 e massimo 1000");
        this.errorsList = errorsList;
    }
}
