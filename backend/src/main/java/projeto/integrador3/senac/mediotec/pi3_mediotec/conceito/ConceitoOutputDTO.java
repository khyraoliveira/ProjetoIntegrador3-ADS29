package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConceitoOutputDTO {
	
    // Informações adicionais para o aluno
    private String nomeDisciplina; // Nome da disciplina
    private String nomeProfessor;  // Nome do professor

    // Conceitos das notas (exibido para o aluno)
    private String conceitoNota1;
    private String conceitoNota2;
    private String conceitoNota3;
    private String conceitoNota4;
    private String conceitoNoa1;    // Conceito do NOA1 (primeiro semestre)
    private String conceitoNoa2;    // Conceito do NOA2 (segundo semestre)
    private String conceitoNoaFinal; // Conceito da recuperação final (NOA Final)

    // Média e status
    private String conceitoFinal;  // Média final calculada
    private Boolean aprovado;  // Status de aprovação (Aprovado/Reprovado)

}
