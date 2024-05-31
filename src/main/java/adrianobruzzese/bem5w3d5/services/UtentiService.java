package adrianobruzzese.bem5w3d5.services;

import adrianobruzzese.bem5w3d5.entities.Evento;
import adrianobruzzese.bem5w3d5.entities.Utente;
import adrianobruzzese.bem5w3d5.exceptions.BadRequestException;
import adrianobruzzese.bem5w3d5.exceptions.NotFoundException;
import adrianobruzzese.bem5w3d5.payloads.NewUtenteDTO;
import adrianobruzzese.bem5w3d5.repositories.EventiDAO;
import adrianobruzzese.bem5w3d5.repositories.UtentiDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UtentiService {

    @Autowired
    private UtentiDAO utentiDAO;

    @Autowired
    private EventiDAO eventiDAO;

    @Autowired
    private PasswordEncoder bcrypt;

    public List<Utente> getUtentiList() {
        return utentiDAO.findAll();
    }

    // Salva un nuovo utente
    public Utente saveUtente(NewUtenteDTO newUtenteDTO) {
        if (utentiDAO.existsByEmail(newUtenteDTO.getEmail())) {
            throw new BadRequestException("L'email " + newUtenteDTO.getEmail() + " è già in uso. Contatta l'assistenza se hai dimenticato la tua password.");
        }
        Utente utente = new Utente(newUtenteDTO.getUsername(), newUtenteDTO.getNome(), newUtenteDTO.getCognome(), newUtenteDTO.getEmail(), bcrypt.encode(newUtenteDTO.getPassword()), newUtenteDTO.getRuolo());
        return utentiDAO.save(utente);
    }

    // Trova un utente per ID
    public Utente findById(int id) {
        return utentiDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + id));
    }

    // Aggiorna un utente esistente
    public Utente findByIdAndUpdate(int id, Utente updatedUtente) {
        Utente found = findById(id);
        found.setNome(updatedUtente.getNome());
        found.setCognome(updatedUtente.getCognome());
        found.setUsername(updatedUtente.getUsername());
        found.setEmail(updatedUtente.getEmail());
        found.setPassword(bcrypt.encode(updatedUtente.getPassword()));
        found.setRuolo(updatedUtente.getRuolo());
        return utentiDAO.save(found);
    }

    // Elimina un utente per ID
    public void findByIdAndDelete(int utenteId) {
        utentiDAO.deleteById(utenteId);
    }

    // Restituisce una pagina di utenti
    public Page<Utente> getUtenti(int page, int size, String sortBy) {
        if (size > 70) size = 70;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return utentiDAO.findAll(pageable);
    }

    // Trova un utente per email
    public Utente findByEmail(String email) {
        return utentiDAO.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

    // Restituisce le prenotazioni di un utente
    @Transactional(readOnly = true)
    public List<Evento> getPrenotazioniUtente(int utenteId) {
        Optional<Utente> optionalUtente = utentiDAO.findById(utenteId);
        return optionalUtente.map(Utente::getPrenotazioni).orElse(Collections.emptyList());
    }

    // Annulla una prenotazione di un evento per un utente
    @Transactional
    public boolean annullaPrenotazione(int utenteId, int eventoId) {
        Optional<Utente> optionalUtente = utentiDAO.findById(utenteId);
        Optional<Evento> optionalEvento = eventiDAO.findById(eventoId);

        if (optionalUtente.isPresent() && optionalEvento.isPresent()) {
            Utente utente = optionalUtente.get();
            Evento evento = optionalEvento.get();

            // Rimuove l'evento dalle prenotazioni dell'utente
            boolean removed = utente.getPrenotazioni().remove(evento);
            if (removed) {
                evento.setCapienza(evento.getCapienza() + 1);
                utentiDAO.save(utente);
                eventiDAO.save(evento);
                return true;
            }
        }
        return false;
    }
}
