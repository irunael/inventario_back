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
	        if (produtoRepository.existsByCodigo(produtoCreateDTO.getCodigo())) {
	            throw new RuntimeException("Código do produto já existe");
	        }

	        Produto produto = new Produto();
	        produto.setCodigo(produtoCreateDTO.getCodigo());
	        produto.setDescricao(produtoCreateDTO.getDescricao());
	        produto.setPrecoUnitario(produtoCreateDTO.getPrecoUnitario());
	        produto.setUnidadeMedida(produtoCreateDTO.getUnidadeMedida());

	        produto = produtoRepository.save(produto);

	        // Criar registro de estoque inicial
	        Estoque estoque = new Estoque();
	        estoque.setProduto(produto);
	        estoque.setQuantidadeAtual(0);
	        estoque.setValorTotal(BigDecimal.ZERO);
	        estoqueRepository.save(estoque);

	        return new ProdutoDTO(produto);
	    }

	    @Transactional(readOnly = true)
	    public List<ProdutoDTO> listarProdutos() {
	        return produtoRepository.findAll()
	            .stream()
	            .map(ProdutoDTO::new)
	            .collect(Collectors.toList());
	    }

	    public ProdutoDTO atualizarProduto(Long id, ProdutoCreateDTO produtoCreateDTO) {
	        Produto produto = produtoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

	        // Verificar se o código não está sendo usado por outro produto
	        if (!produto.getCodigo().equals(produtoCreateDTO.getCodigo()) && 
	            produtoRepository.existsByCodigo(produtoCreateDTO.getCodigo())) {
	            throw new RuntimeException("Código do produto já existe");
	        }

	        produto.setCodigo(produtoCreateDTO.getCodigo());
	        produto.setDescricao(produtoCreateDTO.getDescricao());
	        produto.setPrecoUnitario(produtoCreateDTO.getPrecoUnitario());
	        produto.setUnidadeMedida(produtoCreateDTO.getUnidadeMedida());

	        produto = produtoRepository.save(produto);
	        return new ProdutoDTO(produto);
	    }

	    public void removerProduto(Long id) {
	        Produto produto = produtoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

	        // Verificar se há estoque
	        Estoque estoque = estoqueRepository.findByProdutoId(id).orElse(null);
	        if (estoque != null && estoque.getQuantidadeAtual() > 0) {
	            throw new RuntimeException("Não é possível remover produto com estoque");
	        }

	        if (estoque != null) {
	            estoqueRepository.delete(estoque);
	        }
	        
	        produtoRepository.delete(produto);
	    }

	    @Transactional(readOnly = true)
	    public ProdutoDTO buscarPorId(Long id) {
	        Produto produto = produtoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
	        return new ProdutoDTO(produto);
	    }
	}