/**
 * Testes Unitários - Validações do Sistema de Gerenciamento Escolar
 * 
 * Este arquivo contém testes unitários para as funções de validação
 * utilizadas no frontend do sistema SGE.
 * 
 * Ferramentas: Jest
 * Autor: Equipe PI3-ADS29
 */

const {
  formatarData,
  validarCPF,
  validarEmail,
  validarMatricula,
  validarCamposObrigatoriosAluno,
  validarCamposObrigatoriosDisciplina,
  validarCEP,
  validarTelefone,
  validarLogin
} = require('../utils/validacoes');

// ============================================================================
// TESTE 1: Validação e Formatação de Datas
// ============================================================================
describe('Teste 1: formatarData - Formatação de Datas', () => {
  /**
   * Objetivo: Verificar se a função formata corretamente datas do formato
   * YYYY-MM-DD (padrão input date) para DD/MM/YYYY (padrão brasileiro)
   */

  test('Deve formatar data de YYYY-MM-DD para DD/MM/YYYY', () => {
    // Input: Data no formato ISO (YYYY-MM-DD)
    // Output esperado: Data no formato brasileiro (DD/MM/YYYY)
    const resultado = formatarData('2024-12-02');
    expect(resultado).toBe('02/12/2024');
  });

  test('Deve retornar a mesma data se já estiver no formato DD/MM/YYYY', () => {
    const resultado = formatarData('02/12/2024');
    expect(resultado).toBe('02/12/2024');
  });

  test('Deve retornar undefined para data vazia', () => {
    expect(formatarData('')).toBeUndefined();
    expect(formatarData(null)).toBeUndefined();
    expect(formatarData(undefined)).toBeUndefined();
  });

  test('Deve retornar undefined para formato inválido', () => {
    expect(formatarData('2024/12/02')).toBeUndefined();
    expect(formatarData('02-12-2024')).toBeUndefined();
    expect(formatarData('data-invalida')).toBeUndefined();
  });

  test('Deve formatar corretamente datas com meses e dias de um dígito', () => {
    const resultado = formatarData('2024-01-05');
    expect(resultado).toBe('05/01/2024');
  });
});

// ============================================================================
// TESTE 2: Validação de CPF
// ============================================================================
describe('Teste 2: validarCPF - Validação de CPF', () => {
  /**
   * Objetivo: Verificar se a função valida corretamente o formato do CPF
   * (11 dígitos numéricos, não todos iguais)
   */

  test('Deve aceitar CPF válido com 11 dígitos', () => {
    expect(validarCPF('12345678901')).toBe(true);
    expect(validarCPF('98765432100')).toBe(true);
  });

  test('Deve aceitar CPF com formatação (pontos e traço)', () => {
    expect(validarCPF('123.456.789-01')).toBe(true);
    expect(validarCPF('987.654.321-00')).toBe(true);
  });

  test('Deve rejeitar CPF com menos de 11 dígitos', () => {
    expect(validarCPF('1234567890')).toBe(false);
    expect(validarCPF('123')).toBe(false);
  });

  test('Deve rejeitar CPF com mais de 11 dígitos', () => {
    expect(validarCPF('123456789012')).toBe(false);
  });

  test('Deve rejeitar CPF com todos os dígitos iguais', () => {
    expect(validarCPF('11111111111')).toBe(false);
    expect(validarCPF('00000000000')).toBe(false);
    expect(validarCPF('99999999999')).toBe(false);
  });

  test('Deve rejeitar CPF vazio ou nulo', () => {
    expect(validarCPF('')).toBe(false);
    expect(validarCPF(null)).toBe(false);
    expect(validarCPF(undefined)).toBe(false);
  });
});

// ============================================================================
// TESTE 3: Validação de Email
// ============================================================================
describe('Teste 3: validarEmail - Validação de Email', () => {
  /**
   * Objetivo: Verificar se a função valida corretamente o formato de email
   */

  test('Deve aceitar emails válidos', () => {
    expect(validarEmail('usuario@email.com')).toBe(true);
    expect(validarEmail('aluno.teste@escola.edu.br')).toBe(true);
    expect(validarEmail('professor123@senac.com.br')).toBe(true);
  });

  test('Deve rejeitar emails sem @', () => {
    expect(validarEmail('usuarioemail.com')).toBe(false);
  });

  test('Deve rejeitar emails sem domínio', () => {
    expect(validarEmail('usuario@')).toBe(false);
    expect(validarEmail('usuario@.com')).toBe(false);
  });

  test('Deve rejeitar emails sem nome de usuário', () => {
    expect(validarEmail('@email.com')).toBe(false);
  });

  test('Deve rejeitar emails vazios ou nulos', () => {
    expect(validarEmail('')).toBe(false);
    expect(validarEmail(null)).toBe(false);
    expect(validarEmail(undefined)).toBe(false);
  });

  test('Deve rejeitar emails com espaços', () => {
    expect(validarEmail('usuario @email.com')).toBe(false);
    expect(validarEmail('usuario@ email.com')).toBe(false);
  });
});

