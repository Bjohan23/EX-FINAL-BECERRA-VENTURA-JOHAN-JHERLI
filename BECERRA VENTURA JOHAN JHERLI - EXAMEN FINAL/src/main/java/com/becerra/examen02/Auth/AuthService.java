package com.becerra.examen02.Auth;

import com.becerra.examen02.entity.Usuario;
import com.becerra.examen02.entity.Role;
import com.becerra.examen02.jwt.JwtService;
import com.becerra.examen02.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        try {
            Usuario user = userRepository.findByEmail(request.getUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return AuthResponse.builder()
                        .message("Contraseña incorrecta")
                        .token(null)
                        .tipoUsuario(null)
                        .build();
            }

            String token = jwtService.getToken(user);

            return AuthResponse.builder()
                    .message("Login exitoso")
                    .token(token)
                    .tipoUsuario(user.getRol().name())
                    .build();

        } catch (Exception e) {
            return AuthResponse.builder()
                    .message("Error en la autenticación: " + e.getMessage())
                    .token(null)
                    .tipoUsuario(null)
                    .build();
        }
    }

    public AuthResponse register(RegisterRequest request) {
        try {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return AuthResponse.builder()
                        .message("El email ya existe")
                        .token(null)
                        .tipoUsuario(null)
                        .build();
            }

            Usuario user = Usuario.builder()
                    .email(request.getEmail())  // Aquí está usando usuario en lugar de email
                    .password(passwordEncoder.encode(request.getPassword()))
                    .rol(request.getTipoUsuario().equals("ADMIN") ? Role.ADMIN : Role.USER)
                    .activo(true)
                    .build();

            userRepository.save(user);

            String token = jwtService.getToken(user);

            return AuthResponse.builder()
                    .message("Registro exitoso")
                    .token(token)
                    .tipoUsuario(user.getRol().name())
                    .build();

        } catch (Exception e) {
            return AuthResponse.builder()
                    .message("Error en el registro: " + e.getMessage())
                    .token(null)
                    .tipoUsuario(null)
                    .build();
        }
    }
}