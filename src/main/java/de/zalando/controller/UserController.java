package de.zalando.controller;

import de.zalando.dto.UserRegistrationRequest;
import de.zalando.dto.UserRegistrationResponse;
import de.zalando.exception.DuplicateUserException;
import de.zalando.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  UserService userService;

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
}
