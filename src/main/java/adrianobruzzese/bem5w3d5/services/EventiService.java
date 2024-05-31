package adrianobruzzese.bem5w3d5.services;

import adrianobruzzese.bem5w3d5.entities.Evento;
import adrianobruzzese.bem5w3d5.exceptions.NotFoundException;
import adrianobruzzese.bem5w3d5.payloads.NewEventoDTO;
import adrianobruzzese.bem5w3d5.repositories.EventiDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventiService {

    @Autowired
    private EventiDAO eventiDAO;

    public List<Evento> getEventiList() {
        return eventiDAO.findAll();
    }

    // Salva un nuovo evento
    public Evento saveEvento(NewEventoDTO newEventoDTO) {
        Evento evento = new Evento(newEventoDTO.getTitolo(), newEventoDTO.getDescrizione(), newEventoDTO.getData(), newEventoDTO.getLocation(), newEventoDTO.getCapienza());
        return eventiDAO.save(evento);
    }

    // Trova un evento per ID
    public Evento findById(int id) {
        return eventiDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento non trovato con id: " + id));
    }

    // Aggiorna un evento esistente
    public Evento findByIdAndUpdate(int id, Evento updatedEvento) {
        Evento found = findById(id);
        found.setTitolo(updatedEvento.getTitolo());
        found.setDescrizione(updatedEvento.getDescrizione());
        found.setData(updatedEvento.getData());
        found.setLocation(updatedEvento.getLocation());
        found.setCapienza(updatedEvento.getCapienza());
        return eventiDAO.save(found);
    }

    // Elimina un evento per ID
    public void findByIdAndDelete(int eventoId) {
        eventiDAO.deleteById(eventoId);
    }

    // Restituisce una pagina di eventi
    public Page<Evento> getEventi(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return eventiDAO.findAll(pageable);
    }

    // Prenota un posto a un evento
    @Transactional
    public boolean prenotaPosto(int eventoId) {
        Optional<Evento> optionalEvento = eventiDAO.findById(eventoId);
        if (optionalEvento.isPresent()) {
            Evento evento = optionalEvento.get();
            if (evento.getCapienza() > 0) {
                evento.setCapienza(evento.getCapienza() - 1);
                eventiDAO.save(evento);
                return true; // Prenotazione riuscita
            }
        }
        return false; // Prenotazione non riuscita
    }
}
