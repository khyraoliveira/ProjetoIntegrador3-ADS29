package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorCompletoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConceitoService {

    // ============================= INJEÇÕES DE DEPENDÊNCIA =============================

    @Autowired
    private ConceitoRepository conceitoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;

    // ============================= MÉTODOS DE CRUD =============================

    // Método para buscar um conceito por ID
    public Optional<ConceitoDTO> buscarConceitoPorId(Long id) {
        return conceitoRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Método para salvar um novo conceito (sem turmas ou disciplina específicas)
    @Transactional
    public ConceitoDTO salvarConceito(ConceitoResumidoDTO conceitoResumidoDTO) {
        Aluno aluno = alunoRepository.findById(conceitoResumidoDTO.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        TurmaDisciplinaProfessor turmaDisciplinaProfessor = turmaDisciplinaProfessorRepository.findById(
                new TurmaDisciplinaProfessorId(
                        conceitoResumidoDTO.getTurmaId(),
                        conceitoResumidoDTO.getDisciplinaId(),
                        conceitoResumidoDTO.getProfessorId()))
                .orElseThrow(() -> new RuntimeException("Turma, Disciplina ou Professor não encontrado"));

        Conceito conceito = Conceito.builder()
                .notaUnidade1(conceitoResumidoDTO.getNotaUnidade1())
                .notaUnidade2(conceitoResumidoDTO.getNotaUnidade2())
                .notaUnidade3(conceitoResumidoDTO.getNotaUnidade3())
                .notaUnidade4(conceitoResumidoDTO.getNotaUnidade4())
                .noa1(conceitoResumidoDTO.getNoa1())
                .noa2(conceitoResumidoDTO.getNoa2())
                .noaFinal(conceitoResumidoDTO.getNoaFinal())
                .aluno(aluno)
                .turmaDisciplinaProfessor(turmaDisciplinaProfessor)
                .build();

        // Recalcula e atribui os conceitos
        calcularConceitos(conceito);
        calcularMediaEStatus(conceito);

        conceitoRepository.save(conceito);
        return convertToDTO(conceito);
    }

    // Método para atualizar um conceito
    @Transactional
    public ConceitoDTO atualizarConceito(Long id, ConceitoResumidoDTO conceitoResumidoDTO) {
        Conceito conceito = conceitoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conceito não encontrado"));

        conceito.setNotaUnidade1(Optional.ofNullable(conceitoResumidoDTO.getNotaUnidade1()).orElse(conceito.getNotaUnidade1()));
        conceito.setNotaUnidade2(Optional.ofNullable(conceitoResumidoDTO.getNotaUnidade2()).orElse(conceito.getNotaUnidade2()));
        conceito.setNotaUnidade3(Optional.ofNullable(conceitoResumidoDTO.getNotaUnidade3()).orElse(conceito.getNotaUnidade3()));
        conceito.setNotaUnidade4(Optional.ofNullable(conceitoResumidoDTO.getNotaUnidade4()).orElse(conceito.getNotaUnidade4()));
        conceito.setNoa1(Optional.ofNullable(conceitoResumidoDTO.getNoa1()).orElse(conceito.getNoa1()));
        conceito.setNoa2(Optional.ofNullable(conceitoResumidoDTO.getNoa2()).orElse(conceito.getNoa2()));
        conceito.setNoaFinal(Optional.ofNullable(conceitoResumidoDTO.getNoaFinal()).orElse(conceito.getNoaFinal()));

        // Recalcula os conceitos e a média após atualização
        calcularConceitos(conceito);
        calcularMediaEStatus(conceito);

        conceitoRepository.save(conceito);
        return convertToDTO(conceito);
    }

    // Método para deletar um conceito
    @Transactional
    public void deletarConceito(Long id) {
        Conceito conceito = conceitoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conceito não encontrado"));
        conceitoRepository.delete(conceito);
    }

    // ============================= MÉTODOS PERSONALIZADOS =============================

    // Método para salvar o conceito de um aluno em uma turma e disciplina específica
    @Transactional
    public ConceitoDTO salvarConceitoParaAlunoComTurma(String cpf, Long idAluno, Long idDisciplina, Long idTurma, ConceitoInputDTO inputDTO) {
        // Verifica se o aluno existe
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Verifica se a turma-disciplina-professor existe
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = turmaDisciplinaProfessorRepository.findById(
                new TurmaDisciplinaProfessorId(idTurma, idDisciplina, cpf))
                .orElseThrow(() -> {
                    if (!turmaDisciplinaProfessorRepository.existsByTurma_Id(idTurma)) {
                        return new RuntimeException("Turma não encontrada");
                    } else if (!turmaDisciplinaProfessorRepository.existsByDisciplina_Id(idDisciplina)) {
                        return new RuntimeException("Disciplina não encontrada");
                    } else if (!turmaDisciplinaProfessorRepository.existsByProfessor_Cpf(cpf)) {
                        return new RuntimeException("Professor não encontrado");
                    }
                    return new RuntimeException("TurmaDisciplinaProfessor não encontrado");
                });

        // Busca os conceitos desse aluno na disciplina
        List<Conceito> conceitos = conceitoRepository.findByAluno_IdAndTurmaDisciplinaProfessor_Disciplina_Id(idAluno, idDisciplina);

        // Se não houver conceito, cria um novo
        Conceito conceito;
        if (conceitos.isEmpty()) {
            conceito = new Conceito();
            conceito.setAluno(aluno);
            conceito.setTurmaDisciplinaProfessor(turmaDisciplinaProfessor);
        } else {
            // Caso contrário, usa o primeiro conceito da lista
            conceito = conceitos.get(0);
        }

        // Atualiza as notas conforme preenchido pelo professor
        conceito.setNotaUnidade1(Optional.ofNullable(inputDTO.getNotaUnidade1()).orElse(conceito.getNotaUnidade1()));
        conceito.setNotaUnidade2(Optional.ofNullable(inputDTO.getNotaUnidade2()).orElse(conceito.getNotaUnidade2()));
        conceito.setNotaUnidade3(Optional.ofNullable(inputDTO.getNotaUnidade3()).orElse(conceito.getNotaUnidade3()));
        conceito.setNotaUnidade4(Optional.ofNullable(inputDTO.getNotaUnidade4()).orElse(conceito.getNotaUnidade4()));
        conceito.setNoa1(Optional.ofNullable(inputDTO.getNoa1()).orElse(conceito.getNoa1()));
        conceito.setNoa2(Optional.ofNullable(inputDTO.getNoa2()).orElse(conceito.getNoa2()));
        conceito.setNoaFinal(Optional.ofNullable(inputDTO.getNoaFinal()).orElse(conceito.getNoaFinal()));

        // Recalcula os conceitos e a média
        calcularConceitos(conceito);
        calcularMediaEStatus(conceito);

        conceitoRepository.save(conceito);

        return convertToDTO(conceito);
    }

    // Método para atualizar o conceito de um aluno
    @Transactional
    public ConceitoDTO atualizarConceitoParaAlunoComTurma(
        String idProfessor, Long idAluno, Long idDisciplina, Long idTurma, Long idConceito, ConceitoInputDTO inputDTO) {
        
        // Verifica se o aluno existe
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Verifica se a turma-disciplina-professor existe
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = turmaDisciplinaProfessorRepository.findById(
                new TurmaDisciplinaProfessorId(idTurma, idDisciplina, idProfessor))
                .orElseThrow(() -> new RuntimeException("Turma, Disciplina ou Professor não encontrados"));

        // Busca o conceito do aluno
        Conceito conceito = conceitoRepository.findById(idConceito)
                .orElseThrow(() -> new RuntimeException("Conceito não encontrado para o aluno e a disciplina"));

        // Atualiza as notas conforme preenchido pelo professor
        conceito.setNotaUnidade1(Optional.ofNullable(inputDTO.getNotaUnidade1()).orElse(conceito.getNotaUnidade1()));
        conceito.setNotaUnidade2(Optional.ofNullable(inputDTO.getNotaUnidade2()).orElse(conceito.getNotaUnidade2()));
        conceito.setNotaUnidade3(Optional.ofNullable(inputDTO.getNotaUnidade3()).orElse(conceito.getNotaUnidade3()));
        conceito.setNotaUnidade4(Optional.ofNullable(inputDTO.getNotaUnidade4()).orElse(conceito.getNotaUnidade4()));
        conceito.setNoa1(Optional.ofNullable(inputDTO.getNoa1()).orElse(conceito.getNoa1()));
        conceito.setNoa2(Optional.ofNullable(inputDTO.getNoa2()).orElse(conceito.getNoa2()));
        conceito.setNoaFinal(Optional.ofNullable(inputDTO.getNoaFinal()).orElse(conceito.getNoaFinal()));

        // Recalcula os conceitos e média
        calcularConceitos(conceito);
        calcularMediaEStatus(conceito);

        // Salva as alterações no conceito
        conceitoRepository.save(conceito);

        // Retorna o DTO atualizado
        return convertToDTO(conceito);
    }

    // ============================= MÉTODOS DE LISTAGEM =============================

    // Método para obter os conceitos de todos os alunos de uma turma específica
    public List<ConceitoDTO> getConceitosPorTurma(String idProfessor, Long idDisciplina, Long idTurma) {
        List<Conceito> conceitos = conceitoRepository.findByTurmaDisciplinaProfessor_Id(new TurmaDisciplinaProfessorId(idTurma, idDisciplina, idProfessor));
        return conceitos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Método para obter os conceitos de um aluno específico 
    public ConceitoDTO getConceitoPorAluno(String idProfessor, Long idAluno, Long idDisciplina, Long idTurma) {
        // Verifica se o aluno existe
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Verifica se a TurmaDisciplinaProfessor existe
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = turmaDisciplinaProfessorRepository.findById(
                new TurmaDisciplinaProfessorId(idTurma, idDisciplina, idProfessor))
                .orElseThrow(() -> new RuntimeException("Turma, Disciplina ou Professor não encontrados"));

        // Busca o conceito do aluno com base no idAluno e TurmaDisciplinaProfessor
        Conceito conceito = conceitoRepository.findByAluno_IdAndTurmaDisciplinaProfessor_Disciplina_Id(idAluno, idDisciplina)
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Conceito não encontrado para o aluno na disciplina especificada"));

        // Retorna o DTO correspondente ao conceito encontrado
        return convertToDTO(conceito);
    }

    // ============================= CÁLCULOS E REGRAS DE NEGÓCIO =============================

    // Cálculos automáticos para os conceitos
    private void calcularConceitos(Conceito conceito) {
        if (conceito.getNotaUnidade1() != null) conceito.setConceitoNota1(calcularConceito(conceito.getNotaUnidade1()));
        if (conceito.getNotaUnidade2() != null) conceito.setConceitoNota2(calcularConceito(conceito.getNotaUnidade2()));
        if (conceito.getNotaUnidade3() != null) conceito.setConceitoNota3(calcularConceito(conceito.getNotaUnidade3()));
        if (conceito.getNotaUnidade4() != null) conceito.setConceitoNota4(calcularConceito(conceito.getNotaUnidade4()));
        if (conceito.getNoa1() != null) conceito.setConceitoNoa1(calcularConceito(conceito.getNoa1()));
        if (conceito.getNoa2() != null) conceito.setConceitoNoa2(calcularConceito(conceito.getNoa2()));
        if (conceito.getNoaFinal() != null) conceito.setConceitoNoaFinal(calcularConceito(conceito.getNoaFinal()));
    }

    // Método para calcular o conceito baseado na nota
    private String calcularConceito(Float nota) {
        if (nota >= 9.5) return "Excelente (E)";
        if (nota >= 8.5) return "Ótimo (O)";
        if (nota >= 7.0) return "Bom (B)";
        if (nota >= 5.0) return "Ainda Não Suficiente (ANS)";
        return "Insuficiente (I)";
    }

    // Cálculo da média final e status de aprovação
    private void calcularMediaEStatus(Conceito conceito) {
        // Substituição das menores notas pelos NOAs, se houver
        if (conceito.getNoa1() != null) {
            if (conceito.getNotaUnidade1() != null && conceito.getNotaUnidade2() != null) {
                if (conceito.getNoa1() > conceito.getNotaUnidade1() && conceito.getNotaUnidade1() < conceito.getNotaUnidade2()) {
                    conceito.setNotaUnidade1(conceito.getNoa1());
                } else if (conceito.getNoa1() > conceito.getNotaUnidade2()) {
                    conceito.setNotaUnidade2(conceito.getNoa1());
                }
            }
        }

        if (conceito.getNoa2() != null) {
            if (conceito.getNotaUnidade3() != null && conceito.getNotaUnidade4() != null) {
                if (conceito.getNoa2() > conceito.getNotaUnidade3() && conceito.getNotaUnidade3() < conceito.getNotaUnidade4()) {
                    conceito.setNotaUnidade3(conceito.getNoa2());
                } else if (conceito.getNoa2() > conceito.getNotaUnidade4()) {
                    conceito.setNotaUnidade4(conceito.getNoa2());
                }
            }
        }

        // Calcula a média final
        Float media = (conceito.getNotaUnidade1() + conceito.getNotaUnidade2() + conceito.getNotaUnidade3() + conceito.getNotaUnidade4()) / 4;

        // Caso o aluno tenha nota de recuperação final (NOA Final)
        if (media < 7 && media >= 5 && conceito.getNoaFinal() != null) {
            media = (media + conceito.getNoaFinal()) / 2;
        }

        conceito.setMediaFinal(media);

        // Define o status de aprovação
        if (media >= 7) {
            conceito.setAprovado(true);
        } else {
            conceito.setAprovado(false);
        }

        // Calcula o conceito final com base na média final
        conceito.setConceitoFinal(calcularConceito(media));
    }

    // ============================= MÉTODOS DE BUSCA =============================

    // Busca todos os conceitos de um aluno
    public List<ConceitoDTO> buscarConceitosPorAluno(Long idAluno) {
        // Verifica se o aluno existe
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Busca todos os conceitos associados ao aluno
        List<Conceito> conceitos = conceitoRepository.findByAluno_Id(idAluno);

        // Converte para DTO e retorna
        return conceitos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Busca conceitos de um aluno por uma disciplina específica
    public List<ConceitoDTO> buscarConceitosPorAlunoEDisciplina(Long idAluno, Long idDisciplina) {
        // Verifica se o aluno existe
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Busca os conceitos do aluno para a disciplina especificada
        List<Conceito> conceitos = conceitoRepository.findByAluno_IdAndTurmaDisciplinaProfessor_Disciplina_Id(idAluno, idDisciplina);

        // Verifica se foram encontrados conceitos
        if (conceitos.isEmpty()) {
            throw new RuntimeException("Nenhum conceito encontrado para o aluno na disciplina especificada");
        }

        // Converte para DTO e retorna
        return conceitos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ============================= CONVERSÃO DE ENTIDADES =============================

    // Converte a entidade Conceito para DTO
    private ConceitoDTO convertToDTO(Conceito conceito) {
        return ConceitoDTO.builder()
        		.id(conceito.getId_conceito())
                .aluno(convertToAlunoResumidoDTO(conceito.getAluno()))  // Converte o aluno para AlunoResumidoDTO
                .turmaDisciplinaProfessor(convertToTurmaDisciplinaProfessorDTO(conceito.getTurmaDisciplinaProfessor()))  // Converte a turma-disciplina-professor para o DTO correspondente
                .notaUnidade1(conceito.getNotaUnidade1())
                .notaUnidade2(conceito.getNotaUnidade2())
                .notaUnidade3(conceito.getNotaUnidade3())
                .notaUnidade4(conceito.getNotaUnidade4())
                .noa1(conceito.getNoa1())
                .noa2(conceito.getNoa2())
                .noaFinal(conceito.getNoaFinal())
                .mediaFinal(conceito.getMediaFinal())
                .aprovado(conceito.getAprovado())
                .conceitoNota1(conceito.getConceitoNota1())
                .conceitoNota2(conceito.getConceitoNota2())
                .conceitoNota3(conceito.getConceitoNota3())
                .conceitoNota4(conceito.getConceitoNota4())
                .conceitoNoa1(conceito.getConceitoNoa1())
                .conceitoNoa2(conceito.getConceitoNoa2())
                .conceitoNoaFinal(conceito.getConceitoNoaFinal())
                .conceitoFinal(conceito.getConceitoFinal())  // Conceito final
                .build();
    }

    // Método para converter Aluno para AlunoResumidoDTO
    private AlunoResumidoDTO convertToAlunoResumidoDTO(Aluno aluno) {
        return AlunoResumidoDTO.builder()
                .nomeAluno(aluno.getNome() + " " + aluno.getUltimoNome())
                .email(aluno.getEmail())
                .build();
    }

    // Método para converter TurmaDisciplinaProfessor para TurmaDisciplinaProfessorCompletoDTO
    private TurmaDisciplinaProfessorCompletoDTO convertToTurmaDisciplinaProfessorDTO(TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        return TurmaDisciplinaProfessorCompletoDTO.builder()
                .nomeTurma(turmaDisciplinaProfessor.getTurma().getNome())
                .nomeDisciplina(turmaDisciplinaProfessor.getDisciplina().getNome())
                .nomeProfessor(turmaDisciplinaProfessor.getProfessor().getNome())
                .build();
    }

}
