-- Inserir Coordenador
INSERT INTO coordenador (cpf, nome, ultimo_nome, data_nascimento, email, genero)
VALUES 
('11111111111', 'Carlos', 'Silva', '1975-05-15', 'carlos.silva@colegio.com', 'Masculino');

-- Inserir Coordenação
INSERT INTO coordenacao (id_coordenacao, descricao, nome, coordenador_cpf)
VALUES 
(1, 'Coordenação Geral', 'Coordenação do Ensino Médio', '11111111111');

-- Inserir Professores
INSERT INTO professor (cpf, nome, ultimo_nome, data_nascimento, email, genero, id_coordenacao)
VALUES
('22222222222', 'Ana', 'Pereira', '1980-04-22', 'ana.pereira@colegio.com', 'Feminino', 1),
('33333333333', 'João', 'Santos', '1982-07-11', 'joao.santos@colegio.com', 'Masculino', 1),
('44444444444', 'Mariana', 'Oliveira', '1985-09-25', 'mariana.oliveira@colegio.com', 'Feminino', 1),
('55555555555', 'Pedro', 'Costa', '1979-12-01', 'pedro.costa@colegio.com', 'Masculino', 1),
('66666666666', 'Rafael', 'Ferreira', '1983-03-18', 'rafael.ferreira@colegio.com', 'Masculino', 1),
('77777777777', 'Carla', 'Alves', '1984-08-30', 'carla.alves@colegio.com', 'Feminino', 1),
('88888888888', 'Luciana', 'Mendes', '1987-02-20', 'luciana.mendes@colegio.com', 'Feminino', 1),
('99999999999', 'Ricardo', 'Lima', '1978-11-03', 'ricardo.lima@colegio.com', 'Masculino', 1),
('10101010101', 'Fernanda', 'Ramos', '1986-06-10', 'fernanda.ramos@colegio.com', 'Feminino', 1),
('11111111112', 'Bruno', 'Martins', '1981-01-29', 'bruno.martins@colegio.com', 'Masculino', 1);

-- Inserir Turmas
INSERT INTO turma (id_turma, nome, ano, id_coordenacao)
VALUES
(1, '1º Ano', 2024, 1),
(2, '2º Ano', 2024, 1),
(3, '3º Ano', 2024, 1);

-- Inserir Alunos para Turma 1
INSERT INTO aluno (matricula_aluno, nome, ultimo_nome, data_nascimento, email, genero, cpf)
VALUES
(1, 'Lucas', 'Silva', '2008-05-10', 'lucas.silva@aluno.com', 'Masculino', '11111111101'),
(2, 'Julia', 'Santos', '2008-07-15', 'julia.santos@aluno.com', 'Feminino', '11111111102'),
(3, 'Matheus', 'Pereira', '2008-09-20', 'matheus.pereira@aluno.com', 'Masculino', '11111111103'),
(4, 'Gabriel', 'Rocha', '2008-02-13', 'gabriel.rocha@aluno.com', 'Masculino', '11111111104'),
(5, 'Sophia', 'Costa', '2008-11-25', 'sophia.costa@aluno.com', 'Feminino', '11111111105'),
(6, 'Enzo', 'Melo', '2008-03-07', 'enzo.melo@aluno.com', 'Masculino', '11111111106'),
(7, 'Alice', 'Barros', '2008-04-30', 'alice.barros@aluno.com', 'Feminino', '11111111107'),
(8, 'Laura', 'Fernandes', '2008-06-12', 'laura.fernandes@aluno.com', 'Feminino', '11111111108'),
(9, 'Davi', 'Ribeiro', '2008-08-23', 'davi.ribeiro@aluno.com', 'Masculino', '11111111109'),
(10, 'Lara', 'Gomes', '2008-10-14', 'lara.gomes@aluno.com', 'Feminino', '11111111110'),
(11, 'Miguel', 'Martins', '2008-01-05', 'miguel.martins@aluno.com', 'Masculino', '11111111111'),
(12, 'Beatriz', 'Alves', '2008-12-09', 'beatriz.alves@aluno.com', 'Feminino', '11111111112'),
(13, 'Arthur', 'Lima', '2008-11-02', 'arthur.lima@aluno.com', 'Masculino', '11111111113'),
(14, 'Isabela', 'Souza', '2008-05-19', 'isabela.souza@aluno.com', 'Feminino', '11111111114'),
(15, 'Pedro', 'Oliveira', '2008-09-27', 'pedro.oliveira@aluno.com', 'Masculino', '11111111115'),
(16, 'Yasmin', 'Rodrigues', '2008-07-21', 'yasmin.rodrigues@aluno.com', 'Feminino', '11111111116'),
(17, 'Rafael', 'Carvalho', '2008-08-04', 'rafael.carvalho@aluno.com', 'Masculino', '11111111117'),
(18, 'Bruna', 'Ferreira', '2008-02-17', 'bruna.ferreira@aluno.com', 'Feminino', '11111111118'),
(19, 'Lucas', 'Gonçalves', '2008-06-26', 'lucas.goncalves@aluno.com', 'Masculino', '11111111119'),
(20, 'Ana', 'Dias', '2008-03-11', 'ana.dias@aluno.com', 'Feminino', '11111111120'),
(21, 'Leonardo', 'Cardoso', '2008-05-03', 'leonardo.cardoso@aluno.com', 'Masculino', '11111111121'),
(22, 'Manuela', 'Monteiro', '2008-09-30', 'manuela.monteiro@aluno.com', 'Feminino', '11111111122'),
(23, 'Gustavo', 'Pinto', '2008-07-18', 'gustavo.pinto@aluno.com', 'Masculino', '11111111123'),
(24, 'Mariana', 'Santana', '2008-10-27', 'mariana.santana@aluno.com', 'Feminino', '11111111124'),
(25, 'Eduardo', 'Nascimento', '2008-11-14', 'eduardo.nascimento@aluno.com', 'Masculino', '11111111125');

