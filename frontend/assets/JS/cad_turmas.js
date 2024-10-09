document.getElementById('salvar').addEventListener('click', function(event) {
    event.preventDefault();

    const anoLetivo = document.getElementById('anoLetivo').value;
    const anoEscolar = document.getElementById('anoEscolar').value;
    const turno = document.getElementById('turno').value;
 

    const turma = {
        anoLetivo: anoLetivo,
        anoEscolar: anoEscolar,
        turno: turno,
        status: true,
        coordenacaoID: 1
    };

    console.log('JSON a ser enviado:', JSON.stringify(turma, null, 2));

    // Enviar os dados via POST
    fetch('http://localhost:8080/turmas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(turma)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            return response.text().then(text => {
                throw new Error(`Erro ao cadastrar turma: ${text}`);
            });
        }
    })
    .then(data => {
        console.log('Turma cadastrada com sucesso:', data);
        alert('Turma cadastrada com sucesso!');
        window.location.href = 'turmas.html';
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro ao cadastrar turma.');
    });
});
