package adrianobruzzese.bem5w3d5.controllers;

import adrianobruzzese.bem5w3d5.exceptions.BadRequestException;
import adrianobruzzese.bem5w3d5.payloads.NewUtenteDTO;
import adrianobruzzese.bem5w3d5.payloads.NewUtenteRespDTO;
import adrianobruzzese.bem5w3d5.payloads.UtenteLoginDTO;
import adrianobruzzese.bem5w3d5.payloads.UtenteLoginRespDTO;
import adrianobruzzese.bem5w3d5.services.AuthService;
import adrianobruzzese.bem5w3d5.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UtentiService utentiService;

    // Endpoint per il login
    @PostMapping("/login")
    public UtenteLoginRespDTO login(@RequestBody UtenteLoginDTO payload) {
        // Autentica l'utente e genera un token JWT
        String token = this.authService.authenticateUserAndGenerateToken(payload);
        return new UtenteLoginRespDTO(token);
    }

    // Endpoint per la registrazione
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUtenteRespDTO saveUser(@RequestBody @Validated NewUtenteDTO body, BindingResult validation) {
        // Verifica la presenza di errori di validazione
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        // Salva il nuovo utente e ritorna l'ID dell'utente creato
        Long newUserId = this.utentiService.saveUtente(body).getId();
        return new NewUtenteRespDTO(newUserId);
    }
}
