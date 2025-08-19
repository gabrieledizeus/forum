package alura.com.forum.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

public class SecurityFilter extends UsernamePasswordAuthenticationFilter {

    public SecurityFilter(AuthenticationManager authenticationManager) {
        // define o AuthenticationManager necessário
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/login"); // endpoint de login
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        if ("application/json".equals(request.getContentType())) {
            try {
                Map<String, String> credentials = new ObjectMapper()
                        .readValue(request.getInputStream(), Map.class);

                String username = credentials.get("username");
                String password = credentials.get("password");

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, password);

                return getAuthenticationManager().authenticate(authToken);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return super.attemptAuthentication(request, response);
    }
}
