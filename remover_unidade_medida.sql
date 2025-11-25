-- Remover coluna unidade_medida da tabela de produtos
USE saep_db;

ALTER TABLE tb_produto DROP COLUMN unidade_medida;
