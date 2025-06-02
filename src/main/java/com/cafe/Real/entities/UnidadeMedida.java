package com.cafe.Real.entities;

public enum UnidadeMedida {

	UNIDADE("un", "Unidade"),
    KILOGRAMA("kg", "Kilograma"),
    GRAMA("g", "Grama"),
    LITRO("l", "Litro"),
    MILILITRO("ml", "Mililitro"),
    METRO("m", "Metro"),
    CENTIMETRO("cm", "Centímetro"),
    CAIXA("cx", "Caixa"),
    PACOTE("pct", "Pacote");

    private final String sigla;
    private final String descricao;

    UnidadeMedida(String sigla, String descricao) {
        this.sigla = sigla;
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public String getDescricao() {
        return descricao;
    }
}
