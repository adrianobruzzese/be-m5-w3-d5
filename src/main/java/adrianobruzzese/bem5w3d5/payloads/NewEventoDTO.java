package adrianobruzzese.bem5w3d5.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record NewEventoDTO(

        // Titolo dell'evento, obbligatorio, deve avere tra 3 e 30 caratteri
        @NotEmpty(message = "Il titolo è obbligatorio")
        @Size(min = 3, max = 30, message = "Il titolo deve essere compreso tra gli 3 e i 30 caratteri")
        String titolo,

        // Descrizione dell'evento, obbligatoria, deve avere tra 3 e 30 caratteri
        @NotEmpty(message = "La descrizione è obbligatoria")
        @Size(min = 3, max = 30, message = "La descrizione deve essere compresa tra i 3 e i 30 caratteri")
        String descrizione,

        // Data dell'evento, obbligatoria
        @NotNull(message = "La data è obbligatoria")
        LocalDate data,

        // Location dell'evento, obbligatoria
        @NotEmpty(message = "La location è obbligatoria")
        String location,

        // Capienza dell'evento, obbligatoria, minimo 10 e massimo 1000
        @Min(10)
        @Max(1000)
        int capienza
) {
}
