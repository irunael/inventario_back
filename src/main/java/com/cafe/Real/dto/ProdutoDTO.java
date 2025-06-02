package com.cafe.Real.dto;

import java.math.BigDecimal;
import com.cafe.Real.entities.Produto;

public class ProdutoDTO {

	private Long id;
    private String codigo;
    private String descricao;
    private Double precoUnitario;
    private String unidadeMedida;

    public ProdutoDTO() {}

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.codigo = produto.getCodigo();
        this.descricao = produto.getDescricao();
        this.precoUnitario = produto.getPrecoUnitario();
        this.unidadeMedida = produto.getUnidadeMedida();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public Double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(Double precoUnitario) { this.precoUnitario = precoUnitario; }
    
    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }
}
