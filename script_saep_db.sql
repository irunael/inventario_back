-- Script de criação e população do banco de dados saep_db
-- SAEP - Sistema de Almoxarifado de Equipamentos Eletrônicos

-- Criar banco de dados
CREATE DATABASE IF NOT EXISTS saep_db;
USE saep_db;

-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS tb_usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Tabela de Produtos
CREATE TABLE IF NOT EXISTS tb_produto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    preco_unitario DOUBLE NOT NULL,
    unidade_medida VARCHAR(50) NOT NULL
);

-- Tabela de Estoque
CREATE TABLE IF NOT EXISTS tb_estoque (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_id BIGINT NOT NULL UNIQUE,
    quantidade_atual INT NOT NULL,
    estoque_minimo INT NOT NULL,
    valor_total DECIMAL(19,2) NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES tb_produto(id)
);

-- Tabela de Movimentações
CREATE TABLE IF NOT EXISTS tb_movimentacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    quantidade INT NOT NULL,
    data_hora DATETIME NOT NULL,
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES tb_produto(id),
    FOREIGN KEY (usuario_id) REFERENCES tb_usuario(id)
);

-- ========================================
-- POPULAÇÃO DO BANCO DE DADOS
-- ========================================

-- Inserir 3 usuários (senha: senha123 - BCrypt hash)
INSERT INTO tb_usuario (nome, email, senha, role) VALUES
('João Silva', 'joao@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USUARIO'),
('Maria Santos', 'maria@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USUARIO'),
('Pedro Costa', 'pedro@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USUARIO');

-- Inserir 3 produtos (equipamentos eletrônicos)
INSERT INTO tb_produto (descricao, preco_unitario, unidade_medida) VALUES
('Smartphone Samsung Galaxy S23 - 128GB - Tela 6.1" - 5G', 2999.90, 'UNIDADE'),
('Notebook Dell Inspiron 15 - Intel i5 - 8GB RAM - 256GB SSD', 3499.00, 'UNIDADE'),
('Smart TV LG 55" 4K UHD - WebOS - HDR - 60Hz', 2799.00, 'UNIDADE');

-- Inserir 3 registros de estoque
INSERT INTO tb_estoque (produto_id, quantidade_atual, estoque_minimo, valor_total) VALUES
(1, 15, 5, 44998.50),
(2, 8, 3, 27992.00),
(3, 12, 4, 33588.00);

-- Inserir 3 movimentações (histórico)
INSERT INTO tb_movimentacao (produto_id, tipo, quantidade, data_hora, usuario_id) VALUES
(1, 'ENTRADA', 20, '2024-11-20 10:30:00', 1),
(2, 'ENTRADA', 10, '2024-11-21 14:15:00', 2),
(1, 'SAIDA', 5, '2024-11-22 16:45:00', 3);

-- Verificar dados inseridos
SELECT 'Usuários cadastrados:' AS Info;
SELECT * FROM tb_usuario;

SELECT 'Produtos cadastrados:' AS Info;
SELECT * FROM tb_produto;

SELECT 'Estoque atual:' AS Info;
SELECT e.id, p.id AS produto_id, p.descricao, e.quantidade_atual, e.estoque_minimo, e.valor_total
FROM tb_estoque e
INNER JOIN tb_produto p ON e.produto_id = p.id;

SELECT 'Movimentações registradas:' AS Info;
SELECT m.id, p.id AS produto_id, p.descricao, m.tipo, m.quantidade, m.data_hora, u.nome AS responsavel
FROM tb_movimentacao m
INNER JOIN tb_produto p ON m.produto_id = p.id
INNER JOIN tb_usuario u ON m.usuario_id = u.id
ORDER BY m.data_hora DESC;
