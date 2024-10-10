
# Documentação Completa de Testes de Requisições HTTP do Back-End

Esta documentação abrange todas as operações disponíveis no back-end do **SGE**, descrevendo cada requisição, o tipo de requisição, o corpo esperado e os códigos de resposta HTTP possíveis.

## 1. Gerenciamento de Alunos

### 1.1 Listar Alunos

-   **O que faz**: Retorna uma lista de todos os alunos cadastrados no sistema.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/alunos`
-   **Corpo da Requisição**: Não aplicável.
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de alunos recuperada com sucesso.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

### 1.2 Buscar Aluno por ID

-   **O que faz**: Recupera os detalhes de um aluno específico com base no seu ID.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/alunos/{id}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Aluno encontrado com sucesso.
    -   **404 Not Found**: Aluno com o ID fornecido não encontrado.
    -   **400 Bad Request**: ID fornecido é inválido.

### 1.3 Criar Aluno

-   **O que faz**: Cria um novo aluno no sistema.
-   **Tipo de Requisição**: POST
-   **Endpoint**: `/alunos`
-   **Corpo da Requisição**:
```json
{
  "nome": "string",
  "ultimoNome": "string",
  "genero": "string",
  "data_nascimento": "2024-10-10T04:31:04.635Z",
  "cpf": "string",
  "email": "string",
  "coordenacaoId": 0,
  "turmasIds": [
    0
  ],
  "enderecos": [
    {
      "cep": "string",
      "rua": "string",
      "numero": "string",
      "bairro": "string",
      "cidade": "string",
      "estado": "string"
    }
  ],
  "telefones": [
    {
      "ddd": "string",
      "numero": "string"
    }
  ],
  "responsaveis": [
    {
      "nome": "string",
      "ultimoNome": "string",
      "cpfResponsavel": "string",
      "telefones": [
        {
          "ddd": "string",
          "numero": "string"
        }
      ],
      "grauParentesco": "string"
    }
  ]
}
```
    
-   **Códigos HTTP Possíveis**:
    -   **201 Created**: Aluno criado com sucesso.
    -   **400 Bad Request**: Algum campo obrigatório está ausente ou o CPF é inválido.
    -   **409 Conflict**: CPF já cadastrado para outro aluno.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

### 1.4 Atualizar Aluno

-   **O que faz**: Atualiza as informações de um aluno existente com base no ID fornecido.
-   **Tipo de Requisição**: PUT
-   **Endpoint**: `/alunos/{id}`
-   **Corpo da Requisição**:
```json
{
  "nome": "string",
  "ultimoNome": "string",
  "genero": "string",
  "data_nascimento": "2024-10-10T04:31:45.519Z",
  "cpf": "string",
  "email": "string",
  "coordenacaoId": 0,
  "turmasIds": [
    0
  ],
  "enderecos": [
    {
      "cep": "string",
      "rua": "string",
      "numero": "string",
      "bairro": "string",
      "cidade": "string",
      "estado": "string"
    }
  ],
  "telefones": [
    {
      "ddd": "string",
      "numero": "string"
    }
  ],
  "responsaveis": [
    {
      "nome": "string",
      "ultimoNome": "string",
      "cpfResponsavel": "string",
      "telefones": [
        {
          "ddd": "string",
          "numero": "string"
        }
      ],
      "grauParentesco": "string"
    }
  ]
}
```
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Aluno atualizado com sucesso.
    -   **400 Bad Request**: Dados fornecidos são inválidos.
    -   **404 Not Found**: Aluno com o ID fornecido não encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

### 1.5 Excluir Aluno

-   **O que faz**: Remove um aluno do sistema com base no ID fornecido.
-   **Tipo de Requisição**: DELETE
-   **Endpoint**: `/alunos/{id}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Aluno excluído com sucesso.
    -   **404 Not Found**: Aluno com o ID fornecido não encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

----------

## 2. Gerenciamento de Turmas

### 2.1 Listar Turmas

