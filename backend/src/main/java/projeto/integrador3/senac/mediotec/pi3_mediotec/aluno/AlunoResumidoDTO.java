package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor  // Gera o construtor necessário
public class AlunoResumidoDTO {
    private String nomeAluno;  // Nome do aluno
    private String email;
}
