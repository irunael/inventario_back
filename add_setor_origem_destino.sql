-- Adicionar colunas setor_origem e setor_destino na tabela de movimentações
USE saep_db;

ALTER TABLE tb_movimentacao 
ADD COLUMN setor_origem VARCHAR(255),
ADD COLUMN setor_destino VARCHAR(255);
