package com.cafe.Real.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.cafe.Real.entities.Movimentacao;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MovimentacaoCreateDTO {

	private Long produtoId;
    private Movimentacao.TipoMovimentacao tipo;
    private Integer quantidade;
    private LocalDateTime dataHora;
    private LocalDate data;
    private Long usuarioId;
    private String responsavel;
    private String setorOrigem;
    private String setorDestino;

    // Getters e Setters
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    
    public Movimentacao.TipoMovimentacao getTipo() { return tipo; }
    public void setTipo(Movimentacao.TipoMovimentacao tipo) { this.tipo = tipo; }
    
    // Aceitar "tipoMovimentacao" como alias
    @JsonProperty("tipoMovimentacao")
    public void setTipoMovimentacao(String tipoMovimentacao) {
        // Normalizar o texto removendo acentos
        String normalizado = tipoMovimentacao.toUpperCase()
            .replace("Í", "I")
            .replace("Á", "A")
            .replace("É", "E")
            .replace("Ó", "O")
            .replace("Ú", "U")
            .replace("Ã", "A")
            .replace("Õ", "O")
            .replace("Ê", "E")
            .replace("Â", "A")
            .replace("Ô", "O");
        
        // Se vier "MOVIMENTAR", interpretar como SAIDA
        if ("MOVIMENTAR".equals(normalizado)) {
            normalizado = "SAIDA";
        }
        
        this.tipo = Movimentacao.TipoMovimentacao.valueOf(normalizado); 
    }
    
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { 
        this.data = data;
        // Converter data para dataHora
        if (data != null) {
            this.dataHora = data.atStartOfDay();
        }
    }
    
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    
    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
    
    public String getSetorOrigem() { return setorOrigem; }
    public void setSetorOrigem(String setorOrigem) { this.setorOrigem = setorOrigem; }
    
    public String getSetorDestino() { return setorDestino; }
    public void setSetorDestino(String setorDestino) { this.setorDestino = setorDestino; }
}
