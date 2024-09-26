package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projeto.integrador3.senac.mediotec.pi3_mediotec.conceito.ConceitoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.conceito.ConceitoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private static final Logger logger = LoggerFactory.getLogger(AlunoController.class);

    @Autowired
    private AlunoService alunoService;


    // ============================= GET METHODS =============================

    // Lista todos os alunos
    @Operation(summary = "Listar todos os alunos", description = "Retorna uma lista de todos os alunos cadastrados", tags = { "Aluno" })
    @GetMapping
    public ResponseEntity<List<AlunoDTO>> getAllAlunos() {
        logger.info("Listando todos os alunos.");
        List<AlunoDTO> alunos = alunoService.getAllAlunos();
        if (alunos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum aluno encontrado.");
        }
        return new ResponseEntity<>(alunos, HttpStatus.OK);
    }

    // Busca aluno pelo ID
    @Operation(summary = "Buscar aluno por ID", description = "Retorna um aluno com base no seu ID", tags = { "Aluno" })
    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> getAlunoById(@PathVariable Long id) {
        logger.info("Buscando aluno com ID: {}", id);
        Optional<AlunoDTO> aluno = alunoService.getAlunoById(id);
        return aluno.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseThrow(() -> {
                    logger.error("Aluno com ID {} não encontrado.", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno com ID " + id + " não encontrado.");
                });
    }

    // ============================= POST METHODS =============================

    // Cria um novo aluno
    @Operation(summary = "Criar um novo aluno", description = "Cria um novo aluno com base nos dados fornecidos", tags = { "Aluno" })
    @PostMapping
    public ResponseEntity<AlunoDTO> createAluno(@RequestBody AlunoResumidoDTO2 alunoResumidoDTO) {
        try {
            logger.info("Criando um novo aluno.");
            AlunoDTO savedAluno = alunoService.saveAluno(alunoResumidoDTO);
            return new ResponseEntity<>(savedAluno, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            logger.error("Erro ao criar aluno: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar aluno: " + e.getMessage());
        }
    }

    // ============================= PUT METHODS =============================

    // Atualiza um aluno existente
    @Operation(summary = "Atualizar aluno", description = "Atualiza um aluno existente com base no seu ID", tags = { "Aluno" })
    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> updateAluno(@PathVariable Long id, @RequestBody AlunoResumidoDTO2 alunoResumido2DTO) {
        try {
            logger.info("Atualizando aluno com ID: {}", id);
            AlunoDTO updatedAluno = alunoService.updateAluno(id, alunoResumido2DTO);
            return new ResponseEntity<>(updatedAluno, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Erro ao atualizar aluno com ID {}: {}", id, e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao atualizar aluno com ID " + id + ": " + e.getMessage());
        }
    }

    // ============================= DELETE METHODS =============================

    // Deleta um aluno
    @Operation(summary = "Deletar aluno", description = "Deleta um aluno com base no seu ID", tags = { "Aluno" })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        try {
            logger.info("Deletando aluno com ID: {}", id);
            alunoService.deleteAluno(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.error("Erro ao deletar aluno com ID {}: {}", id, e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao deletar aluno com ID " + id + ": " + e.getMessage());
        }
    }

    
    /*
    // ============================= GET METHODS FOR CONCEITOS =============================

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
*/
    
}
