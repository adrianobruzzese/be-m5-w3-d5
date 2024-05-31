package adrianobruzzese.bem5w3d5.repositories;

import adrianobruzzese.bem5w3d5.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtentiDAO extends JpaRepository<Utente, Integer> {

    // Verifica se esiste un utente con l'email specificata
    boolean existsByEmail(String email);

    // Trova un utente per email
    Optional<Utente> findByEmail(String email);
}
