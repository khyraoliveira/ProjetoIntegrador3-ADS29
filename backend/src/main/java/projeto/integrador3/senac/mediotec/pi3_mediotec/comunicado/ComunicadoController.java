package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/comunicados")
@Tag(name = "Comunicados", description = "Operações relacionadas aos comunicados")
public class ComunicadoController {

    @Autowired
    private ComunicadoService comunicadoService;

    // ============================= POST ROUTES =============================

    // Criação de comunicado por coordenador
    @Operation(summary = "Criar comunicado por coordenador", description = "Cria um comunicado emitido por um coordenador específico para alunos e/ou turmas.")
    @PostMapping("/coordenacao/{coordenacaoId}/coordenador/{coordenadorId}")
    public ResponseEntity<ComunicadoSimplesDTO> criarComunicadoPorCoordenador(
            @PathVariable Long coordenacaoId, @PathVariable String coordenadorId, @RequestBody ComunicadoDTO comunicadoDTO) {
        try {
            ComunicadoSimplesDTO comunicado = comunicadoService.criarComunicadoPorCoordenador(coordenacaoId, coordenadorId, comunicadoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(comunicado);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar comunicado por coordenador: " + e.getMessage(), e);
        }
    }

    // Criação de comunicado para todos os alunos e turmas
    @Operation(summary = "Criar comunicado para todos os alunos e turmas", description = "Cria um comunicado enviado para todos os alunos e todas as turmas.")
    @PostMapping("/coordenacao/{coordenacaoId}/coordenador/{coordenadorId}/todos")
    public ResponseEntity<ComunicadoSimplesDTO> criarComunicadoParaTodos(
            @PathVariable Long coordenacaoId, @PathVariable String coordenadorId, @RequestBody ComunicadoDTO comunicadoDTO) {
        try {
            ComunicadoSimplesDTO comunicado = comunicadoService.criarComunicadoParaTodos(coordenacaoId, coordenadorId, comunicadoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(comunicado);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar comunicado para todos: " + e.getMessage(), e);
        }
    }

    // Criação de comunicado por professor
    @Operation(summary = "Criar comunicado por professor", description = "Cria um comunicado emitido por um professor.")
    @PostMapping("/professor/{professorId}")
    public ResponseEntity<ComunicadoSimplesDTO> criarComunicadoPorProfessor(
            @PathVariable String professorId, @RequestBody ComunicadoDTO comunicadoDTO) {
        try {
            ComunicadoSimplesDTO comunicado = comunicadoService.criarComunicadoPorProfessor(professorId, comunicadoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(comunicado);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar comunicado por professor: " + e.getMessage(), e);
        }
    }

    // ============================= PUT ROUTES =============================

    // Atualização de comunicado por coordenador
    @Operation(summary = "Atualizar comunicado por coordenador", description = "Atualiza um comunicado emitido por um coordenador específico.")
    @PutMapping("/coordenacao/{coordenacaoId}/coordenador/{coordenadorId}/{comunicadoId}")
    public ResponseEntity<ComunicadoSimplesDTO> atualizarComunicadoPorCoordenador(
            @PathVariable Long coordenacaoId, @PathVariable String coordenadorId, @PathVariable Long comunicadoId, @RequestBody ComunicadoDTO comunicadoDTO) {
        try {
            ComunicadoSimplesDTO comunicadoAtualizado = comunicadoService.atualizarComunicadoPorCoordenador(coordenacaoId, coordenadorId, comunicadoId, comunicadoDTO);
            return ResponseEntity.ok(comunicadoAtualizado);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao atualizar comunicado por coordenador: " + e.getMessage(), e);
        }
    }

    // Atualização de comunicado por professor
    @Operation(summary = "Atualizar comunicado por professor", description = "Atualiza um comunicado emitido por um professor.")
    @PutMapping("/professor/{professorId}/{comunicadoId}")
    public ResponseEntity<ComunicadoSimplesDTO> atualizarComunicadoPorProfessor(
            @PathVariable String professorId, @PathVariable Long comunicadoId, @RequestBody ComunicadoDTO comunicadoDTO) {
        try {
            ComunicadoSimplesDTO comunicadoAtualizado = comunicadoService.atualizarComunicadoPorProfessor(professorId, comunicadoId, comunicadoDTO);
            return ResponseEntity.ok(comunicadoAtualizado);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao atualizar comunicado por professor: " + e.getMessage(), e);
        }
    }

    // ============================= DELETE ROUTES =============================

    // Deleção de comunicado por coordenador
    @Operation(summary = "Deletar comunicado por coordenador", description = "Deleta um comunicado emitido por um coordenador.")
    @DeleteMapping("/coordenacao/{coordenacaoId}/coordenador/{coordenadorId}/{comunicadoId}")
    public ResponseEntity<Void> deletarComunicadoPorCoordenador(
            @PathVariable Long coordenacaoId, @PathVariable String coordenadorId, @PathVariable Long comunicadoId) {
        try {
            comunicadoService.deletarComunicadoPorCoordenador(coordenacaoId, coordenadorId, comunicadoId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao deletar comunicado por coordenador: " + e.getMessage(), e);
        }
    }

    // Deleção de comunicado por professor
    @Operation(summary = "Deletar comunicado por professor", description = "Deleta um comunicado emitido por um professor.")
    @DeleteMapping("/professor/{professorId}/{comunicadoId}")
    public ResponseEntity<Void> deletarComunicadoPorProfessor(
            @PathVariable String professorId, @PathVariable Long comunicadoId) {
        try {
            comunicadoService.deletarComunicadoPorProfessor(professorId, comunicadoId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao deletar comunicado por professor: " + e.getMessage(), e);
        }
    }

    // ============================= GET ROUTES =============================

    // Listar todos os comunicados
    @Operation(summary = "Listar todos os comunicados", description = "Lista todos os comunicados.")
    @GetMapping
    public ResponseEntity<List<ComunicadoDetalhadoDTO>> listarTodos() {
        try {
            List<ComunicadoDetalhadoDTO> comunicados = comunicadoService.listarTodos();
            return ResponseEntity.ok(comunicados);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao listar comunicados: " + e.getMessage(), e);
        }
    }

    // Listar comunicados enviados para alunos
    @Operation(summary = "Listar comunicados para alunos", description = "Lista todos os comunicados enviados para alunos.")
    @GetMapping("/alunos")
    public ResponseEntity<List<ComunicadoDetalhadoDTO>> listarComunicadosParaAlunos() {
        try {
            List<ComunicadoDetalhadoDTO> comunicados = comunicadoService.listarComunicadosParaAlunos();
            return ResponseEntity.ok(comunicados);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao listar comunicados para alunos: " + e.getMessage(), e);
        }
    }

    // Listar comunicados por aluno
    @Operation(summary = "Buscar comunicados por aluno", description = "Busca os comunicados enviados para um aluno específico.")
    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<ComunicadoDetalhadoDTO>> listarComunicadosPorAluno(@PathVariable Long alunoId) {
        try {
            List<ComunicadoDetalhadoDTO> comunicados = comunicadoService.listarComunicadosPorAluno(alunoId);
            return ResponseEntity.ok(comunicados);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao listar comunicados por aluno: " + e.getMessage(), e);
        }
    }

    // Listar comunicados enviados para turmas
    @Operation(summary = "Listar comunicados para turmas", description = "Lista todos os comunicados enviados para turmas.")
    @GetMapping("/turmas")
    public ResponseEntity<List<ComunicadoDetalhadoDTO>> listarComunicadosParaTurmas() {
        try {
            List<ComunicadoDetalhadoDTO> comunicados = comunicadoService.listarComunicadosParaTurmas();
            return ResponseEntity.ok(comunicados);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao listar comunicados para turmas: " + e.getMessage(), e);
        }
    }

    // Listar - Buscar comunicados por turma
    @Operation(summary = "Buscar comunicados por turma", description = "Busca os comunicados enviados para uma turma específica.")
    @GetMapping("/turma/{turmaId}")
    public ResponseEntity<List<ComunicadoDetalhadoDTO>> listarComunicadosPorTurma(@PathVariable Long turmaId) {
        try {
            List<ComunicadoDetalhadoDTO> comunicados = comunicadoService.listarComunicadosPorTurma(turmaId);
            return ResponseEntity.ok(comunicados);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao listar comunicados por turma: " + e.getMessage(), e);
        }
    }

    
    // Listar comunicados enviados por coordenador
    @Operation(summary = "Listar comunicados enviados por coordenador", description = "Lista os comunicados enviados por um coordenador.")
    @GetMapping("/coordenador/{coordenadorId}")
    public ResponseEntity<List<ComunicadoDetalhadoDTO>> listarComunicadosPorCoordenador(@PathVariable Long coordenadorId) {
        List<ComunicadoDetalhadoDTO> comunicados = comunicadoService.listarComunicadosPorCoordenador(coordenadorId);
        return ResponseEntity.ok(comunicados);
    }

    // Listar comunicados enviados por professor
    @Operation(summary = "Listar comunicados enviados por professor", description = "Lista os comunicados enviados por um professor.")
    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<ComunicadoDetalhadoDTO>> listarComunicadosPorProfessor(@PathVariable String professorId) {
        List<ComunicadoDetalhadoDTO> comunicados = comunicadoService.listarComunicadosPorProfessor(professorId);
        return ResponseEntity.ok(comunicados);
    }
}
