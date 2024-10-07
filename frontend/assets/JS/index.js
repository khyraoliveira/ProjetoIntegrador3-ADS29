const usuarios = [
  { matricula: '123456', senha: 'senha123', cargo: 'Professor' },
  { matricula: '654321', senha: 'senha123', cargo: 'Coordenador' }
];

/**
 * Função para verificar login do usuário.
 * @param {Event} event - O evento de submissão do formulário.
 */
function verificarLogin(event) {
  // Evita o comportamento padrão do formulário (recarregar a página)
  event.preventDefault();

  // Captura os valores inseridos nos campos de matrícula, senha e cargo
  const matriculaInput = document.getElementById('mtr').value;
  const senhaInput = document.getElementById('password').value;
  const cargoSelecionado = document.getElementById('profi').value;

  // Converte o valor da matrícula para número
  const matriculaNumero = parseInt(matriculaInput, 10);

  // Verifica se a matrícula contém apenas números
  if (isNaN(matriculaNumero)) {
    alert('A matrícula deve conter apenas números.');
    return;
  }

  // Procura o usuário no banco de dados simulado
  const usuarioEncontrado = usuarios.find(
    usuario => 
      usuario.matricula === matriculaNumero.toString() && 
      usuario.senha === senhaInput &&
      usuario.cargo === cargoSelecionado
  );

  // Verifica se o usuário foi encontrado
  if (usuarioEncontrado) {
    // Verifica o cargo do usuário e redireciona para a página correspondente
    if (usuarioEncontrado.cargo === 'Professor') {
      window.location.href = './assets/html/prof_inicio.html';
    } else if (usuarioEncontrado.cargo === 'Coordenador') {
      window.location.href = './assets/html/inicio.html';
    }
  } else {
    // Exibe uma mensagem de erro se as credenciais ou cargo estiverem incorretos
    alert('Matrícula, senha ou cargo incorretos.');
  }
}

// Adiciona o evento de clique ao botão de login
document.getElementById('botao').addEventListener('click', verificarLogin);
