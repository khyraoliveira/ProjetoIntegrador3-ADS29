package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel.Responsavel;
import projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel.ResponsavelDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.DisciplinaProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    // ============================= GET METHODS =============================

    /**
     * Lista todos os alunos cadastrados com suas informações detalhadas.
     * 
     * @return Lista de AlunoDTO com informações detalhadas.
     */
    public List<AlunoDTO> getAllAlunos() {
        return alunoRepository.findAll().stream()
                .map(this::convertToDto) // Converte cada aluno para AlunoDTO
                .collect(Collectors.toList());
    }

    /**
     * Busca um aluno pelo ID e retorna suas informações detalhadas.
     * 
     * @param idAluno ID do aluno a ser buscado.
     * @return Optional com AlunoDTO caso seja encontrado, vazio se não for.
     */
    public Optional<AlunoDTO> getAlunoById(Long idAluno) {
        return alunoRepository.findById(idAluno)
                .map(this::convertToDto); // Converte para AlunoDTO
    }

    // ============================= POST METHODS =============================

    /**
     * Cria um novo aluno com os dados fornecidos e associa informações como endereços,
     * telefones, turmas e responsáveis.
     * 
     * @param alunoResumidoDTO Dados resumidos do aluno a ser criado.
     * @return AlunoDTO com as informações do aluno criado.
     */
    @Transactional
    public AlunoDTO saveAluno(AlunoResumidoDTO2 alunoResumidoDTO) {
        // Verifica se o aluno já existe pelo CPF
        if (alunoRepository.existsByCpf(alunoResumidoDTO.getCpf())) {
            throw new RuntimeException("Aluno com CPF " + alunoResumidoDTO.getCpf() + " já existe");
        }

        // Cria o objeto Aluno e associa os dados básicos
        Aluno aluno = new Aluno();
        aluno.setNome(alunoResumidoDTO.getNome());
        aluno.setUltimoNome(alunoResumidoDTO.getUltimoNome());
        aluno.setGenero(alunoResumidoDTO.getGenero());
        aluno.setCpf(alunoResumidoDTO.getCpf());
        aluno.setEmail(alunoResumidoDTO.getEmail());
        aluno.setData_nascimento(alunoResumidoDTO.getData_nascimento());
        aluno.setStatus(true);

        // Associa endereços, telefones, turmas e responsáveis
        this.associateAddresses(alunoResumidoDTO, aluno);
        this.associatePhones(alunoResumidoDTO, aluno);
        this.associateTurmas(alunoResumidoDTO, aluno);

        // Salva o aluno no banco de dados
        Aluno savedAluno = alunoRepository.save(aluno);

        // Associa e salva responsáveis ao aluno
        this.associateResponsaveis(alunoResumidoDTO, savedAluno);

        // Retorna o AlunoDTO criado
        return convertToDto(savedAluno);
    }

    // ============================= PUT METHODS =============================

    /**
     * Atualiza as informações de um aluno existente com base no ID fornecido.
     * 
     * @param idAluno ID do aluno a ser atualizado.
     * @param alunoResumidoDTO Dados atualizados do aluno.
     * @return AlunoDTO com as informações atualizadas.
     */
    @Transactional
    public AlunoDTO updateAluno(Long idAluno, AlunoResumidoDTO2 alunoResumidoDTO) {
        // Busca o aluno no banco de dados
        Aluno aluno = alunoRepository.findById(idAluno)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Atualiza os dados do aluno
        aluno.setNome(alunoResumidoDTO.getNome());
        aluno.setUltimoNome(alunoResumidoDTO.getUltimoNome());
        aluno.setGenero(alunoResumidoDTO.getGenero());
        aluno.setCpf(alunoResumidoDTO.getCpf());
        aluno.setEmail(alunoResumidoDTO.getEmail());
        aluno.setData_nascimento(alunoResumidoDTO.getData_nascimento());
        aluno.setStatus(true);

        // Atualiza endereços, telefones e turmas
        this.associateAddresses(alunoResumidoDTO, aluno);
        this.associatePhones(alunoResumidoDTO, aluno);
        this.associateTurmas(alunoResumidoDTO, aluno);

        // Atualiza responsáveis e salva o aluno atualizado
        this.associateResponsaveis(alunoResumidoDTO, aluno);

        // Retorna o AlunoDTO atualizado
        return convertToDto(alunoRepository.save(aluno));
    }

    // ============================= DELETE METHODS =============================

    /**
     * Deleta um aluno com base no seu ID.
     * 
     * @param idAluno ID do aluno a ser deletado.
     */
    @Transactional
    public void deleteAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado com o ID: " + idAluno));

        // Remover a associação do aluno com todas as turmas
        aluno.getTurmas().forEach(turma -> turma.getAlunos().remove(aluno));
        
        // Agora que o aluno foi removido das turmas, podemos deletá-lo
        alunoRepository.delete(aluno);
    }


    // ============================= MÉTODOS AUXILIARES =============================

    /**
     * Associa endereços ao aluno.
     */
    private void associateAddresses(AlunoResumidoDTO2 alunoResumidoDTO, Aluno aluno) {
        if (alunoResumidoDTO.getEnderecos() != null && !alunoResumidoDTO.getEnderecos().isEmpty()) {
            Set<Endereco> enderecos = alunoResumidoDTO.getEnderecos().stream()
                .map(enderecoDTO -> Endereco.builder()
                    .cep(enderecoDTO.getCep())
                    .rua(enderecoDTO.getRua())
                    .numero(enderecoDTO.getNumero())
                    .bairro(enderecoDTO.getBairro())
                    .cidade(enderecoDTO.getCidade())
                    .estado(enderecoDTO.getEstado())
                    .aluno(aluno)
                    .build())
                .collect(Collectors.toSet());
            aluno.setEnderecos(enderecos);
        } else {
            aluno.setEnderecos(null); // Não processa endereços se forem nulos ou vazios
        }
    }
    /**
     * Associa telefones ao aluno.
     */
    private void associatePhones(AlunoResumidoDTO2 alunoResumidoDTO, Aluno aluno) {
        if (alunoResumidoDTO.getTelefones() != null && !alunoResumidoDTO.getTelefones().isEmpty()) {
            Set<Telefone> telefones = alunoResumidoDTO.getTelefones().stream()
                .map(telefoneDTO -> Telefone.builder()
                    .ddd(telefoneDTO.getDdd())
                    .numero(telefoneDTO.getNumero())
                    .aluno(aluno)
                    .build())
                .collect(Collectors.toSet());
            aluno.setTelefones(telefones);
        } else {
            aluno.setTelefones(null); // Não processa telefones se forem nulos ou vazios
        }
    }
    /**
     * Associa turmas ao aluno com base nos IDs fornecidos.
     */
    private void associateTurmas(AlunoResumidoDTO2 alunoResumidoDTO, Aluno aluno) {
        if (alunoResumidoDTO.getTurmasIds() != null) {
            Set<Turma> turmas = alunoResumidoDTO.getTurmasIds().stream()
                .map(turmaId -> turmaRepository.findById(turmaId)
                    .orElseThrow(() -> new RuntimeException("Turma com ID " + turmaId + " não encontrada")))
                .collect(Collectors.toSet());

            aluno.setTurmas(turmas);

            // Adiciona o aluno à turma (mapeamento bidirecional)
            for (Turma turma : turmas) {
                turma.getAlunos().add(aluno);
            }
        }
    }

    /**
     * Associa responsáveis e telefones ao aluno.
     */
    private void associateResponsaveis(AlunoResumidoDTO2 alunoResumidoDTO, Aluno aluno) {
        if (alunoResumidoDTO.getResponsaveis() == null || alunoResumidoDTO.getResponsaveis().isEmpty()) {
            throw new RuntimeException("Pelo menos um responsável deve ser fornecido.");
        }

        Set<Responsavel> responsaveis = alunoResumidoDTO.getResponsaveis().stream()
            .map(responsavelDTO -> {
                Responsavel responsavel = convertToResponsavel(responsavelDTO);
                responsavel.setAluno(aluno); // Associa o aluno ao responsável

                // Associa telefones ao responsável
                if (responsavelDTO.getTelefones() != null) {
                    Set<Telefone> telefones = responsavelDTO.getTelefones().stream()
                        .map(telefoneDTO -> Telefone.builder()
                            .ddd(telefoneDTO.getDdd())
                            .numero(telefoneDTO.getNumero())
                            .responsavel(responsavel)
                            .build())
                        .collect(Collectors.toSet());
                    responsavel.setTelefones(telefones);
                }
                return responsavel;
            }).collect(Collectors.toSet());

        aluno.setResponsaveis(responsaveis);
    }

    // ============================= CONVERSORES =============================

    /**
     * Converte um ResponsavelDTO para Responsavel.
     */
    private Responsavel convertToResponsavel(ResponsavelDTO responsavelDTO) {
        if (responsavelDTO.getNome() == null || responsavelDTO.getUltimoNome() == null ||
            responsavelDTO.getCpfResponsavel() == null || responsavelDTO.getGrauParentesco() == null) {
            throw new RuntimeException("Dados do responsável incompletos. Nome, sobrenome, CPF e grau de parentesco são obrigatórios.");
        }

        Responsavel responsavel = new Responsavel();
        responsavel.setNome(responsavelDTO.getNome());
        responsavel.setUltimoNome(responsavelDTO.getUltimoNome());
        responsavel.setCpfResponsavel(responsavelDTO.getCpfResponsavel());
        responsavel.setGrauParentesco(responsavelDTO.getGrauParentesco());

        return responsavel;
    }

    /**
     * Converte um Aluno para AlunoDTO (completo para GET).
     */
    private AlunoDTO convertToDto(Aluno aluno) {
        // Mapeia os endereços do aluno
        Set<EnderecoDTO> enderecosDTO = aluno.getEnderecos() != null ? aluno.getEnderecos().stream()
            .map(endereco -> EnderecoDTO.builder()
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .build())
            .collect(Collectors.toSet()) : Collections.emptySet();

        // Mapeia os telefones do aluno
        Set<TelefoneDTO> telefonesDTO = aluno.getTelefones() != null ? aluno.getTelefones().stream()
            .map(telefone -> TelefoneDTO.builder()
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build())
            .collect(Collectors.toSet()) : Collections.emptySet();

        // Mapeia os responsáveis do aluno
        Set<ResponsavelDTO> responsaveisDTO = aluno.getResponsaveis() != null ? aluno.getResponsaveis().stream()
            .map(responsavel -> ResponsavelDTO.builder()
                .nome(responsavel.getNome())
                .ultimoNome(responsavel.getUltimoNome())
                .cpfResponsavel(responsavel.getCpfResponsavel())
                .grauParentesco(responsavel.getGrauParentesco())
                .telefones(responsavel.getTelefones() != null ? responsavel.getTelefones().stream()
                    .map(telefone -> TelefoneDTO.builder()
                        .ddd(telefone.getDdd())
                        .numero(telefone.getNumero())
                        .build())
                    .collect(Collectors.toSet()) : Collections.emptySet())
                .build())
            .collect(Collectors.toSet()) : Collections.emptySet();

        // Mapeia as turmas associadas ao aluno, incluindo coordenação e disciplinas
        Set<TurmaResumidaDTO> turmasDTO = aluno.getTurmas() != null ? aluno.getTurmas().stream()
            .map(turma -> {
                CoordenacaoResumidaDTO coordenacaoDTO = null;
                Coordenacao coordenacao = turma.getCoordenacao();

                // Verifica se a turma tem coordenação e coordenadores
                List<CoordenadorResumidoDTO> coordenadoresDTO = null;
                if (coordenacao != null && coordenacao.getCoordenadores() != null) {
                    coordenadoresDTO = coordenacao.getCoordenadores().stream()
                        .map(coordenador -> CoordenadorResumidoDTO.builder()
                            .nomeCoordenador(coordenador.getNome() + " " + coordenador.getUltimoNome())
                            .email(coordenador.getEmail())
                            .build())
                        .collect(Collectors.toList());

                    coordenacaoDTO = CoordenacaoResumidaDTO.builder()
                        .nome(coordenacao.getNome())
                        .coordenadores(!coordenadoresDTO.isEmpty() ? coordenadoresDTO : null)
                        .build();
                }

                // Mapeia as disciplinas e professores da turma
                Set<DisciplinaProfessorDTO> disciplinaProfessorDTO = turma.getTurmaDisciplinaProfessores() != null ?
                    turma.getTurmaDisciplinaProfessores().stream()
                        .map(turmaDisciplinaProfessor -> DisciplinaProfessorDTO.builder()
                            .professorId(turmaDisciplinaProfessor.getProfessor().getCpf())
                            .nomeProfessor(turmaDisciplinaProfessor.getProfessor().getNome() + " " +
                                           turmaDisciplinaProfessor.getProfessor().getUltimoNome())
                            .email(turmaDisciplinaProfessor.getProfessor().getEmail())
                            .nomesDisciplinas(Set.of(turmaDisciplinaProfessor.getDisciplina().getNome()))
                            .build())
                        .collect(Collectors.toSet()) : Collections.emptySet();

                return TurmaResumidaDTO.builder()
                    .nome(turma.getNome())
                    .anoLetivo(turma.getAnoLetivo())
                    .anoEscolar(turma.getAnoEscolar())
                    .turno(turma.getTurno())
                    .coordenacao(coordenacaoDTO)
                    .disciplinaProfessores(!disciplinaProfessorDTO.isEmpty() ? disciplinaProfessorDTO : null)
                    .build();
            })
            .collect(Collectors.toSet()) : Collections.emptySet();

        // Converte o Aluno para AlunoDTO com todos os campos
        return AlunoDTO.builder()
        	.id(aluno.getId())
            .nome(aluno.getNome())
            .ultimoNome(aluno.getUltimoNome())
            .genero(aluno.getGenero())
            .cpf(aluno.getCpf())
            .email(aluno.getEmail())
            .data_nascimento(aluno.getData_nascimento())
            .status(aluno.isStatus())
            .enderecos(!enderecosDTO.isEmpty() ? enderecosDTO : null)
            .telefones(!telefonesDTO.isEmpty() ? telefonesDTO : null)
            .responsaveis(!responsaveisDTO.isEmpty() ? responsaveisDTO : null)
            .turmas(!turmasDTO.isEmpty() ? turmasDTO : null)
            .build();
    }

}