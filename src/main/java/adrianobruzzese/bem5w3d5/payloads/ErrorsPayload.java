package adrianobruzzese.bem5w3d5.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorsPayload {
    private String message;
    private LocalDateTime timestamp;

    // La classe ErrorsPayload è utilizzata se devo rappresentare un errore con un messaggio e un timestamp.
}
