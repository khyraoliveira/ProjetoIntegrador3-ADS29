# Documentação do Front-End para o Sistema de Gerenciamento Escolar (SGE)

## 1. Introdução

O **Sistema de Gerenciamento Escolar (SGE)** é uma aplicação desenvolvida para gerenciar operações escolares, como o cadastro de alunos, turmas e professores. O front-end deve ser capaz de interagir com as APIs do back-end para exibir e manipular esses dados.

## 2. Tecnologias Utilizadas

O front-end do projeto é construído com as seguintes tecnologias:

-   **HTML5**: Para estruturar as páginas e os elementos do sistema.
-   **CSS3**: Para estilizar as interfaces e tornar a aplicação responsiva.
-   **JavaScript (ES6)**: Para adicionar interatividade, manipular o DOM
-   **Bootstrap** (opcional): Para facilitar a estilização e tornar o design responsivo.

## 3. Estrutura do Projeto

### 3.1 Módulo de Alunos

###  `cad_alunos.js`

**Descrição**: Gerencia o cadastro de novos alunos no sistema, carregando turmas e enviando dados de alunos e seus responsáveis para a API.

-   **Funções Principais**:
    
    -   `loadTurmas`: Carrega as turmas disponíveis para o campo de seleção.
    -   `salvar`: Envia os dados do aluno para o servidor via requisição `POST`.
-   **Campos Capturados**:
    
    -   Dados do Aluno: Nome, Último Nome, CPF, Gênero, Data de Nascimento, E-mail, Turma.
    -   Endereço: CEP, Rua, Número, Bairro, Cidade, Estado.
    -   Responsável: Nome, Último Nome, CPF, Grau de Parentesco, Telefone.
-   **Endpoints**:
    
    -   `GET /turmas`: Carrega turmas.
    -   `POST /alunos`: Envia os dados de cadastro do aluno.

----------

### 3.2 Módulo de Coordenadores

### `cad_coordenador.js`

**Descrição**: Gerencia o cadastro de coordenadores, enviando dados pessoais e de endereço para a API.

-   **Funções Principais**:
    
    -   `salvar`: Coleta os dados e envia uma requisição `POST` com o novo coordenador.
-   **Campos Capturados**:
    
    -   Nome, Último Nome, CPF, Gênero, Data de Nascimento, E-mail, Telefone, Endereço.
-   **Endpoints**:
    
    -   `POST /coordenadores`: Envia os dados do coordenador para o servidor.

----------

### 3.3 Módulo de Disciplinas

### `cad_disciplinas.js`

**Descrição**: Gerencia o cadastro de disciplinas associadas a professores e turmas.

-   **Funções Principais**:
    
    -   `carregarProfessores`: Carrega os professores disponíveis.
    -   `carregarTurmas`: Carrega as turmas disponíveis.
    -   `salvar`: Envia os dados da disciplina para o servidor via `POST`.
-   **Campos Capturados**:
    
    -   Nome da Disciplina, Carga Horária, CPF do Professor, ID da Turma.
-   **Endpoints**:
    
    -   `GET /professores`: Carrega professores.
    -   `GET /turmas`: Carrega turmas.
    -   `POST /disciplinas`: Cadastra a nova disciplina.

----------

### 3.4 Módulo de Docentes

###  `cad_docentes.js`

**Descrição**: Gerencia o cadastro de professores, coletando dados pessoais e associando disciplinas ao docente.

-   **Funções Principais**:
    
    -   `salvar`: Envia os dados do professor para a API com disciplinas associadas.
-   **Campos Capturados**:
    
    -   Nome, Último Nome, CPF, Gênero, Data de Nascimento, E-mail, Disciplinas.
-   **Endpoints**:
    
    -   `GET /disciplinas`: Carrega disciplinas.
    -   `POST /professores`: Envia os dados do professor para o servidor.

----------

### 3.5 Módulo de Turmas

### `cad_turmas.js`

**Descrição**: Gerencia o cadastro de turmas, coletando informações básicas e enviando ao servidor.

-   **Funções Principais**:
    
    -   `salvar`: Envia os dados da nova turma para o servidor via `POST`.
-   **Campos Capturados**:
    
    -   Ano Letivo, Ano Escolar, Turno.
-   **Endpoints**:
    
    -   `POST /turmas`: Envia os dados da turma.

----------

### 3.6 Módulo de Gerenciamento de Coordenadores

###  `coordenador.js`

