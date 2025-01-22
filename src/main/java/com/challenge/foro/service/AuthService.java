package com.challenge.foro.service;

import com.challenge.foro.dto.LoginDTO;
import com.challenge.foro.dto.RegistroDTO;
import com.challenge.foro.model.Usuario;
import com.challenge.foro.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public AuthService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegistroDTO registroDTO) {
        // Crear el nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setEmail(registroDTO.getEmail());
        usuario.setNombre(registroDTO.getNombre());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        // Guardar usuario en la base de datos
        usuarioRepository.save(usuario);

        // Retornar el token JWT
        return generateToken(usuario.getEmail());
    }

    public String login(LoginDTO loginDTO) {
        // Verificar que el usuario exista
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail());
        if (usuario != null && passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword())) {
            // Si la autenticación es exitosa, generar el token
            return generateToken(usuario.getEmail());
        } else {
            throw new RuntimeException("Credenciales incorrectas");
        }
    }

    private String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día de expiración
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}