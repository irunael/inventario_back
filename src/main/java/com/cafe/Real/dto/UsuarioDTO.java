package com.cafe.Real.dto;

import com.cafe.Real.entities.Usuario;

	public class UsuarioDTO {
	    private Long id;
	    private String nome;
	    private String email;
	    private String role;

	    public UsuarioDTO() {}

	    public UsuarioDTO(Usuario usuario) {
	        this.id = usuario.getId();
	        this.nome = usuario.getNome();
	        this.email = usuario.getEmail();
	        this.role = usuario.getRole().name();
	    }

	    // Getters e Setters
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }
	    
	    public String getNome() { return nome; }
	    public void setNome(String nome) { this.nome = nome; }
	    
	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }
	    
	    public String getRole() { return role; }
	    public void setRole(String role) { this.role = role; }
	}

