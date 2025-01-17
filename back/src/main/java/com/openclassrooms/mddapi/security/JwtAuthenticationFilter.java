package com.openclassrooms.mddapi.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.openclassrooms.mddapi.security.utils.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(
    JwtAuthenticationFilter.class
  );

  private final JwtTokenUtil jwtTokenUtil;
  private final CustomUserDetailsService userDetailsService;

  // Utiliser un constructeur avec tous les dépendances plutôt que @Autowired
  public JwtAuthenticationFilter(
    JwtTokenUtil jwtTokenUtil,
    CustomUserDetailsService userDetailsService
  ) {
    this.jwtTokenUtil = jwtTokenUtil;
    this.userDetailsService = userDetailsService;
  }

  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);

      // Déplacer la validation du token dans un bloc try-catch séparé
      if (jwt != null) {
        if (!jwtTokenUtil.validateToken(jwt)) {
          logger.warn("Invalid JWT token");
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.getWriter().write("Invalid JWT token");
          return;
        }

        String username = jwtTokenUtil.getUsernameFromToken(jwt);
        if (username != null) {
          UserDetails userDetails = userDetailsService.loadUserByUsername(
            username
          );

          // Vérifier si l'utilisateur est toujours valide
          if (userDetails != null && userDetails.isEnabled()) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities()
            );
            authentication.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder
              .getContext()
              .setAuthentication(authentication);
          }
        }
      }

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      logger.error("Authentication error: {}", e.getMessage());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Authentication error: " + e.getMessage());
    }
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    // Amélioration de la validation du header
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      try {
        return headerAuth.substring(7);
      } catch (StringIndexOutOfBoundsException e) {
        logger.warn("Malformed Authorization header");
        return null;
      }
    }
    return null;
  }
}