-   **O que faz**: Retorna uma lista de todas as turmas cadastradas no sistema.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/turmas`
-   **Corpo da Requisição**: Não aplicável.
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de turmas recuperada com sucesso.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

### 2.2 Buscar Turma por ID

-   **O que faz**: Recupera os detalhes de uma turma específica com base no seu ID.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/turmas/{id}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Turma encontrada com sucesso.
    -   **404 Not Found**: Turma com o ID fornecido não encontrada.
    -   **400 Bad Request**: ID fornecido é inválido.

### 2.3 Criar Turma

-   **O que faz**: Cria uma nova turma no sistema.
-   **Tipo de Requisição**: POST
-   **Endpoint**: `/turmas`
-   **Corpo da Requisição**:
```json
{
  "anoLetivo": 0,
  "anoEscolar": "string",
  "turno": "string",
  "status": true,
  "coordenacaoId": 0,
  "alunosIds": [
    0
  ],
  "disciplinasProfessores": [
    {
      "professorId": "string",
      "disciplinasIds": [
        0
      ]
    }
  ]
}
```
    
-   **Códigos HTTP Possíveis**:
    -   **201 Created**: Turma criada com sucesso.
    -   **400 Bad Request**: Algum campo obrigatório está ausente ou inválido.
    -   **409 Conflict**: Já existe uma turma com o mesmo nome e ano.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

### 2.4 Atualizar Turma

-   **O que faz**: Atualiza as informações de uma turma existente com base no ID fornecido.
-   **Tipo de Requisição**: PUT
-   **Endpoint**: `/turmas/{id}`
-   **Corpo da Requisição**:
```json
{
  "anoLetivo": 0,
  "anoEscolar": "string",
  "turno": "string",
  "status": true,
  "coordenacaoId": 0,
  "alunosIds": [
    0
  ],
  "disciplinasProfessores": [
    {
      "professorId": "string",
      "disciplinasIds": [
        0
      ]
    }
  ]
}
```
    
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Turma atualizada com sucesso.
    -   **400 Bad Request**: Dados fornecidos são inválidos.
    -   **404 Not Found**: Turma com o ID fornecido não encontrada.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

### 2.5 Excluir Turma

-   **O que faz**: Remove uma turma do sistema com base no ID fornecido.
-   **Tipo de Requisição**: DELETE
-   **Endpoint**: `/turmas/{id}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Turma excluída com sucesso.
    -   **404 Not Found**: Turma com o ID fornecido não encontrada.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

----------

## 3. Gerenciamento de Professores

### 3.1 Listar Professores

-   **O que faz**: Retorna uma lista de todos os professores cadastrados no sistema.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/professores`
-   **Corpo da Requisição**: Não aplicável.
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de professores recuperada com sucesso.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

### 3.2 Criar Professor

-   **O que faz**: Cria um novo professor no sistema.
-   **Tipo de Requisição**: POST
-   **Endpoint**: `/professores`
-   **Corpo da Requisição**:
```json
{
  "cpf": "string",
  "nome": "string",
  "ultimoNome": "string",
  "genero": "string",
  "data_nascimento": "2024-10-10T04:33:58.961Z",
  "email": "string",
  "status": true,
  "enderecos": [
    {
      "cep": "string",
      "rua": "string",
      "numero": "string",
      "bairro": "string",
      "cidade": "string",
      "estado": "string"
    }
  ],
  "telefones": [
    {
      "ddd": "string",
      "numero": "string"
    }
  ],
  "coordenacaoId": 0,
  "turmasDisciplinas": [
    {
      "turmaId": 0,
      "disciplinasIds": [
        0
      ]
    }
  ]
}
```
    
-   **Códigos HTTP Possíveis**:
    -   **201 Created**: Professor criado com sucesso.
    -   **400 Bad Request**: Algum campo obrigatório está ausente ou inválido.
    -   **409 Conflict**: CPF já cadastrado para outro professor.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

### 3.3 Atualizar Professor

-   **O que faz**: Atualiza as informações de um professor existente com base no CPF fornecido.
-   **Tipo de Requisição**: PUT
-   **Endpoint**: `/professores/{cpf}`
-   **Corpo da Requisição**:
```json
{
  "cpf": "string",
  "nome": "string",
  "ultimoNome": "string",
  "genero": "string",
  "data_nascimento": "2024-10-10T04:38:13.647Z",
  "email": "string",
  "status": true,
  "enderecos": [
    {
      "cep": "string",
      "rua": "string",
      "numero": "string",
      "bairro": "string",
      "cidade": "string",
      "estado": "string"
    }
  ],
  "telefones": [
    {
      "ddd": "string",
      "numero": "string"
    }
  ],
  "coordenacaoId": 0,
  "turmasDisciplinas": [
    {
      "turmaId": 0,
      "disciplinasIds": [
        0
      ]
    }
  ]
}
```` 
    
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Professor atualizado com sucesso.
    -   **400 Bad Request**: Dados fornecidos são inválidos.
    -   **404 Not Found**: Professor com o CPF fornecido não encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

