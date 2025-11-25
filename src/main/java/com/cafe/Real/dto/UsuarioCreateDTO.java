package com.cafe.Real.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioCreateDTO {

	@JsonProperty(value = "nome", access = JsonProperty.Access.WRITE_ONLY)
    private String nome;
    
    private String email;
    private String senha;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    @JsonProperty("nomeCompleto")
    public void setNomeCompleto(String nomeCompleto) { this.nome = nomeCompleto; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
