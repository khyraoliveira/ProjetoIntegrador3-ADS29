package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    // ============================= REPOSITORIES =============================
    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;

    // ============================= CREATE METHODS =============================

    /**
     * Cria uma nova turma e associa os dados fornecidos, como alunos, disciplinas e professores.
     * 
     * @param turmaDTO Dados resumidos da turma a ser criada.
     * @return TurmaDTO com os detalhes da turma criada.
     */
    @Transactional
    public TurmaDTO saveTurma(TurmaInputDTO turmaDTO) {
        // Cria um novo objeto Turma e define os atributos básicos
        Turma turma = new Turma();	
        turma.setAnoLetivo(turmaDTO.getAnoLetivo());
        turma.setAnoEscolar(turmaDTO.getAnoEscolar()); // Novo atributo anoEscolar
        turma.setTurno(turmaDTO.getTurno());
        turma.setStatus(turmaDTO.isStatus());         // Define status ao criar uma nova turma

        // Associa a coordenação
        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        // Salva a turma inicialmente para gerar o ID
        Turma savedTurma = turmaRepository.save(turma);

        // Gera o nome da turma baseado no ID
        String nomeGerado = String.format("Turma %02d", savedTurma.getId());
        savedTurma.setNome(nomeGerado);

        // Salva novamente a turma com o nome gerado
        turmaRepository.save(savedTurma);

        // Associa alunos à turma, se fornecidos
        this.associateAlunos(turmaDTO, savedTurma);

        // Associa disciplinas e professores à turma
        this.associateDisciplinasProfessores(turmaDTO, savedTurma);

        // Retorna a turma criada em formato DTO
        return convertToDto(savedTurma);
    }

    // ============================= UPDATE METHODS =============================

    /**
     * Atualiza uma turma existente com base no ID fornecido.
     * 
     * @param id ID da turma a ser atualizada.
     * @param turmaDTO Dados atualizados da turma.
     * @return TurmaDTO com as informações atualizadas.
     */
    @Transactional
    public TurmaDTO updateTurma(Long id, TurmaInputDTO turmaDTO) {
        // Busca a turma existente no banco de dados
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Atualiza os atributos da turma
        turma.setAnoLetivo(turmaDTO.getAnoLetivo());
        turma.setAnoEscolar(turmaDTO.getAnoEscolar()); 
        turma.setTurno(turmaDTO.getTurno());          
        turma.setStatus(turmaDTO.isStatus());          

        // Atualiza a coordenação
        Coordenacao coordenacao = coordenacaoRepository.findById(turmaDTO.getCoordenacaoId())
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
        turma.setCoordenacao(coordenacao);

        // Gera o nome novamente baseado no ID
        String nomeGerado = String.format("Turma %02d", turma.getId());
        turma.setNome(nomeGerado);

        // Atualiza os alunos associados
        this.associateAlunos(turmaDTO, turma);

        // Remove as associações antigas de Turma-Disciplina-Professor
        turmaDisciplinaProfessorRepository.deleteByTurmaId(turma.getId());

        // Atualiza as disciplinas e professores
        this.associateDisciplinasProfessores(turmaDTO, turma);

        // Salva a turma atualizada e retorna o DTO
        Turma updatedTurma = turmaRepository.save(turma);
        return convertToDto(updatedTurma);
    }

    // ============================= DELETE METHODS =============================

    /**
     * Deleta uma turma existente com base no seu ID.
     * 
     * @param id ID da turma a ser deletada.
     */
    @Transactional
    public void deleteTurma(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Remove as associações de Turma-Disciplina-Professor antes de deletar a turma
        turmaDisciplinaProfessorRepository.deleteByTurmaId(turma.getId());

        turmaRepository.delete(turma);
    }

    // ============================= GET METHODS =============================

    /**
     * Lista todas as turmas cadastradas.
     * 
     * @return Lista de TurmaDTO com as informações de todas as turmas.
     */
    public List<TurmaDTO> getAllTurmas() {
        return turmaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma turma pelo ID e retorna suas informações detalhadas.
     * 
     * @param id ID da turma a ser buscada.
     * @return Optional com TurmaDTO caso a turma seja encontrada, ou vazio se não for.
     */
    public Optional<TurmaDTO> getTurmaById(Long id) {
        return turmaRepository.findById(id)
                .map(this::convertToDto);
    }

    // ============================= ASSOCIATION METHODS =============================

    /**
     * Associa alunos à turma com base nos IDs fornecidos.
     */
    private void associateAlunos(TurmaInputDTO turmaDTO, Turma turma) {
        if (turmaDTO.getAlunosIds() != null) {
            // Limpa os alunos atuais
            turma.getAlunos().clear();

            // Associa os novos alunos à turma
            for (Long alunoId : turmaDTO.getAlunosIds()) {
                Aluno aluno = alunoRepository.findById(alunoId)
                        .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
                turma.addAluno(aluno);  // Atualiza a relação aluno-turma
            }
        }
    }

    /**
     * Associa disciplinas e professores à turma.
     */
    private void associateDisciplinasProfessores(TurmaInputDTO turmaDTO, Turma turma) {
        if (turmaDTO.getDisciplinasProfessores() != null) {
            for (DisciplinaProfessorInputDTO dpDTO : turmaDTO.getDisciplinasProfessores()) {
                Professor professor = professorRepository.findById(dpDTO.getProfessorId())
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

                for (Long disciplinaId : dpDTO.getDisciplinasIds()) {
                    Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                            .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

                    // Cria a relação entre Turma, Disciplina e Professor
                    TurmaDisciplinaProfessor turmaDisciplinaProfessor = new TurmaDisciplinaProfessor();
                    turmaDisciplinaProfessor.setId(new TurmaDisciplinaProfessorId(turma.getId(), disciplina.getId(), professor.getCpf()));
                    turmaDisciplinaProfessor.setTurma(turma);
                    turmaDisciplinaProfessor.setDisciplina(disciplina);
                    turmaDisciplinaProfessor.setProfessor(professor);

                    // Salva a relação no repositório
                    turmaDisciplinaProfessorRepository.save(turmaDisciplinaProfessor);
                }
            }
        }
    }

    // ============================= CONVERSÃO =============================

    /**
     * Converte uma Turma para TurmaDTO, incluindo informações de disciplinas e professores.
     */
    private TurmaDTO convertToDto(Turma turma) {
        // Converte as disciplinas associadas para DisciplinaResumida2DTO
        Set<DisciplinaResumida2DTO> disciplinasDTO = turma.getTurmaDisciplinaProfessores() != null ?
            turma.getTurmaDisciplinaProfessores().stream()
                .map(turmaDisciplinaProfessor -> turmaDisciplinaProfessor.getDisciplina())  // Obtém as disciplinas
                .distinct()
                .map(disciplina -> DisciplinaResumida2DTO.builder()
                        .nome(disciplina.getNome())
                        .build())
                .collect(Collectors.toSet()) : Collections.emptySet();

        // Converte disciplinas e professores para DisciplinaProfessorDTO
        Set<DisciplinaProfessorDTO> disciplinasProfessoresDTO = turma.getTurmaDisciplinaProfessores() != null ?
            turma.getTurmaDisciplinaProfessores().stream()
                .collect(Collectors.groupingBy(
                    tdp -> tdp.getProfessor().getCpf(),
                    Collectors.mapping(tdp -> tdp.getDisciplina(), Collectors.toSet())
                ))
                .entrySet().stream()
                .map(e -> {
                    Professor professor = professorRepository.findById(e.getKey())
                            .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

                    Set<String> nomesDisciplinas = e.getValue().stream()
                            .map(Disciplina::getNome)
                            .collect(Collectors.toSet());

                    return DisciplinaProfessorDTO.builder()
                            .professorId(professor.getCpf())
                            .nomeProfessor(professor.getNome() + " " + professor.getUltimoNome())
                            .email(professor.getEmail())
                            .nomesDisciplinas(nomesDisciplinas)
                            .build();
                })
                .collect(Collectors.toSet()) : Collections.emptySet();

        // Converte os alunos da turma para AlunoResumidoDTO
        Set<AlunoResumidoDTO> alunosDTO = turma.getAlunos() != null ?
            turma.getAlunos().stream()
                .map(aluno -> AlunoResumidoDTO.builder()
                    .nomeAluno(aluno.getNome() + " " + aluno.getUltimoNome())
                    .email(aluno.getEmail())
                    .build())
                .collect(Collectors.toSet()) : Collections.emptySet();

        return TurmaDTO.builder()
        	    .id(turma.getId())
        	    .nome(turma.getNome())
        	    .anoLetivo(turma.getAnoLetivo())
        	    .anoEscolar(turma.getAnoEscolar())
        	    .turno(turma.getTurno())
        	    .status(turma.isStatus())
        	    .coordenacao(CoordenacaoResumidaDTO.builder()
        	        .nome(turma.getCoordenacao().getNome())
        	        .coordenadores(
        	            turma.getCoordenacao().getCoordenadores()
        	                .stream()
        	                .map(coordenador -> CoordenadorResumidoDTO.builder()
        	                        .nomeCoordenador(coordenador.getNome() + " " + coordenador.getUltimoNome()) // Concatenação de nome
        	                        .email(coordenador.getEmail()) // Email do coordenador
        	                        .build()
        	                    )
        	                .collect(Collectors.toList())
        	        )
        	        .build()
        	    )
        	    .disciplinas(disciplinasDTO)
        	    .disciplinasProfessores(disciplinasProfessoresDTO)
        	    .alunos(alunosDTO)
        	    .build();



    }
}
