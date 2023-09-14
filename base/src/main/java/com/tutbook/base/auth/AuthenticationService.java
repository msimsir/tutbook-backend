package com.tutbook.base.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tutbook.base.config.JwtService;
import com.tutbook.base.user.User;
import com.tutbook.base.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse signUp(SignUpRequest request) {
    var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
        .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).build();
    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken)._id(user.get_id()).email(user.getEmail())
        .firstName(user.getFirstName()).lastName(user.getLastName()).build();
  }

  public AuthenticationResponse signIn(SignInRequest request) {
    authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken)._id(user.get_id()).firstName(user.getFirstName())
        .lastName(user.getLastName()).build();
  }

}
