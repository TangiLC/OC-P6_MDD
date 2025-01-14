package com.openclassrooms.mddapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

@Entity
@Data
@NoArgsConstructor
public class Theme {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 255)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @Column(length = 36)
  private String icon;

  @Column(length = 6)
  private String color;

  @ManyToMany(mappedBy = "themes")
  private Set<Article> articles = new HashSet<>();
}
