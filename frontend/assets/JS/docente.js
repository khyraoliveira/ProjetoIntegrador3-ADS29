document.addEventListener('DOMContentLoaded', function () {
    const apiUrl = 'http://localhost:8080'; // Ajuste para a URL da sua API

    const professorModal = document.getElementById('modalProfessor');
    const professorForm = document.getElementById('professorForm');
    const modalTitleProfessor = document.getElementById('modalTitleProfessor');
    const fecharModalBtn = document.getElementById('fecharModal'); // Seleciona o botão de fechar
    const disciplinaSelect = document.getElementById('disciplina'); // Seleciona o campo de disciplina
    let editProfessorId = null;

    // Função para fechar o modal
    fecharModalBtn.addEventListener('click', () => {
        professorModal.style.display = 'none';
    });

    // Carregar lista de professores
    const loadProfessores = async () => {
        try {
            const response = await fetch(`${apiUrl}/professores`);
            if (!response.ok) {
                throw new Error('Erro ao buscar professores');
            }
            const professores = await response.json();
            const tableBody = document.getElementById('listaProfessores');
            tableBody.innerHTML = '';

            professores.forEach(professor => {
                const nomeCompleto = `${professor.nome} ${professor.ultimoNome}`;
                const row = document.createElement('tr');

                row.innerHTML = `
                    <td>${nomeCompleto}</td>
                    <td>${professor.cpf}</td>
                    <td>${professor.email}</td>
                    <td>
                        <button class="editProfessorBtn" data-cpf="${professor.cpf}">Editar</button>
                        <button class="deleteProfessorBtn" data-cpf="${professor.cpf}">Deletar</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });

            // Adicionar eventos aos botões de edição e exclusão
            document.querySelectorAll('.editProfessorBtn').forEach(button => {
                button.addEventListener('click', () => openEditProfessorModal(button.getAttribute('data-cpf')));
            });
            document.querySelectorAll('.deleteProfessorBtn').forEach(button => {
                button.addEventListener('click', () => deleteProfessor(button.getAttribute('data-cpf')));
            });
        } catch (error) {
            console.error('Erro ao carregar professores:', error);
        }
    };

    // Carregar disciplinas disponíveis no banco de dados
    const loadDisciplinas = async () => {
        try {
            const response = await fetch(`${apiUrl}/disciplinas`); // URL para carregar disciplinas
            if (!response.ok) {
                throw new Error('Erro ao buscar disciplinas');
            }
            const disciplinas = await response.json();
            disciplinaSelect.innerHTML = ''; // Limpa o select

            disciplinas.forEach(disciplina => {
                const option = document.createElement('option');
                option.value = disciplina.id;
                option.textContent = disciplina.nome;
                disciplinaSelect.appendChild(option);
            });
        } catch (error) {
            console.error('Erro ao carregar disciplinas:', error);
        }
    };

    // Abrir modal de edição de professor
    const openEditProfessorModal = async (cpf) => {
        try {
            const response = await fetch(`${apiUrl}/professores/${cpf}`);
            if (!response.ok) {
                throw new Error('Erro ao buscar professor');
            }
            const professor = await response.json();
            modalTitleProfessor.textContent = 'Editar Professor';
            professorForm.nome.value = professor.nome;
            professorForm.ultimoNome.value = professor.ultimoNome;
            professorForm.cpf.value = professor.cpf;
            professorForm.genero.value = professor.genero;
            professorForm.dataNascimento.value = professor.dataNascimento;
            professorForm.email.value = professor.email;

            await loadDisciplinas(); // Carregar disciplinas antes de abrir o modal
            professorForm.disciplina.value = professor.disciplina; // Seleciona a disciplina do professor
            editProfessorId = cpf;
            professorModal.style.display = 'block';
        } catch (error) {
            console.error('Erro ao abrir modal de edição de professor:', error);
        }
    };

    // Função para atualizar professor
    const updateProfessor = async (cpf) => {
        const professorData = {
            nome: professorForm.nome.value,
            ultimoNome: professorForm.ultimoNome.value,
            cpf: professorForm.cpf.value,
            genero: professorForm.genero.value,
            dataNascimento: professorForm.dataNascimento.value,
            email: professorForm.email.value,
            disciplina: professorForm.disciplina.value // Seleciona a disciplina
        };

        try {
            const response = await fetch(`${apiUrl}/professores/${cpf}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(professorData),
            });

            if (!response.ok) {
                throw new Error('Erro ao atualizar professor');
            }
            professorModal.style.display = 'none';
            loadProfessores(); // Recarregar a lista após a atualização
        } catch (error) {
            console.error('Erro ao atualizar professor:', error);
        }
    };

    // Função para excluir professor
    const deleteProfessor = async (cpf) => {
        if (confirm('Tem certeza que deseja excluir este professor?')) {
            try {
                const response = await fetch(`${apiUrl}/professores/${cpf}`, {
                    method: 'DELETE',
                });
                if (!response.ok) {
                    throw new Error('Erro ao deletar professor');
                }
                loadProfessores(); // Recarregar a lista após a exclusão
            } catch (error) {
                console.error('Erro ao deletar professor:', error);
            }
        }
    };

    professorForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        if (editProfessorId) {
            updateProfessor(editProfessorId); 
        }
    });

    loadProfessores();
});
