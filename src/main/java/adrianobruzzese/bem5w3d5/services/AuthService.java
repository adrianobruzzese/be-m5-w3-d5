package adrianobruzzese.bem5w3d5.services;

import adrianobruzzese.bem5w3d5.entities.Utente;
import adrianobruzzese.bem5w3d5.exceptions.UnauthorizedException;
import adrianobruzzese.bem5w3d5.payloads.UtenteLoginDTO;
import adrianobruzzese.bem5w3d5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    // Autentica l'utente e genera un token JWT
    public String authenticateUserAndGenerateToken(UtenteLoginDTO payload) {
        Utente utente = utentiService.findByEmail(payload.getEmail());
        // Verifica la password
        if (bcrypt.matches(payload.getPassword(), utente.getPassword())) {
            // Genera e ritorna il token
            return jwtTools.createToken(utente);
        } else {
            throw new UnauthorizedException("Credenziali non valide! Impossibile accedere.");
        }
    }
}
