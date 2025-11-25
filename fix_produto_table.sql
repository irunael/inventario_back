-- Script para remover a coluna codigo da tabela tb_produto
USE saep_db;

-- Remover a coluna codigo
ALTER TABLE tb_produto DROP COLUMN codigo;

-- Verificar a estrutura da tabela
DESCRIBE tb_produto;