### 3.4 Excluir Professor

-   **O que faz**: Remove um professor do sistema com base no CPF fornecido.
-   **Tipo de Requisição**: DELETE
-   **Endpoint**: `/professores/{cpf}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Professor excluído com sucesso.
    -   **404 Not Found**: Professor com o CPF fornecido não encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

----------
## 4. Gerenciamento de Comunicados


### 4.1 Operações relacionados a Professor
 
#### 4.1.1 Criar Comunicado por Professor

-   **O que faz**: Cria um novo comunicado enviado por um professor.
-   **Tipo de Requisição**: POST
-   **Endpoint**: `/comunicados/professor/{professorId}`
-   **Corpo da Requisição**:
```json
{
  "conteudo": "string",
  "dataEnvio": "2024-10-10T05:04:04.383Z",
  "alunoIds": [
    0
  ],
  "turmaIds": [
    0
  ]
}
```
    
-   **Códigos HTTP Possíveis**:
    -   **201 Created**: Comunicado criado com sucesso.
    -   **400 Bad Request**: Algum campo obrigatório está ausente ou inválido.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------
#### 4.1.2 Atualizar Comunicado por Professor

-   **O que faz**: Atualiza um comunicado específico enviado por um professor.
-   **Tipo de Requisição**: PUT
-   **Endpoint**: `/comunicados/professor/{professorId}/{comunicadoId}`
-   **Corpo da Requisição**:
```json
{
  "conteudo": "string",
  "dataEnvio": "2024-10-10T05:05:54.602Z",
  "alunoIds": [
    0
  ],
  "turmaIds": [
    0
  ]
}
``` 
    
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Comunicado atualizado com sucesso.
    -   **400 Bad Request**: Dados fornecidos são inválidos ou incompletos.
    -   **404 Not Found**: Comunicado ou professor com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------
#### 4.1.3 Deletar Comunicado por Professor

-   **O que faz**: Remove um comunicado específico enviado por um professor.
-   **Tipo de Requisição**: DELETE
-   **Endpoint**: `/comunicados/professor/{professorId}/{comunicadoId}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Comunicado excluído com sucesso.
    -   **404 Not Found**: Comunicado ou professor com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
   ----------

#### 4.1.4 Listar Comunicados Enviados por Professor

-   **O que faz**: Recupera a lista de todos os comunicados enviados por um professor específico.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/comunicados/professor/{professorId}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de comunicados recuperada com sucesso.
    -   **404 Not Found**: Professor com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------
### 4.2 Operações relacionadas a **Coordenador**:

#### 4.2.1 Criar Comunicado por Coordenador

-   **O que faz**: Cria um novo comunicado enviado por um coordenador.
-   **Tipo de Requisição**: POST
-   **Endpoint**: `/comunicados/coordenacao/{coordenacaoId}/coordenador/{coordenadorId}`
-   **Corpo da Requisição**:
```json
{
  "conteudo": "string",
  "dataEnvio": "2024-10-10T05:14:02.385Z",
  "alunoIds": [
    0
  ],
  "turmaIds": [
    0
  ]
}
```
    
-   **Códigos HTTP Possíveis**:
    -   **201 Created**: Comunicado criado com sucesso.
    -   **400 Bad Request**: Algum campo obrigatório está ausente ou inválido.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------
  
  #### 4.2.2 Atualizar Comunicado por Coordenador

