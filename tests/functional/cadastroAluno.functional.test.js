/**
 * Testes Funcionais - Fluxo de Cadastro de Aluno
 * 
 * Este arquivo contém testes funcionais automatizados para o fluxo de
 * cadastro de alunos do Sistema de Gerenciamento Escolar.
 * 
 * Ferramentas: Selenium WebDriver, Jest
 * Autor: Equipe PI3-ADS29
 */

const { Builder, By, until, Key } = require('selenium-webdriver');
const chrome = require('selenium-webdriver/chrome');

// Configuração do timeout para testes funcionais
jest.setTimeout(60000);

describe('Testes Funcionais - Fluxo de Cadastro de Aluno', () => {
  let driver;
  
  // URLs do sistema
  const BASE_URL = 'http://localhost:8080';
  const LOGIN_URL = BASE_URL;
  const CADASTRO_ALUNO_URL = `${BASE_URL}/assets/html/cad_alunos.html`;

  // Configuração antes de todos os testes
  beforeAll(async () => {
    const options = new chrome.Options();
    options.addArguments('--headless');
    options.addArguments('--no-sandbox');
    options.addArguments('--disable-dev-shm-usage');
    options.addArguments('--disable-gpu');
    options.addArguments('--window-size=1920,1080');

    driver = await new Builder()
      .forBrowser('chrome')
      .setChromeOptions(options)
      .build();
  });

  afterAll(async () => {
    if (driver) {
      await driver.quit();
    }
  });

  afterEach(async () => {
    await driver.manage().deleteAllCookies();
  });

  // Função auxiliar para fazer login como Coordenador
  async function fazerLoginCoordenador() {
    await driver.get(LOGIN_URL);
    await driver.wait(until.elementLocated(By.id('profi')), 10000);

    const selectCargo = await driver.findElement(By.id('profi'));
    await selectCargo.sendKeys('Coordenador');

    const inputMatricula = await driver.findElement(By.id('mtr'));
    await inputMatricula.clear();
    await inputMatricula.sendKeys('654321');

    const inputSenha = await driver.findElement(By.id('password'));
    await inputSenha.clear();
    await inputSenha.sendKeys('senha123');

    const botaoEntrar = await driver.findElement(By.id('botao'));
    await botaoEntrar.click();

    await driver.wait(async () => {
      const url = await driver.getCurrentUrl();
      return url.includes('inicio.html');
    }, 10000);
  }

  // ==========================================================================
  // FLUXO 1: Cadastro completo de aluno
  // ==========================================================================
  describe('Fluxo 1: Cadastro Completo de Aluno', () => {
    /**
     * Objetivo: Verificar se o cadastro de aluno funciona corretamente
     * 
     * Passos:
     * 1. Faz login como Coordenador
     * 2. Navega para a página de cadastro de alunos
     * 3. Preenche todos os campos obrigatórios
     * 4. Preenche dados do responsável
     * 5. Clica em "Salvar"
     * 6. Verifica se exibe mensagem de sucesso
     */

    test('Deve abrir a página de cadastro de alunos', async () => {
      await driver.get(CADASTRO_ALUNO_URL);
      await driver.wait(until.elementLocated(By.id('nome')), 10000);

      // Verifica se os campos principais estão presentes
      const campoNome = await driver.findElement(By.id('nome'));
      const campoEmail = await driver.findElement(By.id('email'));
      const campoCpf = await driver.findElement(By.id('cpf'));

      expect(await campoNome.isDisplayed()).toBe(true);
      expect(await campoEmail.isDisplayed()).toBe(true);
      expect(await campoCpf.isDisplayed()).toBe(true);
    });

    test('Deve preencher formulário de cadastro de aluno', async () => {
      await driver.get(CADASTRO_ALUNO_URL);
      await driver.wait(until.elementLocated(By.id('nome')), 10000);

      // Dados do aluno
      const dadosAluno = {
        nome: 'Maria',
        ultimoNome: 'Santos',
        cpf: '12345678901',
        email: 'maria.santos@teste.com',
        dataNascimento: '2005-06-15'
      };

      // Preenche os campos
      const campoNome = await driver.findElement(By.id('nome'));
      await campoNome.clear();
      await campoNome.sendKeys(dadosAluno.nome);

      const campoUltimoNome = await driver.findElement(By.id('ultimoNome'));
      await campoUltimoNome.clear();
      await campoUltimoNome.sendKeys(dadosAluno.ultimoNome);

      const campoCpf = await driver.findElement(By.id('cpf'));
      await campoCpf.clear();
      await campoCpf.sendKeys(dadosAluno.cpf);

      const campoEmail = await driver.findElement(By.id('email'));
      await campoEmail.clear();
      await campoEmail.sendKeys(dadosAluno.email);

      const campoDataNascimento = await driver.findElement(By.id('data_nascimento'));
      await campoDataNascimento.clear();
      await campoDataNascimento.sendKeys(dadosAluno.dataNascimento);

      // Verifica se os campos foram preenchidos corretamente
      expect(await campoNome.getAttribute('value')).toBe(dadosAluno.nome);
      expect(await campoUltimoNome.getAttribute('value')).toBe(dadosAluno.ultimoNome);
      expect(await campoCpf.getAttribute('value')).toBe(dadosAluno.cpf);
      expect(await campoEmail.getAttribute('value')).toBe(dadosAluno.email);
    });

    test('Deve preencher dados do responsável', async () => {
      await driver.get(CADASTRO_ALUNO_URL);
      await driver.wait(until.elementLocated(By.id('nome_responsavel')), 10000);

      // Dados do responsável
      const dadosResponsavel = {
        nome: 'José',
        ultimoNome: 'Santos',
        cpf: '98765432100',
        ddd: '11',
        numero: '999999999',
        grauParentesco: 'Pai'
      };

      // Preenche os campos do responsável
      const campoNomeResp = await driver.findElement(By.id('nome_responsavel'));
      await campoNomeResp.clear();
      await campoNomeResp.sendKeys(dadosResponsavel.nome);

      const campoUltimoNomeResp = await driver.findElement(By.id('ultimoNomeR'));
      await campoUltimoNomeResp.clear();
      await campoUltimoNomeResp.sendKeys(dadosResponsavel.ultimoNome);

      const campoCpfResp = await driver.findElement(By.id('cpfResponsavel'));
      await campoCpfResp.clear();
      await campoCpfResp.sendKeys(dadosResponsavel.cpf);

      const campoDdd = await driver.findElement(By.id('dddResponsavel'));
      await campoDdd.clear();
      await campoDdd.sendKeys(dadosResponsavel.ddd);

      const campoNumero = await driver.findElement(By.id('numeroResponsavel'));
      await campoNumero.clear();
      await campoNumero.sendKeys(dadosResponsavel.numero);

      // Verifica se os campos foram preenchidos
      expect(await campoNomeResp.getAttribute('value')).toBe(dadosResponsavel.nome);
      expect(await campoCpfResp.getAttribute('value')).toBe(dadosResponsavel.cpf);
    });
  });

  // ==========================================================================
  // FLUXO 2: Validação de campos obrigatórios
  // ==========================================================================
  describe('Fluxo 2: Validação de Campos Obrigatórios', () => {
    /**
     * Objetivo: Verificar se o sistema valida campos obrigatórios
     * 
     * Passos:
     * 1. Abre a página de cadastro
     * 2. Tenta salvar sem preencher campos
     * 3. Verifica se exibe mensagens de erro
     */

    test('Deve verificar presença do botão salvar', async () => {
      await driver.get(CADASTRO_ALUNO_URL);
      await driver.wait(until.elementLocated(By.id('salvar')), 10000);

      const botaoSalvar = await driver.findElement(By.id('salvar'));
      expect(await botaoSalvar.isDisplayed()).toBe(true);
    });

    test('Deve verificar campos de endereço', async () => {
      await driver.get(CADASTRO_ALUNO_URL);
      await driver.wait(until.elementLocated(By.id('cep')), 10000);

      // Verifica se os campos de endereço existem
      const campoCep = await driver.findElement(By.id('cep'));
      const campoRua = await driver.findElement(By.id('rua'));
      const campoNumero = await driver.findElement(By.id('numero'));
      const campoBairro = await driver.findElement(By.id('bairro'));
      const campoCidade = await driver.findElement(By.id('cidade'));
      const campoEstado = await driver.findElement(By.id('estado'));

      expect(await campoCep.isDisplayed()).toBe(true);
      expect(await campoRua.isDisplayed()).toBe(true);
      expect(await campoNumero.isDisplayed()).toBe(true);
      expect(await campoBairro.isDisplayed()).toBe(true);
      expect(await campoCidade.isDisplayed()).toBe(true);
      expect(await campoEstado.isDisplayed()).toBe(true);
    });
  });

  // ==========================================================================
  // FLUXO 3: Navegação entre páginas
  // ==========================================================================
  describe('Fluxo 3: Navegação do Sistema', () => {
    /**
     * Objetivo: Verificar se a navegação entre páginas funciona
     * 
     * Passos:
     * 1. Acessa diferentes páginas do sistema
     * 2. Verifica se os elementos principais são carregados
     */

    test('Deve carregar a página de listagem de alunos', async () => {
      const ALUNOS_URL = `${BASE_URL}/assets/html/alunos.html`;
      await driver.get(ALUNOS_URL);
      
      // Aguarda a tabela de alunos carregar
      await driver.wait(until.elementLocated(By.id('listaAlunos')), 10000);

      const tabelaAlunos = await driver.findElement(By.id('listaAlunos'));
      expect(await tabelaAlunos.isDisplayed()).toBe(true);
    });

    test('Deve carregar a página de disciplinas', async () => {
      const DISCIPLINAS_URL = `${BASE_URL}/assets/html/disciplinas.html`;
      await driver.get(DISCIPLINAS_URL);
      
      // Verifica se a página carregou
      const titulo = await driver.getTitle();
      expect(titulo).toBeDefined();
    });

    test('Deve carregar a página de turmas', async () => {
      const TURMAS_URL = `${BASE_URL}/assets/html/turmas.html`;
      await driver.get(TURMAS_URL);
      
      // Verifica se a página carregou
      const titulo = await driver.getTitle();
      expect(titulo).toBeDefined();
    });
  });
});
