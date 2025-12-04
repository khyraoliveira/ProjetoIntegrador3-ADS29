module.exports = {
  // Ambiente de teste para simular o DOM do navegador
  testEnvironment: 'jsdom',
  
  // Mostra detalhes de cada teste executado
  verbose: true,
  
  // Padrões de arquivos de teste
  testMatch: [
    '**/unit/**/*.test.js',
    '**/functional/**/*.functional.test.js'
  ],
  
  // Cobertura de código
  collectCoverageFrom: [
    '../frontend/assets/JS/**/*.js',
    '!**/node_modules/**'
  ],
  
  // Diretório de saída para relatórios de cobertura
  coverageDirectory: './coverage',
  
  // Configuração de setup antes dos testes
  setupFilesAfterEnv: ['<rootDir>/setup.js'],
  
  // Timeout para testes (útil para testes funcionais)
  testTimeout: 30000,
  
  // Transformações (se necessário no futuro)
  transform: {},
  
  // Módulos a serem ignorados
  modulePathIgnorePatterns: ['node_modules']
};