-   **O que faz**: Atualiza um comunicado específico enviado por um coordenador.
-   **Tipo de Requisição**: PUT
-   **Endpoint**: `/comunicados/coordenacao/{coordenacaoId}/coordenador/{coordenadorId}/{comunicadoId}`
-   **Corpo da Requisição**:
```json
{
  "conteudo": "string",
  "dataEnvio": "2024-10-10T05:15:19.577Z",
  "alunoIds": [
    0
  ],
  "turmaIds": [
    0
  ]
}
```
    
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Comunicado atualizado com sucesso.
    -   **400 Bad Request**: Dados fornecidos são inválidos ou incompletos.
    -   **404 Not Found**: Comunicado, coordenador ou coordenação não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

----------

#### 4.2.3 Listar Comunicados Enviados por Coordenador

-   **O que faz**: Recupera a lista de comunicados enviados por um coordenador específico.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/comunicados/coordenador/{coordenadorId}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de comunicados recuperada com sucesso.
    -   **404 Not Found**: Coordenador com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

----------

#### 4.2.4 Deletar Comunicado por Coordenador

-   **O que faz**: Remove um comunicado específico enviado por um coordenador.
-   **Tipo de Requisição**: DELETE
-   **Endpoint**: `/comunicados/coordenacao/{coordenacaoId}/coordenador/{coordenadorId}/{comunicadoId}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Comunicado excluído com sucesso.
    -   **404 Not Found**: Comunicado, coordenador ou coordenação não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

### 4.3 Operações Gerais:

#### 4.3.1 Criar Comunicado para Todos os Alunos e Turmas

-   **O que faz**: Cria um comunicado enviado para todos os alunos e turmas.
-   **Tipo de Requisição**: POST
-   **Endpoint**: `/comunicados/coordenacao/{coordenacaoId}/coordenador/{coordenadorId}/todos`
-   **Corpo da Requisição**:
```json
{
  "conteudo": "string",
  "dataEnvio": "2024-10-10T05:19:56.219Z",
  "alunoIds": [
    0
  ],
  "turmaIds": [
    0
  ]
}
```
    
-   **Códigos HTTP Possíveis**:
    -   **201 Created**: Comunicado criado com sucesso.
    -   **400 Bad Request**: Algum campo obrigatório está ausente ou inválido.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

----------

#### 4.3.2 Listar Todos os Comunicados

-   **O que faz**: Retorna a lista de todos os comunicados do sistema.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/comunicados`
-   **Corpo da Requisição**: Não aplicável.
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de comunicados recuperada com sucesso.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

----------

#### 4.3.3 Listar Comunicados para Turmas

-   **O que faz**: Retorna a lista de comunicados enviados para turmas.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/comunicados/turmas`
-   **Corpo da Requisição**: Não aplicável.
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de comunicados para turmas recuperada com sucesso.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

----------

#### 4.3.4 Buscar Comunicados por Turma

-   **O que faz**: Retorna os comunicados específicos enviados para uma turma específica.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/comunicados/turma/{turmaId}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Comunicados para a turma recuperados com sucesso.
    -   **404 Not Found**: Turma com o ID fornecido não encontrada.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

----------

#### 4.3.5 Listar Comunicados para Alunos

-   **O que faz**: Retorna a lista de comunicados enviados para os alunos.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/comunicados/alunos`
-   **Corpo da Requisição**: Não aplicável.
-   **Códigos HTTP Possíveis**:
    -   **200 OK

## 5. Gerenciamento de Conceitos

### 5.1 Atribuir Conceito a um Aluno

-   **O que faz**: Atribui um conceito a um aluno em uma disciplina e turma específica
-   **Tipo de Requisição**: POST
-   **Endpoint**:  `/conceitos/{idProfessor}/aluno/{idAluno}/disciplina/{idDisciplina}/turma/{idTurma}/conceitos`
-   **Corpo da Requisição**:
```json
{
  "notaUnidade1": 0,
  "notaUnidade2": 0,
  "notaUnidade3": 0,
  "notaUnidade4": 0,
  "noa1": 0,
  "noa2": 0,
  "noaFinal": 0
}
```
    
-   **Códigos HTTP Possíveis**:
    -   **201 Created**: Conceito atribuído com sucesso.
    -   **400 Bad Request**: Algum campo obrigatório está ausente ou inválido.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

----------
### 5.2 Atualizar Conceito de um Aluno

