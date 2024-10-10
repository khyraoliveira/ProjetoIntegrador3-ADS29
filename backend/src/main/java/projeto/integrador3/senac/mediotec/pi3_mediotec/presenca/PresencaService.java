package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumido3DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PresencaService {

    // ============================= INJEÇÕES DE DEPENDÊNCIA =============================
    
    @Autowired
    private PresencaRepository presencaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    // ============================= MÉTODOS DE CRUD =============================
    
    /**
     * Salvar uma nova presença ou atualizar uma já existente.
     * 
     * @param idAluno ID do aluno
     * @param idTurma ID da turma
     * @param idDisciplina ID da disciplina
     * @param idProfessor ID do professor
     * @param presencaInputDTO Dados da presença a ser registrada
     * @return PresencaDTO Objeto com os dados da presença salva
     */
    @Transactional
    public PresencaDTO salvarPresenca(Long idAluno, Long idTurma, Long idDisciplina, String idProfessor, PresencaInputDTO presencaInputDTO) {
        Aluno aluno = buscarAlunoPorId(idAluno);
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = buscarTurmaDisciplinaProfessor(idTurma, idDisciplina, idProfessor);

        Presenca presenca = convertToEntity(presencaInputDTO);
        presenca.setAluno(aluno);
        presenca.setTurmaDisciplinaProfessor(turmaDisciplinaProfessor);

        Presenca savedPresenca = presencaRepository.save(presenca);
        return convertToDTO(savedPresenca);
    }

    /**
     * Buscar uma presença por ID.
     * 
     * @param idAluno ID do aluno
     * @param idPresenca ID da presença
     * @return Optional<PresencaDTO> Presença encontrada
     */
    public Optional<PresencaDTO> buscarPresencaPorId(Long idAluno, Long idPresenca) {
        Aluno aluno = buscarAlunoPorId(idAluno);
        return presencaRepository.findById(idPresenca)
                .filter(presenca -> presenca.getAluno().equals(aluno))
                .map(this::convertToDTO);
    }

    /**
     * Listar todas as presenças de um aluno.
     * 
     * @param idAluno ID do aluno
     * @return Lista de presenças do aluno
     */
    public List<PresencaDTO> listarPresencasPorAluno(Long idAluno) {
        Aluno aluno = buscarAlunoPorId(idAluno);
        return presencaRepository.findByAluno(aluno).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Listar todas as presenças de um aluno em uma disciplina.
     * 
     * @param idAluno ID do aluno
     * @param idDisciplina ID da disciplina
     * @return Lista de presenças do aluno na disciplina
     */
    public List<PresencaDTO> listarPresencasPorAlunoEDisciplina(Long idAluno, Long idDisciplina) {
        Aluno aluno = buscarAlunoPorId(idAluno);
        Disciplina disciplina = buscarDisciplinaPorId(idDisciplina);
        return presencaRepository.findByAlunoAndTurmaDisciplinaProfessor_Disciplina(aluno, disciplina).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Listar todas as presenças emitidas por um professor.
     * 
     * @param idProfessor ID do professor
     * @return Lista de presenças emitidas pelo professor
     */
    public List<PresencaDTO> listarPresencasEmitidasPorProfessor(String idProfessor) {
        Professor professor = buscarProfessorPorId(idProfessor);
        return presencaRepository.findByTurmaDisciplinaProfessor_Professor(professor).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Deletar uma presença por ID.
     * 
     * @param idAluno ID do aluno
     * @param idPresenca ID da presença
     */
    @Transactional
    public void deletarPresenca(Long idAluno, Long idPresenca) {
        Aluno aluno = buscarAlunoPorId(idAluno);
        Presenca presenca = presencaRepository.findById(idPresenca)
                .filter(p -> p.getAluno().equals(aluno))
                .orElseThrow(() -> new RuntimeException("Presença não encontrada ou não pertence ao aluno informado"));
        presencaRepository.delete(presenca);
    }

    // ============================= MÉTODOS AUXILIARES =============================
    
    private Aluno buscarAlunoPorId(Long idAluno) {
        return alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    private TurmaDisciplinaProfessor buscarTurmaDisciplinaProfessor(Long idTurma, Long idDisciplina, String idProfessor) {
        return turmaDisciplinaProfessorRepository.findById(new TurmaDisciplinaProfessorId(idTurma, idDisciplina, idProfessor))
                .orElseThrow(() -> new RuntimeException("Turma, Disciplina ou Professor não encontrados"));
    }

    private Disciplina buscarDisciplinaPorId(Long idDisciplina) {
        return disciplinaRepository.findById(idDisciplina)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
    }

    private Professor buscarProfessorPorId(String idProfessor) {
        return professorRepository.findById(idProfessor)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    // ============================= MÉTODOS DE CONVERSÃO =============================
    
    private PresencaDTO convertToDTO(Presenca presenca) {
        return PresencaDTO.builder()
                .id_presenca(presenca.getId_presenca())
                .data(presenca.getData())
                .presenca(presenca.getPresenca())
                .aluno(AlunoResumidoDTO.builder()
                        .nomeAluno(presenca.getAluno().getNome() + " " + presenca.getAluno().getUltimoNome())
                        .email(presenca.getAluno().getEmail())
                        .build())
                .turma(TurmaResumida2DTO.builder()
                        .nome(presenca.getTurmaDisciplinaProfessor().getTurma().getNome())
                        .anoLetivo(presenca.getTurmaDisciplinaProfessor().getTurma().getAnoLetivo())
                        .anoEscolar(presenca.getTurmaDisciplinaProfessor().getTurma().getAnoEscolar())
                        .turno(presenca.getTurmaDisciplinaProfessor().getTurma().getTurno())
                        .build())
                .disciplina(DisciplinaResumida2DTO.builder()
                        .nome(presenca.getTurmaDisciplinaProfessor().getDisciplina().getNome())
                        .build())
                .professor(ProfessorResumido3DTO.builder()
                        .nomeProfessor(presenca.getTurmaDisciplinaProfessor().getProfessor().getNome())
                        .email(presenca.getTurmaDisciplinaProfessor().getProfessor().getEmail())
                        .build())
                .build();
    }

    private Presenca convertToEntity(PresencaInputDTO presencaInputDTO) {
        return Presenca.builder()
                .data(presencaInputDTO.getData())
                .presenca(presencaInputDTO.getPresenca())
                .build();
    }
}
