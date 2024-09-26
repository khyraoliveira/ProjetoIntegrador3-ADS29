package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;

import lombok.Builder;
import lombok.Data;

@Builder 
@Data
public class TurmaDisciplinaProfessorIdDTO {
    private Long turmaId;
    private Long disciplinaId;
    private String professorId;
}
