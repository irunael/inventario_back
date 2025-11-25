package com.cafe.Real.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cafe.Real.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    java.util.List<Produto> findByAtivoTrue();
    Optional<Produto> findByIdAndAtivoTrue(Long id);
}
