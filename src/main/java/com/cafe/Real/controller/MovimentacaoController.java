package com.cafe.Real.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cafe.Real.dto.MovimentacaoCreateDTO;
import com.cafe.Real.dto.MovimentacaoDTO;
import com.cafe.Real.service.MovimentacaoService;

@RestController
@RequestMapping("/movimentacoes")

public class MovimentacaoController {

	private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping
    public ResponseEntity<?> registrarMovimentacao(
            @RequestBody MovimentacaoCreateDTO movimentacaoCreateDTO) {
        try {
            MovimentacaoDTO movimentacaoDTO = movimentacaoService.registrarMovimentacao(movimentacaoCreateDTO);
            
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(movimentacaoDTO.getId())
                    .toUri();
            
            return ResponseEntity.created(location).body(movimentacaoDTO);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.status(404).body(java.util.Map.of("message", e.getMessage(), "status", 404));
            }
            if (e.getMessage().contains("Estoque insuficiente")) {
                return ResponseEntity.status(400).body(java.util.Map.of("message", e.getMessage(), "status", 400));
            }
            e.printStackTrace();
            return ResponseEntity.status(400).body(java.util.Map.of("message", e.getMessage(), "status", 400));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(java.util.Map.of("message", "Erro ao registrar movimentação: " + e.getMessage(), "status", 500));
        }
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoDTO>> listarMovimentacoes() {
        List<MovimentacaoDTO> movimentacoes = movimentacaoService.listarMovimentacoes();
        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/relatorio")
    public ResponseEntity<String> gerarRelatorio() {
        String relatorio = movimentacaoService.gerarRelatorio();
        return ResponseEntity.ok(relatorio);
    }
}
