package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
@RestController
@RequestMapping("/turmaDisciplinasProfessores")
public class TurmaDisciplinaProfessorController {

    @Autowired
    private TurmaDisciplinaProfessorService turmaDisciplinaProfessorService;

    // Listar todas as associações de Turma, Disciplina e Professor
    @GetMapping
    public ResponseEntity<List<TurmaDisciplinaProfessorDTO>> getAllTurmaDisciplinaProfessores() {
        List<TurmaDisciplinaProfessorDTO> turmaDisciplinaProfessores = turmaDisciplinaProfessorService.getAllTurmaDisciplinaProfessores();
        return ResponseEntity.ok(turmaDisciplinaProfessores); // Retorna 200 OK com a lista
    }

    // Obter uma associação específica de Turma, Disciplina e Professor
    @GetMapping("/{turmaId}/{disciplinaId}/{professorId}")
    public ResponseEntity<TurmaDisciplinaProfessorDTO> getTurmaDisciplinaProfessorById(
            @PathVariable Long turmaId, 
            @PathVariable Long disciplinaId,
            @PathVariable String professorId) {

        return turmaDisciplinaProfessorService.getTurmaDisciplinaProfessorById(turmaId, disciplinaId, professorId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // Verifica se a entidade foi encontrada
    }

    // Criar uma nova associação entre Turma, Disciplina e Professor
    @PostMapping
    public ResponseEntity<TurmaDisciplinaProfessorDTO> createTurmaDisciplinaProfessor(
            @RequestParam Long turmaId, 
            @RequestParam Long disciplinaId,
            @RequestParam String professorId) {
        
        TurmaDisciplinaProfessorDTO createdTurmaDisciplinaProfessor = turmaDisciplinaProfessorService.saveTurmaDisciplinaProfessor(turmaId, disciplinaId, professorId);
        
        // Retorna 201 Created e a entidade criada
        return ResponseEntity.status(201).body(createdTurmaDisciplinaProfessor);
    }

    // Atualizar uma associação existente entre Turma, Disciplina e Professor
    @PutMapping("/{turmaId}/{disciplinaId}/{professorId}")
    public ResponseEntity<TurmaDisciplinaProfessorDTO> updateTurmaDisciplinaProfessor(
            @PathVariable Long turmaId, 
            @PathVariable Long disciplinaId,
            @PathVariable String professorId,
            @RequestBody TurmaDisciplinaProfessorDTO turmaDisciplinaProfessorDetails) {

        TurmaDisciplinaProfessorDTO updatedTurmaDisciplinaProfessor = turmaDisciplinaProfessorService.updateTurmaDisciplinaProfessor(turmaId, disciplinaId, professorId, turmaDisciplinaProfessorDetails);

        // Retorna 200 OK e a entidade atualizada
        return ResponseEntity.ok(updatedTurmaDisciplinaProfessor);
    }

    // Deletar uma associação específica entre Turma, Disciplina e Professor
    @DeleteMapping("/{turmaId}/{disciplinaId}/{professorId}")
    public ResponseEntity<Void> deleteTurmaDisciplinaProfessor(
            @PathVariable Long turmaId, 
            @PathVariable Long disciplinaId,
            @PathVariable String professorId) {

        turmaDisciplinaProfessorService.deleteTurmaDisciplinaProfessor(turmaId, disciplinaId, professorId);

        // Retorna 204 No Content se a exclusão for bem-sucedida
        return ResponseEntity.noContent().build();
    }
}

*/