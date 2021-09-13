package org.frv.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Roman V.F.
 * Date: 20.08.2021
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode()
                .put("error", true)
                .put("cause", "Unauthorised.")
                .put("message", "You do not have the privileges to access this resource.");
        httpServletResponse.getWriter().write(node.toString());
    }
}