-   **O que faz**: Atualiza o conceito de um aluno em uma disciplina e turma específica.
-   **Tipo de Requisição**: PUT
-   **Endpoint**: `/conceitos/{idProfessor}/aluno/{idAluno}/disciplina/{idDisciplina}/turma/{idTurma}/conceitos/{idConceito}`
-   **Corpo da Requisição**:
```json
{
  "notaUnidade1": 0,
  "notaUnidade2": 0,
  "notaUnidade3": 0,
  "notaUnidade4": 0,
  "noa1": 0,
  "noa2": 0,
  "noaFinal": 0
}
``` 
    
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Conceito atualizado com sucesso.
    -   **400 Bad Request**: Dados fornecidos são inválidos ou incompletos.
    -   **404 Not Found**: Conceito, professor, aluno, disciplina ou turma com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
 ----------
### 5.3 Visualizar Nota de um Aluno em uma Disciplina e Turma

-   **O que faz**: Visualiza a nota de um aluno específico em uma disciplina e turma específica.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/conceitos/{idProfessor}/aluno/{idAluno}/disciplina/{idDisciplina}/turma/{idTurma}/conceitos`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Nota do aluno recuperada com sucesso.
    -   **404 Not Found**: Professor, aluno, disciplina ou turma com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------
### 5.4 Buscar Conceito por ID

-   **O que faz**: Recupera um conceito específico baseado no ID do conceito.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/conceitos/{id}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Conceito recuperado com sucesso.
    -   **404 Not Found**: Conceito com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
  ----------
### 5.5 Listar Conceitos de todos os alunos de uma turma

-   **O que faz**: Recupera uma lista de todos os conceitos atribuídos aos alunos em uma turma.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/conceitos`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de conceitos recuperada com sucesso.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
   ----------

### 5.6 Listar Todos os Conceitos de um Aluno

-   **O que faz**: Lista todos os conceitos atribuídos a um aluno em todas as disciplinas e turmas.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/conceitos/{idAluno}/conceitos`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de conceitos do aluno recuperada com sucesso.
    -   **404 Not Found**: Aluno com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------

### 5.7 Buscar Conceito de um Aluno em uma Disciplina Específica

-   **O que faz**: Recupera o conceito de um aluno específico em uma disciplina específica.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/conceitos/{idAluno}/conceitos/disciplina/{idDisciplina}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Conceito do aluno na disciplina recuperado com sucesso.
    -   **404 Not Found**: Aluno ou disciplina com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------

### 5.8 Deletar Conceito

-   **O que faz**: Remove um conceito do sistema com base no ID fornecido.
-   **Tipo de Requisição**: DELETE
-   **Endpoint**: `/conceitos/{id}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Conceito excluído com sucesso.
    -   **404 Not Found**: Conceito com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
    
----------
## 6. Gerenciamento de Presenças

### 6.1 Registrar Presença de um Aluno

-   **O que faz**: Registra a presença de um aluno em uma disciplina ou turma.
-   **Tipo de Requisição**: POST
-   **Endpoint**: `/presencas/aluno/{idAluno}/turma/{idTurma}/disciplina/{idDisciplina}/professor/{idProfessor}`
-   **Corpo da Requisição**:
```json
{
  "data": "2024-10-10T05:37:07.616Z",
  "presenca": true
}
``` 
    
-   **Códigos HTTP Possíveis**:
    -   **201 Created**: Presença registrada com sucesso.
    -   **400 Bad Request**: Algum campo obrigatório está ausente ou inválido.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

----------

### 6.2 Atualizar uma Presença Existente

-   **O que faz**: Atualiza a presença de um aluno em uma turma, disciplina e professor específicos.
-   **Tipo de Requisição**: PUT
-   **Endpoint**: `/presencas/{id}/aluno/{idAluno}/turma/{idTurma}/disciplina/{idDisciplina}/professor/{idProfessor}`
-   **Corpo da Requisição**:
```json
{
  "data": "2024-10-10T05:38:37.364Z",
  "presenca": true
}
```
    
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Presença atualizada com sucesso.
    -   **400 Bad Request**: Dados fornecidos são inválidos ou incompletos.
    -   **404 Not Found**: Presença, aluno, turma, disciplina ou professor com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
 ----------
 ### 6.3 Buscar presença por ID

