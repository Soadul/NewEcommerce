package com.example.NewEcommerce.config;

import com.example.NewEcommerce.Repository.RoleRepository;
import com.example.NewEcommerce.Repository.UserRepository;
import com.example.NewEcommerce.Service.CustomUserDetailService;
import com.example.NewEcommerce.model.Role;
import com.example.NewEcommerce.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@EnableWebSecurity
public class GoogleOauth2SuccessHandler implements AuthenticationSuccessHandler {
  @Autowired UserRepository userRepository;
  @Autowired RoleRepository roleRepository;
  @Autowired
  CustomUserDetailService customUserDetailService;

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String email = token.getPrincipal().getAttributes().get("email").toString();
        if(userRepository.finduserByEmail(email).isPresent()){

        }
        else{
          User user = new User();
          user.setFirstName(token.getPrincipal().getAttributes().get("givenName").toString());
          user.setLastName(token.getPrincipal().getAttributes().get("familyName").toString());
          user.setEmail(email);
          List<Role> roles = new ArrayList<>();
          roles.add(roleRepository.findById(2).get());
          user.setRoles(roles);
          userRepository.save(user);

        }
        redirectStrategy.sendRedirect(request, response, "/");
       // AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    }
}
