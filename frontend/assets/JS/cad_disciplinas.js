document.addEventListener('DOMContentLoaded', function () {
    carregarProfessores();
    carregarTurmas();
});

function carregarProfessores() {
    fetch('http://localhost:8080/professores')
        .then(response => response.json())
        .then(data => {
            const selectProfessor = document.getElementById('professorId');
            data.forEach(professor => {
                const option = document.createElement('option');
                option.value = professor.id;
                option.textContent = `${professor.nome} ${professor.ultimoNome}`;
                selectProfessor.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar professores:', error);
        });
}

function carregarTurmas() {
    fetch('http://localhost:8080/turmas')
        .then(response => response.json())
        .then(data => {
            const selectTurma = document.getElementById('turmaId');
            data.forEach(turma => {
                const option = document.createElement('option');
                option.value = turma.id;
                option.textContent = turma.nome;
                selectTurma.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar turmas:', error);
        });
}

document.getElementById('salvar').addEventListener('click', function(event) {
    event.preventDefault();

    const nome = document.getElementById('nome').value;
    const cargaHoraria = parseInt(document.getElementById('cargaHoraria').value);
    const professorId = document.getElementById('professorId').value;
    const turmaId = document.getElementById('turmaId').value;

    const disciplina = {
        nome: nome,
        cargaHoraria: cargaHoraria,
        professorId: professorId,
        turmaId: turmaId
    };

    console.log(disciplina);
    
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
        window.location.href = 'disciplinas.html'
    })
    .catch(error => {
        console.error('Erro ao cadastrar disciplina:', error);
        alert('Erro ao cadastrar disciplina.')
    });
});
