package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TurmaInputDTO {
    private int anoLetivo;      
    private String anoEscolar;
    private String turno;
    private boolean status;
    private Long coordenacaoId;
    private Set<Long> alunosIds;
    private Set<DisciplinaProfessorInputDTO> disciplinasProfessores;
}

