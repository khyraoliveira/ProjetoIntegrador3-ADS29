# 🧪 Testes Automatizados - Sistema de Gerenciamento Escolar (SGE)

Este diretório contém os testes automatizados para o Sistema de Gerenciamento Escolar, desenvolvidos como parte da disciplina de Verificação & Validação de Software.

## 📁 Estrutura do Diretório

```
tests/
├── unit/                          # Testes unitários (Jest)
│   └── validacoes.test.js         # Testes de validação de campos
├── functional/                    # Testes funcionais (Selenium)
│   ├── login.functional.test.js   # Testes do fluxo de login
│   └── cadastroAluno.functional.test.js  # Testes do cadastro de alunos
├── utils/                         # Funções utilitárias testáveis
│   └── validacoes.js              # Funções de validação
├── package.json                   # Dependências e scripts
├── jest.config.js                 # Configuração do Jest
├── setup.js                       # Setup inicial dos testes
└── README.md                      # Este arquivo
```

## 🚀 Como Executar os Testes

### Pré-requisitos

- Node.js 18+ instalado
- Google Chrome instalado (para testes funcionais)
- Frontend rodando em `http://localhost:8080` (para testes funcionais)

### Instalação

```bash
cd tests
npm install
```

### Executar Todos os Testes

```bash
npm test
```

### Executar Apenas Testes Unitários

```bash
npm run test:unit
```

### Executar Apenas Testes Funcionais

```bash
# Primeiro, inicie o frontend em outro terminal:
cd ../frontend
npx http-server -p 8080

# Depois, execute os testes funcionais:
cd ../tests
npm run test:functional
```

### Executar com Cobertura de Código

```bash
npm run test:coverage
```

## 📋 Testes Implementados

### Testes Unitários (8 suites)

| # | Teste | Descrição |
|---|-------|-----------|
| 1 | `formatarData` | Formatação de datas YYYY-MM-DD para DD/MM/YYYY |
| 2 | `validarCPF` | Validação de formato de CPF (11 dígitos) |
| 3 | `validarEmail` | Validação de formato de email |
| 4 | `validarCamposObrigatoriosAluno` | Validação de campos do formulário de aluno |
| 5 | `validarCamposObrigatoriosDisciplina` | Validação de campos do formulário de disciplina |
| 6 | `validarCEP` | Validação de formato de CEP (8 dígitos) |
| 7 | `validarTelefone` | Validação de DDD e número de telefone |
| 8 | `validarLogin` | Validação de credenciais de login |

### Testes Funcionais (3 fluxos)

| # | Fluxo | Descrição |
|---|-------|-----------|
| 1 | Login Professor/Coordenador | Testa o fluxo completo de login com diferentes perfis |
| 2 | Cadastro de Aluno | Testa o preenchimento do formulário de cadastro |
| 3 | Navegação do Sistema | Testa a navegação entre páginas do sistema |

## 🔄 Integração Contínua (CI)

O projeto utiliza GitHub Actions para executar os testes automaticamente a cada push ou pull request.

### Pipeline CI

O arquivo `.github/workflows/ci.yml` configura:

1. **Testes Unitários**: Executa Jest com relatório de cobertura
2. **Testes Funcionais**: Executa Selenium com Chrome headless
3. **Análise de Qualidade**: Verifica sintaxe JavaScript
4. **Notificação**: Gera resumo dos resultados

### Status do Build

Os resultados podem ser visualizados na aba "Actions" do repositório GitHub.

## 📊 Relatório de Cobertura

Após executar `npm run test:coverage`, o relatório é gerado em:

```
tests/coverage/
├── lcov-report/    # Relatório HTML
│   └── index.html  # Abrir no navegador
└── lcov.info       # Dados de cobertura
```

## 🛠️ Ferramentas Utilizadas

| Ferramenta | Versão | Uso |
|------------|--------|-----|
| Jest | 29.7.0 | Framework de testes unitários |
| jest-environment-jsdom | 29.7.0 | Simulação do DOM |
| Selenium WebDriver | 4.15.0 | Testes funcionais automatizados |
| ChromeDriver | 119.0.1 | Driver do Chrome para Selenium |
| GitHub Actions | - | Integração contínua |

## 👥 Equipe

Projeto Integrador 3 - ADS29 - SENAC

## 📝 Licença

Este projeto é parte de um trabalho acadêmico.
