package adrianobruzzese.bem5w3d5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import adrianobruzzese.bem5w3d5.enums.Ruolo;

public record NewUtenteDTO(

        // Username dell'utente, obbligatorio, deve avere tra 3 e 30 caratteri
        @NotEmpty(message = "È obbligatorio avere un username")
        @Size(min = 3, max = 30, message = "L'username deve essere compreso tra gli 3 e i 30 caratteri")
        String username,

        // Nome dell'utente, obbligatorio, deve avere tra 3 e 30 caratteri
        @NotEmpty(message = "Il nome è obbligatorio")
        @Size(min = 3, max = 30, message = "Il nome deve essere compreso tra i 3 e i 30 caratteri")
        String nome,

        // Cognome dell'utente, obbligatorio, deve avere tra 3 e 30 caratteri
        @NotEmpty(message = "Il cognome è obbligatorio")
        @Size(min = 3, max = 30, message = "Il cognome deve essere compreso tra i 3 e i 30 caratteri")
        String cognome,

        // Email dell'utente, obbligatoria e deve essere valida
        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "L'email inserita non è valida")
        String email,

        // Password dell'utente, obbligatoria
        @NotEmpty(message = "La password è obbligatoria")
        String password,

        // Ruolo dell'utente
        Ruolo ruolo
) {
}
