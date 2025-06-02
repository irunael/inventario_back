package com.cafe.Real.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.Real.dto.LoginRequestDTO;
import com.cafe.Real.dto.LoginResponseDTO;
import com.cafe.Real.dto.UsuarioCreateDTO;
import com.cafe.Real.dto.UsuarioDTO;
import com.cafe.Real.entities.Usuario;
import com.cafe.Real.repository.UsuarioRepository;
import com.cafe.Real.security.JWTUtil;

@Service
@Transactional
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public UsuarioService(UsuarioRepository usuarioRepository, 
                         PasswordEncoder passwordEncoder,
                         AuthenticationManager authenticationManager,
                         JWTUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public UsuarioDTO criarUsuario(UsuarioCreateDTO usuarioCreateDTO) {
        if (usuarioRepository.existsByEmail(usuarioCreateDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioCreateDTO.getNome());
        usuario.setEmail(usuarioCreateDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioCreateDTO.getSenha()));
        usuario.setRole(usuarioCreateDTO.getRole());

        usuario = usuarioRepository.save(usuario);
        return new UsuarioDTO(usuario);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), 
                loginRequest.getSenha()
            )
        );

        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtUtil.generateToken(
            usuario.getEmail(), 
            usuario.getRole().name(), 
            usuario.getId()
        );

        return new LoginResponseDTO(token, new UsuarioDTO(usuario));
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
            .stream()
            .map(UsuarioDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UsuarioDTO(usuario);
    }
}