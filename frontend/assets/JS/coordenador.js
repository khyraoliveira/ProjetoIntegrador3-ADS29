document.addEventListener('DOMContentLoaded', function() {
    const apiUrl = 'http://localhost:8080';
    
    const coordenadorModal = document.getElementById('modalCoordenador');
    const coordenadorForm = document.getElementById('coordenadorForm');
    const modalTitleCoordenador = document.getElementById('modalTitleCoordenador');
    let editCoordenadorId = null;

    // Função para carregar coordenadores
    const loadCoordenadores = async () => {
        const response = await fetch(`${apiUrl}/coordenadores`);
        const coordenadores = await response.json();
        const tableBody = document.querySelector('#listaCoordenadores');
        tableBody.innerHTML = '';

        coordenadores.forEach(coordenador => {
            const row = document.createElement('tr');
            const nomeCompleto = `${coordenador.nome} ${coordenador.ultimoNome}`; 
            const telefone = `(${coordenador.telefones[0].ddd}) ${coordenador.telefones[0].numero}`;

            row.innerHTML = `
                <td>${nomeCompleto}</td>
                <td>${coordenador.cpf}</td>
                <td>${coordenador.email}</td>
                <td>${telefone}</td>
                <td>
                    <button class="editCoordenadorBtn" data-id="${coordenador.cpf}">Editar</button>
                    <button class="deleteCoordenadorBtn" data-id="${coordenador.cpf}">Deletar</button>
                </td>
            `;
            tableBody.appendChild(row);
        });

        // Adicionar eventos de edição e deleção
        document.querySelectorAll('.editCoordenadorBtn').forEach(button => {
            button.addEventListener('click', (e) => openEditCoordenadorModal(e.target.dataset.id));
        });

        document.querySelectorAll('.deleteCoordenadorBtn').forEach(button => {
            button.addEventListener('click', (e) => confirmDeleteCoordenador(e.target.dataset.id));
        });
    };

    // Função para atualizar coordenador
    const updateCoordenador = async (cpf, coordenador) => {
        await fetch(`${apiUrl}/coordenadores/${cpf}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(coordenador)
        });
        loadCoordenadores();
    };

    // Função para deletar coordenador com confirmação
    const confirmDeleteCoordenador = (cpf) => {
        if (confirm("Tem certeza que deseja deletar este coordenador?")) {
            deleteCoordenador(cpf);
        }
    };

    const deleteCoordenador = async (cpf) => {
        await fetch(`${apiUrl}/coordenadores/${cpf}`, {
            method: 'DELETE'
        });
        loadCoordenadores();
    };

    // Abrir modal para editar coordenador
    const openEditCoordenadorModal = async (cpf) => {
        editCoordenadorId = cpf;
        modalTitleCoordenador.innerText = 'Editar Coordenador';

        // Buscar os dados do coordenador para preencher o modal
        const response = await fetch(`${apiUrl}/coordenadores/${cpf}`);
        if (response.status === 404) {
            console.error('Coordenador não encontrado');
            return;
        }
        const coordenador = await response.json();

        // Preenchendo os campos do modal com os dados do coordenador
        document.getElementById('nome').value = coordenador.nome;
        document.getElementById('ultimoNome').value = coordenador.ultimoNome;
        document.getElementById('cpf').value = coordenador.cpf;
        document.getElementById('genero').value = coordenador.genero;
        document.getElementById('dataNascimento').value = coordenador.data_nascimento.split('T')[0]; // Ajustando a data
        document.getElementById('cep').value = coordenador.enderecos[0].cep;
        document.getElementById('rua').value = coordenador.enderecos[0].rua;
        document.getElementById('numero').value = coordenador.enderecos[0].numero;
        document.getElementById('bairro').value = coordenador.enderecos[0].bairro;
        document.getElementById('cidade').value = coordenador.enderecos[0].cidade;
        document.getElementById('estado').value = coordenador.enderecos[0].estado;
        document.getElementById('ddd').value = coordenador.telefones[0].ddd;
        document.getElementById('numero01').value = coordenador.telefones[0].numero;

        coordenadorModal.style.display = 'block';
    };

    // Submissão do formulário para editar coordenador
    coordenadorForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const coordenadorData = {
            nome: document.getElementById('nome').value,
            ultimoNome: document.getElementById('ultimoNome').value,
            cpf: document.getElementById('cpf').value,
            genero: document.getElementById('genero').value,
            data_nascimento: document.getElementById('dataNascimento').value,
            email: document.getElementById('email').value,
            status: true,
            enderecos: [
                {
                    cep: document.getElementById('cep').value,
                    rua: document.getElementById('rua').value,
                    numero: document.getElementById('numero').value,
                    bairro: document.getElementById('bairro').value,
                    cidade: document.getElementById('cidade').value,
                    estado: document.getElementById('estado').value
                }
            ],
            telefones: [
                {
                    ddd: document.getElementById('ddd').value,
                    numero: document.getElementById('numero01').value
                }
            ],
        };

        if (editCoordenadorId) {
            await updateCoordenador(editCoordenadorId, coordenadorData);
        }

        coordenadorModal.style.display = 'none';
        loadCoordenadores();
    });

    // Inicializando o carregamento de coordenadores e eventos
    loadCoordenadores();
});
