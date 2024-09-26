package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;

@Builder
@Data
public class DisciplinaResumidaDTO {
    private String nome;         // Nome da Disciplina
    private int cargaHoraria;    // Carga Horária

    private TurmaResumidaDTO turma;  // Detalhes simples da turma (ID, nome, ano)
    private ProfessorResumidoDTO professor;  // Detalhes simples do professor (ID, nome, sobrenome)

}

