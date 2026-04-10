# Registro Oficial da Guilda de Aventureiros

Projeto final desenvolvido com Spring Boot reunindo os conteúdos construídos ao longo dos trabalhos da disciplina.

A aplicação consolida:

- camada web REST
- persistência relacional com JPA
- integração com banco legado
- domínio de aventura
- consultas operacionais e relatórios
- integração com Elasticsearch
- melhorias arquiteturais com cache

---

## Funcionalidades

### Núcleo legado (`audit`)
- Mapeamento das tabelas do schema `audit`
- Organizações
- Usuários
- Roles
- Permissions
- Relacionamento entre usuários e papéis
- Testes de mapeamento do banco legado

### Domínio de aventura (`aventura`)
- Aventureiros
- Companheiros
- Missões
- Participações em missão
- Consultas com filtros
- Busca por nome
- Detalhamento completo de aventureiro
- Detalhamento completo de missão
- Ranking de participação
- Relatório de missões com métricas

### Operações táticas (`operacoes`)
- Leitura da view `vw_painel_tatico_missao`
- Endpoint com top 10 missões dos últimos 15 dias
- Ordenação por índice de prontidão
- Uso de cache para otimização

### Busca e agregações (`Elasticsearch`)
- Busca por nome
- Busca por descrição
- Busca por frase
- Busca fuzzy
- Busca multicampos
- Busca com filtros
- Busca por faixa de preço
- Busca avançada
- Agregações por categoria
- Agregações por raridade
- Preço médio
- Faixas de preço

---

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Elasticsearch
- Maven
- Lombok
- JUnit 5

---

## Endpoint Principais
- http://localhost:8080/aventura/aventureiros
- http://localhost:8080/aventura/aventureiros/1
- http://localhost:8080/aventura/missoes
- http://localhost:8080/aventura/missoes/1
- http://localhost:8080/aventura/relatorios/ranking-participacao?inicio=2026-04-01T00:00:00Z&fim=2026-04-30T23:59:59Z
- http://localhost:8080/aventura/relatorios/missoes?inicio=2026-04-01T00:00:00Z&fim=2026-04-30T23:59:59Z
- http://localhost:8080/missoes/top15dias
- http://localhost:8080/produtos/agregacoes/preco-medio
