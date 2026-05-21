# 🎓 Sistema Acadêmico - Java & MySQL

Projeto desenvolvido para a disciplina de Programação Orientada a Objetos (POO). Trata-se de um sistema completo de gestão acadêmica com interface gráfica (GUI) e integração com banco de dados relacional.

## 🚀 Funcionalidades
* **Navegação por Abas:** Interface fluida dividida em módulos, construída com `JTabbedPane`.
* **Validação de Dados:** Máscaras de entrada (`JFormattedTextField`) para formatação automática de CPF, Celular e Data de Nascimento.
* **Gestão de Alunos (CRUD):** Inserção, consulta, atualização e exclusão de cadastros.
* **Controle de Notas:** Lançamento de notas e faltas com vínculo referencial direto ao aluno.
* **Deleção em Cascata:** Regra de negócio no banco que remove automaticamente o histórico de notas caso o aluno seja excluído.
* **Boletim Dinâmico:** Geração de um relatório em tempo real, realizando junção de dados das tabelas de alunos e notas.

## 🛠️ Tecnologias Utilizadas
* **Java:** Lógica de programação e POO.
* **Java Swing:** Construção da Interface Gráfica nativa.
* **MySQL:** Armazenamento relacional dos dados.
* **JDBC (MySQL Connector):** Driver de conexão entre a aplicação e o banco.

---

## ⚙️ Como executar
Para testar o projeto sem precisar compilar o código, basta baixar o arquivo `.jar` na aba **Releases** ao lado e executá-lo com o Java instalado na máquina.

## ⚙️ Como configurar e rodar o Banco de Dados

Para que o sistema funcione perfeitamente na sua máquina, é necessário preparar o ambiente do banco de dados antes de executar o Java. Siga os passos abaixo:

### 1. Criar as Tabelas (Script SQL)
Abra o seu gerenciador do MySQL (como o MySQL Workbench) e execute o script abaixo para criar o banco e as tabelas com as regras de chave estrangeira:

```sql
-- Cria o banco de dados e coloca em uso
CREATE DATABASE sistema_academico;
USE sistema_academico;

-- Tabela principal: Alunos
CREATE TABLE alunos (
    rgm VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento VARCHAR(10),
    cpf VARCHAR(14) UNIQUE,
    email VARCHAR(100),
    endereco VARCHAR(150),
    municipio VARCHAR(100),
    uf CHAR(2),
    celular VARCHAR(15),
    curso VARCHAR(100),
    campus VARCHAR(100),
    periodo VARCHAR(20)
);

-- Tabela secundária: Notas e Faltas
CREATE TABLE notas_faltas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rgm_aluno VARCHAR(20) NOT NULL,
    disciplina VARCHAR(100),
    semestre VARCHAR(10),
    nota DECIMAL(4,2),
    faltas INT,
    FOREIGN KEY (rgm_aluno) REFERENCES alunos(rgm) ON DELETE CASCADE
);
