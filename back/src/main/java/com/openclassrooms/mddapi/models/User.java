package com.openclassrooms.mddapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true, name = "id")
  private Long id;

  @Column(nullable = false, unique = true, length = 50, name = "email")
  private String email;

  @Column(nullable = false, unique = true, length = 30, name = "username")
  private String username;

  @Column(nullable = false, length = 255, name = "password")
  private String password;

  @Column(length = 36, name = "picture")
  private String picture;

  @Column(nullable = false, name = "is_admin")
  private Boolean isAdmin;
}
