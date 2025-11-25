package com.cafe.Real.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe.Real.dto.ProdutoCreateDTO;
import com.cafe.Real.dto.ProdutoDTO;
import com.cafe.Real.entities.Estoque;
import com.cafe.Real.entities.Produto;
import com.cafe.Real.repository.EstoqueRepository;
import com.cafe.Real.repository.ProdutoRepository;

@Service
@Transactional
public class ProdutoService {

	 private final ProdutoRepository produtoRepository;
	    private final EstoqueRepository estoqueRepository;

	    public ProdutoService(ProdutoRepository produtoRepository, EstoqueRepository estoqueRepository) {
	        this.produtoRepository = produtoRepository;
	        this.estoqueRepository = estoqueRepository;
	    }

	    public ProdutoDTO criarProduto(ProdutoCreateDTO produtoCreateDTO) {
	        Produto produto = new Produto();
	        produto.setDescricao(produtoCreateDTO.getDescricao());
	        produto.setPrecoUnitario(produtoCreateDTO.getPrecoUnitario());
	        produto.setCategoria(produtoCreateDTO.getCategoria());
	        produto.setSetor(produtoCreateDTO.getSetor());

	        produto = produtoRepository.save(produto);

	        // Criar registro de estoque inicial
	        Integer quantidadeInicial = produtoCreateDTO.getQuantidadeInicial() != null ? 
	            produtoCreateDTO.getQuantidadeInicial() : 0;
	        Integer estoqueMinimo = produtoCreateDTO.getEstoqueMinimo() != null ? 
	            produtoCreateDTO.getEstoqueMinimo() : 5;
	            
	        Estoque estoque = new Estoque();
	        estoque.setProduto(produto);
	        estoque.setQuantidadeAtual(quantidadeInicial);
	        estoque.setEstoqueMinimo(estoqueMinimo);
	        
	        BigDecimal valorTotal = BigDecimal.valueOf(produto.getPrecoUnitario())
	            .multiply(BigDecimal.valueOf(quantidadeInicial));
	        estoque.setValorTotal(valorTotal);
	        
	        estoqueRepository.save(estoque);

	        // Retornar DTO com informações de estoque
	        ProdutoDTO dto = new ProdutoDTO(produto);
	        dto.setQuantidade(quantidadeInicial);
	        dto.setEstoqueMinimo(estoqueMinimo);
	        return dto;
	    }

	    @Transactional(readOnly = true)
	    public List<ProdutoDTO> listarProdutos() {
	        // Listar apenas produtos ativos
	        return produtoRepository.findByAtivoTrue()
	            .stream()
	            .map(produto -> {
	                ProdutoDTO dto = new ProdutoDTO(produto);
	                // Buscar informações de estoque
	                estoqueRepository.findByProdutoId(produto.getId()).ifPresent(estoque -> {
	                    dto.setQuantidade(estoque.getQuantidadeAtual());
	                    dto.setEstoqueMinimo(estoque.getEstoqueMinimo());
	                });
	                return dto;
	            })
	            .collect(Collectors.toList());
	    }

	    public ProdutoDTO atualizarProduto(Long id, ProdutoCreateDTO produtoCreateDTO) {
	        Produto produto = produtoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

	        // Atualizar campos do produto
	        if (produtoCreateDTO.getDescricao() != null) {
	            produto.setDescricao(produtoCreateDTO.getDescricao());
	        }
	        if (produtoCreateDTO.getPrecoUnitario() != null) {
	            produto.setPrecoUnitario(produtoCreateDTO.getPrecoUnitario());
	        }
	        if (produtoCreateDTO.getCategoria() != null) {
	            produto.setCategoria(produtoCreateDTO.getCategoria());
	        }
	        if (produtoCreateDTO.getSetor() != null) {
	            produto.setSetor(produtoCreateDTO.getSetor());
	        }

	        produto = produtoRepository.save(produto);
	        
	        // Atualizar estoque
	        Estoque estoque = estoqueRepository.findByProdutoId(id)
	            .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
	        
	        // Aceitar tanto quantidadeInicial quanto quantidade
	        Integer novaQuantidade = produtoCreateDTO.getQuantidadeInicial();
	        if (novaQuantidade != null) {
	            estoque.setQuantidadeAtual(novaQuantidade);
	            BigDecimal valorTotal = BigDecimal.valueOf(produto.getPrecoUnitario())
	                .multiply(BigDecimal.valueOf(novaQuantidade));
	            estoque.setValorTotal(valorTotal);
	        }
	        
	        if (produtoCreateDTO.getEstoqueMinimo() != null) {
	            estoque.setEstoqueMinimo(produtoCreateDTO.getEstoqueMinimo());
	        }
	        
	        estoqueRepository.save(estoque);
	        
	        ProdutoDTO dto = new ProdutoDTO(produto);
	        dto.setQuantidade(estoque.getQuantidadeAtual());
	        dto.setEstoqueMinimo(estoque.getEstoqueMinimo());
	        return dto;
	    }

	    public void removerProduto(Long id) {
	        Produto produto = produtoRepository.findByIdAndAtivoTrue(id)
	            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

	        // Exclusão lógica - marcar como inativo
	        produto.setAtivo(false);
	        produtoRepository.save(produto);
	        
	        System.out.println("✓ Produto ID " + id + " marcado como inativo (exclusão lógica)");
	    }

	    @Transactional(readOnly = true)
	    public ProdutoDTO buscarPorId(Long id) {
	        // Buscar apenas produtos ativos
	        Produto produto = produtoRepository.findByIdAndAtivoTrue(id)
	            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
	        ProdutoDTO dto = new ProdutoDTO(produto);
	        // Buscar informações de estoque
	        estoqueRepository.findByProdutoId(id).ifPresent(estoque -> {
	            dto.setQuantidade(estoque.getQuantidadeAtual());
	            dto.setEstoqueMinimo(estoque.getEstoqueMinimo());
	        });
	        return dto;
	    }
	}