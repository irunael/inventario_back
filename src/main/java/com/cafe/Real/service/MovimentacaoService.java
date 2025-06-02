package com.cafe.Real.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.Real.dto.MovimentacaoCreateDTO;
import com.cafe.Real.dto.MovimentacaoDTO;
import com.cafe.Real.entities.Movimentacao;
import com.cafe.Real.entities.Produto;
import com.cafe.Real.repository.MovimentacaoRepository;
import com.cafe.Real.repository.ProdutoRepository;

@Service
@Transactional
public class MovimentacaoService {

	private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueService estoqueService;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository,
                             ProdutoRepository produtoRepository,
                             EstoqueService estoqueService) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
        this.estoqueService = estoqueService;
    }

    public MovimentacaoDTO registrarMovimentacao(MovimentacaoCreateDTO movimentacaoCreateDTO) {
        Produto produto = produtoRepository.findById(movimentacaoCreateDTO.getProdutoId())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setTipo(movimentacaoCreateDTO.getTipo());
        movimentacao.setQuantidade(movimentacaoCreateDTO.getQuantidade());
        movimentacao.setDataHora(LocalDateTime.now());
        movimentacao.setOrigemDeposito(movimentacaoCreateDTO.getOrigemDeposito());
        movimentacao.setDestinoDeposito(movimentacaoCreateDTO.getDestinoDeposito());

        movimentacao = movimentacaoRepository.save(movimentacao);

        // Atualizar estoque
        boolean isEntrada = movimentacao.getTipo() == Movimentacao.TipoMovimentacao.ENTRADA;
        estoqueService.atualizarEstoque(produto.getId(), movimentacao.getQuantidade(), isEntrada);

        return new MovimentacaoDTO(movimentacao);
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoDTO> listarMovimentacoes() {
        return movimentacaoRepository.findAll()
            .stream()
            .map(MovimentacaoDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public String gerarRelatorio() {
        List<Movimentacao> movimentacoes = movimentacaoRepository.findAll();
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO DE MOVIMENTAÇÕES ===\n\n");
        
        long totalEntradas = movimentacoes.stream()
            .filter(m -> m.getTipo() == Movimentacao.TipoMovimentacao.ENTRADA)
            .count();
        
        long totalSaidas = movimentacoes.stream()
            .filter(m -> m.getTipo() == Movimentacao.TipoMovimentacao.SAIDA)
            .count();
        
        long totalTransferencias = movimentacoes.stream()
            .filter(m -> m.getTipo() == Movimentacao.TipoMovimentacao.TRANSFERENCIA)
            .count();
        
        relatorio.append("Total de Movimentações: ").append(movimentacoes.size()).append("\n");
        relatorio.append("Entradas: ").append(totalEntradas).append("\n");
        relatorio.append("Saídas: ").append(totalSaidas).append("\n");
        relatorio.append("Transferências: ").append(totalTransferencias).append("\n\n");
        
        relatorio.append("=== DETALHES ===\n");
        for (Movimentacao mov : movimentacoes) {
            relatorio.append(String.format(
                "ID: %d | Produto: %s | Tipo: %s | Qtd: %d | Data: %s\n",
                mov.getId(),
                mov.getProduto().getDescricao(),
                mov.getTipo(),
                mov.getQuantidade(),
                mov.getDataHora().toString()
            ));
        }
        
        return relatorio.toString();
    }
}