package projeto.integrador3.senac.mediotec.pi3_mediotec.horario;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;

import java.time.LocalTime;

@Data
@Builder
public class HorarioDTO {
    private Long idHorario;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private TurmaDisciplinaProfessorId turmaDisciplinaProfessorId;
}