-- Associações aluno-turma (Turma 1)
INSERT INTO aluno_turma (turma_id, aluno_id)
VALUES 
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
(1, 6), (1, 7), (1, 8), (1, 9), (1, 10),
(1, 11), (1, 12), (1, 13), (1, 14), (1, 15),
(1, 16), (1, 17), (1, 18), (1, 19), (1, 20),
(1, 21), (1, 22), (1, 23), (1, 24), (1, 25);

-- Inserir Alunos para Turma 2
INSERT INTO aluno (matricula_aluno, nome, ultimo_nome, data_nascimento, email, genero, cpf)
VALUES
(26, 'Felipe', 'Araújo', '2007-05-10', 'felipe.araujo@aluno.com', 'Masculino', '11111111126'),
(27, 'Larissa', 'Andrade', '2007-07-15', 'larissa.andrade@aluno.com', 'Feminino', '11111111127'),
(28, 'Caio', 'Nascimento', '2007-09-20', 'caio.nascimento@aluno.com', 'Masculino', '11111111128'),
(29, 'Bianca', 'Moraes', '2007-02-13', 'bianca.moraes@aluno.com', 'Feminino', '11111111129'),
(30, 'Lucas', 'Teixeira', '2007-11-25', 'lucas.teixeira@aluno.com', 'Masculino', '11111111130'),
(31, 'Renata', 'Matos', '2007-03-07', 'renata.matos@aluno.com', 'Feminino', '11111111131'),
(32, 'Thiago', 'Freitas', '2007-04-30', 'thiago.freitas@aluno.com', 'Masculino', '11111111132'),
(33, 'Paula', 'Correia', '2007-06-12', 'paula.correia@aluno.com', 'Feminino', '11111111133'),
(34, 'Vitor', 'Cavalcante', '2007-08-23', 'vitor.cavalcante@aluno.com', 'Masculino', '11111111134'),
(35, 'Letícia', 'Gomes', '2007-10-14', 'leticia.gomes@aluno.com', 'Feminino', '11111111135'),
(36, 'Rodrigo', 'Moreira', '2007-01-05', 'rodrigo.moreira@aluno.com', 'Masculino', '11111111136'),
(37, 'Carolina', 'Moura', '2007-12-09', 'carolina.moura@aluno.com', 'Feminino', '11111111137'),
(38, 'Bruno', 'Martins', '2007-11-02', 'bruno.martins@aluno.com', 'Masculino', '11111111138'),
(39, 'Camila', 'Ferreira', '2007-05-19', 'camila.ferreira@aluno.com', 'Feminino', '11111111139'),
(40, 'Diego', 'Lopes', '2007-09-27', 'diego.lopes@aluno.com', 'Masculino', '11111111140'),
(41, 'Rafaela', 'Fernandes', '2007-07-21', 'rafaela.fernandes@aluno.com', 'Feminino', '11111111141'),
(42, 'Lucas', 'Santana', '2007-08-04', 'lucas.santana@aluno.com', 'Masculino', '11111111142'),
(43, 'Isabel', 'Silva', '2007-02-17', 'isabel.silva@aluno.com', 'Feminino', '11111111143'),
(44, 'Daniel', 'Medeiros', '2007-06-26', 'daniel.medeiros@aluno.com', 'Masculino', '11111111144'),
(45, 'Marta', 'Sousa', '2007-03-11', 'marta.sousa@aluno.com', 'Feminino', '11111111145'),
(46, 'Ronaldo', 'Barros', '2007-05-03', 'ronaldo.barros@aluno.com', 'Masculino', '11111111146'),
(47, 'Tatiana', 'Almeida', '2007-09-30', 'tatiana.almeida@aluno.com', 'Feminino', '11111111147'),
(48, 'Gustavo', 'Santos', '2007-07-18', 'gustavo.santos@aluno.com', 'Masculino', '11111111148'),
(49, 'Patrícia', 'Moura', '2007-10-27', 'patricia.moura@aluno.com', 'Feminino', '11111111149'),
(50, 'Marcelo', 'Pereira', '2007-11-14', 'marcelo.pereira@aluno.com', 'Masculino', '11111111150');

