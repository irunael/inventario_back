-- Script para limpar usuários do banco saep_db
USE saep_db;

-- Limpar tabela de usuários
DELETE FROM tb_usuario;

-- Resetar o auto_increment
ALTER TABLE tb_usuario AUTO_INCREMENT = 1;

-- Verificar se está vazio
SELECT * FROM tb_usuario;
