package com.cafe.Real.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.Real.dto.LoginRequestDTO;
import com.cafe.Real.dto.LoginResponseDTO;
import com.cafe.Real.dto.UsuarioCreateDTO;
import com.cafe.Real.dto.UsuarioDTO;
import com.cafe.Real.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = usuarioService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        UsuarioDTO usuarioDTO = usuarioService.criarUsuario(usuarioCreateDTO);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioDTO.getId())
                .toUri();
        
        return ResponseEntity.created(location).body(usuarioDTO);
    
