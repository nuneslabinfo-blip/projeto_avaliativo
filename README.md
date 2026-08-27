\# Biblioteca



Sistema de gerenciamento de livros desenvolvido em Java, com persistência de dados em banco PostgreSQL, usando Maven para gerenciamento de dependências.



\## Tema



Cadastro, listagem, busca, atualização e remoção de livros (CRUD) em uma biblioteca, com menu interativo via console.



\## Como rodar



1\. Crie o banco de dados `biblioteca` no PostgreSQL (via pgAdmin ou psql).



2\. Crie a tabela `livros`:



&#x20;  ## sql

&#x20;  CREATE TABLE livros (

&#x20;      id SERIAL PRIMARY KEY,

&#x20;      titulo VARCHAR(100) NOT NULL,

&#x20;      autor VARCHAR(100) NOT NULL

&#x20;  );

