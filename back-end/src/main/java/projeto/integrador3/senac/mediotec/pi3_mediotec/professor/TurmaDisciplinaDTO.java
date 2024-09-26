package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TurmaDisciplinaDTO {
    private Long turmaId;       // ID da turma preexistente
    private Long disciplinaId;  // ID da disciplina preexistente
}
