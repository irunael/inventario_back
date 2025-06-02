package com.cafe.Real.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cafe.Real.entities.Movimentacao;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByProdutoIdOrderByDataHoraDesc(Long produtoId);
    
    @Query("SELECT m FROM Movimentacao m WHERE m.dataHora BETWEEN :inicio AND :fim ORDER BY m.dataHora DESC")
    List<Movimentacao> findByDataHoraBetween(@Param("inicio") LocalDateTime inicio, 
                                           @Param("fim") LocalDateTime fim);
    
    List<Movimentacao> findByTipoOrderByDataHoraDesc(Movimentacao.TipoMovimentacao tipo);
}
