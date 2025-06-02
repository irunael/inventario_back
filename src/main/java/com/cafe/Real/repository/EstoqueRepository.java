package com.cafe.Real.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.cafe.Real.entities.Estoque;
import java.math.BigDecimal;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByProdutoId(Long produtoId);
    
    @Query("SELECT SUM(e.valorTotal) FROM Estoque e")
    BigDecimal calcularValorTotalEstoque();
}