-- Associações aluno-turma (Turma 2)
INSERT INTO aluno_turma (turma_id, aluno_id)
VALUES 
(2, 26), (2, 27), (2, 28), (2, 29), (2, 30),
(2, 31), (2, 32), (2, 33), (2, 34), (2, 35),
(2, 36), (2, 37), (2, 38), (2, 39), (2, 40),
(2, 41), (2, 42), (2, 43), (2, 44), (2, 45),
(2, 46), (2, 47), (2, 48), (2, 49), (2, 50);

-- Inserir Alunos para Turma 3
INSERT INTO aluno (matricula_aluno, nome, ultimo_nome, data_nascimento, email, genero, cpf)
VALUES
(51, 'João', 'Mendes', '2006-05-10', 'joao.mendes@aluno.com', 'Masculino', '11111111151'),
(52, 'Maria', 'Rodrigues', '2006-07-15', 'maria.rodrigues@aluno.com', 'Feminino', '11111111152'),
(53, 'Henrique', 'Alves', '2006-09-20', 'henrique.alves@aluno.com', 'Masculino', '11111111153'),
(54, 'Natália', 'Gomes', '2006-02-13', 'natalia.gomes@aluno.com', 'Feminino', '11111111154'),
(55, 'Vinícius', 'Silva', '2006-11-25', 'vinicius.silva@aluno.com', 'Masculino', '11111111155'),
(56, 'Eduarda', 'Lima', '2006-03-07', 'eduarda.lima@aluno.com', 'Feminino', '11111111156'),
(57, 'Rafael', 'Oliveira', '2006-04-30', 'rafael.oliveira@aluno.com', 'Masculino', '11111111157'),
(58, 'Patricia', 'Costa', '2006-06-12', 'patricia.costa@aluno.com', 'Feminino', '11111111158'),
(59, 'Igor', 'Souza', '2006-08-23', 'igor.souza@aluno.com', 'Masculino', '11111111159'),
(60, 'Mariana', 'Santos', '2006-10-14', 'mariana.santos@aluno.com', 'Feminino', '11111111160'),
(61, 'Renan', 'Barros', '2006-01-05', 'renan.barros@aluno.com', 'Masculino', '11111111161'),
(62, 'Gabriela', 'Teixeira', '2006-12-09', 'gabriela.teixeira@aluno.com', 'Feminino', '11111111162'),
(63, 'Caio', 'Moraes', '2006-11-02', 'caio.moraes@aluno.com', 'Masculino', '11111111163'),
(64, 'Isabela', 'Monteiro', '2006-05-19', 'isabela.monteiro@aluno.com', 'Feminino', '11111111164'),
(65, 'Juliana', 'Carvalho', '2006-09-27', 'juliana.carvalho@aluno.com', 'Feminino', '11111111165'),
(66, 'Guilherme', 'Matos', '2006-07-21', 'guilherme.matos@aluno.com', 'Masculino', '11111111166'),
(67, 'Fernanda', 'Freitas', '2006-08-04', 'fernanda.freitas@aluno.com', 'Feminino', '11111111167'),
(68, 'Alexandre', 'Pinto', '2006-02-17', 'alexandre.pinto@aluno.com', 'Masculino', '11111111168'),
(69, 'Bruno', 'Correia', '2006-06-26', 'bruno.correia@aluno.com', 'Masculino', '11111111169'),
(70, 'Thais', 'Cavalcante', '2006-03-11', 'thais.cavalcante@aluno.com', 'Feminino', '11111111170'),
(71, 'Lucas', 'Lima', '2006-05-03', 'lucas.lima@aluno.com', 'Masculino', '11111111171'),
(72, 'Daniela', 'Pereira', '2006-09-30', 'daniela.pereira@aluno.com', 'Feminino', '11111111172'),
(73, 'Marcelo', 'Santana', '2006-07-18', 'marcelo.santana@aluno.com', 'Masculino', '11111111173'),
(74, 'Mariana', 'Rocha', '2006-10-27', 'mariana.rocha@aluno.com', 'Feminino', '11111111174'),
(75, 'Paulo', 'Mendes', '2006-11-14', 'paulo.mendes@aluno.com', 'Masculino', '11111111175');

