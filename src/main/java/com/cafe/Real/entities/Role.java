package com.cafe.Real.entities;

public enum Role {

	ADMIN("Administrador"),
    OPERADOR("Operador de estoque"),
    CONSULTA("Consulta apenas");

    private final String descricao;

    Role(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
