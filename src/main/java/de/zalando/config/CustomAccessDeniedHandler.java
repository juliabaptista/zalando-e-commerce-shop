package de.zalando.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      org.springframework.security.access.AccessDeniedException accessDeniedException
  ) throws IOException, ServletException {
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("error", "Access Denied - You do not have permission to access this resource.");
    response.setContentType("application/json");
    response.setStatus(HttpStatus.FORBIDDEN.value());
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(response.getWriter(), responseBody
    );
  }
}
