	package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;




import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class TurmaDTO {
	private Long id;
    private String nome;
    private int anoLetivo;      
    private String anoEscolar;
    private String turno;
    private boolean status;
    private CoordenacaoResumidaDTO coordenacao;
    private Set<DisciplinaResumida2DTO> disciplinas; 
    private Set<DisciplinaProfessorDTO> disciplinasProfessores;
    private Set<AlunoResumidoDTO> alunos;
}
