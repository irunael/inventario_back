package com.cafe.Real.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_estoque")
public class Estoque {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @OneToOne
	    @JoinColumn(name = "produto_id", nullable = false, unique = true)
	    private Produto produto;
	    
	    @Column(name = "quantidade_atual", nullable = false)
	    private Integer quantidadeAtual;
	    
	    @Column(name = "valor_total", nullable = false)
	    private BigDecimal valorTotal;
	    
	    // Getters e Setters
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Produto getProduto() {
	        return produto;
	    }

	    public void setProduto(Produto produto) {
	        this.produto = produto;
	    }

	    public Integer getQuantidadeAtual() {
	        return quantidadeAtual;
	    }

	    public void setQuantidadeAtual(Integer quantidadeAtual) {
	        this.quantidadeAtual = quantidadeAtual;
	    }

	    public BigDecimal getValorTotal() {
	        return valorTotal;
	    }

	    public void setValorTotal(BigDecimal valorTotal) {
	        this.valorTotal = valorTotal;
	    }
}
