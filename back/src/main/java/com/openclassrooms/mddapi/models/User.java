package com.openclassrooms.mddapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@Data
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(nullable = false, unique = true, length = 30)
  private String username;

  @Column(nullable = false, length = 255)
  private String password;

  @Column(length = 36)
  private String picture;

  @Column(nullable = false, name = "is_admin")
  private Boolean isAdmin;
}
