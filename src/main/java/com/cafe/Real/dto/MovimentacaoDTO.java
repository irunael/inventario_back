package com.cafe.Real.dto;

import java.time.LocalDateTime;
import com.cafe.Real.entities.Movimentacao;

public class MovimentacaoDTO {

	 private Long id;
	    private ProdutoDTO produto;
	    private String tipo;
	    private Integer quantidade;
	    private LocalDateTime dataHora;
	    private String origemDeposito;
	    private String destinoDeposito;

	    public MovimentacaoDTO() {}

	    public MovimentacaoDTO(Movimentacao movimentacao) {
	        this.id = movimentacao.getId();
	        this.produto = new ProdutoDTO(movimentacao.getProduto());
	        this.tipo = movimentacao.getTipo().name();
	        this.quantidade = movimentacao.getQuantidade();
	        this.dataHora = movimentacao.getDataHora();
	        this.origemDeposito = movimentacao.getOrigemDeposito();
	        this.destinoDeposito = movimentacao.getDestinoDeposito();
	    }

	    // Getters e Setters
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }
	    
	    public ProdutoDTO getProduto() { return produto; }
	    public void setProduto(ProdutoDTO produto) { this.produto = produto; }
	    
	    public String getTipo() { return tipo; }
	    public void setTipo(String tipo) { this.tipo = tipo; }
	    
	    public Integer getQuantidade() { return quantidade; }
	    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
	    
	    public LocalDateTime getDataHora() { return dataHora; }
	    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
	    
	    public String getOrigemDeposito() { return origemDeposito; }
	    public void setOrigemDeposito(String origemDeposito) { this.origemDeposito = origemDeposito; }
	    
	    public String getDestinoDeposito() { return destinoDeposito; }
	    public void setDestinoDeposito(String destinoDeposito) { this.destinoDeposito = destinoDeposito; }
}
