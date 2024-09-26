package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumido3DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumida2DTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComunicadoService {

    // ============================= INJEÇÕES DE DEPENDÊNCIA =============================

    @Autowired
    private ComunicadoRepository comunicadoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    // ============================= CRIAÇÃO DE COMUNICADO =============================

    // Cria um comunicado enviado por um coordenador
    public ComunicadoSimplesDTO criarComunicadoPorCoordenador(Long coordenacaoId, String coordenadorId, ComunicadoDTO comunicadoDTO) {
        validarCoordenador(coordenacaoId, coordenadorId);
        Comunicado comunicado = Comunicado.builder()
                .conteudo(comunicadoDTO.getConteudo())
                .dataEnvio(comunicadoDTO.getDataEnvio())
                .remetenteCoordenacao(coordenacaoRepository.findById(coordenacaoId)
                        .orElseThrow(() -> new RuntimeException("Coordenação não encontrada")))
                .receptorAlunos(comunicadoDTO.getAlunoIds())
                .receptorTurmas(comunicadoDTO.getTurmaIds())
                .build();
        return convertToSimplesDTO(comunicadoRepository.save(comunicado));
    }

    // Cria um comunicado enviado por um professor
    public ComunicadoSimplesDTO criarComunicadoPorProfessor(String professorId, ComunicadoDTO comunicadoDTO) {
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        Comunicado comunicado = Comunicado.builder()
                .conteudo(comunicadoDTO.getConteudo())
                .dataEnvio(comunicadoDTO.getDataEnvio())
                .remetenteProfessor(professor)
                .receptorAlunos(comunicadoDTO.getAlunoIds())
                .receptorTurmas(comunicadoDTO.getTurmaIds())
                .build();
        return convertToSimplesDTO(comunicadoRepository.save(comunicado));
    }

    // Cria um comunicado enviado para todos os alunos e turmas
    public ComunicadoSimplesDTO criarComunicadoParaTodos(Long coordenacaoId, String coordenadorId, ComunicadoDTO comunicadoDTO) {
        validarCoordenador(coordenacaoId, coordenadorId);
        List<Long> todosAlunos = alunoRepository.findAllIds();
        List<Long> todasTurmas = turmaRepository.findAllIds();
        comunicadoDTO.setAlunoIds(todosAlunos);
        comunicadoDTO.setTurmaIds(todasTurmas);
        return criarComunicadoPorCoordenador(coordenacaoId, coordenadorId, comunicadoDTO);
    }

    // ============================= ATUALIZAÇÃO DE COMUNICADO =============================

    // Atualiza um comunicado enviado por um coordenador
    public ComunicadoSimplesDTO atualizarComunicadoPorCoordenador(Long coordenacaoId, String coordenadorId, Long comunicadoId, ComunicadoDTO comunicadoDTO) {
        validarCoordenador(coordenacaoId, coordenadorId);
        Comunicado comunicado = buscarComunicadoPorId(comunicadoId);
        atualizarDadosComunicado(comunicado, comunicadoDTO);
        return convertToSimplesDTO(comunicadoRepository.save(comunicado));
    }

    // Atualiza um comunicado enviado por um professor
    public ComunicadoSimplesDTO atualizarComunicadoPorProfessor(String professorId, Long comunicadoId, ComunicadoDTO comunicadoDTO) {
        Comunicado comunicado = buscarComunicadoPorId(comunicadoId);
        atualizarDadosComunicado(comunicado, comunicadoDTO);
        return convertToSimplesDTO(comunicadoRepository.save(comunicado));
    }

    // ============================= DELEÇÃO DE COMUNICADO =============================

    // Deleta um comunicado enviado por um coordenador
    public void deletarComunicadoPorCoordenador(Long coordenacaoId, String coordenadorId, Long comunicadoId) {
        validarCoordenador(coordenacaoId, coordenadorId);
        comunicadoRepository.deleteById(comunicadoId);
    }

    // Deleta um comunicado enviado por um professor
    public void deletarComunicadoPorProfessor(String professorId, Long comunicadoId) {
        comunicadoRepository.deleteById(comunicadoId);
    }

    // ============================= LISTAGEM DE COMUNICADO =============================

    // Lista comunicados enviados por um coordenador
    public List<ComunicadoDetalhadoDTO> listarComunicadosPorCoordenador(Long coordenadorId) {
        return comunicadoRepository.findByRemetenteCoordenacao_Id(coordenadorId)
                .stream()
                .map(this::convertToDetalhadoDTO)
                .collect(Collectors.toList());
    }

    // Lista comunicados enviados por um professor
    public List<ComunicadoDetalhadoDTO> listarComunicadosPorProfessor(String professorId) {
        return comunicadoRepository.findByRemetenteProfessor_Cpf(professorId)
                .stream()
                .map(this::convertToDetalhadoDTO)
                .collect(Collectors.toList());
    }

    // Lista todos os comunicados
    public List<ComunicadoDetalhadoDTO> listarTodos() {
        return comunicadoRepository.findAll()
                .stream()
                .map(this::convertToDetalhadoDTO)
                .collect(Collectors.toList());
    }

    // Lista comunicados específicos para um aluno
    public List<ComunicadoDetalhadoDTO> listarComunicadosPorAluno(Long alunoId) {
        return comunicadoRepository.findByReceptorAlunosContaining(alunoId)
                .stream()
                .map(this::convertToDetalhadoDTO)
                .collect(Collectors.toList());
    }

    // Lista comunicados enviados para alunos
    public List<ComunicadoDetalhadoDTO> listarComunicadosParaAlunos() {
        return comunicadoRepository.findAll().stream()
                .filter(comunicado -> !comunicado.getReceptorAlunos().isEmpty())
                .map(this::convertToDetalhadoDTO)
                .collect(Collectors.toList());
    }

    // Lista comunicados específicos para uma turma
    public List<ComunicadoDetalhadoDTO> listarComunicadosPorTurma(Long turmaId) {
        return comunicadoRepository.findByReceptorTurmasContaining(turmaId)
                .stream()
                .map(this::convertToDetalhadoDTO)
                .collect(Collectors.toList());
    }

    // Lista comunicados enviados para turmas
    public List<ComunicadoDetalhadoDTO> listarComunicadosParaTurmas() {
        return comunicadoRepository.findAll().stream()
                .filter(comunicado -> !comunicado.getReceptorTurmas().isEmpty())
                .map(this::convertToDetalhadoDTO)
                .collect(Collectors.toList());
    }

    // ============================= MÉTODOS AUXILIARES =============================

    // Valida se o coordenador está vinculado à coordenação especificada
    private void validarCoordenador(Long coordenacaoId, String coordenadorId) {
        if (!coordenacaoRepository.existsByIdAndCoordenadores_Cpf(coordenacaoId, coordenadorId)) {
            throw new RuntimeException("Coordenador não vinculado à coordenação");
        }
    }

    // Busca um comunicado por ID
    private Comunicado buscarComunicadoPorId(Long comunicadoId) {
        return comunicadoRepository.findById(comunicadoId)
            .orElseThrow(() -> new RuntimeException("Comunicado não encontrado com ID: " + comunicadoId));
    }

    // Atualiza os dados de um comunicado
    private void atualizarDadosComunicado(Comunicado comunicado, ComunicadoDTO comunicadoDTO) {
        comunicado.setConteudo(comunicadoDTO.getConteudo());
        comunicado.setDataEnvio(comunicadoDTO.getDataEnvio());
        comunicado.setReceptorAlunos(comunicadoDTO.getAlunoIds());
        comunicado.setReceptorTurmas(comunicadoDTO.getTurmaIds());
    }

    // Converte para DTO simples
    private ComunicadoSimplesDTO convertToSimplesDTO(Comunicado comunicado) {
        return ComunicadoSimplesDTO.builder()
                .id(comunicado.getId())
                .conteudo(comunicado.getConteudo())
                .dataEnvio(comunicado.getDataEnvio())
                .build();
    }

    // Converte para DTO detalhado
    private ComunicadoDetalhadoDTO convertToDetalhadoDTO(Comunicado comunicado) {
        // Processa a lista de alunos receptores
        List<AlunoResumidoDTO> alunos = comunicado.getReceptorAlunos().stream()
                .map(alunoId -> alunoRepository.findById(alunoId)
                        .map(aluno -> new AlunoResumidoDTO(aluno.getNome(), aluno.getEmail()))
                        .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + alunoId)))
                .collect(Collectors.toList());

        // Processa a lista de turmas receptoras
        List<TurmaResumida2DTO> turmas = comunicado.getReceptorTurmas().stream()
                .map(turmaId -> turmaRepository.findById(turmaId)
                        .map(turma -> new TurmaResumida2DTO(turma.getNome(), turma.getAnoLetivo(), turma.getAnoEscolar(), turma.getTurno()))
                        .orElseThrow(() -> new RuntimeException("Turma não encontrada com ID: " + turmaId)))
                .collect(Collectors.toList());

        // Obtem o remetente do comunicado (professor ou coordenação)
        RemetenteResumidoDTO remetente = obterRemetenteResumido(comunicado);

        // Retorna o DTO detalhado
        return ComunicadoDetalhadoDTO.builder()
                .id(comunicado.getId())
                .conteudo(comunicado.getConteudo())
                .dataEnvio(comunicado.getDataEnvio())
                .remetenteProfessor(remetente.getProfessor())  // Se remetente for professor
                .remetenteCoordenacao(remetente.getCoordenacao())  // Se remetente for coordenação
                .alunos(alunos)
                .turmas(turmas)
                .build();
    }

    // Obtém o remetente do comunicado (pode ser professor ou coordenação)
    private RemetenteResumidoDTO obterRemetenteResumido(Comunicado comunicado) {
        if (comunicado.getRemetenteProfessor() != null) {
            return RemetenteResumidoDTO.builder()
                    .professor(ProfessorResumido3DTO.builder()
                            .nomeProfessor(comunicado.getRemetenteProfessor().getNome())
                            .email(comunicado.getRemetenteProfessor().getEmail())
                            .build())
                    .build();
        } else if (comunicado.getRemetenteCoordenacao() != null) {
            return RemetenteResumidoDTO.builder()
                    .coordenacao(CoordenacaoResumidaDTO.builder()
                            .nome(comunicado.getRemetenteCoordenacao().getNome())
                            .coordenadores(comunicado.getRemetenteCoordenacao().getCoordenadores().stream()
                                    .map(coordenador -> CoordenadorResumidoDTO.builder()
                                            .nomeCoordenador(coordenador.getNome())
                                            .email(coordenador.getEmail())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build())
                    .build();
        }
        throw new RuntimeException("Remetente não encontrado.");
    }
}
