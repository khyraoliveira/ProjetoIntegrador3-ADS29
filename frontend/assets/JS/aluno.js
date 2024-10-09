document.addEventListener('DOMContentLoaded', function () {
    const apiUrl = 'http://localhost:8080'; // Ajuste para a URL correta da sua API

    const alunoModal = document.getElementById('modalAluno');
    const alunoForm = document.getElementById('alunoForm');
    const modalTitleAluno = document.getElementById('modalTitleAluno');
    const closeModalButton = document.getElementById('fecharModal'); // Botão para fechar o modal
    let editAlunoId = null;

    // Função para carregar alunos da API
    const loadAlunos = async () => {
        try {
            const response = await fetch(`${apiUrl}/alunos`);
            if (!response.ok) {
                throw new Error('Erro ao buscar alunos');
            }
            const alunos = await response.json();
            const tableBody = document.querySelector('#listaAlunos');
            tableBody.innerHTML = '';

            alunos.forEach(aluno => {
                const nomeCompleto = `${aluno.nome} ${aluno.ultimoNome}`;
                const turmaNome = aluno.turmas && aluno.turmas.length > 0 ? aluno.turmas[0].nome : 'Sem turma';
                const responsavelCompleto = aluno.responsaveis && aluno.responsaveis.length > 0
                    ? `${aluno.responsaveis[0].nome} ${aluno.responsaveis[0].ultimoNome}`
                    : 'Sem responsável';
                const telefoneResponsavel = aluno.responsaveis && aluno.responsaveis.length > 0 && aluno.responsaveis[0].telefones.length > 0
                    ? `(${aluno.responsaveis[0].telefones[0].ddd}) ${aluno.responsaveis[0].telefones[0].numero}`
                    : 'Sem telefone';

                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${nomeCompleto}</td>
                    <td>${turmaNome}</td>
                    <td>${responsavelCompleto}</td>
                    <td>${telefoneResponsavel}</td>
                    <td>
                        <button class="editAlunoBtn" data-id="${aluno.id}">Editar</button>
                        <button class="deleteAlunoBtn" data-id="${aluno.id}">Deletar</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });

            document.querySelectorAll('.editAlunoBtn').forEach(button => {
                button.addEventListener('click', (e) => openEditAlunoModal(e.target.dataset.id));
            });

            document.querySelectorAll('.deleteAlunoBtn').forEach(button => {
                button.addEventListener('click', (e) => confirmDeleteAluno(e.target.dataset.id));
            });
        } catch (error) {
            console.error('Erro ao carregar alunos:', error);
        }
    };

    const confirmDeleteAluno = (id) => {
        if (confirm("Tem certeza que deseja deletar este aluno?")) {
            deleteAluno(id);
        }
    };

    const deleteAluno = async (id) => {
        try {
            const response = await fetch(`${apiUrl}/alunos/${id}`, {
                method: 'DELETE'
            });
            if (!response.ok) {
                throw new Error('Erro ao deletar aluno');
            }
            loadAlunos(); // Recarrega a lista de alunos após a deleção
        } catch (error) {
            console.error('Erro ao deletar aluno:', error);
        }
    };

    const updateAluno = async (id, aluno) => {
        try {
            const response = await fetch(`${apiUrl}/alunos/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(aluno)
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Erro ao atualizar aluno: ${errorText}`);
            }

            loadAlunos();  // Recarrega a lista de alunos após a atualização
        } catch (error) {
            console.error('Erro ao atualizar aluno:', error);
        }
    };

    // Função para carregar as turmas da API e preencher o select
    const loadTurmas = async () => {
        try {
            const response = await fetch(`${apiUrl}/turmas`);
            if (!response.ok) {
                throw new Error('Erro ao buscar turmas');
            }
            const turmas = await response.json();
            const turmaSelect = document.getElementById('turma');
            turmaSelect.innerHTML = '<option value="">Selecione uma turma</option>'; // Limpa e adiciona uma opção padrão

            turmas.forEach(turma => {
                const option = document.createElement('option');
                option.value = turma.id;
                option.textContent = turma.nome;
                turmaSelect.appendChild(option);
            });
        } catch (error) {
            console.error('Erro ao carregar turmas:', error);
        }
    };

    const openEditAlunoModal = async (id) => {
        editAlunoId = id;
        modalTitleAluno.innerText = 'Editar Aluno';

        // Carrega as turmas antes de abrir o modal
        await loadTurmas();

        try {
            const response = await fetch(`${apiUrl}/alunos/${id}`);
            if (!response.ok) {
                throw new Error(`Aluno com ID ${id} não encontrado.`);
            }
            const aluno = await response.json();

            console.log('Dados do aluno:', aluno); // Log para verificar os dados do aluno

            // Preenche os campos com os dados do aluno
            document.getElementById('nome').value = aluno.nome || '';
            document.getElementById('ultimoNome').value = aluno.ultimoNome || '';
            document.getElementById('genero').value = aluno.genero || 'null';
            document.getElementById('data_nascimento').value = aluno.data_nascimento || '';
            document.getElementById('cpf').value = aluno.cpf || '';
            document.getElementById('email').value = aluno.email || '';

            // Preenche os campos de endereço se existirem
            if (aluno.enderecos && aluno.enderecos.length > 0) {
                const endereco = aluno.enderecos[0];
                document.getElementById('cep').value = endereco.cep || '';
                document.getElementById('rua').value = endereco.rua || '';
                document.getElementById('numero').value = endereco.numero || '';
                document.getElementById('bairro').value = endereco.bairro || '';
                document.getElementById('cidade').value = endereco.cidade || '';
                document.getElementById('estado').value = endereco.estado || '';
            }

            // Preenche os campos de telefone se existirem
            if (aluno.telefones && aluno.telefones.length > 0) {
                const telefone = aluno.telefones[0];
                document.getElementById('ddd').value = telefone.ddd || '';
                document.getElementById('numero_telefone').value = telefone.numero || '';
            }

            // Preenche os dados do responsável se existirem
            if (aluno.responsaveis && aluno.responsaveis.length > 0) {
                const responsavel = aluno.responsaveis[0];
                document.getElementById('responsavelNome').value = responsavel.nome || '';
                document.getElementById('responsavelUltimoNome').value = responsavel.ultimoNome || '';
                document.getElementById('cpfResponsavel').value = responsavel.cpfResponsavel || '';

                if (responsavel.telefones && responsavel.telefones.length > 0) {
                    const telefoneResponsavel = responsavel.telefones[0];
                    document.getElementById('dddResponsavel').value = telefoneResponsavel.ddd || '';
                    document.getElementById('numeroResponsavel').value = telefoneResponsavel.numero || '';
                }

                document.getElementById('grauParentesco').value = responsavel.grauParentesco || '';
            }

            // Seleciona a turma correta se o aluno já tiver uma associada
            if (aluno.turmas && aluno.turmas.length > 0) {
                document.getElementById('turma').value = aluno.turmas[0].id;
            }

            alunoModal.style.display = 'block'; // Abre o modal para edição
        } catch (error) {
            console.error('Erro ao carregar aluno:', error);
        }
    };

    // Fecha o modal ao clicar no botão de fechar
    closeModalButton.addEventListener('click', () => {
        alunoModal.style.display = 'none';
    });

    // Função para submeter o formulário e atualizar o aluno
    alunoForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        

        const alunoData = {
            nome: document.getElementById('nome').value,
            ultimoNome: document.getElementById('ultimoNome').value,
            genero: document.getElementById('genero').value,
            data_nascimento: document.getElementById('data_nascimento').value,
            cpf: document.getElementById('cpf').value,
            email: document.getElementById('email').value,
            coordenacaoId: 1, // Exemplo: pode ser ajustado conforme necessário
            turmasIds: [Number(document.getElementById('turma').value)],  // Converte a string para número
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
                    numero: document.getElementById('numero_telefone').value
                }
            ],
            responsaveis: [
                {
                    nome: document.getElementById('responsavelNome').value,
                    ultimoNome: document.getElementById('responsavelUltimoNome').value,
                    cpfResponsavel: document.getElementById('cpfResponsavel').value,
                    telefones: [
                        {
                            ddd: document.getElementById('dddResponsavel').value,
                            numero: document.getElementById('numeroResponsavel').value
                        }
                    ],
                    grauParentesco: document.getElementById('grauParentesco').value
                }
            ]
        };

        if (editAlunoId) {
            await updateAluno(editAlunoId, alunoData);
        } else {
            console.error('ID do aluno não definido');
        }

        alunoModal.style.display = 'none';
        loadAlunos(); // Recarrega a lista de alunos após a atualização
    });

    // Inicialização
    loadAlunos();
});
