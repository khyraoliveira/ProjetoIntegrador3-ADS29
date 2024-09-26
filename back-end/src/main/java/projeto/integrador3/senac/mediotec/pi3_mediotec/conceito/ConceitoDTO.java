package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;


import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorCompletoDTO;

@Builder
@Data
public class ConceitoDTO {
	
	private long id;
	
    // Informações sobre o aluno e a disciplina
    private AlunoResumidoDTO aluno;
    private TurmaDisciplinaProfessorCompletoDTO turmaDisciplinaProfessor;



    // Notas das unidades (visíveis apenas para o professor)
    private Float notaUnidade1;
    private Float notaUnidade2;
    private Float notaUnidade3;
    private Float notaUnidade4;

    // Notas de NOA (nova oportunidade de aprendizado) e recuperação final
    private Float noa1;
    private Float noa2;
    private Float noaFinal; // NOA Final (recuperação final)
    
    private Float mediaFinal;

    // Conceitos das notas (exibido tanto para o professor quanto para o aluno)
    private String conceitoNota1;
    private String conceitoNota2;
    private String conceitoNota3;
    private String conceitoNota4;
    private String conceitoNoa1;   // Conceito do NOA1 (primeiro semestre)
    private String conceitoNoa2;   // Conceito do NOA2 (segundo semestre)
    private String conceitoNoaFinal; // Conceito do NOA final (recuperação final)

    private String conceitoFinal;
    
    
    private Boolean aprovado;

;
}
