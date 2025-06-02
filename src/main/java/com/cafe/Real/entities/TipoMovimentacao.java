package com.cafe.Real.entities;

public enum TipoMovimentacao {

	ENTRADA("Entrada de mercadoria"),
    SAIDA("Saída de mercadoria"),
    TRANSFERENCIA("Transferência entre depósitos");

    private final String descricao;

    TipoMovimentacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
