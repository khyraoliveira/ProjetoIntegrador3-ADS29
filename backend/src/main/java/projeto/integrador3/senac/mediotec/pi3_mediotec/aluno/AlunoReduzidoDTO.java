package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AlunoReduzidoDTO {
    private Long id;  // ID do aluno
    private String nomeAluno;  // Nome do aluno
}
