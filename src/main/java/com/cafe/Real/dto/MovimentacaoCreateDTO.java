package com.cafe.Real.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.cafe.Real.entities.Movimentacao;

public class MovimentacaoCreateDTO {

	private Long produtoId;
    private Movimentacao.TipoMovimentacao tipo;
    private Integer quantidade;
    private String origemDeposito;
    private String destinoDeposito;

    // Getters e Setters
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    
    public Movimentacao.TipoMovimentacao getTipo() { return tipo; }
    public void setTipo(Movimentacao.TipoMovimentacao tipo) { this.tipo = tipo; }
    
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    
    public String getOrigemDeposito() { return origemDeposito; }
    public void setOrigemDeposito(String origemDeposito) { this.origemDeposito = origemDeposito; }
    
    public String getDestinoDeposito() { return destinoDeposito; }
    public void setDestinoDeposito(String destinoDeposito) { this.destinoDeposito = destinoDeposito; }
}
