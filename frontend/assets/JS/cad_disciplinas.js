document.addEventListener('DOMContentLoaded', function () {
    carregarProfessores();
    carregarTurmas();
});

// Carrega os professores, mas ao invés do id, usaremos o CPF como o valor do option
function carregarProfessores() {
    fetch('http://localhost:8080/professores')
        .then(response => response.json())
        .then(data => {
            console.log(data);  // Para verificar se a resposta está correta
            if (!Array.isArray(data)) {
                throw new Error('A resposta não é um array.');
            }
            const selectProfessor = document.getElementById('professorId');
            data.forEach(professor => {
                const option = document.createElement('option');
                option.value = professor.cpf;  // Usar o CPF como identificador
                option.textContent = `${professor.nome} ${professor.ultimoNome}`;
                selectProfessor.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar professores:', error);
        });
}

// Carrega as turmas
function carregarTurmas() {
    fetch('http://localhost:8080/turmas')
        .then(response => response.json())
        .then(data => {
            console.log(data);  // Para verificar se a resposta está correta
            if (!Array.isArray(data)) {
                throw new Error('A resposta não é um array.');
            }
            const selectTurma = document.getElementById('turmaId');
            data.forEach(turma => {
                const option = document.createElement('option');
                option.value = Number(turma.id);  // Garantir que o ID seja um número
                option.textContent = turma.nome;
                selectTurma.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar turmas:', error);
        });
}

// Ao clicar em salvar, enviamos o CPF do professor e o ID da turma para o backend
document.getElementById('salvar').addEventListener('click', function(event) {
    event.preventDefault();

    const nome = document.getElementById('nome').value;
    const cargaHoraria = parseInt(document.getElementById('cargaHoraria').value, 10);
    const professorCpf = document.getElementById('professorId').value;  // O CPF é o professorId
    const turmaId = parseInt(document.getElementById('turmaId').value, 10);  // Garantir que seja número inteiro

    if (!nome || isNaN(cargaHoraria) || isNaN(turmaId) || !professorCpf) {
        alert("Por favor, preencha todos os campos corretamente.");
        return;
    }

    const disciplina = {
        nome: nome,
        cargaHoraria: cargaHoraria,
        professorId: professorCpf,  // O professorId é o CPF
        turmaId: turmaId  // Deve ser um número inteiro
    };

    console.log(disciplina);  // Exibe o objeto para depuração

    fetch('http://localhost:8080/disciplinas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(disciplina)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao cadastrar disciplina');
        }
        return response.json();
    })
    .then(data => {
        console.log('Disciplina cadastrada com sucesso:', data);
        alert('Disciplina cadastrada com sucesso!');
        window.location.href = 'disciplinas.html';
    })
    .catch(error => {
        console.error('Erro ao cadastrar disciplina:', error);
        alert('Erro ao cadastrar disciplina.');
    });
});
