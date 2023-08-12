
package dev.ehyeon.SpringAndFirebaseAuthentication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import dev.ehyeon.SpringAndFirebaseAuthentication.error.ErrorCode;
import dev.ehyeon.SpringAndFirebaseAuthentication.error.ErrorResponse;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class FirebaseTokenFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final UserDetailsService customUserDetailsService;
    private final FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            setResponse(response, ErrorCode.INVALID_TOKEN.getStatus(),
                    ErrorCode.INVALID_TOKEN.getMessage());
            return;
        }

        FirebaseToken decodedToken;

        try {
            decodedToken = firebaseAuth.verifyIdToken(header.substring(7));
        } catch (FirebaseAuthException e) {
            setResponse(response, ErrorCode.INVALID_TOKEN.getStatus(), e.getAuthErrorCode().name());
            return;
        }

        UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(decodedToken.getUid() + " " + decodedToken.getEmail());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private void setResponse(HttpServletResponse response, int status, String message)
            throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);

        ErrorResponse errorResponse = new ErrorResponse(status, message);

        String json = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(json);
    }
}
