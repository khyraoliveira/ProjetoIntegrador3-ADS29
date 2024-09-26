package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;



@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder 
@Data
public class DisciplinaDTO {
    private String nome;
    private int cargaHoraria;
    private Long turmaId;
    private String professorId;
}
