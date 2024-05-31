package michelavivacqua.gestione.eventi.entities;

import jakarta.persistence.*;
import lombok.*;
import michelavivacqua.gestione.eventi.enums.Ruolo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Utente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;
    @ManyToMany
    @JoinTable(
            name = "prenotazioni_utente",
            joinColumns = @JoinColumn(name = "utente_id"),
            inverseJoinColumns = @JoinColumn(name = "evento_id")
    )
    private List<Evento> prenotazioni = new ArrayList<>();


    public Utente(String username, String nome, String cognome, String email, String password, Ruolo ruolo) {
        this.username=username;
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.ruolo=ruolo;
//        this.prenotazioni = new ArrayList<>();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}