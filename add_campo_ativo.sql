-- Adicionar campo ativo na tabela de produtos
USE saep_db;

ALTER TABLE tb_produto ADD COLUMN ativo BOOLEAN DEFAULT TRUE;
UPDATE tb_produto SET ativo = TRUE WHERE ativo IS NULL;
