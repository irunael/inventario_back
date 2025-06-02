package com.cafe.Real.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.Real.dto.EstoqueDTO;
import com.cafe.Real.entities.Estoque;
import com.cafe.Real.repository.EstoqueRepository;

@Service
@Transactional
public class EstoqueService {

	private final EstoqueRepository estoqueRepository;

    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularValorTotal() {
        BigDecimal valorTotal = estoqueRepository.calcularValorTotalEstoque();
        return valorTotal != null ? valorTotal : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public EstoqueDTO consultarEstoquePorProduto(Long produtoId) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
            .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto"));
        return new EstoqueDTO(estoque);
    }

    public void atualizarEstoque(Long produtoId, Integer quantidadeMovimentacao, boolean isEntrada) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
            .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto"));

        Integer novaQuantidade;
        if (isEntrada) {
            novaQuantidade = estoque.getQuantidadeAtual() + quantidadeMovimentacao;
        } else {
            novaQuantidade = estoque.getQuantidadeAtual() - quantidadeMovimentacao;
            if (novaQuantidade < 0) {
                throw new RuntimeException("Quantidade insuficiente em estoque");
            }
        }

        estoque.setQuantidadeAtual(novaQuantidade);
        
        // Calcular novo valor total
        BigDecimal precoUnitario = BigDecimal.valueOf(estoque.getProduto().getPrecoUnitario());
        BigDecimal novoValorTotal = precoUnitario.multiply(BigDecimal.valueOf(novaQuantidade));
        estoque.setValorTotal(novoValorTotal);

        estoqueRepository.save(estoque);
    }
}