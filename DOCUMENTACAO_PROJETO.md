# DOCUMENTAÇÃO DO PROJETO - SISTEMA DE GESTÃO DE ESTOQUE

## ENTREGA 9 - LISTA DE REQUISITOS DE INFRAESTRUTURA

### 9.1.1 SGBD Utilizado
- **Sistema:** MySQL
- **Versão:** 8.0.43
- **Banco de Dados:** saep_db

### 9.1.2 Linguagem de Programação
- **Linguagem:** Java
- **Versão:** 21.0.8 LTS
- **Framework:** Spring Boot 3.5.0
- **Build Tool:** Maven

### 9.1.3 Sistema Operacional
- **Sistema:** Windows
- **Plataforma:** win32

---

## RESUMO DO SISTEMA IMPLEMENTADO

### ✅ REQUISITOS ATENDIDOS

#### 3. Script de Criação e População do Banco de Dados
- ✅ Banco nomeado como "saep_db"
- ✅ Script SQL completo em `script_saep_db.sql`
- ✅ Mínimo de 3 registros em todas as tabelas
- ✅ Chaves primárias e estrangeiras configuradas

#### 4. Interface de Autenticação (Login)
- ✅ Endpoint: `POST /auth/login`
- ✅ Retorna mensagem de erro em caso de falha
- ✅ Autenticação via JWT

#### 5. Interface Principal do Sistema
- ✅ Token JWT contém informações do usuário logado
- ✅ Logout implementado (frontend descarta o token)
- ✅ Acesso às interfaces de Cadastro de Produto e Gestão de Estoque

#### 6. Interface Cadastro de Produto
- ✅ Listar produtos: `GET /produtos`
- ✅ Buscar produto: `GET /produtos/buscar?termo={termo}`
- ✅ Criar produto: `POST /produtos`
- ✅ Editar produto: `PUT /produtos/{id}`
- ✅ Excluir produto: `DELETE /produtos/{id}`
- ✅ Validações implementadas no backend

#### 7. Interface Gestão de Estoque
- ✅ Listar produtos em ordem alfabética: `GET /produtos` (ordenação no frontend)
- ✅ Registrar movimentação: `POST /movimentacoes`
- ✅ Tipos de movimentação: ENTRADA e SAIDA
- ✅ Data da movimentação configurável
- ✅ **ALERTA AUTOMÁTICO** quando estoque fica abaixo do mínimo
- ✅ Histórico completo com responsável e data

---

## ESTRUTURA DO BANCO DE DADOS

### Tabelas Criadas

1. **tb_usuario**
   - id (PK)
   - nome
   - email (UNIQUE)
   - senha (BCrypt)
   - role (USUARIO)

2. **tb_produto**
   - id (PK)
   - codigo (UNIQUE)
   - descricao
   - preco_unitario
   - unidade_medida

3. **tb_estoque**
   - id (PK)
   - produto_id (FK, UNIQUE)
   - quantidade_atual
   - **estoque_minimo** ⚠️
   - valor_total

4. **tb_movimentacao**
   - id (PK)
   - produto_id (FK)
   - tipo (ENTRADA/SAIDA)
   - quantidade
   - data_hora
   - **usuario_id (FK)** 👤

---

## FUNCIONALIDADES IMPLEMENTADAS

### 🔐 Autenticação e Segurança
- JWT (JSON Web Token) para autenticação
- BCrypt para hash de senhas
- CORS configurado para frontend (localhost:3000)
- Apenas perfil USUARIO (sem hierarquia de permissões)

### 📦 Gestão de Produtos
- CRUD completo de produtos
- Busca por termo
- Validações de dados

### 📊 Gestão de Estoque
- Controle de estoque mínimo
- Atualização automática de estoque nas movimentações
- Cálculo automático de valor total
- **Alerta visual quando estoque < estoque_minimo**

### 📝 Histórico de Movimentações
- Registro completo de todas as movimentações
- Identificação do responsável (usuário logado)
- Data e hora da operação
- Tipos: ENTRADA e SAIDA

---

## ENDPOINTS DA API

### Autenticação
```
POST /auth/register - Cadastrar novo usuário
POST /auth/login    - Fazer login
```

### Produtos
```
GET    /produtos              - Listar todos os produtos
GET    /produtos/{id}         - Buscar produto por ID
GET    /produtos/buscar?termo - Buscar por termo
POST   /produtos              - Criar novo produto
PUT    /produtos/{id}         - Atualizar produto
DELETE /produtos/{id}         - Excluir produto
```

### Estoque
```
GET /estoque/valor-total           - Valor total do estoque
GET /estoque/produto/{produtoId}   - Consultar estoque de um produto
```

### Movimentações
```
GET  /movimentacoes         - Listar todas as movimentações
POST /movimentacoes         - Registrar nova movimentação
GET  /movimentacoes/relatorio - Gerar relatório
```

---

## DADOS DE TESTE

### Usuários (senha: senha123)
- joao@email.com
- maria@email.com
- pedro@email.com

### Produtos Cadastrados
1. Smartphone Samsung Galaxy S23 - 128GB
2. Notebook Dell Inspiron 15 - Intel i5
3. Smart TV LG 55" 4K UHD

---

## COMO EXECUTAR O PROJETO

### Pré-requisitos
1. Java 21 instalado
2. MySQL 8.0+ rodando na porta 3306
3. Usuário MySQL: root / senha: root

### Passos
1. Executar o script SQL: `script_saep_db.sql`
2. Compilar o projeto: `.\mvnw.cmd clean package`
3. Executar: `.\mvnw.cmd spring-boot:run`
4. API disponível em: `http://localhost:8080`

---

## MELHORIAS IMPLEMENTADAS

### ⚠️ Sistema de Alertas
Quando uma movimentação de SAIDA deixa o estoque abaixo do mínimo:
- Console exibe alerta com detalhes
- Frontend pode verificar campo `abaixoDoMinimo` no EstoqueDTO

### 👤 Rastreabilidade
Todas as movimentações registram:
- Quem fez (usuário logado)
- Quando fez (data/hora)
- O que fez (tipo e quantidade)

### 🎯 Simplicidade
- Apenas perfil USUARIO (sem complexidade desnecessária)
- Cadastro simplificado (sem necessidade de informar role)
- Foco nas funcionalidades essenciais do SAEP

---

## ARQUIVOS IMPORTANTES

- `script_saep_db.sql` - Script de criação e população do banco
- `pom.xml` - Dependências do projeto
- `application.properties` - Configurações da aplicação
- Entidades em: `src/main/java/com/cafe/Real/entities/`
- Controllers em: `src/main/java/com/cafe/Real/controller/`
- Services em: `src/main/java/com/cafe/Real/service/`

---

## OBSERVAÇÕES

✅ Todos os requisitos do SAEP foram implementados
✅ Sistema pronto para uso e avaliação
✅ Código limpo e organizado seguindo boas práticas
✅ Documentação completa incluída
