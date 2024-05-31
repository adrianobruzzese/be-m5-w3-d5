package adrianobruzzese.bem5w3d5.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Evento {

    // Identificatore univoco per l'evento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Titolo dell'evento
    private String titolo;

    // Descrizione dell'evento
    private String descrizione;

    // Data dell'evento
    private LocalDate data;

    // Luogo dell'evento
    private String location;

    // Capienza massima dell'evento
    private int capienza;

    // Costruttore personalizzato per creare un evento senza specificare l'ID (che sarà generato automaticamente)
    public Evento(String titolo, String descrizione, LocalDate data, String location, int capienza) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.location = location;
        this.capienza = capienza;
    }
}
