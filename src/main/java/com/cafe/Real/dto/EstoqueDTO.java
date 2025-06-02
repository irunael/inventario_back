package com.cafe.Real.dto;

import java.math.BigDecimal;
import com.cafe.Real.entities.Estoque;

public class EstoqueDTO {

	private Long id;
    private ProdutoDTO produto;
    private Integer quantidadeAtual;
    private BigDecimal valorTotal;

    public EstoqueDTO() {}

    public EstoqueDTO(Estoque estoque) {
        this.id = estoque.getId();
        this.produto = new ProdutoDTO(estoque.getProduto());
        this.quantidadeAtual = estoque.getQuantidadeAtual();
        this.valorTotal = estoque.getValorTotal();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public ProdutoDTO getProduto() { return produto; }
    public void setProduto(ProdutoDTO produto) { this.produto = produto; }
    
    public Integer getQuantidadeAtual() { return quantidadeAtual; }
    public void setQuantidadeAtual(Integer quantidadeAtual) { this.quantidadeAtual = quantidadeAtual; }
    
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
}
