package adrianobruzzese.bem5w3d5.payloads;

import java.time.LocalDateTime;

public record ErrorsResponseDTO(
        // Il messaggio di errore
        String message,

        // Il timestamp dell'errore
        LocalDateTime timestamp
) {
}
