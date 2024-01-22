package de.zalando.controller;

import de.zalando.dto.AuthenticationResponse;
import de.zalando.dto.LoginRequest;
import de.zalando.dto.UserRegistrationRequest;
import de.zalando.dto.UserRegistrationResponse;
import de.zalando.exception.DuplicateUserException;
import de.zalando.helper.JwtTokenProvider;
import de.zalando.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  UserService userService;
  AuthenticationManager authenticationManager;
  JwtTokenProvider jwtTokenProvider;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(
      @RequestBody UserRegistrationRequest registrationRequest
  ) {
    try {
      UserRegistrationResponse response = userService.registerUser(registrationRequest);
      return ResponseEntity.ok(response);

    } catch (DuplicateUserException e) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(
      @RequestBody LoginRequest loginRequest
  ) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getEmail(),
              loginRequest.getPassword()
          )
      );

      final String jwt = jwtTokenProvider.generateToken(loginRequest.getEmail());
      return ResponseEntity.ok(new AuthenticationResponse(jwt, "Successful login!"));

    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
    }
  }
}
