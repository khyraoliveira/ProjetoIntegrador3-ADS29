package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;

    /**
     * Lista todas as disciplinas (GET)
     * @return List de DisciplinaGetDTO com os dados das disciplinas
     */
    public List<DisciplinaGetDTO> getAllDisciplinas() {
        return disciplinaRepository.findAll().stream()
                .map(this::convertToDisciplinaGetDTO)  // Converte para DTO de resposta
                .collect(Collectors.toList());
    }

    /**
     * Busca uma disciplina pelo id (GET)
     * @param id ID da disciplina a ser buscada
     * @return Optional de DisciplinaGetDTO se encontrada
     */
    public Optional<DisciplinaGetDTO> getDisciplinaById(Long id) {
        return disciplinaRepository.findById(id)
                .map(this::convertToDisciplinaGetDTO);  // Converte para DTO de resposta
    }

    /**
     * Cria uma nova disciplina e associa à turma e (opcionalmente) a um professor
     * @param disciplinaDTO Dados da disciplina a ser criada
     * @return DisciplinaResumidaDTO com as informações da nova disciplina
     */
    public DisciplinaResumidaDTO saveDisciplina(DisciplinaDTO disciplinaDTO) {
        // Cria a entidade Disciplina
        Disciplina disciplina = Disciplina.builder()
                .nome(disciplinaDTO.getNome())
                .carga_horaria(disciplinaDTO.getCargaHoraria())
                .build();

        // Salva a disciplina
        Disciplina savedDisciplina = disciplinaRepository.save(disciplina);

        // Cenário 1: Se apenas a turma for fornecida
        if (disciplinaDTO.getTurmaId() != null && disciplinaDTO.getProfessorId() == null) {
            Turma turma = turmaRepository.findById(disciplinaDTO.getTurmaId())
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            associarTurmaProfessorSemProfessor(savedDisciplina, turma);
        }

        // Cenário 2: Se apenas o professor for fornecido
        if (disciplinaDTO.getTurmaId() == null && disciplinaDTO.getProfessorId() != null) {
            Professor professor = professorRepository.findByCpf(disciplinaDTO.getProfessorId())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            associarDisciplinaProfessorSemTurma(savedDisciplina, professor);
        }

        // Cenário 3: Se ambos, professor e turma, forem fornecidos
        if (disciplinaDTO.getTurmaId() != null && disciplinaDTO.getProfessorId() != null) {
            Turma turma = turmaRepository.findById(disciplinaDTO.getTurmaId())
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            Professor professor = professorRepository.findByCpf(disciplinaDTO.getProfessorId())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            associarTurmaProfessor(savedDisciplina, turma, professor);
        }

        // Retorna o DTO resumido
        return convertToResumidaDto(savedDisciplina);
    }


    /**
     * Atualiza uma disciplina existente e suas associações
     * @param id ID da disciplina a ser atualizada
     * @param disciplinaDTO Dados atualizados da disciplina
     * @return DisciplinaResumidaDTO com as informações atualizadas
     */
    public DisciplinaResumidaDTO updateDisciplina(Long id, DisciplinaDTO disciplinaDTO) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        // Atualiza os campos da disciplina
        disciplina.setNome(disciplinaDTO.getNome());
        disciplina.setCarga_horaria(disciplinaDTO.getCargaHoraria());

        // Salva a atualização
        Disciplina updatedDisciplina = disciplinaRepository.save(disciplina);

        // Remove as associações anteriores
        turmaDisciplinaProfessorRepository.deleteByDisciplina_Id(disciplina.getId());

        // Associa a nova turma (e professor, se fornecido)
        if (disciplinaDTO.getTurmaId() != null) {
            Turma turma = turmaRepository.findById(disciplinaDTO.getTurmaId())
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

            if (disciplinaDTO.getProfessorId() != null) {
                Professor professor = professorRepository.findById(disciplinaDTO.getProfessorId())
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
                associarTurmaProfessor(updatedDisciplina, turma, professor);
            } else {
                associarTurmaProfessorSemProfessor(updatedDisciplina, turma);
            }
        }

        // Retorna o DTO resumido
        return convertToResumidaDto(updatedDisciplina);
    }

    /**
     * Método para associar uma disciplina a uma turma e professor
     * @param disciplina Disciplina a ser associada
     * @param turma Turma a ser associada
     * @param professor Professor a ser associado
     */
    private void associarTurmaProfessor(Disciplina disciplina, Turma turma, Professor professor) {
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = new TurmaDisciplinaProfessor();
        turmaDisciplinaProfessor.setId(new TurmaDisciplinaProfessorId(turma.getId(), disciplina.getId(), professor.getCpf()));
        turmaDisciplinaProfessor.setTurma(turma);
        turmaDisciplinaProfessor.setDisciplina(disciplina);
        turmaDisciplinaProfessor.setProfessor(professor);
        turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor);
    }

    /**
     * Método para associar uma disciplina a uma turma sem professor
     * @param disciplina Disciplina a ser associada
     * @param turma Turma a ser associada
     */
    private void associarTurmaProfessorSemProfessor(Disciplina disciplina, Turma turma) {
        TurmaDisciplinaProfessor turmaDisciplinaProfessor = new TurmaDisciplinaProfessor();
        turmaDisciplinaProfessor.setId(new TurmaDisciplinaProfessorId(turma.getId(), disciplina.getId(), null));
        turmaDisciplinaProfessor.setTurma(turma);
        turmaDisciplinaProfessor.setDisciplina(disciplina);
        turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor);
    }
      
    
    private void associarDisciplinaProfessorSemTurma(Disciplina disciplina, Professor professor) {
        if (professor == null) {
            throw new RuntimeException("Professor não pode ser nulo");
        }

        TurmaDisciplinaProfessor turmaDisciplinaProfessor = new TurmaDisciplinaProfessor();
        turmaDisciplinaProfessor.setDisciplina(disciplina);
        turmaDisciplinaProfessor.setProfessor(professor);

        // Não seta a turma pois ela é null neste caso
        turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor);
    }
  

    /**
     * Deleta uma disciplina existente
     * @param id ID da disciplina a ser deletada
     */
    public void deleteDisciplina(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        disciplinaRepository.delete(disciplina);
    }

    /**
     * Converte Disciplina para DisciplinaResumidaDTO
     * @param disciplina Entidade Disciplina
     * @return DisciplinaResumidaDTO com dados da disciplina
     */
    private DisciplinaResumidaDTO convertToResumidaDto(Disciplina disciplina) {
        List<TurmaDisciplinaProfessor> turmaDisciplinaProfessores = turmaDisciplinaProfessorRepository
            .findByDisciplinaId(disciplina.getId());

        TurmaDisciplinaProfessor turmaDisciplinaProfessor = !turmaDisciplinaProfessores.isEmpty()
            ? turmaDisciplinaProfessores.get(0)
            : null;  // Retorna a primeira associação, se houver

        // Retorna um DTO resumido, podendo haver associações nulas
        return DisciplinaResumidaDTO.builder()
            .nome(disciplina.getNome())
            .cargaHoraria(disciplina.getCarga_horaria())
            .build();
    }
    
    
    /**
     * Converte Disciplina para DisciplinaGetDTO
     * @param disciplina Entidade Disciplina
     * @return DisciplinaGetDTO com dados da disciplina
     */
    private DisciplinaGetDTO convertToDisciplinaGetDTO(Disciplina disciplina) {
        List<TurmaDisciplinaProfessor> turmaDisciplinaProfessores = turmaDisciplinaProfessorRepository
            .findByDisciplinaId(disciplina.getId());

        TurmaDisciplinaProfessor turmaDisciplinaProfessor = !turmaDisciplinaProfessores.isEmpty()
            ? turmaDisciplinaProfessores.get(0)
            : null;  // Retorna a primeira associação, se houver

        // Retorna o DTO com os dados completos
        return DisciplinaGetDTO.builder()
        	.id(disciplina.getId())
            .nome(disciplina.getNome())
            .carga_horaria(disciplina.getCarga_horaria())
            .nomeTurma(turmaDisciplinaProfessor != null && turmaDisciplinaProfessor.getTurma() != null
                ? turmaDisciplinaProfessor.getTurma().getNome()
                : null)
            .nomeProfessor(turmaDisciplinaProfessor != null && turmaDisciplinaProfessor.getProfessor() != null
                ? turmaDisciplinaProfessor.getProfessor().getNome() + " " + turmaDisciplinaProfessor.getProfessor().getUltimoNome()
                : null)
            .build();
    }
}
