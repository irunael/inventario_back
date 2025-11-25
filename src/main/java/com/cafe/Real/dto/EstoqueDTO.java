package com.cafe.Real.dto;

import java.math.BigDecimal;
import com.cafe.Real.entities.Estoque;

public class EstoqueDTO {

	private Long id;
    private ProdutoDTO produto;
    private Integer quantidadeAtual;
    private Integer estoqueMinimo;
    private BigDecimal valorTotal;
    private Boolean abaixoDoMinimo;

    public EstoqueDTO() {}

    public EstoqueDTO(Estoque estoque) {
        this.id = estoque.getId();
        this.produto = new ProdutoDTO(estoque.getProduto());
        this.quantidadeAtual = estoque.getQuantidadeAtual();
        this.estoqueMinimo = estoque.getEstoqueMinimo();
        this.valorTotal = estoque.getValorTotal();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public ProdutoDTO getProduto() { return produto; }
    public void setProduto(ProdutoDTO produto) { this.produto = produto; }
    
    public Integer getQuantidadeAtual() { return quantidadeAtual; }
    public void setQuantidadeAtual(Integer quantidadeAtual) { this.quantidadeAtual = quantidadeAtual; }
    
    public Integer getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(Integer estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }
    
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    
    public Boolean getAbaixoDoMinimo() { return abaixoDoMinimo; }
    public void setAbaixoDoMinimo(Boolean abaixoDoMinimo) { this.abaixoDoMinimo = abaixoDoMinimo; }
}
