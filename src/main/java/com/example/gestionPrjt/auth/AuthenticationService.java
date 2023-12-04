package com.example.gestionPrjt.auth;

import com.example.gestionPrjt.Config.JwtService;
import com.example.gestionPrjt.Models.*;
import com.example.gestionPrjt.Repo.TokenRepository;
import com.example.gestionPrjt.Repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private  final UserRepository userRepo ;
    private final PasswordEncoder passwordEncoder ;
    private  final JwtService jwtService ;
    private final AuthenticationManager authenticationManager;
    private TokenRepository tokenRepository ;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationResponse register(RegisterRequest request) {
        Role role = Role.valueOf(request.getRole().name().toUpperCase());
        logger.info("Role value: {}", role);
        User user;
        switch (role) {
            case ADMIN:
                user = new Admin();
                break;

            case MANAGER:
                user = new Manager();
                break;
            case COLLABORATOR:
                user = new Collaborator();
                break;
            default:
                // Handle unsupported roles
                throw new IllegalArgumentException("Invalid role specified.");
        }
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepo.save(user);
        userRepo.save(user);
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }



    // in case if the username or the password is not correct an exception will be thrown
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        var user=userRepo.findByEmail(request.getEmail()).orElseThrow();
        //once i get the user I will generate the token then return the authenticationResponse
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


}

