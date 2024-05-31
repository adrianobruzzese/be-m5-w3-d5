package adrianobruzzese.bem5w3d5.controllers;

import adrianobruzzese.bem5w3d5.entities.Evento;
import adrianobruzzese.bem5w3d5.exceptions.BadRequestException;
import adrianobruzzese.bem5w3d5.payloads.NewEventoDTO;
import adrianobruzzese.bem5w3d5.payloads.NewEventoRespDTO;
import adrianobruzzese.bem5w3d5.services.EventiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventiController {

    @Autowired
    private EventiService eventiService;

    // Crea un nuovo evento. Solo gli organizzatori possono creare eventi.
    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEventoRespDTO saveEvento(@RequestBody @Validated NewEventoDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new NewEventoRespDTO(this.eventiService.saveEvento(body).getId());
    }

    // Trova un evento specifico tramite ID.
    @GetMapping("/{eventoId}")
    public Evento findEventoById(@PathVariable int eventoId) {
        return this.eventiService.findById(eventoId);
    }

    // Ottiene una lista di tutti gli eventi.
    @GetMapping
    public List<Evento> getAllEvento() {
        return this.eventiService.getEventiList();
    }

    // Ottiene una pagina di eventi ordinati secondo specifici criteri.
    @GetMapping("/page")
    public Page<Evento> getAllEventi(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        return this.eventiService.getEventi(page, size, sortBy);
    }

    // Aggiorna un evento esistente. Solo gli organizzatori possono modificare eventi.
    @PutMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento findByIdAndUpdate(@PathVariable int eventoId, @RequestBody Evento body) {
        return this.eventiService.findByIdAndUpdate(eventoId, body);
    }

    // Elimina un evento specifico. Solo gli organizzatori possono cancellare eventi.
    @DeleteMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEventoById(@PathVariable int eventoId) {
        this.eventiService.findByIdAndDelete(eventoId);
    }

    // Prenota un posto a un evento. Solo i partecipanti possono prenotare.
    @PostMapping("/{eventoId}/prenota")
    @PreAuthorize("hasAuthority('PARTECIPANTE')")
    public ResponseEntity<String> prenotaPosto(@PathVariable int eventoId) {
        boolean prenotazioneRiuscita = eventiService.prenotaPosto(eventoId);
        if (prenotazioneRiuscita) {
            return ResponseEntity.ok("Prenotazione avvenuta con successo!");
        } else {
            return ResponseEntity.badRequest().body("Impossibile effettuare la prenotazione per questo evento. Posti esauriti.");
        }
    }
}
