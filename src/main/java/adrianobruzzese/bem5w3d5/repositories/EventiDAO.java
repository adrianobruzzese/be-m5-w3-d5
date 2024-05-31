package adrianobruzzese.bem5w3d5.repositories;

import adrianobruzzese.bem5w3d5.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventiDAO extends JpaRepository<Evento, Integer> {

    // Verifica se esiste un evento con il titolo specificato
    boolean existsByTitolo(String titolo);

    // Trova un evento per titolo
    Optional<Evento> findByTitolo(String titolo);
    
}
