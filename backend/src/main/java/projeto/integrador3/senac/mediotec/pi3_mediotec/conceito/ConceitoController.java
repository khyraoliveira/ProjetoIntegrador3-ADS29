package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/conceitos")
@Tag(name = "Conceitos", description = "Operações relacionadas aos conceitos dos alunos")
public class ConceitoController {

    // ============================= INJEÇÕES DE DEPENDÊNCIA =============================
    private static final Logger logger = LoggerFactory.getLogger(ConceitoController.class);

    @Autowired
    private ConceitoService conceitoService;

    // ============================= GET METHODS =============================

    /**
     * Buscar conceito por ID (GET) - Retorna um conceito específico com base no ID.
     */
    @Operation(summary = "Buscar conceito por ID", description = "Retorna um conceito com base no seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<ConceitoDTO> buscarConceitoPorId(@PathVariable Long id) {
        logger.info("Buscando conceito com ID: {}", id);
        Optional<ConceitoDTO> conceito = conceitoService.buscarConceitoPorId(id);
        return conceito.map(ResponseEntity::ok)
                       .orElseGet(() -> {
                           logger.error("Conceito com ID {} não encontrado.", id);
                           return ResponseEntity.notFound().build();
                       });
    }


    // Rota para visualizar todos os conceitos de um aluno
    @Operation(summary = "Listar todos os conceitos de um aluno", description = "Retorna uma lista de conceitos associados a um aluno", tags = { "Conceitos" })
    @GetMapping("/{idAluno}/conceitos")
    public ResponseEntity<List<ConceitoDTO>> listarTodosConceitosPorAluno(@PathVariable Long idAluno) {
        logger.info("Listando todos os conceitos do aluno com ID: {}", idAluno);
        List<ConceitoDTO> conceitos = conceitoService.buscarConceitosPorAluno(idAluno);
        if (conceitos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum conceito encontrado para o aluno com ID " + idAluno);
        }
        return ResponseEntity.ok(conceitos);
    }

    // Rota para buscar conceito de um aluno em uma disciplina específica
    @Operation(summary = "Buscar conceito de um aluno em uma disciplina específica", description = "Retorna os conceitos de um aluno em uma disciplina", tags = { "Conceitos" })
    @GetMapping("/{idAluno}/conceitos/disciplina/{idDisciplina}")
    public ResponseEntity<List<ConceitoDTO>> listarConceitosPorAlunoEDisciplina(
            @PathVariable Long idAluno, @PathVariable Long idDisciplina) {
        logger.info("Buscando conceitos do aluno com ID: {} na disciplina com ID: {}", idAluno, idDisciplina);
        List<ConceitoDTO> conceitos = conceitoService.buscarConceitosPorAlunoEDisciplina(idAluno, idDisciplina);
        if (conceitos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum conceito encontrado para o aluno com ID " + idAluno + " na disciplina com ID " + idDisciplina);
        }
        return ResponseEntity.ok(conceitos);
    }
    
    
    
    /**
     * Visualizar notas de todos os alunos de uma turma específica em uma disciplina.
     */
    @Operation(summary = "Visualizar notas de todos os alunos de uma turma", 
               description = "Retorna as notas de todos os alunos de uma turma específica em uma disciplina.")
    @GetMapping("/{idProfessor}/disciplina/{idDisciplina}/turma/{idTurma}/conceitos")
    public ResponseEntity<List<ConceitoDTO>> getConceitosPorTurma(
            @PathVariable String idProfessor,
            @PathVariable Long idDisciplina,
            @PathVariable Long idTurma) {
        try {
            List<ConceitoDTO> conceitos = conceitoService.getConceitosPorTurma(idProfessor, idDisciplina, idTurma);
            if (conceitos.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum conceito encontrado para essa turma.");
            }
            return new ResponseEntity<>(conceitos, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar conceitos", e);
        }
    }

    /**
     * Visualizar nota de um aluno específico em uma disciplina e turma.
     */
    @Operation(summary = "Visualizar nota de um aluno", 
               description = "Retorna as notas de um aluno específico em uma disciplina e turma.")
    @GetMapping("/{idProfessor}/aluno/{idAluno}/disciplina/{idDisciplina}/turma/{idTurma}/conceitos")
    public ResponseEntity<ConceitoDTO> getConceitoPorAluno(
            @PathVariable String idProfessor,
            @PathVariable Long idAluno,
            @PathVariable Long idDisciplina,
            @PathVariable Long idTurma) {
        try {
            ConceitoDTO conceito = conceitoService.getConceitoPorAluno(idProfessor, idAluno, idDisciplina, idTurma);
            return ResponseEntity.ok(conceito);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao buscar conceito para o aluno: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar conceito", e);
        }
    }

    // ============================= POST METHODS =============================

    /**
     * Adicionar conceito a um aluno em uma disciplina e turma específicas.
     */
    @Operation(summary = "Adicionar conceito a um aluno em uma disciplina e turma específica", 
               description = "Adiciona um conceito para um aluno em uma disciplina e turma específicas.")
    @PostMapping("/{idProfessor}/aluno/{idAluno}/disciplina/{idDisciplina}/turma/{idTurma}/conceitos")
    public ResponseEntity<ConceitoDTO> adicionarConceitoParaAlunoComTurma(
            @PathVariable String idProfessor,
            @PathVariable Long idAluno,
            @PathVariable Long idDisciplina,
            @PathVariable Long idTurma,
            @RequestBody ConceitoInputDTO conceitoInputDTO) {
        try {
            ConceitoDTO novoConceito = conceitoService.salvarConceitoParaAlunoComTurma(idProfessor, idAluno, idDisciplina, idTurma, conceitoInputDTO);
            return new ResponseEntity<>(novoConceito, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao adicionar conceito: " + e.getMessage(), e);
        }
    }

    // ============================= PUT METHODS =============================

    /**
     * Atualizar conceito de um aluno em uma disciplina e turma específicas.
     */
    @Operation(summary = "Atualizar conceito de um aluno em uma disciplina e turma específica", 
               description = "Atualiza um conceito existente para um aluno em uma disciplina e turma específicas.")
    @PutMapping("/{idProfessor}/aluno/{idAluno}/disciplina/{idDisciplina}/turma/{idTurma}/conceitos/{idConceito}")
    public ResponseEntity<ConceitoDTO> atualizarConceitoParaAlunoComTurma(
            @PathVariable String idProfessor,
            @PathVariable Long idAluno,
            @PathVariable Long idDisciplina,
            @PathVariable Long idTurma,
            @PathVariable Long idConceito,
            @RequestBody ConceitoInputDTO conceitoInputDTO) {
        try {
            ConceitoDTO conceitoAtualizado = conceitoService.atualizarConceitoParaAlunoComTurma(idProfessor, idAluno, idDisciplina, idTurma, idConceito, conceitoInputDTO);
            return new ResponseEntity<>(conceitoAtualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao atualizar conceito: " + e.getMessage(), e);
        }
    }

    // ============================= DELETE METHODS =============================

    /**
     * Deletar conceito por ID.
     */
    @Operation(summary = "Deletar conceito", description = "Deleta um conceito com base no seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConceito(@PathVariable Long id) {
        try {
            logger.info("Deletando conceito com ID: {}", id);
            conceitoService.deletarConceito(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Erro ao deletar conceito com ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
