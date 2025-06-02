package com.cafe.Real.dto;

import com.cafe.Real.entities.Usuario;

public class UsuarioCreateDTO {

	private String nome;
    private String email;
    private String senha;
    private Usuario.Role role;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public Usuario.Role getRole() { return role; }
    public void setRole(Usuario.Role role) { this.role = role; }
}
