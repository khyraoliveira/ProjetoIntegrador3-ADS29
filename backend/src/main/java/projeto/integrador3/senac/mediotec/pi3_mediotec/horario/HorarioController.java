package projeto.integrador3.senac.mediotec.pi3_mediotec.horario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/*
@RestController
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    // Rota para criar um novo horário
    @PostMapping
    public ResponseEntity<HorarioDTO> criarHorario(@RequestBody HorarioDTO horarioDTO) {
        HorarioDTO novoHorario = horarioService.salvarHorarioDTO(horarioDTO);
        return ResponseEntity.ok(novoHorario);
    }

    // Rota para atualizar um horário existente
    @PutMapping("/{id}")
    public ResponseEntity<HorarioDTO> atualizarHorario(@PathVariable Long id, @RequestBody HorarioDTO horarioDTO) {
        HorarioDTO horarioAtualizado = horarioService.atualizarHorario(id, horarioDTO);
        return ResponseEntity.ok(horarioAtualizado);
    }

    // Rota para listar todos os horários de uma turma específica
    @GetMapping("/turma/{turmaId}")
    public ResponseEntity<List<HorarioDTO>> listarHorariosPorTurma(@PathVariable Long turmaId) {
        List<HorarioDTO> horarios = horarioService.listarHorariosPorTurma(turmaId);
        return ResponseEntity.ok(horarios);
    }

    // Rota para buscar um horário específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<HorarioDTO> buscarHorarioPorId(@PathVariable Long id) {
        Optional<HorarioDTO> horarioDTO = horarioService.buscarHorarioPorId(id);
        return horarioDTO.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Rota para deletar um horário pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarHorario(@PathVariable Long id) {
        horarioService.deletarHorario(id);
        return ResponseEntity.noContent().build();
    }
}
*/