// ============================================================================
// TESTE 4: Validação de Campos Obrigatórios do Aluno
// ============================================================================
describe('Teste 4: validarCamposObrigatoriosAluno - Campos Obrigatórios', () => {
  /**
   * Objetivo: Verificar se a função valida corretamente todos os campos
   * obrigatórios do formulário de cadastro de aluno
   */

  const alunoValido = {
    nome: 'João',
    ultimoNome: 'Silva',
    cpf: '12345678901',
    email: 'joao@email.com',
    genero: 'Masculino',
    data_nascimento: '2000-01-01'
  };

  test('Deve aceitar aluno com todos os campos válidos', () => {
    const resultado = validarCamposObrigatoriosAluno(alunoValido);
    expect(resultado.valido).toBe(true);
    expect(resultado.erros).toHaveLength(0);
  });

  test('Deve rejeitar aluno sem nome', () => {
    const aluno = { ...alunoValido, nome: '' };
    const resultado = validarCamposObrigatoriosAluno(aluno);
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('Nome deve ter pelo menos 3 caracteres');
  });

  test('Deve rejeitar aluno com nome muito curto', () => {
    const aluno = { ...alunoValido, nome: 'Jo' };
    const resultado = validarCamposObrigatoriosAluno(aluno);
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('Nome deve ter pelo menos 3 caracteres');
  });

  test('Deve rejeitar aluno sem sobrenome', () => {
    const aluno = { ...alunoValido, ultimoNome: '' };
    const resultado = validarCamposObrigatoriosAluno(aluno);
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('Sobrenome deve ter pelo menos 3 caracteres');
  });

  test('Deve rejeitar aluno com CPF inválido', () => {
    const aluno = { ...alunoValido, cpf: '123' };
    const resultado = validarCamposObrigatoriosAluno(aluno);
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('CPF inválido');
  });

  test('Deve rejeitar aluno com email inválido', () => {
    const aluno = { ...alunoValido, email: 'email-invalido' };
    const resultado = validarCamposObrigatoriosAluno(aluno);
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('Email inválido');
  });

  test('Deve rejeitar aluno sem gênero', () => {
    const aluno = { ...alunoValido, genero: '' };
    const resultado = validarCamposObrigatoriosAluno(aluno);
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('Gênero é obrigatório');
  });

  test('Deve rejeitar aluno sem data de nascimento', () => {
    const aluno = { ...alunoValido, data_nascimento: '' };
    const resultado = validarCamposObrigatoriosAluno(aluno);
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('Data de nascimento é obrigatória');
  });

  test('Deve retornar múltiplos erros quando vários campos são inválidos', () => {
    const alunoInvalido = {
      nome: '',
      ultimoNome: '',
      cpf: '',
      email: '',
      genero: '',
      data_nascimento: ''
    };
    const resultado = validarCamposObrigatoriosAluno(alunoInvalido);
    expect(resultado.valido).toBe(false);
    expect(resultado.erros.length).toBeGreaterThan(1);
  });
});

// ============================================================================
// TESTE 5: Validação de Campos Obrigatórios da Disciplina
// ============================================================================
describe('Teste 5: validarCamposObrigatoriosDisciplina - Campos Obrigatórios', () => {
  /**
   * Objetivo: Verificar se a função valida corretamente todos os campos
   * obrigatórios do formulário de cadastro de disciplina
   */

  const disciplinaValida = {
    nome: 'Matemática',
    cargaHoraria: 60,
    professorId: '12345678901',
    turmaId: 1
  };

  test('Deve aceitar disciplina com todos os campos válidos', () => {
    const resultado = validarCamposObrigatoriosDisciplina(disciplinaValida);
    expect(resultado.valido).toBe(true);
    expect(resultado.erros).toHaveLength(0);
  });

  test('Deve rejeitar disciplina sem nome', () => {
    const disciplina = { ...disciplinaValida, nome: '' };
    const resultado = validarCamposObrigatoriosDisciplina(disciplina);
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('Nome da disciplina deve ter pelo menos 3 caracteres');
  });

  test('Deve rejeitar disciplina com carga horária zero ou negativa', () => {
    let disciplina = { ...disciplinaValida, cargaHoraria: 0 };
    let resultado = validarCamposObrigatoriosDisciplina(disciplina);
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('Carga horária deve ser um número positivo');

    disciplina = { ...disciplinaValida, cargaHoraria: -10 };
    resultado = validarCamposObrigatoriosDisciplina(disciplina);
    expect(resultado.valido).toBe(false);
  });

  test('Deve rejeitar disciplina sem professor', () => {
    const disciplina = { ...disciplinaValida, professorId: '' };
    const resultado = validarCamposObrigatoriosDisciplina(disciplina);
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('Professor é obrigatório');
  });

  test('Deve rejeitar disciplina sem turma', () => {
    const disciplina = { ...disciplinaValida, turmaId: null };
    const resultado = validarCamposObrigatoriosDisciplina(disciplina);
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('Turma é obrigatória');
  });
});

