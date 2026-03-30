package com.ismail.chitchat.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ismail.chitchat.configs.JwtService;
import com.ismail.chitchat.dto.AuthenticationRequest;
import com.ismail.chitchat.dto.AuthenticationResponse;
import com.ismail.chitchat.dto.RegisterRequest;
import com.ismail.chitchat.entities.MyUser;
import com.ismail.chitchat.entities.Token;
import com.ismail.chitchat.entities.TokenType;
import com.ismail.chitchat.mappers.MyUserMapper;
import com.ismail.chitchat.repositories.MyUserRepository;
import com.ismail.chitchat.repositories.TokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final MyUserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }

        var user = MyUser.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .age(request.getAge())
                .role(com.ismail.chitchat.entities.Role.USER)
                .password(encoder.encode(request.getPassword()))
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        var user = repository.findByUsername(request.getUsername()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .user(MyUserMapper.toDTO(user))
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(MyUser user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public void logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        String jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt).orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
    }
}
