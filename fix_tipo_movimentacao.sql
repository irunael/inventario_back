-- Ajustar coluna tipo para aceitar TRANSFERENCIA
USE saep_db;

ALTER TABLE tb_movimentacao MODIFY COLUMN tipo VARCHAR(20) NOT NULL;
