document.getElementById('salvar').addEventListener('click', function(event) {
    event.preventDefault();

    const nome = document.getElementById('nome').value;
    const ultimoNome = document.getElementById('ultimoNome').value;
    const cpf = document.getElementById('cpf').value;
    const genero = document.getElementById('dc_genero').value;
    const dataNascimento = document.getElementById('data_nascimento').value;
    const email = document.getElementById('email').value;
    const ddd = document.getElementById('ddd').value;
    const numero01 = document.getElementById('dc_numero01').value;

    const cep = document.getElementById('cep').value;
    const rua = document.getElementById('rua').value;
    const numero = document.getElementById('numero').value;
    const bairro = document.getElementById('bairro').value;
    const cidade = document.getElementById('cidade').value;
    const estado = document.getElementById('estado').value;

    const coordenador = {
        cpf: cpf,
        nome: nome,
        ultimoNome: ultimoNome,
        genero: genero,
        data_nascimento: dataNascimento,
        email: email,
        status: true,
        enderecos: [
            {
                cep: cep,
                rua: rua,
                numero: numero,
                bairro: bairro,
                cidade: cidade,
                estado: estado
            }
        ],
        telefones: [
            {
                ddd: ddd,
                numero: numero01
            }
        ],
        idCoordenacao: 1
    };

    console.log('JSON a ser enviado:', JSON.stringify(coordenador, null, 2));

    fetch('http://localhost:8080/coordenadores', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(coordenador)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            return response.text().then(text => {
                throw new Error(`Erro ao cadastrar coordenador: ${text}`);
            });
        }
    })
    .then(data => {
        console.log('Coordenador cadastrado com sucesso:', data);
        alert('Coordenador cadastrado com sucesso!');
        window.location.href = 'coordenador.html';
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro ao cadastrar coordenador.');
    });
});
