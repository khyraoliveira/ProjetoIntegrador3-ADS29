package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/presencas")
@Tag(name = "Presenças", description = "Operações relacionadas às presenças dos alunos")
public class PresencaController {

    // ============================= INJEÇÃO DE DEPENDÊNCIA =============================
    
    @Autowired
    private PresencaService presencaService;

    // ============================= MÉTODOS DE CRUD =============================
    
    /**
     * Criar uma nova presença.
     * 
     * @param idAluno ID do aluno
     * @param idTurma ID da turma
     * @param idDisciplina ID da disciplina
     * @param idProfessor ID do professor
     * @param presencaInputDTO Dados da presença
     * @return PresencaDTO com os dados da presença criada
     */
    @Operation(summary = "Criar uma nova presença", description = "Cria uma nova presença para um aluno em uma disciplina e turma específicas")
    @PostMapping("/aluno/{idAluno}/turma/{idTurma}/disciplina/{idDisciplina}/professor/{idProfessor}")
    public ResponseEntity<PresencaDTO> criarPresenca(
            @PathVariable Long idAluno, 
            @PathVariable Long idTurma, 
            @PathVariable Long idDisciplina, 
            @PathVariable String idProfessor, 
            @RequestBody PresencaInputDTO presencaInputDTO) {
        
        PresencaDTO novaPresenca = presencaService.salvarPresenca(idAluno, idTurma, idDisciplina, idProfessor, presencaInputDTO);
        return ResponseEntity.ok(novaPresenca);
    }

    /**
     * Atualizar uma presença existente.
     * 
     * @param id ID da presença
     * @param idAluno ID do aluno
     * @param idTurma ID da turma
     * @param idDisciplina ID da disciplina
     * @param idProfessor ID do professor
     * @param presencaInputDTO Dados da presença a ser atualizada
     * @return PresencaDTO atualizado
     */
    @Operation(summary = "Atualizar uma presença existente", description = "Atualiza uma presença existente de um aluno")
    @PutMapping("/{id}/aluno/{idAluno}/turma/{idTurma}/disciplina/{idDisciplina}/professor/{idProfessor}")
    public ResponseEntity<PresencaDTO> atualizarPresenca(
            @PathVariable Long id, 
            @PathVariable Long idAluno, 
            @PathVariable Long idTurma, 
            @PathVariable Long idDisciplina, 
            @PathVariable String idProfessor, 
            @RequestBody PresencaInputDTO presencaInputDTO) {
        
        PresencaDTO presencaAtualizada = presencaService.salvarPresenca(idAluno, idTurma, idDisciplina, idProfessor, presencaInputDTO);
        return ResponseEntity.ok(presencaAtualizada);
    }

    /**
     * Buscar presença por ID.
     * 
     * @param id ID da presença
     * @param idAluno ID do aluno
     * @return PresencaDTO da presença encontrada
     */
    @Operation(summary = "Buscar presença por ID", description = "Busca uma presença específica por ID")
    @GetMapping("/{id}/aluno/{idAluno}")
    public ResponseEntity<PresencaDTO> buscarPresencaPorId(
            @PathVariable Long id, 
            @PathVariable Long idAluno) {
        
        Optional<PresencaDTO> presencaDTO = presencaService.buscarPresencaPorId(idAluno, id);
        return presencaDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Listar todas as presenças de um aluno.
     * 
     * @param idAluno ID do aluno
     * @return Lista de presenças do aluno
     */
    @Operation(summary = "Listar todas as presenças de um aluno", description = "Retorna todas as presenças de um aluno")
    @GetMapping("/aluno/{idAluno}")
    public ResponseEntity<List<PresencaDTO>> listarPresencasPorAluno(@PathVariable Long idAluno) {
        List<PresencaDTO> presencas = presencaService.listarPresencasPorAluno(idAluno);
        return ResponseEntity.ok(presencas);
    }

    /**
     * Listar presenças de um aluno em uma disciplina específica.
     * 
     * @param idAluno ID do aluno
     * @param idDisciplina ID da disciplina
     * @return Lista de presenças do aluno na disciplina
     */
    @Operation(summary = "Listar presenças de um aluno em uma disciplina", description = "Retorna todas as presenças de um aluno em uma disciplina específica")
    @GetMapping("/aluno/{idAluno}/disciplina/{idDisciplina}")
    public ResponseEntity<List<PresencaDTO>> listarPresencasPorAlunoEDisciplina(
            @PathVariable Long idAluno, 
            @PathVariable Long idDisciplina) {
        
        List<PresencaDTO> presencas = presencaService.listarPresencasPorAlunoEDisciplina(idAluno, idDisciplina);
        return ResponseEntity.ok(presencas);
    }

    /**
     * Listar todas as presenças emitidas por um professor.
     * 
     * @param idProfessor ID do professor
     * @return Lista de presenças emitidas pelo professor
     */
    @Operation(summary = "Listar presenças emitidas por um professor", description = "Retorna todas as presenças emitidas por um professor específico")
    @GetMapping("/professor/{idProfessor}")
    public ResponseEntity<List<PresencaDTO>> listarPresencasEmitidasPorProfessor(@PathVariable String idProfessor) {
        List<PresencaDTO> presencas = presencaService.listarPresencasEmitidasPorProfessor(idProfessor);
        return ResponseEntity.ok(presencas);
    }

    /**
     * Deletar uma presença por ID.
     * 
     * @param id ID da presença
     * @param idAluno ID do aluno
     */
    @Operation(summary = "Deletar uma presença por ID", description = "Deleta uma presença específica")
    @DeleteMapping("/{id}/aluno/{idAluno}")
    public ResponseEntity<Void> deletarPresenca(
            @PathVariable Long id, 
            @PathVariable Long idAluno) {
        
        presencaService.deletarPresenca(idAluno, id);
        return ResponseEntity.noContent().build();
    }
}