-   **O que faz**: Busca uma presença específica com base no ID da presença.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/presencas/{id}/aluno/{idAluno}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Presença recuperada com sucesso.
    -   **404 Not Found**: Presença com o ID fornecido não foi encontrada.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
 ----------

### 6.4 Deletar uma Presença por ID

-   **O que faz**: Remove uma presença do sistema com base no ID fornecido.
-   **Tipo de Requisição**: DELETE
-   **Endpoint**: `/presencas/{id}/aluno/{idAluno}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Presença excluída com sucesso.
    -   **404 Not Found**: Presença com o ID fornecido não foi encontrada.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
 ----------

### 6.5 Listar Presenças Emitidas por um Professor

-   **O que faz**: Recupera a lista de todas as presenças emitidas por um professor específico.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/presencas/professor/{idProfessor}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de presenças recuperada com sucesso.
    -   **404 Not Found**: Professor com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
 ----------

### 6.6 Listar Todas as Presenças de um Aluno

-   **O que faz**: Recupera a lista de todas as presenças de um aluno em todas as turmas e disciplinas.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/presencas/aluno/{idAluno}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de presenças do aluno recuperada com sucesso.
    -   **404 Not Found**: Aluno com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
 ----------
### 6.7 Listar Presenças de um Aluno em uma Disciplina

-   **O que faz**: Recupera a lista de presenças de um aluno em uma disciplina específica.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/presencas/aluno/{idAluno}/disciplina/{idDisciplina}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de presenças do aluno na disciplina recuperada com sucesso.
    -   **404 Not Found**: Aluno ou disciplina com o ID fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
 ----------

## 7. Gerenciamento de Coordenadores

### 7.1 Listar Coordenadores

-   **O que faz**: Retorna uma lista de todos os coordenadores cadastrados no sistema.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/coordenadores`
-   **Corpo da Requisição**: Não aplicável.
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de coordenadores recuperada com sucesso.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

### 7.2 Criar Coordenador

-   **O que faz**: Cria um novo coordenador no sistema.
-   **Tipo de Requisição**: POST
-   **Endpoint**: `/coordenadores`
-   **Corpo da Requisição**:
 ```json
{
  "cpf": "string",
  "nome": "string",
  "ultimoNome": "string",
  "genero": "string",
  "data_nascimento": "2024-10-10T05:45:40.128Z",
  "email": "string",
  "status": true,
  "enderecos": [
    {
      "cep": "string",
      "rua": "string",
      "numero": "string",
      "bairro": "string",
      "cidade": "string",
      "estado": "string"
    }
  ],
  "telefones": [
    {
      "ddd": "string",
      "numero": "string"
    }
  ],
  "idCoordenacao": 0
}
```
    
-   **Códigos HTTP Possíveis**:
    -   **201 Created**: Coordenador criado com sucesso.
    -   **400 Bad Request**: Algum campo obrigatório está ausente ou o CPF é inválido.
    -   **409 Conflict**: CPF já cadastrado para outro coordenador.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

----------
### 7.3 Atualizar Coordenador

-   **O que faz**: Atualiza as informações de um coordenador existente com base no CPF fornecido.
-   **Tipo de Requisição**: PUT
-   **Endpoint**: `/coordenadores/{cpf}`
-   **Corpo da Requisição**:
```json
{
  "cpf": "string",
  "nome": "string",
  "ultimoNome": "string",
  "genero": "string",
  "data_nascimento": "2024-10-10T05:46:18.363Z",
  "email": "string",
  "status": true,
  "enderecos": [
    {
      "cep": "string",
      "rua": "string",
      "numero": "string",
      "bairro": "string",
      "cidade": "string",
      "estado": "string"
    }
  ],
  "telefones": [
    {
      "ddd": "string",
      "numero": "string"
    }
  ],
  "idCoordenacao": 0
}
``` 
    
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Coordenador atualizado com sucesso.
    -   **400 Bad Request**: Dados fornecidos são inválidos ou incompletos.
    -   **404 Not Found**: Coordenador com o CPF fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------
### 7.4 Buscar Coordenador por CPF

-   **O que faz**: Busca um coordenador específico com base no CPF fornecido.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/coordenadores/{cpf}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Coordenador recuperado com sucesso.
    -   **404 Not Found**: Coordenador com o CPF fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------

