package adrianobruzzese.bem5w3d5.payloads;

public record UtenteLoginDTO(
        // Email dell'utente per il login
        String email,

        // Password dell'utente per il login
        String password
) {
}
