package org.frv.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Roman V.F.
 * Date: 20.08.2021
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(403);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode()
                .put("error", true)
                .put("cause", "Forbidden.")
                .put("message", "You do not have the privileges to access this resource.");
        response.getWriter().write(node.toString());
    }
}
