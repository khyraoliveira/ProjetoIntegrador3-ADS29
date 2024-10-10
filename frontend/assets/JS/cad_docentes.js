// Fetch para obter as disciplinas disponíveis no backend
fetch('http://localhost:8080/disciplinas')
    .then(response => response.json())
    .then(data => {
        const disciplinasContainer = document.getElementById('disciplinas-container');
        data.forEach(disciplina => {
            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.id = `disciplina_${disciplina.id}`;
            checkbox.value = disciplina.id;

            const label = document.createElement('label');
            label.htmlFor = `disciplina_${disciplina.id}`;
            label.textContent = disciplina.nome;

            // Adiciona a checkbox e o label ao container
            disciplinasContainer.appendChild(checkbox);
            disciplinasContainer.appendChild(label);
            disciplinasContainer.appendChild(document.createElement('br')); // Quebra de linha
        });
    })
    .catch(error => console.error('Erro ao carregar disciplinas:', error));

// Evento de clique no botão salvar
document.getElementById('salvar').addEventListener('click', function(event) {
    event.preventDefault();

    const nome = document.getElementById('nome').value;
    const ultimoNome = document.getElementById('ultimoNome').value;
    const cpf = document.getElementById('cpf').value;
    const genero = document.getElementById('dc_genero').value;
    const dataNascimento = document.getElementById('data_nascimento').value;
    const email = document.getElementById('email').value;

    // Obter as disciplinas selecionadas (checkboxes marcadas)
    const checkboxes = document.querySelectorAll('#disciplinas-container input[type="checkbox"]:checked');
    const disciplinasSelecionadas = Array.from(checkboxes).map(checkbox => checkbox.value);

    function formatarData(data) {
        if (!data.includes('-')) {
            return data; // Caso a data já esteja no formato desejado, retorna o valor como está
        }
        const partes = data.split('-');
        if (partes.length === 3) {
            return `${partes[2]}/${partes[1]}/${partes[0]}`;
        } else {
            return undefined; // Se o formato não estiver correto, retorna indefinido
        }
    }

    const dataNascimentoFormatada = formatarData(dataNascimento);

    if (!dataNascimentoFormatada) {
        alert('Erro: Data de nascimento inválida.');
        return; // Interrompe o fluxo se a data for inválida
    }

    const professor = {
        nome: nome,
        ultimoNome: ultimoNome,
        cpf: cpf,
        genero: genero,
        data_nascimento: dataNascimentoFormatada,
        email: email,
        disciplinas: disciplinasSelecionadas // Enviar a lista de disciplinas selecionadas
    };

    // Enviar os dados via POST
    fetch('http://localhost:8080/professores', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(professor)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Professor cadastrado com sucesso:', data);
        alert('Professor cadastrado com sucesso!');
        window.location.href = 'docentes.html';
    })
    .catch(error => {
        console.error('Erro ao cadastrar professor:', error);
        alert('Erro ao cadastrar professor.');
    });
});
