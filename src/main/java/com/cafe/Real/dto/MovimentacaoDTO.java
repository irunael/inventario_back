package com.cafe.Real.dto;

import java.time.LocalDateTime;
import com.cafe.Real.entities.Movimentacao;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MovimentacaoDTO {

	 private Long id;
	    private Long produtoId;
	    private String produtoDescricao;
	    private String tipoMovimentacao;
	    private Integer quantidade;
	    private String data;
	    private String responsavel;
	    private String setorOrigem;
	    private String setorDestino;

	    public MovimentacaoDTO() {}

	    public MovimentacaoDTO(Movimentacao movimentacao) {
	        this.id = movimentacao.getId();
	        this.produtoId = movimentacao.getProduto().getId();
	        this.produtoDescricao = movimentacao.getProduto().getDescricao();
	        this.tipoMovimentacao = movimentacao.getTipo().name();
	        this.quantidade = movimentacao.getQuantidade();
	        this.data = movimentacao.getDataHora().toLocalDate().toString();
	        this.responsavel = movimentacao.getUsuario().getNome();
	        this.setorOrigem = movimentacao.getSetorOrigem();
	        this.setorDestino = movimentacao.getSetorDestino();
	    }

	    // Getters e Setters
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }
	    
	    public Long getProdutoId() { return produtoId; }
	    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
	    
	    public String getProdutoDescricao() { return produtoDescricao; }
	    public void setProdutoDescricao(String produtoDescricao) { this.produtoDescricao = produtoDescricao; }
	    
	    public String getTipoMovimentacao() { return tipoMovimentacao; }
	    public void setTipoMovimentacao(String tipoMovimentacao) { this.tipoMovimentacao = tipoMovimentacao; }
	    
	    public Integer getQuantidade() { return quantidade; }
	    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
	    
	    public String getData() { return data; }
	    public void setData(String data) { this.data = data; }
	    
	    public String getResponsavel() { return responsavel; }
	    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
	    
	    public String getSetorOrigem() { return setorOrigem; }
	    public void setSetorOrigem(String setorOrigem) { this.setorOrigem = setorOrigem; }
	    
	    public String getSetorDestino() { return setorDestino; }
	    public void setSetorDestino(String setorDestino) { this.setorDestino = setorDestino; }
}
