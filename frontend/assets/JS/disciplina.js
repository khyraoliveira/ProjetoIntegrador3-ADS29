document.addEventListener('DOMContentLoaded', function () {
    const apiUrl = 'http://localhost:8080'; // Update this to match your API URL

    const disciplinaModal = document.getElementById('modalDisciplina');
    const disciplinaForm = document.getElementById('disciplinaForm');
    const modalTitleDisciplina = document.getElementById('modalTitleDisciplina');
    let editDisciplinaId = null;

    const loadDisciplinas = async () => {
        try {
            const response = await fetch(`${apiUrl}/disciplinas`);
            if (!response.ok) {
                throw new Error('Erro ao buscar disciplinas');
            }
            const disciplinas = await response.json();
            const tableBody = document.querySelector('#listaDisciplinas');
            tableBody.innerHTML = '';

            disciplinas.forEach(disciplina => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${disciplina.nome}</td>
                    <td>${disciplina.cargaHoraria ? disciplina.cargaHoraria : 'Sem carga horária'}</td>
                    <td>${disciplina.nomeProfessor ? disciplina.nomeProfessor : 'Sem professor'}</td>
                    <td>
                        <button class="editDisciplinaBtn" data-id="${disciplina.id}">Editar</button>
                        <button class="deleteDisciplinaBtn" data-id="${disciplina.id}">Deletar</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });

            document.querySelectorAll('.editDisciplinaBtn').forEach(button => {
                button.addEventListener('click', (e) => openEditDisciplinaModal(e.target.dataset.id));
            });

            document.querySelectorAll('.deleteDisciplinaBtn').forEach(button => {
                button.addEventListener('click', (e) => confirmDeleteDisciplina(e.target.dataset.id));
            });
        } catch (error) {
            console.error('Erro ao carregar disciplinas:', error);
        }
    };

    const confirmDeleteDisciplina = (id) => {
        if (confirm("Tem certeza que deseja deletar esta disciplina?")) {
            deleteDisciplina(id);
        }
    };

    const deleteDisciplina = async (id) => {
        try {
            const response = await fetch(`${apiUrl}/disciplinas/${id}`, {
                method: 'DELETE'
            });
            if (!response.ok) {
                throw new Error('Erro ao deletar disciplina');
            }
            loadDisciplinas(); // Recarrega a lista de disciplinas após a deleção
        } catch (error) {
            console.error('Erro ao deletar disciplina:', error);
        }
    };

    const updateDisciplina = async (id, disciplina) => {
        try {
            const response = await fetch(`${apiUrl}/disciplinas/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(disciplina)
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Erro ao atualizar disciplina: ${errorText}`);
            }

            loadDisciplinas();  // Recarrega a lista de disciplinas após a atualização
        } catch (error) {
            console.error('Erro ao atualizar disciplina:', error);
        }
    };

    const openEditDisciplinaModal = async (id) => {
        editDisciplinaId = id;
        modalTitleDisciplina.innerText = 'Editar Disciplina';

        try {
            const response = await fetch(`${apiUrl}/disciplinas/${id}`);
            if (!response.ok) {
                throw new Error(`Disciplina com ID ${id} não encontrada.`);
            }
            const disciplina = await response.json();

            // Preenche os campos com os dados da disciplina
            document.getElementById('nome').value = disciplina.nome;
            document.getElementById('cargaHoraria').value = disciplina.cargaHoraria !== null ? disciplina.cargaHoraria : '';

            // Carregar turmas e professores
            await loadTurmas(disciplina.turmaId);
            await loadProfessores(disciplina.professorId);

            disciplinaModal.style.display = 'block'; // Abre o modal para edição
        } catch (error) {
            console.error('Erro ao carregar disciplina:', error);
        }
    };

    const loadTurmas = async (selectedTurmaId = null) => {
        try {
            const response = await fetch(`${apiUrl}/turmas`);
            if (!response.ok) {
                throw new Error('Erro ao buscar turmas');
            }
            const turmas = await response.json();
            const turmaSelect = document.getElementById('turma');
            turmaSelect.innerHTML = '';

            turmas.forEach(turma => {
                const option = document.createElement('option');
                option.value = turma.id;
                option.text = turma.nome;
                if (selectedTurmaId && selectedTurmaId === turma.id) {
                    option.selected = true;
                }
                turmaSelect.appendChild(option);
            });
        } catch (error) {
            console.error('Erro ao carregar turmas:', error);
        }
    };

    const loadProfessores = async (selectedProfessorId = null) => {
        try {
            const response = await fetch(`${apiUrl}/professores`);
            if (!response.ok) {
                throw new Error('Erro ao buscar professores');
            }
            const professores = await response.json();
            const professorSelect = document.getElementById('professor');
            professorSelect.innerHTML = '';

            professores.forEach(professor => {
                const option = document.createElement('option');
                option.value = professor.id;
                option.text = `${professor.nome} ${professor.ultimoNome}`;
                if (selectedProfessorId && selectedProfessorId === professor.id) {
                    option.selected = true;
                }
                professorSelect.appendChild(option);
            });
        } catch (error) {
            console.error('Erro ao carregar professores:', error);
        }
    };

    // Função para submeter o formulário e atualizar a disciplina
    disciplinaForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const disciplinaData = {
            nome: document.getElementById('nome').value,
            cargaHoraria: document.getElementById('cargaHoraria').value,
            turmaId: Number(document.getElementById('turma').value),
            professorId: Number(document.getElementById('professor').value)
        };

        if (editDisciplinaId) {
            await updateDisciplina(editDisciplinaId, disciplinaData);
        }

        disciplinaModal.style.display = 'none';
        loadDisciplinas(); // Recarrega a lista de disciplinas após a atualização
    });

    // Inicialização
    loadDisciplinas();
});
