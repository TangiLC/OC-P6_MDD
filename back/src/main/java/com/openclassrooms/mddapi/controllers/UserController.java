package com.openclassrooms.mddapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.auth.JwtResponse;
import com.openclassrooms.mddapi.dto.auth.LoginRequest;
import com.openclassrooms.mddapi.dto.auth.RegisterRequest;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.security.UserPrincipal;
import com.openclassrooms.mddapi.security.utils.JwtTokenUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class UserController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserController(
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

  /**
   * Authenticate a user using their username and password.
   *
   * @param loginRequest The login request containing the username and password.
   * @return A ResponseEntity containing a JWT token if authentication is successful.
   */
  @Operation(
    summary = "Authenticate a user and generate a JWT token.",
    description = "Allows a user to log in using their username or email and password," +
    " and returns a JWT token upon successful authentication.",
    security = {}
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Authentication successful",
        content = @Content(schema = @Schema(implementation = JwtResponse.class))
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Invalid username or password",
        content = @Content
      ),
    }
  )
  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(
    @RequestBody @Valid LoginRequest loginRequest
  ) {
    String login = loginRequest.getUserIdentity();
    User user = userRepository
      .findByUsernameOrEmail(login, login)
      .orElseThrow(() ->
        new UsernameNotFoundException("username or email unknown")
      );

    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        user.getUsername(),
        loginRequest.getPassword()
      )
    );

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String jwt = jwtTokenUtil.generateToken((UserPrincipal) userDetails);

    return ResponseEntity.ok(new JwtResponse(jwt));
  }

  /**
   * Register a new user.
   *
   * @param registerRequest The register request containing the user's details.
   * @return A ResponseEntity indicating success or failure of the registration process.
   */
  @Operation(
    summary = "Register a new user.",
    description = "Allows a new user to create an account by providing" +
    " their username, email, and password."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "User registered successfully",
        content = @Content
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid input or user already exists",
        content = @Content
      ),
    }
  )
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(
    @RequestBody @Valid RegisterRequest registerRequest
  ) {
    if (
      userRepository.existsByUsernameOrEmail(
        registerRequest.getUsername(),
        registerRequest.getEmail()
      )
    ) {
      return ResponseEntity
        .badRequest()
        .body("Username or email is already taken!");
    }

    User user = new User();
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setIsAdmin(false);

    userRepository.save(user);
    return ResponseEntity.ok("User registered successfully!");
  }
}
