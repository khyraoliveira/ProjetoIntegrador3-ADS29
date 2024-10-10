package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import java.util.Set;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.DisciplinaResumida2DTO;

@Builder
@Data
public class TurmaResumidaDTO {
    private String nome;  // Nome da Turma
    private int anoLetivo;      // Ano da Turma
    private String anoEscolar;
    private String turno;
    private CoordenacaoResumidaDTO coordenacao;
    private Set<DisciplinaProfessorDTO> disciplinaProfessores;
}

