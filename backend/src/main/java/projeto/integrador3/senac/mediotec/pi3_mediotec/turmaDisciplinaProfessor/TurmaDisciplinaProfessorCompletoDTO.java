package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TurmaDisciplinaProfessorCompletoDTO {
    private String nomeTurma;
    private String nomeDisciplina;
    private String nomeProfessor;
}
