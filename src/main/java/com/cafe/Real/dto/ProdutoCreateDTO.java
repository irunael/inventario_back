package com.cafe.Real.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class ProdutoCreateDTO {

	private String descricao;
    private Double precoUnitario;
    private String categoria;
    private String setor;
    private Integer estoqueMinimo;
    private Integer quantidadeInicial;

    // Getters e Setters
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    // Aceitar "nome" como alias para "descricao"
    @JsonProperty("nome")
    public void setNome(String nome) { this.descricao = nome; }
    
    public Double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(Double precoUnitario) { this.precoUnitario = precoUnitario; }
    
    // Aceitar "preco" como alias para "precoUnitario"
    @JsonProperty("preco")
    public void setPreco(Double preco) { this.precoUnitario = preco; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }
    
    public Integer getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(Integer estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }
    
    public Integer getQuantidadeInicial() { return quantidadeInicial; }
    public void setQuantidadeInicial(Integer quantidadeInicial) { this.quantidadeInicial = quantidadeInicial; }
    
    // Aceitar "quantidade" como alias
    @JsonProperty("quantidade")
    public void setQuantidade(Integer quantidade) { this.quantidadeInicial = quantidade; }
}
