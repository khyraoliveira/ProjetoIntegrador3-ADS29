package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TurmaDisciplinaDTO {
    private Long turmaId;       // ID da turma preexistente
    private Set<Long>  disciplinasIds;  // ID da disciplina preexistente
    
    
}
