package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurmaDisciplinaProfessorService {

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    // Lista todas as TurmaDisciplinaProfessor
    public List<TurmaDisciplinaProfessorDTO> getAllTurmaDisciplinaProfessores() {
        return turmaDisciplinaProfessorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Busca TurmaDisciplinaProfessor pelo id (composto)
    public Optional<TurmaDisciplinaProfessorDTO> getTurmaDisciplinaProfessorById(Long turmaId, Long disciplinaId, String professorId) {
        TurmaDisciplinaProfessorId id = new TurmaDisciplinaProfessorId(turmaId, disciplinaId, professorId);
        return turmaDisciplinaProfessorRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Cria nova TurmaDisciplinaProfessor
    public TurmaDisciplinaProfessorDTO saveTurmaDisciplinaProfessor(Long turmaId, Long disciplinaId, String professorId) {
        validateTurmaDisciplinaProfessor(turmaId, disciplinaId, professorId);

        TurmaDisciplinaProfessor turmaDisciplinaProfessor = TurmaDisciplinaProfessor.builder()
                .id(new TurmaDisciplinaProfessorId(turmaId, disciplinaId, professorId))
                .build();

        return convertToDTO(turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor));
    }

    // Edita TurmaDisciplinaProfessor
    public TurmaDisciplinaProfessorDTO updateTurmaDisciplinaProfessor(Long turmaId, Long disciplinaId, String professorId,
                                                                      TurmaDisciplinaProfessorDTO turmaDisciplinaProfessorDetails) {
        TurmaDisciplinaProfessorId id = new TurmaDisciplinaProfessorId(turmaId, disciplinaId, professorId);

        TurmaDisciplinaProfessor turmaDisciplinaProfessor = turmaDisciplinaProfessorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplinaProfessor não encontrado"));

        // Atualiza apenas os IDs se necessário
        turmaDisciplinaProfessor.setId(new TurmaDisciplinaProfessorId(
                turmaDisciplinaProfessorDetails.getTurmaId(),
                turmaDisciplinaProfessorDetails.getDisciplinaId(),
                turmaDisciplinaProfessorDetails.getProfessorId()
        ));

        return convertToDTO(turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor));
    }

    // Deleta TurmaDisciplinaProfessor
    public void deleteTurmaDisciplinaProfessor(Long turmaId, Long disciplinaId, String professorId) {
        TurmaDisciplinaProfessorId id = new TurmaDisciplinaProfessorId(turmaId, disciplinaId, professorId);
        turmaDisciplinaProfessorRepository.deleteById(id);
    }

    // Lista todas as disciplinas associadas a uma turma
    public List<Long> getDisciplinasByTurma(Long turmaId) {
        List<TurmaDisciplinaProfessor> turmaDisciplinaProfessors = turmaDisciplinaProfessorRepository.findByTurmaId(turmaId);

        return turmaDisciplinaProfessors.stream()
                .map(tdp -> tdp.getId().getDisciplinaId()) // Retorna apenas os IDs das disciplinas
                .collect(Collectors.toList());
    }

    // Lista todos os professores que lecionam uma disciplina em uma turma
    public List<String> getProfessoresByTurmaAndDisciplina(Long turmaId, Long disciplinaId) {
        List<TurmaDisciplinaProfessor> turmaDisciplinaProfessors = turmaDisciplinaProfessorRepository.findById_TurmaIdAndId_DisciplinaId(turmaId, disciplinaId);

        return turmaDisciplinaProfessors.stream()
                .map(tdp -> tdp.getId().getProfessorId()) // Retorna apenas os CPFs dos professores
                .collect(Collectors.toList());
    }

    // Método de conversão para DTO
    private TurmaDisciplinaProfessorDTO convertToDTO(TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        return TurmaDisciplinaProfessorDTO.builder()
                .turmaId(turmaDisciplinaProfessor.getId().getTurmaId())
                .disciplinaId(turmaDisciplinaProfessor.getId().getDisciplinaId())
                .professorId(turmaDisciplinaProfessor.getId().getProfessorId())
                .build();
    }

    // Método para validar se Turma, Disciplina e Professor existem
    private void validateTurmaDisciplinaProfessor(Long turmaId, Long disciplinaId, String professorCpf) {
        // Valida a existência da Turma
        if (!turmaRepository.existsById(turmaId)) {
            throw new RuntimeException("Turma com ID " + turmaId + " não encontrada");
        }

        // Valida a existência da Disciplina
        if (!disciplinaRepository.existsById(disciplinaId)) {
            throw new RuntimeException("Disciplina com ID " + disciplinaId + " não encontrada");
        }

        // Valida a existência do Professor
        if (!professorRepository.existsById(professorCpf)) {
            throw new RuntimeException("Professor com CPF " + professorCpf + " não encontrado");
        }
    }
}
