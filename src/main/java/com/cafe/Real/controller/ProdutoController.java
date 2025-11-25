package com.cafe.Real.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cafe.Real.dto.ProdutoCreateDTO;
import com.cafe.Real.dto.ProdutoDTO;
import com.cafe.Real.service.ProdutoService;

@RestController
@RequestMapping("/produtos")

public class ProdutoController {

	private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<?> criarProduto(@RequestBody ProdutoCreateDTO produtoCreateDTO) {
        try {
            ProdutoDTO produtoDTO = produtoService.criarProduto(produtoCreateDTO);
            
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(produtoDTO.getId())
                    .toUri();
            
            return ResponseEntity.created(location).body(produtoDTO);
        } catch (Exception e) {
            e.printStackTrace(); // Log do erro no console
            return ResponseEntity.status(500).body(java.util.Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        List<ProdutoDTO> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProduto(@PathVariable Long id) {
        ProdutoDTO produtoDTO = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produtoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(
            @PathVariable Long id,
            @RequestBody ProdutoCreateDTO produtoCreateDTO) {
        try {
            ProdutoDTO produtoDTO = produtoService.atualizarProduto(id, produtoCreateDTO);
            return ResponseEntity.ok(produtoDTO);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.status(404).body(java.util.Map.of("error", e.getMessage()));
            }
            e.printStackTrace();
            return ResponseEntity.status(500).body(java.util.Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(java.util.Map.of("error", "Erro ao atualizar produto: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerProduto(@PathVariable Long id) {
        try {
            produtoService.removerProduto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.status(404).body(java.util.Map.of("error", e.getMessage()));
            }
            e.printStackTrace();
            return ResponseEntity.status(400).body(java.util.Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(java.util.Map.of("error", "Erro ao remover produto: " + e.getMessage()));
        }
    }
}
