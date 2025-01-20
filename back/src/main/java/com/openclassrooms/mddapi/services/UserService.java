package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.auth.JwtResponse;
import com.openclassrooms.mddapi.dto.auth.LoginRequest;
import com.openclassrooms.mddapi.dto.auth.RegisterRequest;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.security.UserPrincipal;
import com.openclassrooms.mddapi.security.utils.JwtTokenUtil;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(
    AuthenticationManager authenticationManager,
    JwtTokenUtil jwtTokenUtil,
    UserRepository userRepository,
    PasswordEncoder passwordEncoder
  ) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public JwtResponse authenticateUser(LoginRequest loginRequest) {
    String login = loginRequest.getUserIdentity();
    User user = userRepository
      .findByUsernameOrEmail(login, login)
      .orElseThrow(() -> new BadCredentialsException("Identifiants incorrects")
      );

    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        user.getUsername(),
        loginRequest.getPassword()
      )
    );

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String jwt = jwtTokenUtil.generateToken((UserPrincipal) userDetails);
    return new JwtResponse(jwt);
  }

  public void registerUser(RegisterRequest registerRequest) {
    if (
      userRepository.existsByUsernameOrEmail(
        registerRequest.getUsername(),
        registerRequest.getEmail()
      )
    ) {
      throw new IllegalArgumentException("Username or email is already taken!");
    }

    User user = new User();
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setIsAdmin(false);

    userRepository.save(user);
  }

  public UserDto getUserDetails(String username) {
    User user = userRepository
      .findByUsername(username)
      .orElseThrow(() ->
        new RuntimeException("User not found with username: " + username)
      );

    List<Long> themesIds = user.getThemes().stream().map(Theme::getId).toList();
    List<Long> commentsIds;
    commentsIds = user.getComments().stream().map(Comment::getId).toList();

    return new UserDto(
      user.getId(),
      user.getEmail(),
      user.getUsername(),
      user.getPicture(),
      themesIds,
      commentsIds
    );
  }
}
