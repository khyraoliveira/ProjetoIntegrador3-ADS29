package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaDTO;

@Builder
@Data
public class TurmaDisciplinaProfessorDTO {
    private Long turmaId;
    private Long disciplinaId;
    private String professorId;
}

