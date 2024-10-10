package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConceitoResumidoDTO {
	
    // IDs para referenciar entidades relacionadas
    private Long alunoId;
    private Long turmaId;
    private Long disciplinaId;
    private String professorId;
    
    // Notas das quatro unidades
    private Float notaUnidade1;
    private Float notaUnidade2;
    private Float notaUnidade3;
    private Float notaUnidade4;
    
    // Nova Oportunidade de Aprendizado
    private Float noa1;  // NOA para o 1º semestre
    private Float noa2;  // NOA para o 2º semestre
    private Float noaFinal;  // NOA Final (Recuperação Final)
}