-- Associações aluno-turma (Turma 3)
INSERT INTO aluno_turma (turma_id, aluno_id)
VALUES 
(3, 51), (3, 52), (3, 53), (3, 54), (3, 55),
(3, 56), (3, 57), (3, 58), (3, 59), (3, 60),
(3, 61), (3, 62), (3, 63), (3, 64), (3, 65),
(3, 66), (3, 67), (3, 68), (3, 69), (3, 70),
(3, 71), (3, 72), (3, 73), (3, 74), (3, 75);

-- Inserir Disciplinas
INSERT INTO disciplina (id_disciplina, nome, carga_horaria, id_coordenacao)
VALUES
(1, 'Matemática', 180, 1),
(2, 'Português', 180, 1),
(3, 'História', 120, 1),
(4, 'Geografia', 120, 1),
(5, 'Biologia', 120, 1);

-- Inserir TurmaDisciplinaProfessor (associações entre turmas, disciplinas e professores)
INSERT INTO turma_disciplina_professor (id_disciplina, id_professor, id_turma)
VALUES
(1, '22222222222', 1),
(2, '33333333333', 1),
(3, '44444444444', 1),
(4, '55555555555', 1),
(5, '66666666666', 1),
(1, '22222222222', 2),
(2, '33333333333', 2),
(3, '44444444444', 2),
(4, '55555555555', 2),
(5, '66666666666', 2),
(1, '77777777777', 3),
(2, '88888888888', 3),
(3, '99999999999', 3),
(4, '10101010101', 3),
(5, '11111111112', 3);

-- Inserir Horários para as turmas (exemplo de segunda a sexta, 7h às 12h)
-- Inserir Horários para as turmas (segunda a sexta, 7h às 12h)
-- SEGUNDA
INSERT INTO horario (id_horario, dia_semana, hora_inicio, hora_fim, id_coordenacao, id_disciplina, id_professor, id_turma)
VALUES
(1, 'SEGUNDA', '07:00:00', '08:00:00', 1, 1, '22222222222', 1),
(2, 'SEGUNDA', '08:00:00', '09:00:00', 1, 2, '33333333333', 1),
(3, 'SEGUNDA', '09:00:00', '10:00:00', 1, 3, '44444444444', 1),
(4, 'SEGUNDA', '10:00:00', '11:00:00', 1, 4, '55555555555', 1),
(5, 'SEGUNDA', '11:00:00', '12:00:00', 1, 5, '66666666666', 1);

-- TERÇA
INSERT INTO horario (id_horario, dia_semana, hora_inicio, hora_fim, id_coordenacao, id_disciplina, id_professor, id_turma)
VALUES
(6, 'TERÇA', '07:00:00', '08:00:00', 1, 1, '22222222222', 1),
(7, 'TERÇA', '08:00:00', '09:00:00', 1, 2, '33333333333', 1),
(8, 'TERÇA', '09:00:00', '10:00:00', 1, 3, '44444444444', 1),
(9, 'TERÇA', '10:00:00', '11:00:00', 1, 4, '55555555555', 1),
(10, 'TERÇA', '11:00:00', '12:00:00', 1, 5, '66666666666', 1);

-- QUARTA
INSERT INTO horario (id_horario, dia_semana, hora_inicio, hora_fim, id_coordenacao, id_disciplina, id_professor, id_turma)
VALUES
(11, 'QUARTA', '07:00:00', '08:00:00', 1, 1, '22222222222', 1),
(12, 'QUARTA', '08:00:00', '09:00:00', 1, 2, '33333333333', 1),
(13, 'QUARTA', '09:00:00', '10:00:00', 1, 3, '44444444444', 1),
(14, 'QUARTA', '10:00:00', '11:00:00', 1, 4, '55555555555', 1),
(15, 'QUARTA', '11:00:00', '12:00:00', 1, 5, '66666666666', 1);

