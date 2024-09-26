package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disciplinas")
@Tag(name = "Disciplina", description = "Operações relacionadas às disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    /**
     * Lista todas as disciplinas (GET)
     * @return Lista de DisciplinaGetDTO com os detalhes de todas as disciplinas
     */
    @Operation(summary = "Listar todas as disciplinas", description = "Retorna uma lista de todas as disciplinas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de disciplinas retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro ao buscar disciplinas")
    })
    @GetMapping
    public List<DisciplinaGetDTO> getAllDisciplinas() {
        try {
            return disciplinaService.getAllDisciplinas();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar disciplinas: " + e.getMessage(), e);
        }
    }

    /**
     * Busca disciplina pelo ID (GET)
     * @param id ID da disciplina
     * @return Detalhes da disciplina como ResponseEntity<DisciplinaGetDTO>
     */
    @Operation(summary = "Buscar disciplina por ID", description = "Retorna os detalhes de uma disciplina específica pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Disciplina retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Disciplina não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro ao buscar disciplina")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaGetDTO> getDisciplinaById(@PathVariable Long id) {
        try {
            Optional<DisciplinaGetDTO> disciplina = disciplinaService.getDisciplinaById(id);
            return disciplina.map(ResponseEntity::ok)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada com o ID: " + id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar disciplina com ID: " + id, e);
        }
    }

    /**
     * Cria uma nova disciplina (POST)
     * @param disciplinaDTO DTO com os dados da disciplina
     * @return Detalhes da disciplina criada como ResponseEntity<DisciplinaResumidaDTO>
     */
    @Operation(summary = "Criar nova disciplina", description = "Cria uma nova disciplina")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Disciplina criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro ao criar disciplina")
    })
    @PostMapping
    public ResponseEntity<DisciplinaResumidaDTO> createDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        try {
            DisciplinaResumidaDTO savedDisciplina = disciplinaService.saveDisciplina(disciplinaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDisciplina);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar disciplina: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza uma disciplina existente (PUT)
     * @param id ID da disciplina a ser atualizada
     * @param disciplinaDTO DTO com os dados atualizados da disciplina
     * @return Detalhes da disciplina atualizada como ResponseEntity<DisciplinaResumidaDTO>
     */
    @Operation(summary = "Atualizar disciplina", description = "Atualiza uma disciplina existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Disciplina atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Disciplina não encontrada"),
        @ApiResponse(responseCode = "400", description = "Erro ao atualizar disciplina")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaResumidaDTO> updateDisciplina(@PathVariable Long id, @RequestBody DisciplinaDTO disciplinaDTO) {
        try {
            DisciplinaResumidaDTO updatedDisciplina = disciplinaService.updateDisciplina(id, disciplinaDTO);
            return ResponseEntity.ok(updatedDisciplina);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada com o ID: " + id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar disciplina com o ID: " + id, e);
        }
    }

    /**
     * Deleta uma disciplina existente (DELETE)
     * @param id ID da disciplina a ser deletada
     * @return Resposta vazia com código HTTP 204 se for bem-sucedido
     */
    @Operation(summary = "Deletar disciplina", description = "Deleta uma disciplina existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Disciplina deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
        try {
            disciplinaService.deleteDisciplina(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada com o ID: " + id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar disciplina com o ID: " + id, e);
        }
    }
}
