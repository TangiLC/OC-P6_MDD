package com.openclassrooms.mddapi.controllers;

import static java.time.LocalDateTime.now;

import com.openclassrooms.mddapi.dto.ErrorResponse;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.auth.JwtResponse;
import com.openclassrooms.mddapi.dto.auth.LoginRequest;
import com.openclassrooms.mddapi.dto.auth.RegisterRequest;
import com.openclassrooms.mddapi.security.UserPrincipal;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Validated
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

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
  @PostMapping("/auth/login")
  public ResponseEntity<?> authenticateUser(
    @RequestBody @Valid LoginRequest loginRequest
  ) {
    try {
      JwtResponse response = userService.authenticateUser(loginRequest);
      return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(
          new ErrorResponse(401, "Identifiants incorrects", now().toString())
        );
    }
  }

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
  @PostMapping("/auth/register")
  public ResponseEntity<?> registerUser(
    @RequestBody @Valid RegisterRequest registerRequest
  ) {
    try {
      userService.registerUser(registerRequest);
      return ResponseEntity.ok("User registered successfully!");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Operation(
    summary = "Retrieve the authenticated user's information.",
    description = "Fetches the details of the currently authenticated user."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "User information retrieved successfully",
        content = @Content(schema = @Schema(implementation = UserDto.class))
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Unauthorized access",
        content = @Content
      ),
    }
  )
  @Transactional
  @GetMapping("/me")
  public ResponseEntity<UserDto> getAuthenticatedUser(
    @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    if (userPrincipal == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    UserDto userDto = userService.getUserDetails(userPrincipal.getUsername());
    return ResponseEntity.ok(userDto);
  }
}
