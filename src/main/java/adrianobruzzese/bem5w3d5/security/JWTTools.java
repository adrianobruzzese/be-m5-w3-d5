package adrianobruzzese.bem5w3d5.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import adrianobruzzese.bem5w3d5.entities.Utente;
import adrianobruzzese.bem5w3d5.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String secret;

    // Crea un token JWT per un utente
    public String createToken(Utente utente) {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data di emissione del token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // Data di scadenza del token (7 giorni)
                .setSubject(String.valueOf(utente.getId())) // Imposta l'ID dell'utente come subject
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // Firma il token con il segreto
                .compact();
    }

    // Verifica la validità del token
    public void verifyToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token); // Il metodo parseClaimsJws lancia eccezioni in caso di token non valido
        } catch (Exception ex) {
            throw new UnauthorizedException("Token non valido!");
        }
    }

    // Estrae l'ID dell'utente dal token
    public String extractIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // Il subject contiene l'ID dell'utente
    }
}
