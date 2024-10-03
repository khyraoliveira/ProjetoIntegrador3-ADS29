document.addEventListener('DOMContentLoaded', function () {
    const apiUrl = 'http://localhost:8080'; // URL da API

    // Função para carregar turmas e disciplinas do backend
    const loadTurmasEDisciplinas = async () => {
        try {
            const turmasResponse = await fetch(`${apiUrl}/turmas`);
            const disciplinasResponse = await fetch(`${apiUrl}/disciplinas`);

            if (!turmasResponse.ok || !disciplinasResponse.ok) {
                throw new Error('Erro ao buscar turmas ou disciplinas');
            }

            const turmas = await turmasResponse.json();
            const disciplinas = await disciplinasResponse.json();

            const turmaSelect = document.getElementById('dc_turma');
            const disciplinaSelect = document.getElementById('dc_disciplina');

            turmaSelect.innerHTML = '<option value="null">Selecione as turmas</option>';
            disciplinaSelect.innerHTML = '<option value="null">Selecione as disciplinas</option>';

            turmas.forEach(turma => {
                const option = document.createElement('option');
                option.value = turma.id;
                option.textContent = `${turma.nome} (${turma.turno})`;
                turmaSelect.appendChild(option);
            });

            disciplinas.forEach(disciplina => {
                const option = document.createElement('option');
                option.value = disciplina.id;
                option.textContent = disciplina.nome;
                disciplinaSelect.appendChild(option);
            });
        } catch (error) {
            console.error('Erro ao carregar turmas ou disciplinas:', error);
        }
    };

    loadTurmasEDisciplinas();

    // Função para salvar os dados do professor
    const salvarProfessor = async (event) => {
        event.preventDefault();

        const nome = document.getElementById('nome').value;
        const ultimoNome = document.getElementById('ultimoNome').value;
        const cpf = document.getElementById('cpf').value;
        const genero = document.getElementById('dc_genero').value;
        const data_nascimento = document.getElementById('data_nascimento').value;
        const email = document.getElementById('email').value;
        const cep = document.getElementById('cep').value;
        const rua = document.getElementById('rua').value;
        const numero = document.getElementById('numero').value;
        const bairro = document.getElementById('bairro').value;
        const cidade = document.getElementById('cidade').value;
        const estado = document.getElementById('estado').value;
        const ddd = document.getElementById('ddd').value;
        const numero_telefone = document.getElementById('numero_telefone').value;
        const turmaId = document.getElementById('dc_turma').value;
        const disciplinaId = document.getElementById('dc_disciplina').value;

        const professor = {
            cpf: cpf,
            nome: nome,
            ultimoNome: ultimoNome,
            genero: genero,
            data_nascimento: data_nascimento,
            email: email,
            status: true,
            enderecos: [{
                cep: cep,
                rua: rua,
                numero: numero,
                bairro: bairro,
                cidade: cidade,
                estado: estado
            }],
            telefones: [{
                ddd: ddd,
                numero: numero_telefone
            }],
            coordenacaoId: 1,
            turmasDisciplinas: [{
                turmaId: Number(turmaId),
                disciplinaId: Number(disciplinaId)
            }]
        };

        console.log("Dados enviados para o backend:", professor);

        try {
            const response = await fetch('http://localhost:8080/professores', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(professor)
            });

            if (response.ok) {
                console.log('Professor cadastrado com sucesso!');
                alert('Professor cadastrado com sucesso!');
                window.location.href = 'docentes.html';
            } else {
                const errorData = await response.json();
                throw new Error(`Erro ao cadastrar professor: ${JSON.stringify(errorData)}`);
            }
        } catch (error) {
            console.error('Erro ao cadastrar professor:', error);
            alert('Erro ao cadastrar professor!');
        }
    };

    // Adicionar evento ao botão de salvar
    document.getElementById('salvar').addEventListener('click', salvarProfessor);
});
