/**
 * Testes Funcionais - Fluxo de Login
 * 
 * Este arquivo contém testes funcionais automatizados para o fluxo de login
 * do Sistema de Gerenciamento Escolar usando Selenium WebDriver.
 * 
 * Ferramentas: Selenium WebDriver, Jest
 * Autor: Equipe PI3-ADS29
 */

const { Builder, By, until, Key } = require('selenium-webdriver');
const chrome = require('selenium-webdriver/chrome');

// Configuração do timeout para testes funcionais
jest.setTimeout(60000);

describe('Testes Funcionais - Fluxo de Login', () => {
  let driver;
  
  // URL base do frontend (ajuste conforme necessário)
  const BASE_URL = 'http://localhost:8080';

  // Configuração antes de todos os testes
  beforeAll(async () => {
    // Configuração do Chrome em modo headless para CI
    const options = new chrome.Options();
    options.addArguments('--headless'); // Executar sem interface gráfica
    options.addArguments('--no-sandbox');
    options.addArguments('--disable-dev-shm-usage');
    options.addArguments('--disable-gpu');
    options.addArguments('--window-size=1920,1080');

    driver = await new Builder()
      .forBrowser('chrome')
      .setChromeOptions(options)
      .build();
  });

  // Limpeza após todos os testes
  afterAll(async () => {
    if (driver) {
      await driver.quit();
    }
  });

  // Limpeza após cada teste
  afterEach(async () => {
    // Limpa cookies e storage entre testes
    await driver.manage().deleteAllCookies();
  });

  // ==========================================================================
  // FLUXO 1: Login bem-sucedido como Professor
  // ==========================================================================
  describe('Fluxo 1: Login como Professor', () => {
    /**
     * Objetivo: Verificar se o login como Professor funciona corretamente
     * 
     * Passos:
     * 1. Abre a página de login
     * 2. Seleciona o cargo "Professor"
     * 3. Preenche a matrícula
     * 4. Preenche a senha
     * 5. Clica no botão "Entrar"
     * 6. Verifica se foi redirecionado para a página do professor
     */

    test('Deve fazer login com sucesso como Professor', async () => {
      // Passo 1: Abre a página de login
      await driver.get(BASE_URL);
      
      // Aguarda a página carregar
      await driver.wait(until.elementLocated(By.id('profi')), 10000);

      // Passo 2: Seleciona o cargo "Professor"
      const selectCargo = await driver.findElement(By.id('profi'));
      await selectCargo.sendKeys('Professor');

      // Passo 3: Preenche a matrícula
      const inputMatricula = await driver.findElement(By.id('mtr'));
      await inputMatricula.clear();
      await inputMatricula.sendKeys('123456');

      // Passo 4: Preenche a senha
      const inputSenha = await driver.findElement(By.id('password'));
      await inputSenha.clear();
      await inputSenha.sendKeys('senha123');

      // Passo 5: Clica no botão "Entrar"
      const botaoEntrar = await driver.findElement(By.id('botao'));
      await botaoEntrar.click();

      // Passo 6: Verifica o redirecionamento
      await driver.wait(async () => {
        const url = await driver.getCurrentUrl();
        return url.includes('prof_inicio.html');
      }, 10000);

      const urlAtual = await driver.getCurrentUrl();
      expect(urlAtual).toContain('prof_inicio.html');
    });

    test('Deve exibir erro com credenciais inválidas', async () => {
      await driver.get(BASE_URL);
      await driver.wait(until.elementLocated(By.id('profi')), 10000);

      // Preenche com credenciais inválidas
      const selectCargo = await driver.findElement(By.id('profi'));
      await selectCargo.sendKeys('Professor');

      const inputMatricula = await driver.findElement(By.id('mtr'));
      await inputMatricula.clear();
      await inputMatricula.sendKeys('999999');

      const inputSenha = await driver.findElement(By.id('password'));
      await inputSenha.clear();
      await inputSenha.sendKeys('senhaerrada');

      const botaoEntrar = await driver.findElement(By.id('botao'));
      await botaoEntrar.click();

      // Aguarda o alert aparecer
      await driver.wait(until.alertIsPresent(), 5000);
      
      const alert = await driver.switchTo().alert();
      const textoAlert = await alert.getText();
      
      expect(textoAlert).toContain('incorretos');
      
      await alert.accept();
    });
  });

  // ==========================================================================
  // FLUXO 2: Login bem-sucedido como Coordenador
  // ==========================================================================
  describe('Fluxo 2: Login como Coordenador', () => {
    /**
     * Objetivo: Verificar se o login como Coordenador funciona corretamente
     * 
     * Passos:
     * 1. Abre a página de login
     * 2. Seleciona o cargo "Coordenador"
     * 3. Preenche a matrícula
     * 4. Preenche a senha
     * 5. Clica no botão "Entrar"
     * 6. Verifica se foi redirecionado para a página do coordenador
     */

    test('Deve fazer login com sucesso como Coordenador', async () => {
      await driver.get(BASE_URL);
      await driver.wait(until.elementLocated(By.id('profi')), 10000);

      // Seleciona Coordenador
      const selectCargo = await driver.findElement(By.id('profi'));
      await selectCargo.sendKeys('Coordenador');

      // Preenche credenciais
      const inputMatricula = await driver.findElement(By.id('mtr'));
      await inputMatricula.clear();
      await inputMatricula.sendKeys('654321');

      const inputSenha = await driver.findElement(By.id('password'));
      await inputSenha.clear();
      await inputSenha.sendKeys('senha123');

      // Clica em entrar
      const botaoEntrar = await driver.findElement(By.id('botao'));
      await botaoEntrar.click();

      // Verifica redirecionamento para página do coordenador
      await driver.wait(async () => {
        const url = await driver.getCurrentUrl();
        return url.includes('inicio.html');
      }, 10000);

      const urlAtual = await driver.getCurrentUrl();
      expect(urlAtual).toContain('inicio.html');
    });
  });

  // ==========================================================================
  // FLUXO 3: Validação de campos vazios no login
  // ==========================================================================
  describe('Fluxo 3: Validação de Campos Vazios', () => {
    /**
     * Objetivo: Verificar se o sistema valida campos vazios no login
     * 
     * Passos:
     * 1. Abre a página de login
     * 2. Deixa os campos vazios
     * 3. Clica no botão "Entrar"
     * 4. Verifica se exibe mensagem de erro apropriada
     */

    test('Deve exibir erro ao tentar login com matrícula vazia', async () => {
      await driver.get(BASE_URL);
      await driver.wait(until.elementLocated(By.id('profi')), 10000);

      // Deixa matrícula vazia, preenche apenas senha
      const inputSenha = await driver.findElement(By.id('password'));
      await inputSenha.clear();
      await inputSenha.sendKeys('senha123');

      const botaoEntrar = await driver.findElement(By.id('botao'));
      await botaoEntrar.click();

      // Aguarda o alert
      await driver.wait(until.alertIsPresent(), 5000);
      
      const alert = await driver.switchTo().alert();
      const textoAlert = await alert.getText();
      
      // Verifica se a mensagem indica problema com a matrícula
      expect(textoAlert.toLowerCase()).toMatch(/matrícula|números|incorretos/);
      
      await alert.accept();
    });

    test('Deve exibir erro ao tentar login com matrícula não numérica', async () => {
      await driver.get(BASE_URL);
      await driver.wait(until.elementLocated(By.id('profi')), 10000);

      // Preenche matrícula com letras
      const inputMatricula = await driver.findElement(By.id('mtr'));
      await inputMatricula.clear();
      await inputMatricula.sendKeys('abcdef');

      const inputSenha = await driver.findElement(By.id('password'));
      await inputSenha.clear();
      await inputSenha.sendKeys('senha123');

      const botaoEntrar = await driver.findElement(By.id('botao'));
      await botaoEntrar.click();

      // Aguarda o alert
      await driver.wait(until.alertIsPresent(), 5000);
      
      const alert = await driver.switchTo().alert();
      const textoAlert = await alert.getText();
      
      expect(textoAlert).toContain('números');
      
      await alert.accept();
    });
  });
});
