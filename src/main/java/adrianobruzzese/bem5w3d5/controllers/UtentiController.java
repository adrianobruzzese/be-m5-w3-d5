package adrianobruzzese.bem5w3d5.controllers;

import adrianobruzzese.bem5w3d5.entities.Evento;
import adrianobruzzese.bem5w3d5.entities.Utente;
import adrianobruzzese.bem5w3d5.exceptions.BadRequestException;
import adrianobruzzese.bem5w3d5.payloads.NewUtenteDTO;
import adrianobruzzese.bem5w3d5.payloads.NewUtenteRespDTO;
import adrianobruzzese.bem5w3d5.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utenti")
public class UtentiController {

    @Autowired
    private UtentiService utentiService;

    // Crea un nuovo utente
    // POST http://localhost:3001/utenti
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewUtenteRespDTO saveUtente(@RequestBody @Validated NewUtenteDTO body, BindingResult validation) {
        // Verifica la presenza di errori di validazione
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        // Salva il nuovo utente e ritorna l'ID dell'utente creato
        return new NewUtenteRespDTO(this.utentiService.saveUtente(body).getId());
    }

    // Ottieni il profilo dell'utente autenticato
    // GET http://localhost:3001/utenti/me
    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return currentAuthenticatedUtente;
    }

    // Aggiorna il profilo dell'utente autenticato
    // PUT http://localhost:3001/utenti/me
    @PutMapping("/me")
    public Utente updateProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody Utente updatedUtente) {
        return this.utentiService.findByIdAndUpdate(currentAuthenticatedUtente.getId(), updatedUtente);
    }

    // Elimina il profilo dell'utente autenticato
    // DELETE http://localhost:3001/utenti/me
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        this.utentiService.findByIdAndDelete(currentAuthenticatedUtente.getId());
    }

    // Trova un utente specifico tramite ID
    // GET http://localhost:3001/utenti/{{utenteId}}
    @GetMapping("/{utenteId}")
    public Utente findUtenteById(@PathVariable int utenteId) {
        return this.utentiService.findById(utenteId);
    }

    // Ottieni una lista di tutti gli utenti. Solo gli organizzatori possono visualizzare tutti gli utenti.
    // GET http://localhost:3001/utenti
    @GetMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public List<Utente> getAllUtenti() {
        return this.utentiService.getUtentiList();
    }

    // Ottieni una pagina di utenti ordinati secondo specifici criteri
    // GET http://localhost:3001/utenti/page
    @GetMapping("/page")
    public Page<Utente> getAllUtenti(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        return this.utentiService.getUtenti(page, size, sortBy);
    }

    // Aggiorna un utente specifico tramite ID
    // PUT http://localhost:3001/utenti/{{utenteId}}
    @PutMapping("/{utenteId}")
    public Utente findByIdAndUpdate(@PathVariable int utenteId, @RequestBody Utente body) {
        return this.utentiService.findByIdAndUpdate(utenteId, body);
    }

    // Elimina un utente specifico tramite ID
    // DELETE http://localhost:3001/utenti/{utenteId}
    @DeleteMapping("/{utenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUtenteById(@PathVariable int utenteId) {
        this.utentiService.findByIdAndDelete(utenteId);
    }

    // Ottieni le prenotazioni di un utente specifico. Solo i partecipanti possono visualizzare le loro prenotazioni.
    // GET http://localhost:3001/utenti/{utenteId}/prenotazioni
    @GetMapping("/{utenteId}/prenotazioni")
    @PreAuthorize("hasAuthority('PARTECIPANTE')")
    public ResponseEntity<List<Evento>> getPrenotazioniUtente(@PathVariable int utenteId) {
        List<Evento> prenotazioni = utentiService.getPrenotazioniUtente(utenteId);
        return ResponseEntity.ok(prenotazioni);
    }

    // Annulla una prenotazione di un utente specifico. Solo i partecipanti possono annullare le loro prenotazioni.
    // DELETE http://localhost:3001/utenti/{utenteId}/prenotazioni/{eventoId}
    @DeleteMapping("/{utenteId}/prenotazioni/{eventoId}")
    @PreAuthorize("hasAuthority('PARTECIPANTE')")
    public ResponseEntity<String> annullaPrenotazione(@PathVariable int utenteId, @PathVariable int eventoId) {
        boolean annullamentoRiuscito = utentiService.annullaPrenotazione(utenteId, eventoId);
        if (annullamentoRiuscito) {
            return ResponseEntity.ok("Prenotazione annullata con successo!");
        } else {
            return ResponseEntity.badRequest().body("Impossibile annullare la prenotazione per questo evento.");
        }
    }
}
