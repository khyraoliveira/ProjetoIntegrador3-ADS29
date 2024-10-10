package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConceitoInputDTO {
    private Float notaUnidade1;
    private Float notaUnidade2;
    private Float notaUnidade3;
    private Float notaUnidade4;
    private Float noa1;   // NOA do 1º semestre
    private Float noa2;   // NOA do 2º semestre
    private Float noaFinal;  // Recuperação final
}
