document.addEventListener('DOMContentLoaded', function () {
    const apiUrl = 'http://localhost:8080'; // Ajuste para a URL correta da sua API

    // Função para carregar as turmas no select
    const loadTurmas = async () => {
        try {
            const response = await fetch(`${apiUrl}/turmas`);
            if (!response.ok) {
                throw new Error('Erro ao buscar turmas');
            }
            const turmas = await response.json();
            const selectTurma = document.getElementById('al_turma');

            turmas.forEach(turma => {
                const option = document.createElement('option');
                option.value = turma.id; // O valor do option será o ID da turma
                option.text = `${turma.nome} - ${turma.anoEscolar} (${turma.turno})`; // Texto a ser exibido
                selectTurma.appendChild(option);
            });
        } catch (error) {
            console.error('Erro ao carregar turmas:', error);
        }
    };

    loadTurmas();

    // Função para salvar aluno
    document.getElementById('salvar').addEventListener('click', function(event) {
        event.preventDefault();

        const nome = document.getElementById('nome').value;
        const ultimoNome = document.getElementById('ultimoNome').value;
        const cpf = document.getElementById('cpf').value;
        const genero = document.getElementById('al_genero').value;
        const dataNascimento = document.getElementById('data_nascimento').value;
        const email = document.getElementById('email').value;
        const turmaId = document.getElementById('al_turma').value; // Captura o ID da turma selecionada

        const cep = document.getElementById('cep').value;
        const rua = document.getElementById('rua').value;
        const numero = document.getElementById('numero').value;
        const bairro = document.getElementById('bairro').value;
        const cidade = document.getElementById('cidade').value;
        const estado = document.getElementById('estado').value;

        const nomeResponsavel = document.getElementById('nome_responsavel').value;
        const ultimoNomeR = document.getElementById('ultimoNomeR').value;
        const cpfResponsavel = document.getElementById('cpfResponsavel').value;
        const dddResponsavel = document.getElementById('dddResponsavel').value;
        const numeroResponsavel = document.getElementById('numeroResponsavel').value;
        const grauParentesco = document.getElementById('grauParentesco').value;

        const aluno = {
            nome: nome,
            ultimoNome: ultimoNome,
            genero: genero,
            data_nascimento: dataNascimento,
            cpf: cpf,
            email: email,
            coordenacaoId: 1,
            turmasIds: turmaId !== 'null' ? [Number(turmaId)] : [], // Converte a string turmaId para número e coloca em um array
            enderecos: [
              {
                cep: cep,
                rua: rua,
                numero: numero,
                bairro: bairro,
                cidade: cidade,
                estado: estado
              }
            ],
            telefones: [
              {
                ddd: dddResponsavel,
                numero: numeroResponsavel
              }
            ],
            responsaveis: [
              {
                nome: nomeResponsavel,
                ultimoNome: ultimoNomeR,
                cpfResponsavel: cpfResponsavel,
                telefones: [
                  {
                    ddd: dddResponsavel,
                    numero: numeroResponsavel
                  }
                ],
                grauParentesco: grauParentesco
              }
            ]
        };

        console.log('JSON a ser enviado:', JSON.stringify(aluno, null, 2));

        fetch(`${apiUrl}/alunos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(aluno)
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return response.text().then(text => {
                    throw new Error(`Erro ao cadastrar um aluno: ${text}`);
                });
            }
        })
        .then(data => {
            console.log('Aluno cadastrado com sucesso:', data);
            alert('Aluno cadastrado com sucesso!');
            window.location.href = 'alunos.html';
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao cadastrar aluno.');
        });
    });
});
