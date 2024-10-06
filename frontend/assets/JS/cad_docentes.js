document.getElementById('salvar').addEventListener('click', function(event) {
    event.preventDefault();

    const nome = document.getElementById('nome').value;
    const ultimoNome = document.getElementById('ultimoNome').value;
    const cpf = document.getElementById('cpf').value;
    const genero = document.getElementById('dc_genero').value;
    const dataNascimento = document.getElementById('data_nascimento').value; // data no formato yyyy-MM-dd
    const email = document.getElementById('email').value;

    // Função para converter a data de yyyy-MM-dd para dd/MM/yyyy
    function formatarDataParaBarras(data) {
        const partes = data.split('-'); // Divide a data em [ano, mes, dia]
        if (partes.length === 3) {
            return `${partes[2]}/${partes[1]}/${partes[0]}`; // Retorna no formato dd/MM/yyyy
        }
        return null;
    }

    const dataNascimentoFormatada = formatarDataParaBarras(dataNascimento);

    if (!dataNascimentoFormatada) {
        alert('Data de nascimento inválida.');
        return;
    }

    const professor = {
        nome: nome,
        ultimoNome: ultimoNome,
        cpf: cpf,
        genero: genero,
        data_nascimento: dataNascimentoFormatada, // Data formatada para dd/MM/yyyy
        email: email
    };

    console.log('JSON a ser enviado:', JSON.stringify(professor, null, 2));

    // Enviar os dados via POST
    fetch('http://localhost:8080/professores', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(professor)
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error(`Erro ao cadastrar professor: ${text}`);
            });
        }
        return response.json();
    })
    .then(data => {
        console.log('Professor cadastrado com sucesso:', data);
        alert('Professor cadastrado com sucesso!');
        window.location.href = 'docentes.html';
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro ao cadastrar professor.');
    });
});
