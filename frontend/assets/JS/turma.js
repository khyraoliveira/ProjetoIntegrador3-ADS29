document.addEventListener('DOMContentLoaded', function() {
    const apiUrl = 'http://localhost:8080';

    const turmaModal = document.getElementById('modalTurma');
    const turmaForm = document.getElementById('turmaForm');
    const modalTitleTurma = document.getElementById('modalTitleTurma');
    let editTurmaId = null;

    const loadTurmas = async () => {
        try {
            const response = await fetch(`${apiUrl}/turmas`);
            if (!response.ok) {
                throw new Error('Erro ao buscar turmas');
            }
            const turmas = await response.json();
            const tableBody = document.querySelector('#listaTurmas');
            tableBody.innerHTML = '';

            turmas.forEach(turma => {
                const row = document.createElement('tr');

                row.innerHTML = `
                    <td>${turma.nome}</td>
                    <td>${turma.anoEscolar}</td>
                    <td>${turma.turno}</td>
                    <td>
                        <button class="editTurmaBtn" data-id="${turma.id}">Editar</button>
                        <button class="deleteTurmaBtn" data-id="${turma.id}">Deletar</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });

            // Adicionando eventos aos botões de editar e deletar
            document.querySelectorAll('.editTurmaBtn').forEach(button => {
                button.addEventListener('click', (e) => openEditTurmaModal(e.target.dataset.id));
            });

            document.querySelectorAll('.deleteTurmaBtn').forEach(button => {
                button.addEventListener('click', (e) => confirmDeleteTurma(e.target.dataset.id));
            });
        } catch (error) {
            console.error('Erro ao carregar turmas:', error);
        }
    };

    const updateTurma = async (id, turma) => {
        await fetch(`${apiUrl}/turmas/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(turma)
        });
        loadTurmas();
    };

    const confirmDeleteTurma = (id) => {
        if (confirm("Tem certeza que deseja deletar esta turma?")) {
            deleteTurma(id);
        }
    };

    const deleteTurma = async (id) => {
        await fetch(`${apiUrl}/turmas/${id}`, {
            method: 'DELETE'
        });
        loadTurmas();
    };

    const openEditTurmaModal = async (id) => {
        editTurmaId = id;
        modalTitleTurma.innerText = 'Editar Turma';

        const response = await fetch(`${apiUrl}/turmas/${id}`);
        if (response.status === 404) {
            console.error('Turma não encontrada');
            return;
        }
        const turma = await response.json();

        document.getElementById('nomeTurma').value = turma.nome;
        document.getElementById('anoLetivo').value = turma.anoLetivo;
        document.getElementById('anoEscolar').value = turma.anoEscolar;
        document.getElementById('turno').value = turma.turno;

        turmaModal.style.display = 'block';
    };

    turmaForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const turmaData = {
            nome: document.getElementById('nomeTurma').value,
            anoLetivo: document.getElementById('anoLetivo').value,
            anoEscolar: document.getElementById('anoEscolar').value,
            turno: document.getElementById('turno').value
        };

        if (editTurmaId) {
            await updateTurma(editTurmaId, turmaData);
        }

        turmaModal.style.display = 'none';
        loadTurmas();
    });

    loadTurmas();
});
