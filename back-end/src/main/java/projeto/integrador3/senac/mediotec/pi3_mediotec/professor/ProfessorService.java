package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    // ============================= POST METHODS =============================

    /**
     * Cria um novo professor e associa as informações fornecidas, como endereços,
     * telefones, turmas e disciplinas.
     * 
     * @param professorDTO Dados completos do professor.
     * @return ProfessorResumidoDTO com as informações do professor criado.
     */
    @Transactional
    public ProfessorResumidoDTO saveProfessor(ProfessorDTO professorDTO) {
        // Verifica se o CPF já está registrado
        if (professorRepository.existsByCpf(professorDTO.getCpf())) {
            throw new RuntimeException("Professor com CPF " + professorDTO.getCpf() + " já existe.");
        }

        // Busca e associa a coordenação (opcional)
        Coordenacao coordenacao = null;
        if (professorDTO.getCoordenacaoId() != null) {
            coordenacao = coordenacaoRepository.findById(professorDTO.getCoordenacaoId())
                    .orElseThrow(() -> new RuntimeException("Coordenação não encontrada."));
        }

        // Cria o objeto Professor
        Professor professor = Professor.builder()
                .cpf(professorDTO.getCpf())
                .nome(professorDTO.getNome())
                .ultimoNome(professorDTO.getUltimoNome())
                .genero(professorDTO.getGenero())
                .data_nascimento(professorDTO.getData_nascimento())
                .email(professorDTO.getEmail())
                .coordenacao(coordenacao)
                .status(true)  // Define status como true
                .build();

        // Salva o professor no banco de dados
        Professor savedProfessor = professorRepository.save(professor);

        // Associa e salva os endereços do professor
        this.associateEnderecos(professorDTO, savedProfessor);

        // Associa e salva os telefones do professor
        this.associateTelefones(professorDTO, savedProfessor);

        // Associa e salva turmas e disciplinas ao professor
        this.associateTurmasDisciplinas(professorDTO, savedProfessor);

        // Atualiza e retorna o ProfessorResumidoDTO
        return convertToDto(savedProfessor);
    }

    // ============================= PUT METHODS =============================

    /**
     * Atualiza as informações de um professor existente com base no CPF.
     * 
     * @param cpf CPF do professor.
     * @param professorDTO Dados atualizados do professor.
     * @return ProfessorResumidoDTO com as informações atualizadas.
     */
    @Transactional
    public ProfessorResumidoDTO updateProfessor(String cpf, ProfessorDTO professorDTO) {
        // Busca o professor no banco de dados
        Professor professor = professorRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Professor com CPF " + cpf + " não encontrado"));

        // Atualiza os dados básicos do professor
        professor.setNome(professorDTO.getNome());
        professor.setUltimoNome(professorDTO.getUltimoNome());
        professor.setGenero(professorDTO.getGenero());
        professor.setEmail(professorDTO.getEmail());
        professor.setData_nascimento(professorDTO.getData_nascimento());
        professor.setStatus(professorDTO.isStatus());

        // Atualiza a coordenação (opcional)
        if (professorDTO.getCoordenacaoId() != null) {
            Coordenacao coordenacao = coordenacaoRepository.findById(professorDTO.getCoordenacaoId())
                    .orElseThrow(() -> new RuntimeException("Coordenação com ID " + professorDTO.getCoordenacaoId() + " não encontrada"));
            professor.setCoordenacao(coordenacao);
        }

        // Atualiza endereços, telefones, turmas e disciplinas
        this.associateEnderecos(professorDTO, professor);
        this.associateTelefones(professorDTO, professor);
        this.associateTurmasDisciplinas(professorDTO, professor);

        // Retorna o ProfessorResumidoDTO atualizado
        return convertToDto(professorRepository.save(professor));
    }

    // ============================= DELETE METHODS =============================

    /**
     * Deleta um professor com base no CPF.
     * 
     * @param cpf CPF do professor a ser deletado.
     */
    public void deleteProfessor(String cpf) {
        Professor professor = professorRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com o CPF: " + cpf));
        professorRepository.delete(professor);
    }

    // ============================= GET METHODS =============================

    /**
     * Busca todos os professores e retorna uma lista resumida.
     * 
     * @return Lista de ProfessorResumidoDTO.
     */
    public List<ProfessorResumidoDTO> getAllProfessores() {
        return professorRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Busca um professor pelo CPF e retorna as informações resumidas.
     * 
     * @param cpf CPF do professor.
     * @return Optional com ProfessorResumidoDTO, vazio se não encontrado.
     */
    public Optional<ProfessorResumidoDTO> getProfessorById(String cpf) {
        return professorRepository.findById(cpf)
                .map(this::convertToDto);
    }

    // ============================= MÉTODOS AUXILIARES =============================

    /**
     * Associa endereços ao professor.
     */
    private void associateEnderecos(ProfessorDTO professorDTO, Professor professor) {
        if (professorDTO.getEnderecos() != null && !professorDTO.getEnderecos().isEmpty()) {
            Set<Endereco> enderecos = professorDTO.getEnderecos().stream()
                    .map(enderecoDTO -> Endereco.builder()
                            .cep(enderecoDTO.getCep())
                            .rua(enderecoDTO.getRua())
                            .numero(enderecoDTO.getNumero())
                            .bairro(enderecoDTO.getBairro())
                            .cidade(enderecoDTO.getCidade())
                            .estado(enderecoDTO.getEstado())
                            .professor(professor)
                            .build())
                    .collect(Collectors.toSet());
            professor.setEnderecos(enderecos);
        }
    }

    /**
     * Associa telefones ao professor.
     */
    private void associateTelefones(ProfessorDTO professorDTO, Professor professor) {
        if (professorDTO.getTelefones() != null && !professorDTO.getTelefones().isEmpty()) {
            Set<Telefone> telefones = professorDTO.getTelefones().stream()
                    .map(telefoneDTO -> Telefone.builder()
                            .ddd(telefoneDTO.getDdd())
                            .numero(telefoneDTO.getNumero())
                            .professor(professor)
                            .build())
                    .collect(Collectors.toSet());
            professor.setTelefones(telefones);
        }
    }

    /**
     * Associa turmas e disciplinas ao professor.
     */
    private void associateTurmasDisciplinas(ProfessorDTO professorDTO, Professor professor) {
        if (professorDTO.getTurmasDisciplinas() != null && !professorDTO.getTurmasDisciplinas().isEmpty()) {
            professorDTO.getTurmasDisciplinas().forEach(turmaDisciplinaDTO -> {
                Turma turma = turmaRepository.findById(turmaDisciplinaDTO.getTurmaId())
                    .orElseThrow(() -> new RuntimeException("Turma com ID " + turmaDisciplinaDTO.getTurmaId() + " não encontrada"));
                Disciplina disciplina = disciplinaRepository.findById(turmaDisciplinaDTO.getDisciplinaId())
                    .orElseThrow(() -> new RuntimeException("Disciplina com ID " + turmaDisciplinaDTO.getDisciplinaId() + " não encontrada"));

                TurmaDisciplinaProfessor tdp = TurmaDisciplinaProfessor.builder()
                    .professor(professor)
                    .turma(turma)
                    .disciplina(disciplina)
                    .id(new TurmaDisciplinaProfessorId(turma.getId(), disciplina.getId(), professor.getCpf()))
                    .build();

                turmaDisciplinaProfessorRepository.save(tdp);
                professor.addTurmaDisciplinaProfessor(tdp);
            });
        } else {
            // Log or handle the case where no turmasDisciplinas are provided
            System.out.println("Nenhuma turma ou disciplina foi fornecida.");
        }
    }
    // ============================= CONVERSORES =============================

    /**
     * Converte um Professor para ProfessorResumidoDTO.
     */
    private ProfessorResumidoDTO convertToDto(Professor professor) {
        // Mapeia coordenadores da coordenação
        CoordenacaoResumidaDTO coordenacaoDTO = null;
        if (professor.getCoordenacao() != null) {
            Coordenacao coordenacao = professor.getCoordenacao();
            List<CoordenadorResumidoDTO> coordenadoresDTO = coordenacao.getCoordenadores().stream()
                    .map(coordenador -> CoordenadorResumidoDTO.builder()
                            .nomeCoordenador(coordenador.getNome() + " " + coordenador.getUltimoNome())
                            .email(coordenador.getEmail())
                            .build())
                    .collect(Collectors.toList());

            coordenacaoDTO = CoordenacaoResumidaDTO.builder()
                    .nome(coordenacao.getNome())
                    .coordenadores(coordenadoresDTO)
                    .build();
        }

        // Mapeia turmas e disciplinas associadas ao professor
        Set<TurmaDisciplinaResumidaDTO> turmasDisciplinas = professor.getTurmaDisciplinaProfessores() != null
        	    ? professor.getTurmaDisciplinaProfessores().stream()
        	        .map(tdp -> TurmaDisciplinaResumidaDTO.builder()
        	            .turma(TurmaResumida2DTO.builder()
        	                .nome(tdp.getTurma().getNome())
        	                .anoLetivo(tdp.getTurma().getAnoLetivo())
        	                .anoEscolar(tdp.getTurma().getAnoEscolar())
        	                .turno(tdp.getTurma().getTurno())
        	                .build())
        	            .disciplina(DisciplinaResumida2DTO.builder()
        	                .nome(tdp.getDisciplina().getNome())
        	                .build())
        	            .build())
        	        .collect(Collectors.toSet())
        	    : new HashSet<>(); // Retorna um Set vazio se for null

        // Mapeia endereços do professor
        Set<EnderecoDTO> enderecosDTO = professor.getEnderecos().stream()
                .map(endereco -> EnderecoDTO.builder()
                        .cep(endereco.getCep())
                        .rua(endereco.getRua())
                        .numero(endereco.getNumero())
                        .bairro(endereco.getBairro())
                        .cidade(endereco.getCidade())
                        .estado(endereco.getEstado())
                        .build())
                .collect(Collectors.toSet());

        // Mapeia telefones do professor
        Set<TelefoneDTO> telefonesDTO = professor.getTelefones().stream()
                .map(telefone -> TelefoneDTO.builder()
                        .ddd(telefone.getDdd())
                        .numero(telefone.getNumero())
                        .build())
                .collect(Collectors.toSet());

        // Constrói e retorna o DTO completo
        return ProfessorResumidoDTO.builder()
        	    .cpf(professor.getCpf())
        	    .nome(professor.getNome())
        	    .ultimoNome(professor.getUltimoNome())
        	    .email(professor.getEmail())
        	    .coordenacao(coordenacaoDTO)
        	    .turmaDisciplinaProfessores(turmasDisciplinas)  // Use as turmas mapeadas
        	    .enderecos(!enderecosDTO.isEmpty() ? enderecosDTO : null)
        	    .telefones(!telefonesDTO.isEmpty() ? telefonesDTO : null)
        	    .build();
    }
}
