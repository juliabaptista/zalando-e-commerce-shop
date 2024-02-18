package de.zalando.service;

import de.zalando.dto.UserRegistrationRequest;
import de.zalando.dto.UserRegistrationResponse;
import de.zalando.exception.DuplicateUserException;
import de.zalando.exception.UserNotFoundException;
import de.zalando.model.entities.User;
import de.zalando.model.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

public User getUserByEmail(String email) throws UserNotFoundException {
  Optional<User> optionalUser = userRepository.getUserByEmail(email);
  if (optionalUser.isPresent()) {
    return optionalUser.get();
  }
  else throw new UserNotFoundException("User not found!");
}

  public UserRegistrationResponse registerUser(UserRegistrationRequest request)
      throws DuplicateUserException {
    if (isEmailRegistered(request.getEmail())) {
      throw new DuplicateUserException("Email is already registered.");
    }

    User user = new User();
    user.setEmail(request.getEmail());
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(request.getRole());
    User savedUser = userRepository.save(user);

    return new UserRegistrationResponse(
        savedUser.getUserId(),
        savedUser.getFirstName(),
        savedUser.getLastName(),
        savedUser.getEmail(),
        savedUser.getRole());
  }

  public boolean isEmailRegistered(String email) {
    Optional<User> optionalUser = userRepository.getUserByEmail(email);
    return optionalUser.isPresent();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.getUserByEmail(username).get();
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(), user.getPassword(), user.getRole().getGrantedAuthorities());
  }
}