### 7.5 Listar Todos os Coordenadores

-   **O que faz**: Retorna uma lista de todos os coordenadores cadastrados no sistema.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/coordenadores`
-   **Corpo da Requisição**: Não aplicável.
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de coordenadores recuperada com sucesso.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------

### 7.6 Deletar Coordenador

-   **O que faz**: Remove um coordenador do sistema com base no CPF fornecido.
-   **Tipo de Requisição**: DELETE
-   **Endpoint**: `/coordenadores/{cpf}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Coordenador excluído com sucesso.
    -   **404 Not Found**: Coordenador com o CPF fornecido não foi encontrado.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------
## 8. Gerenciamento de Coordenações

### 8.1 Criar Coordenação

-   **O que faz**: Cria uma nova coordenação no sistema.
-   **Tipo de Requisição**: POST
-   **Endpoint**: `/coordenacoes`
-   **Corpo da Requisição**:
```json
{
  "nome": "string",
  "descricao": "string",
  "enderecos": [
    {
      "cep": "string",
      "rua": "string",
      "numero": "string",
      "bairro": "string",
      "cidade": "string",
      "estado": "string"
    }
  ],
  "telefones": [
    {
      "ddd": "string",
      "numero": "string"
    }
  ],
  "coordenadoresIds": [
    "string"
  ],
  "turmasIds": [
    0
  ],
  "professoresIds": [
    "string"
  ]
}
``` 
    
-   **Códigos HTTP Possíveis**:
    -   **201 Created**: Coordenação criada com sucesso.
    -   **400 Bad Request**: Algum campo obrigatório está ausente ou inválido.
    -   **500 Internal Server Error**: Erro inesperado no servidor.

----------
### 8.2 Atualizar Coordenação

-   **O que faz**: Atualiza as informações de uma coordenação existente com base no ID fornecido.
-   **Tipo de Requisição**: PUT
-   **Endpoint**: `/coordenacoes/{id}`
-   **Corpo da Requisição**:
 ```json
{
  "nome": "string",
  "descricao": "string",
  "enderecos": [
    {
      "cep": "string",
      "rua": "string",
      "numero": "string",
      "bairro": "string",
      "cidade": "string",
      "estado": "string"
    }
  ],
  "telefones": [
    {
      "ddd": "string",
      "numero": "string"
    }
  ],
  "coordenadoresIds": [
    "string"
  ],
  "turmasIds": [
    0
  ],
  "professoresIds": [
    "string"
  ]
}
``` 
    
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Coordenação atualizada com sucesso.
    -   **400 Bad Request**: Dados fornecidos são inválidos ou incompletos.
    -   **404 Not Found**: Coordenação com o ID fornecido não foi encontrada.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------
### 8.3 Buscar Coordenação por ID

-   **O que faz**: Busca uma coordenação específica com base no ID fornecido.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/coordenacoes/{id}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Coordenação recuperada com sucesso.
    -   **404 Not Found**: Coordenação com o ID fornecido não foi encontrada.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------
### 8.4 Listar Todas as Coordenações

-   **O que faz**: Retorna uma lista de todas as coordenações cadastradas no sistema.
-   **Tipo de Requisição**: GET
-   **Endpoint**: `/coordenacoes`
-   **Corpo da Requisição**: Não aplicável.
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Lista de coordenações recuperada com sucesso.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------
### 8.5 Deletar Coordenação

-   **O que faz**: Remove uma coordenação do sistema com base no ID fornecido.
-   **Tipo de Requisição**: DELETE
-   **Endpoint**: `/coordenacoes/{id}`
-   **Corpo da Requisição**: Não aplicável (Requer apenas ID).
-   **Códigos HTTP Possíveis**:
    -   **200 OK**: Coordenação excluída com sucesso.
    -   **404 Not Found**: Coordenação com o ID fornecido não foi encontrada.
    -   **500 Internal Server Error**: Erro inesperado no servidor.
----------
Essa documentação cobre todas as operações de CRUD para **Alunos**, **Turmas**, **Professores**, **Comunicados**, **Conceitos**, **Presenças**, **Coordenadores** e **Coordenações**, oferecendo uma visão completa de como interagir com a API do sistema, incluindo tratamento de erros e validações necessárias.
