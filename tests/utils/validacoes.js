/**
 * Funções utilitárias de validação para o Sistema de Gerenciamento Escolar
 * Estas funções são extraídas/baseadas no código do frontend para serem testadas
 */

/**
 * Formata uma data do formato YYYY-MM-DD para DD/MM/YYYY
 * @param {string} data - Data no formato YYYY-MM-DD
 * @returns {string|undefined} - Data formatada ou undefined se inválida
 */
function formatarData(data) {
  if (!data) return undefined;
  
  if (!data.includes('-')) {
    // Verifica se já está no formato DD/MM/YYYY
    if (/^\d{2}\/\d{2}\/\d{4}$/.test(data)) {
      return data;
    }
    return undefined;
  }
  
  const partes = data.split('-');
  if (partes.length === 3) {
    const [ano, mes, dia] = partes;
    // Validação básica
    if (ano.length === 4 && mes.length === 2 && dia.length === 2) {
      return `${dia}/${mes}/${ano}`;
    }
  }
  return undefined;
}

/**
 * Valida se um CPF tem o formato correto (apenas números, 11 dígitos)
 * @param {string} cpf - CPF a ser validado
 * @returns {boolean} - true se válido, false caso contrário
 */
function validarCPF(cpf) {
  if (!cpf) return false;
  
  // Remove caracteres não numéricos
  const cpfLimpo = cpf.replace(/\D/g, '');
  
  // Verifica se tem 11 dígitos
  if (cpfLimpo.length !== 11) return false;
  
  // Verifica se todos os dígitos são iguais
  if (/^(\d)\1+$/.test(cpfLimpo)) return false;
  
  return true;
}

/**
 * Valida se um email tem formato válido
 * @param {string} email - Email a ser validado
 * @returns {boolean} - true se válido, false caso contrário
 */
function validarEmail(email) {
  if (!email) return false;
  
  const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regexEmail.test(email);
}

/**
 * Valida se a matrícula contém apenas números
 * @param {string} matricula - Matrícula a ser validada
 * @returns {boolean} - true se válida, false caso contrário
 */
function validarMatricula(matricula) {
  if (!matricula) return false;
  
  const matriculaNumero = parseInt(matricula, 10);
  return !isNaN(matriculaNumero) && matriculaNumero > 0;
}

/**
 * Valida campos obrigatórios de um formulário de aluno
 * @param {Object} aluno - Objeto com dados do aluno
 * @returns {Object} - { valido: boolean, erros: string[] }
 */
function validarCamposObrigatoriosAluno(aluno) {
  const erros = [];
  
  if (!aluno.nome || aluno.nome.trim().length < 3) {
    erros.push('Nome deve ter pelo menos 3 caracteres');
  }
  
  if (!aluno.ultimoNome || aluno.ultimoNome.trim().length < 3) {
    erros.push('Sobrenome deve ter pelo menos 3 caracteres');
  }
  
  if (!validarCPF(aluno.cpf)) {
    erros.push('CPF inválido');
  }
  
  if (!validarEmail(aluno.email)) {
    erros.push('Email inválido');
  }
  
  if (!aluno.genero) {
    erros.push('Gênero é obrigatório');
  }
  
  if (!aluno.data_nascimento) {
    erros.push('Data de nascimento é obrigatória');
  }
  
  return {
    valido: erros.length === 0,
    erros
  };
}

/**
 * Valida campos obrigatórios de uma disciplina
 * @param {Object} disciplina - Objeto com dados da disciplina
 * @returns {Object} - { valido: boolean, erros: string[] }
 */
function validarCamposObrigatoriosDisciplina(disciplina) {
  const erros = [];
  
  if (!disciplina.nome || disciplina.nome.trim().length < 3) {
    erros.push('Nome da disciplina deve ter pelo menos 3 caracteres');
  }
  
  if (!disciplina.cargaHoraria || isNaN(disciplina.cargaHoraria) || disciplina.cargaHoraria <= 0) {
    erros.push('Carga horária deve ser um número positivo');
  }
  
  if (!disciplina.professorId) {
    erros.push('Professor é obrigatório');
  }
  
  if (!disciplina.turmaId || isNaN(disciplina.turmaId)) {
    erros.push('Turma é obrigatória');
  }
  
  return {
    valido: erros.length === 0,
    erros
  };
}

/**
 * Valida o formato de um CEP
 * @param {string} cep - CEP a ser validado
 * @returns {boolean} - true se válido, false caso contrário
 */
function validarCEP(cep) {
  if (!cep) return false;
  
  const cepLimpo = cep.replace(/\D/g, '');
  return cepLimpo.length === 8;
}

/**
 * Valida o formato de um telefone (DDD + número)
 * @param {string} ddd - DDD do telefone
 * @param {string} numero - Número do telefone
 * @returns {boolean} - true se válido, false caso contrário
 */
function validarTelefone(ddd, numero) {
  if (!ddd || !numero) return false;
  
  const dddLimpo = ddd.replace(/\D/g, '');
  const numeroLimpo = numero.replace(/\D/g, '');
  
  // DDD deve ter 2 dígitos
  if (dddLimpo.length !== 2) return false;
  
  // Número deve ter 8 ou 9 dígitos
  if (numeroLimpo.length < 8 || numeroLimpo.length > 9) return false;
  
  return true;
}

/**
 * Valida credenciais de login
 * @param {string} matricula - Matrícula do usuário
 * @param {string} senha - Senha do usuário
 * @param {string} cargo - Cargo selecionado
 * @returns {Object} - { valido: boolean, erros: string[] }
 */
function validarLogin(matricula, senha, cargo) {
  const erros = [];
  
  if (!validarMatricula(matricula)) {
    erros.push('Matrícula deve conter apenas números');
  }
  
  if (!senha || senha.length < 6) {
    erros.push('Senha deve ter pelo menos 6 caracteres');
  }
  
  if (!cargo || !['Professor', 'Coordenador'].includes(cargo)) {
    erros.push('Cargo inválido');
  }
  
  return {
    valido: erros.length === 0,
    erros
  };
}

// Exporta as funções para uso nos testes
module.exports = {
  formatarData,
  validarCPF,
  validarEmail,
  validarMatricula,
  validarCamposObrigatoriosAluno,
  validarCamposObrigatoriosDisciplina,
  validarCEP,
  validarTelefone,
  validarLogin
};
