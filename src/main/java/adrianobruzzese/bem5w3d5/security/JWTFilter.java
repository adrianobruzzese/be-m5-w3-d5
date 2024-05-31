package adrianobruzzese.bem5w3d5.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import adrianobruzzese.bem5w3d5.entities.Utente;
import adrianobruzzese.bem5w3d5.exceptions.UnauthorizedException;
import adrianobruzzese.bem5w3d5.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UtentiService utentiService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Estrazione dell'header Authorization
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Inserisci il token nell'Authorization Header");
        }

        // Estrazione del token dall'header
        String accessToken = authHeader.substring(7);

        // Verifica della signature e della data di scadenza del token
        jwtTools.verifyToken(accessToken);

        // Recupero dell'utente dal database tramite l'ID estratto dal token
        String id = jwtTools.extractIdFromToken(accessToken);
        int utenteId = Integer.parseInt(id);
        Utente currentUtente = utentiService.findById(utenteId);

        // Associo l'utente alla richiesta corrente
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUtente, null, currentUtente.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Non applica il filtro per le rotte di autenticazione
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
