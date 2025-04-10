package com.unipiloto.APPET_FT.services;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unipiloto.APPET_FT.models.Token;
import com.unipiloto.APPET_FT.repositories.TokenRepository;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String generarToken(String email) {
        int tokenInt = (int) (Math.random() * 900000) + 100000;
        String token = String.valueOf(tokenInt);

        Token verificarToken = new Token();
        verificarToken.setToken(token);
        verificarToken.setEmail(email);
        verificarToken.setExpiracionToken(LocalDateTime.now().plusMinutes(5));
        tokenRepository.save(verificarToken);
        return token;
    }

    public boolean verificarToken(String token, String correo) {
        Token verificarToken = tokenRepository.buscarPorToken(token, correo);
        if (verificarToken == null) {
            return false;
        }
        if (verificarToken.getExpiracionToken().isBefore(LocalDateTime.now())) {
            return false;
        }
        tokenRepository.eliminarToken(token);
        return true;
    }
}