-- QUINTA
INSERT INTO horario (id_horario, dia_semana, hora_inicio, hora_fim, id_coordenacao, id_disciplina, id_professor, id_turma)
VALUES
(16, 'QUINTA', '07:00:00', '08:00:00', 1, 1, '22222222222', 1),
(17, 'QUINTA', '08:00:00', '09:00:00', 1, 2, '33333333333', 1),
(18, 'QUINTA', '09:00:00', '10:00:00', 1, 3, '44444444444', 1),
(19, 'QUINTA', '10:00:00', '11:00:00', 1, 4, '55555555555', 1),
(20, 'QUINTA', '11:00:00', '12:00:00', 1, 5, '66666666666', 1);

-- SEXTA
INSERT INTO horario (id_horario, dia_semana, hora_inicio, hora_fim, id_coordenacao, id_disciplina, id_professor, id_turma)
VALUES
(21, 'SEXTA', '07:00:00', '08:00:00', 1, 1, '22222222222', 1),
(22, 'SEXTA', '08:00:00', '09:00:00', 1, 2, '33333333333', 1),
(23, 'SEXTA', '09:00:00', '10:00:00', 1, 3, '44444444444', 1),
(24, 'SEXTA', '10:00:00', '11:00:00', 1, 4, '55555555555', 1),
(25, 'SEXTA', '11:00:00', '12:00:00', 1, 5, '66666666666', 1);

-- Inserir Conceitos para os alunos da Turma 1
INSERT INTO conceito (nota, conceito, id_aluno, id_coordenacao, id_disciplina, id_professor, id_turma)
VALUES 
(9.5, 'Excelente', 1, 1, 1, '22222222222', 1),
(8.0, 'Bom', 2, 1, 2, '33333333333', 1),
(7.5, 'Bom', 3, 1, 3, '44444444444', 1),
(6.5, 'Suficiente', 4, 1, 4, '55555555555', 1),
(5.0, 'Insuficiente', 5, 1, 5, '66666666666', 1),
(9.0, 'Excelente', 6, 1, 1, '22222222222', 1),
(8.5, 'Bom', 7, 1, 2, '33333333333', 1),
(7.0, 'Suficiente', 8, 1, 3, '44444444444', 1),
(6.0, 'Suficiente', 9, 1, 4, '55555555555', 1),
(9.0, 'Excelente', 10, 1, 5, '66666666666', 1);

-- Inserir Presenças para os alunos da Turma 1
INSERT INTO presenca (data, presenca, id_aluno, id_coordenacao, id_disciplina, id_professor, id_turma)
VALUES
('2024-09-01', 1, 1, 1, 1, '22222222222', 1),
('2024-09-02', 1, 2, 1, 2, '33333333333', 1),
('2024-09-03', 0, 3, 1, 3, '44444444444', 1),
('2024-09-04', 1, 4, 1, 4, '55555555555', 1),
('2024-09-05', 1, 5, 1, 5, '66666666666', 1),
('2024-09-06', 1, 6, 1, 1, '22222222222', 1),
('2024-09-07', 1, 7, 1, 2, '33333333333', 1),
('2024-09-08', 0, 8, 1, 3, '44444444444', 1),
('2024-09-09', 1, 9, 1, 4, '55555555555', 1),
('2024-09-10', 1, 10, 1, 5, '66666666666', 1);

-- Inserir Comunicados (professor)
INSERT INTO comunicado (conteudo, data_envio, id_coordenacao, id_professor, id_aluno, id_turma)
VALUES
('Parabéns pela excelente nota!', '2024-09-01', 1, '22222222222', 1, 1),
('Continue se esforçando!', '2024-09-02', 1, '33333333333', 2, 1),
('Atenção nas próximas aulas.', '2024-09-03', 1, '44444444444', 3, 1);

-- Inserir Comunicados (coordenador)
INSERT INTO comunicado (conteudo, data_envio, id_coordenacao, id_professor, id_aluno, id_turma)
VALUES
('Bom desempenho da turma no último mês.', '2024-09-05', 1, NULL, NULL, 1),
('Reunião de pais agendada.', '2024-09-06', 1, NULL, NULL, 2),
('Foco no ENEM!', '2024-09-07', 1, NULL, NULL, 3);