**Descrição**: Gerencia a exibição, edição e remoção de coordenadores no sistema.

-   **Funções Principais**:
    
    -   `loadCoordenadores`: Carrega os coordenadores existentes e os exibe em uma tabela.
    -   `updateCoordenador`: Atualiza os dados de um coordenador.
    -   `deleteCoordenador`: Remove um coordenador do sistema.
-   **Endpoints**:
    
    -   `GET /coordenadores`: Carrega todos os coordenadores.
    -   `PUT /coordenadores/{cpf}`: Atualiza os dados do coordenador.
    -   `DELETE /coordenadores/{cpf}`: Remove o coordenador.

----------

### 3.7 Módulo de Gerenciamento de Disciplinas

### `disciplina.js`

**Descrição**: Gerencia a exibição, edição e remoção de disciplinas cadastradas no sistema.

-   **Funções Principais**:
    
    -   `loadDisciplinas`: Carrega as disciplinas e as exibe.
    -   `updateDisciplina`: Atualiza os dados de uma disciplina.
    -   `deleteDisciplina`: Remove uma disciplina.
-   **Endpoints**:
    
    -   `GET /disciplinas`: Carrega todas as disciplinas.
    -   `PUT /disciplinas/{id}`: Atualiza os dados da disciplina.
    -   `DELETE /disciplinas/{id}`: Remove uma disciplina.

----------

### 3.8 Módulo de Gerenciamento de Docentes

### `docente.js`

**Descrição**: Gerencia a exibição, edição e remoção de professores no sistema.

-   **Funções Principais**:
    
    -   `loadProfessores`: Carrega os professores cadastrados.
    -   `updateProfessor`: Atualiza os dados de um professor.
    -   `deleteProfessor`: Remove um professor do sistema.
-   **Endpoints**:
    
    -   `GET /professores`: Carrega todos os professores.
    -   `PUT /professores/{cpf}`: Atualiza os dados do professor.
    -   `DELETE /professores/{cpf}`: Remove um professor.

----------

### 3.9 Módulo de Exibição de Alunos

### `aluno.js`

**Descrição**: Gerencia a exibição, edição e remoção de alunos cadastrados no sistema.

-   **Funções Principais**:
    
    -   `loadAlunos`: Carrega os alunos e exibe na interface.
    -   `updateAluno`: Atualiza os dados de um aluno.
    -   `deleteAluno`: Remove um aluno do sistema.
-   **Endpoints**:
    
    -   `GET /alunos`: Carrega todos os alunos.
    -   `PUT /alunos/{id}`: Atualiza os dados de um aluno.
    -   `DELETE /alunos/{id}`: Remove um aluno.

----------

### 3.10 Navegação (Navbar)

### `navbar.js`

**Descrição**: Gerencia a navegação da aplicação com controle de expansão do menu.

-   **Funções Principais**:
    -   `selectlink`: Adiciona a classe `ativo` ao item do menu clicado.
    -   `expandir`: Expande ou recolhe a barra de navegação.

----------

### 3.11 Módulo de Gerenciamento de Turmas

###  `turma.js`

**Descrição**: Gerencia a exibição, edição e remoção de turmas no sistema.

-   **Funções Principais**:
    
    -   `loadTurmas`: Carrega as turmas e exibe na tabela.
    -   `updateTurma`: Atualiza os dados de uma turma.
    -   `deleteTurma`: Remove uma turma.
-   **Endpoints**:
    
    -   `GET /turmas`: Carrega todas as turmas.
    -   `PUT /turmas/{id}`: Atualiza os dados de uma turma.
    -   `DELETE /turmas/{id}`: Remove uma turma.

----------

### 3.12 Autenticação

### `index.js`

**Descrição**: Gerencia a autenticação do usuário com base na matrícula, senha e cargo.

-   **Funções Principais**:
    
    -   `verificarLogin`: Verifica as credenciais do usuário e redireciona para a página correta de acordo com o cargo (Professor ou Coordenador).
-   **Banco de Dados Simulado**:
    
    -   Lista de usuários com matrícula, senha e cargo.

----------

Esta documentação do front-end do **Sistema de Gerenciamento Escolar (SGE)** apresenta uma visão abrangente sobre o funcionamento dos diferentes módulos da aplicação, abrangendo desde o gerenciamento de alunos, professores, turmas e disciplinas até a autenticação e navegação. Cada módulo foi detalhado com suas funções principais, os campos capturados e os endpoints de comunicação com o back-end.
