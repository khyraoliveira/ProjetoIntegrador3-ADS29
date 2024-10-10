package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumido3DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumida2DTO;



import java.util.Date;

@Builder
@Data
public class PresencaDTO {
    private Long id_presenca;
    private Date data;
    private Boolean presenca;
    
    // Objetos resumidos
    private AlunoResumidoDTO aluno;
    private TurmaResumida2DTO turma;
    private DisciplinaResumida2DTO disciplina;
    private ProfessorResumido3DTO professor;
}