// ============================================================================
// TESTE 6: Validação de CEP
// ============================================================================
describe('Teste 6: validarCEP - Validação de CEP', () => {
  /**
   * Objetivo: Verificar se a função valida corretamente o formato do CEP
   */

  test('Deve aceitar CEP válido com 8 dígitos', () => {
    expect(validarCEP('12345678')).toBe(true);
    expect(validarCEP('01310100')).toBe(true);
  });

  test('Deve aceitar CEP com formatação (traço)', () => {
    expect(validarCEP('12345-678')).toBe(true);
    expect(validarCEP('01310-100')).toBe(true);
  });

  test('Deve rejeitar CEP com menos de 8 dígitos', () => {
    expect(validarCEP('1234567')).toBe(false);
    expect(validarCEP('123')).toBe(false);
  });

  test('Deve rejeitar CEP com mais de 8 dígitos', () => {
    expect(validarCEP('123456789')).toBe(false);
  });

  test('Deve rejeitar CEP vazio ou nulo', () => {
    expect(validarCEP('')).toBe(false);
    expect(validarCEP(null)).toBe(false);
  });
});

// ============================================================================
// TESTE 7: Validação de Telefone
// ============================================================================
describe('Teste 7: validarTelefone - Validação de Telefone', () => {
  /**
   * Objetivo: Verificar se a função valida corretamente o formato do telefone
   */

  test('Deve aceitar telefone válido com 8 dígitos', () => {
    expect(validarTelefone('11', '12345678')).toBe(true);
  });

  test('Deve aceitar telefone válido com 9 dígitos (celular)', () => {
    expect(validarTelefone('11', '912345678')).toBe(true);
  });

  test('Deve rejeitar DDD inválido', () => {
    expect(validarTelefone('1', '12345678')).toBe(false);
    expect(validarTelefone('111', '12345678')).toBe(false);
  });

  test('Deve rejeitar número com menos de 8 dígitos', () => {
    expect(validarTelefone('11', '1234567')).toBe(false);
  });

  test('Deve rejeitar número com mais de 9 dígitos', () => {
    expect(validarTelefone('11', '1234567890')).toBe(false);
  });

  test('Deve rejeitar telefone sem DDD ou número', () => {
    expect(validarTelefone('', '12345678')).toBe(false);
    expect(validarTelefone('11', '')).toBe(false);
    expect(validarTelefone('', '')).toBe(false);
  });
});

// ============================================================================
// TESTE 8: Validação de Login
// ============================================================================
describe('Teste 8: validarLogin - Validação de Credenciais de Login', () => {
  /**
   * Objetivo: Verificar se a função valida corretamente as credenciais de login
   */

  test('Deve aceitar login válido de Professor', () => {
    const resultado = validarLogin('123456', 'senha123', 'Professor');
    expect(resultado.valido).toBe(true);
    expect(resultado.erros).toHaveLength(0);
  });

  test('Deve aceitar login válido de Coordenador', () => {
    const resultado = validarLogin('654321', 'senha123', 'Coordenador');
    expect(resultado.valido).toBe(true);
    expect(resultado.erros).toHaveLength(0);
  });

  test('Deve rejeitar matrícula não numérica', () => {
    const resultado = validarLogin('abc123', 'senha123', 'Professor');
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('Matrícula deve conter apenas números');
  });

  test('Deve rejeitar senha muito curta', () => {
    const resultado = validarLogin('123456', '12345', 'Professor');
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('Senha deve ter pelo menos 6 caracteres');
  });

  test('Deve rejeitar cargo inválido', () => {
    const resultado = validarLogin('123456', 'senha123', 'Aluno');
    expect(resultado.valido).toBe(false);
    expect(resultado.erros).toContain('Cargo inválido');
  });

  test('Deve rejeitar login com campos vazios', () => {
    const resultado = validarLogin('', '', '');
    expect(resultado.valido).toBe(false);
    expect(resultado.erros.length).toBeGreaterThan(0);
  });
});
