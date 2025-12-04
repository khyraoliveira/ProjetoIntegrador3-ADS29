/**
 * Arquivo de configuração inicial para os testes Jest
 * Este arquivo é executado antes de cada suite de testes
 */

// Configuração global para simular o ambiente do navegador
global.console = {
  ...console,
  // Silencia logs durante os testes (descomente se necessário)
  // log: jest.fn(),
  // warn: jest.fn(),
  error: console.error,
};

// Mock do fetch API para testes unitários
global.fetch = jest.fn();

// Mock do alert para evitar erros durante os testes
global.alert = jest.fn();

// Mock do window.location
delete window.location;
window.location = {
  href: '',
  reload: jest.fn(),
};

// Limpa todos os mocks após cada teste
afterEach(() => {
  jest.clearAllMocks();
});
