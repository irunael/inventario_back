package com.cafe.Real.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.Real.dto.MovimentacaoCreateDTO;
import com.cafe.Real.dto.MovimentacaoDTO;
import com.cafe.Real.entities.Estoque;
import com.cafe.Real.entities.Movimentacao;
import com.cafe.Real.entities.Produto;
import com.cafe.Real.entities.Usuario;
import com.cafe.Real.repository.EstoqueRepository;
import com.cafe.Real.repository.MovimentacaoRepository;
import com.cafe.Real.repository.ProdutoRepository;
import com.cafe.Real.repository.UsuarioRepository;

@Service
@Transactional
public class MovimentacaoService {

	private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstoqueService estoqueService;
    private final EstoqueRepository estoqueRepository;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository,
                             ProdutoRepository produtoRepository,
                             UsuarioRepository usuarioRepository,
                             EstoqueService estoqueService,
                             EstoqueRepository estoqueRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.estoqueService = estoqueService;
        this.estoqueRepository = estoqueRepository;
    }

    public MovimentacaoDTO registrarMovimentacao(MovimentacaoCreateDTO movimentacaoCreateDTO) {
        // Validar produto
        Produto produto = produtoRepository.findById(movimentacaoCreateDTO.getProdutoId())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Validar quantidade
        if (movimentacaoCreateDTO.getQuantidade() == null || movimentacaoCreateDTO.getQuantidade() <= 0) {
            throw new RuntimeException("Quantidade deve ser maior que 0");
        }

        // Buscar ou criar usuário
        Usuario usuario;
        if (movimentacaoCreateDTO.getUsuarioId() != null) {
            usuario = usuarioRepository.findById(movimentacaoCreateDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        } else if (movimentacaoCreateDTO.getResponsavel() != null) {
            // Buscar usuário pelo nome ou criar um novo
            usuario = usuarioRepository.findByNome(movimentacaoCreateDTO.getResponsavel())
                .orElseGet(() -> {
                    Usuario novoUsuario = new Usuario();
                    novoUsuario.setNome(movimentacaoCreateDTO.getResponsavel());
                    novoUsuario.setEmail(movimentacaoCreateDTO.getResponsavel().toLowerCase().replace(" ", ".") + "@sistema.com");
                    novoUsuario.setSenha("senha123"); // Senha padrão
                    return usuarioRepository.save(novoUsuario);
                });
        } else {
            throw new RuntimeException("Responsável ou usuarioId é obrigatório");
        }

        // Validar estoque para SAIDA
        if (movimentacaoCreateDTO.getTipo() == Movimentacao.TipoMovimentacao.SAIDA) {
            Estoque estoque = estoqueRepository.findByProdutoId(produto.getId())
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
            
            if (estoque.getQuantidadeAtual() < movimentacaoCreateDTO.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente. Disponível: " + 
                    estoque.getQuantidadeAtual() + ", Solicitado: " + movimentacaoCreateDTO.getQuantidade());
            }
        }

        // Validar e processar TRANSFERENCIA
        if (movimentacaoCreateDTO.getTipo() == Movimentacao.TipoMovimentacao.TRANSFERENCIA) {
            if (movimentacaoCreateDTO.getSetorOrigem() == null || movimentacaoCreateDTO.getSetorDestino() == null) {
                throw new RuntimeException("Setor de origem e destino são obrigatórios para transferência");
            }
            
            if (movimentacaoCreateDTO.getSetorOrigem().equals(movimentacaoCreateDTO.getSetorDestino())) {
                throw new RuntimeException("Setor de origem e destino não podem ser iguais");
            }
            
            if (!produto.getSetor().equals(movimentacaoCreateDTO.getSetorOrigem())) {
                throw new RuntimeException("Setor de origem deve ser igual ao setor do produto");
            }
            
            // Validar estoque disponível
            Estoque estoqueOrigem = estoqueRepository.findByProdutoId(produto.getId())
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
            
            if (estoqueOrigem.getQuantidadeAtual() < movimentacaoCreateDTO.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente. Disponível: " + 
                    estoqueOrigem.getQuantidadeAtual() + ", Solicitado: " + movimentacaoCreateDTO.getQuantidade());
            }
            
            // Processar transferência
            processarTransferencia(produto, movimentacaoCreateDTO.getQuantidade(), 
                movimentacaoCreateDTO.getSetorDestino());
        }

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setTipo(movimentacaoCreateDTO.getTipo());
        movimentacao.setQuantidade(movimentacaoCreateDTO.getQuantidade());
        movimentacao.setDataHora(movimentacaoCreateDTO.getDataHora() != null ? 
            movimentacaoCreateDTO.getDataHora() : LocalDateTime.now());
        movimentacao.setUsuario(usuario);
        
        // Preencher setorOrigem e setorDestino conforme o tipo de movimentação
        if (movimentacaoCreateDTO.getTipo() == Movimentacao.TipoMovimentacao.TRANSFERENCIA) {
            // Para transferência, usar os valores enviados
            movimentacao.setSetorOrigem(movimentacaoCreateDTO.getSetorOrigem());
            movimentacao.setSetorDestino(movimentacaoCreateDTO.getSetorDestino());
        } else {
            // Para ENTRADA e SAIDA, setorOrigem = setor do produto (rastreabilidade)
            movimentacao.setSetorOrigem(produto.getSetor());
            movimentacao.setSetorDestino(null);
        }

        movimentacao = movimentacaoRepository.save(movimentacao);

        // Atualizar estoque (ENTRADA aumenta, SAIDA diminui, TRANSFERENCIA já foi processada)
        if (movimentacao.getTipo() != Movimentacao.TipoMovimentacao.TRANSFERENCIA) {
            boolean isEntrada = movimentacao.getTipo() == Movimentacao.TipoMovimentacao.ENTRADA;
            estoqueService.atualizarEstoque(produto.getId(), movimentacao.getQuantidade(), isEntrada);
        }

        // Verificar estoque mínimo após saída
        if (movimentacao.getTipo() == Movimentacao.TipoMovimentacao.SAIDA) {
            Estoque estoque = estoqueRepository.findByProdutoId(produto.getId())
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
            
            if (estoque.getQuantidadeAtual() < estoque.getEstoqueMinimo()) {
                System.out.println("⚠️ ALERTA: Estoque do produto '" + produto.getDescricao() + 
                    "' está abaixo do mínimo! Atual: " + estoque.getQuantidadeAtual() + 
                    " | Mínimo: " + estoque.getEstoqueMinimo());
            }
        }

        return new MovimentacaoDTO(movimentacao);
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoDTO> listarMovimentacoes() {
        return movimentacaoRepository.findAll()
            .stream()
            .map(MovimentacaoDTO::new)
            .collect(Collectors.toList());
    }

    private void processarTransferencia(Produto produtoOrigem, Integer quantidade, String setorDestino) {
        // 1. Diminuir quantidade do produto de origem
        estoqueService.atualizarEstoque(produtoOrigem.getId(), quantidade, false);
        
        // 2. Buscar produto com mesma categoria no setor destino
        List<Produto> produtosDestino = produtoRepository.findAll().stream()
            .filter(p -> p.getCategoria() != null && 
                        p.getCategoria().equals(produtoOrigem.getCategoria()) && 
                        p.getSetor().equals(setorDestino))
            .collect(Collectors.toList());
        
        if (!produtosDestino.isEmpty()) {
            // 3a. Produto já existe no destino - aumentar quantidade
            Produto produtoDestino = produtosDestino.get(0);
            estoqueService.atualizarEstoque(produtoDestino.getId(), quantidade, true);
            
            System.out.println("✓ Transferência: Produto '" + produtoOrigem.getDescricao() + 
                "' transferido de '" + produtoOrigem.getSetor() + "' para '" + setorDestino + 
                "' (produto existente ID: " + produtoDestino.getId() + ")");
        } else {
            // 3b. Produto não existe no destino - criar novo
            Produto novoProduto = new Produto();
            novoProduto.setDescricao(produtoOrigem.getDescricao());
            novoProduto.setPrecoUnitario(produtoOrigem.getPrecoUnitario());
            novoProduto.setCategoria(produtoOrigem.getCategoria());
            novoProduto.setSetor(setorDestino);
            novoProduto.setAtivo(true);
            
            novoProduto = produtoRepository.save(novoProduto);
            
            // Criar estoque para o novo produto - copiar estoque mínimo do produto origem
            Estoque estoqueOrigem = estoqueRepository.findByProdutoId(produtoOrigem.getId())
                .orElseThrow(() -> new RuntimeException("Estoque origem não encontrado"));
            
            Estoque novoEstoque = new Estoque();
            novoEstoque.setProduto(novoProduto);
            novoEstoque.setQuantidadeAtual(quantidade);
            novoEstoque.setEstoqueMinimo(estoqueOrigem.getEstoqueMinimo()); // Copiar estoque mínimo do produto origem
            novoEstoque.setValorTotal(java.math.BigDecimal.valueOf(novoProduto.getPrecoUnitario())
                .multiply(java.math.BigDecimal.valueOf(quantidade)));
            
            estoqueRepository.save(novoEstoque);
            
            System.out.println("✓ Transferência: Novo produto criado no setor '" + setorDestino + 
                "' (ID: " + novoProduto.getId() + ") com " + quantidade + " unidades");
        }
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
        
        relatorio.append("Total de Movimentações: ").append(movimentacoes.size()).append("\n");
        relatorio.append("Entradas: ").append(totalEntradas).append("\n");
        relatorio.append("Saídas: ").append(totalSaidas).append("\n\n");
        
        relatorio.append("=== DETALHES ===\n");
        for (Movimentacao mov : movimentacoes) {
            relatorio.append(String.format(
                "ID: %d | Produto: %s | Tipo: %s | Qtd: %d | Data: %s | Responsável: %s\n",
                mov.getId(),
                mov.getProduto().getDescricao(),
                mov.getTipo(),
                mov.getQuantidade(),
                mov.getDataHora().toString(),
                mov.getUsuario().getNome()
            ));
        }
        
        return relatorio.toString();
    }
}
