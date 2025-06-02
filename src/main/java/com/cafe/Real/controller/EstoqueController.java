package com.cafe.Real.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.Real.dto.EstoqueDTO;
import com.cafe.Real.service.EstoqueService;

@RestController
@RequestMapping("/estoque")

public class EstoqueController {

	 private final EstoqueService estoqueService;

	    public EstoqueController(EstoqueService estoqueService) {
	        this.estoqueService = estoqueService;
	    }

	    @GetMapping
	    public ResponseEntity<BigDecimal> consultarValorTotalEstoque() {
	        BigDecimal valorTotal = estoqueService.calcularValorTotal();
	        return ResponseEntity.ok(valorTotal);
	    }

	    @GetMapping("/{produtoId}")
	    public ResponseEntity<EstoqueDTO> consultarEstoquePorProduto(@PathVariable Long produtoId) {
	        EstoqueDTO estoqueDTO = estoqueService.consultarEstoquePorProduto(produtoId);
	        return ResponseEntity.ok(estoqueDTO);
	    }
}
