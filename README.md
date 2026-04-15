# 🛡️ Sistema de Gestão da Guilda

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-API-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-Search-yellow)
![Docker](https://img.shields.io/badge/Docker-Container-blue)

---

## 📌 Visão geral

API REST desenvolvida para gerenciamento de uma guilda de aventureiros, permitindo controle de missões, participantes, companheiros e análises estratégicas.

O projeto foi construído com foco em:
- organização em camadas
- desempenho
- simulação de cenário real

---

## 🏗️ Estrutura do sistema

A aplicação está organizada por domínios:

- **aventura** → regras principais (aventureiros, missões, participações)
- **audit** → controle de usuários e organização
- **operacoes** → dados analíticos (painel tático)
- **busca** → integração com Elasticsearch

### 🔧 Camadas

- Controller → exposição da API
- Service → regras de negócio
- Repository → acesso ao banco
- DTO → estrutura de resposta

---

## ⚙️ Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Elasticsearch
- Docker / Docker Compose

---

## ⭐ Funcionalidades principais

- Cadastro e consulta de aventureiros
- Associação de companheiros
- Gerenciamento de missões
- Participação em missões
- Perfil completo do aventureiro
- Relatórios e ranking de participação
- Consulta de missões relevantes dos últimos 15 dias
- Busca avançada com Elasticsearch

---

## 🚀 Diferenciais

- Uso de DTOs para desacoplamento
- Queries derivadas e customizadas com JPA
- Cache com `@Cacheable` para otimização
- Consulta via materialized view
- Separação por domínio
- Integração com mecanismo de busca

---

## 📦 Pré-requisitos

Antes de executar, instale:

- Java 21+
- Maven
- Docker + Docker Compose

---

## 🐳 Execução com Docker

### ▶️ Subir ambiente

```bash
docker compose up --build
```

Isso irá subir:
- PostgreSQL
- Elasticsearch
- Aplicação Spring Boot

---

## ▶️ Executando manualmente

```bash
mvn clean package
mvn spring-boot:run
```

---

## 🌐 Acesso

http://localhost:8080

---

## 🗄️ Carga inicial de dados

Execute os scripts SQL na seguinte ordem:

1. aventureiros
2. companheiros
3. missoes
4. participacoes_missao

---

## 📡 Principais endpoints

### 🧙 Aventureiros

GET /aventura/aventureiros  
GET /aventura/aventureiros/{id}  
GET /aventura/aventureiros/buscar?nome=...

---

### ⚔️ Missões

GET /missoes  
GET /missoes/{id}  
GET /missoes/top15dias  

---

### 📊 Relatórios

GET /relatorios/ranking-participacao  
GET /relatorios/missoes-metricas  

---

### 🔎 Busca (Elasticsearch)

GET /produtos/busca/nome  
GET /produtos/busca/multicampos  
GET /produtos/busca/fuzzy  
GET /produtos/busca/faixa-preco  

---

## 📊 Regra do Top 15 dias

O endpoint:

GET /missoes/top15dias

retorna:

- apenas missões com atualização nos últimos 15 dias
- ordenadas por índice de prontidão em ordem decrescente
- limitado aos 10 primeiros registros
- baseado na materialized view

---

## ⚡ Cache

Para melhorar performance:

- foi aplicado `@Cacheable` na camada de serviço
- evita múltiplas execuções da mesma consulta
- reduz carga no banco
- melhora o tempo de resposta do endpoint

---

## ⚠️ Problemas comuns

### ❌ Banco não conecta
- verificar porta 5432
- verificar container ativo

### ❌ Elasticsearch não responde
- verificar porta 9200

### ❌ API sem dados
- executar scripts SQL

### ❌ Docker não sobe
- rodar `mvn clean package` antes

---

## 🧠 Observações

- A aplicação utiliza materialized view para consultas analíticas
- Não há modificação na estrutura do banco
- Separação clara entre dados operacionais e analíticos
- PostgreSQL para dados relacionais
- Elasticsearch para busca

---

## 👨‍💻 Autor

Projeto desenvolvido para fins acadêmicos com foco em backend.

---

## 📌 Status

- ✅ Funcional
- ✅ Testado
- 🚀 Pronto para execução

---

## 🏁 Conclusão

Este projeto demonstra a construção de uma API REST estruturada, com integração entre banco relacional, cache e mecanismo de busca.

A solução simula um ambiente real de backend, aplicando boas práticas de organização, desempenho e separação de responsabilidades.
