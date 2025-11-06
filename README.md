# Catálogo de Livros - App Android com SQLite

**Instituição:** Fundação Universidade Federal de Rondônia (UNIR) (Inferido de `com.unir.bib_unir` e `R.drawable.unir`)
**Disciplina:** Programação para Dispositivos Móveis
**Professor:** Prof. Dr. Lucas Marques

---
<img width="1284" height="837" alt="image" src="https://github.com/user-attachments/assets/39306425-d788-48f5-8a1c-eb7799089bec" />

## 1. Descrição do Projeto

Este é um projeto acadêmico para a disciplina de Programação para Dispositivos Móveis. Consiste em um aplicativo Android para gerenciamento de um "Catálogo de Livros". O foco principal é a implementação da persistência de dados local usando o banco de dados SQLite nativo do Android.

A aplicação permite realizar as quatro operações básicas (CRUD - Create, Read, Update, Delete) em um banco de dados de livros, que armazena informações como título, autor e ano.

## 2. Funcionalidades

* **Cadastrar Livro:** Permite inserir um novo livro (título, autor, ano) no banco de dados.
* **Pesquisar Livros:** A tela principal oferece uma busca que pode ser filtrada por:
    * Todos os livros cadastrados.
    * Título do livro (busca parcial).
    * Ano de publicação.
* **Atualizar Livro:** Na tela principal, o usuário pode informar o ID de um livro para carregar seus dados na tela de cadastro e salvar as alterações.
* **Remover Livro:** Na tela principal, o usuário pode informar o ID de um livro para removê-lo do banco de dados.

## 3. Tecnologias Utilizadas

* **Linguagem:** Java
* **Banco de Dados:** SQLite (nativo do Android)
* **Arquitetura/Padrão:** DAO (Data Access Object)
* **Principais APIs Android:**
    * `SQLiteOpenHelper`
    * `SQLiteDatabase`
    * `ContentValues`
    * `Cursor`

## 4. Estrutura do Código

O projeto está organizado nos seguintes pacotes e classes principais:

### /ui (Pacote de Interface do Usuário)

* **MainActivity.java**: Tela principal com os botões de ação ("Cadastrar", "Pesquisar", "Remover", "Atualizar"). Gerencia a navegação e exibe diálogos de confirmação para obter o ID do livro.
* **CadastroActivity.java**: Formulário para entrada de dados (título, autor, ano). Esta tela é usada tanto para criar um novo registro quanto para atualizar um existente (verificando se um "id" foi passado via Intent).
* **PesquisaActivity.java**: Exibe os resultados da busca (seja por todos, título ou ano) em um `TableLayout`.

### /database (Pacote de Acesso a Dados)

* **DbHelper.java**: Herda de `SQLiteOpenHelper`. É responsável por definir o nome do banco (`DATABASE_NAME = "bibunir"`), a versão e executar o comando SQL `CREATE_TABLE_LIVRO`.
* **ILivroDAO.java**: Interface que define o contrato do DAO, listando todos os métodos de acesso a dados necessários.
* **LivroDAO.java**: Implementação concreta do `ILivroDAO`. Contém toda a lógica SQL para `inserir`, `alterarRegistro`, `deletarRegistro` e os métodos de pesquisa (`pesquisarPorId`, `pesquisarPorTitulo`, etc.).
