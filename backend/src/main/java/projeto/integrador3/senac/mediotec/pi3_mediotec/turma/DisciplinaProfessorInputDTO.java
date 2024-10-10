package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DisciplinaProfessorInputDTO {

    private String professorId;
    private Set<Long> disciplinasIds;
}