package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumido3DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumida2DTO;

@Data
@Builder
public class ComunicadoDetalhadoDTO {
    private Long id;
    private String conteudo;
    private LocalDateTime dataEnvio;
    private ProfessorResumido3DTO remetenteProfessor;  // Dados do professor que enviou o comunicado
    private CoordenacaoResumidaDTO remetenteCoordenacao;  // Dados da coordenação que enviou o comunicado
    private List<AlunoResumidoDTO> alunos;   // Alunos que receberam o comunicado
    private List<TurmaResumida2DTO> turmas;   // Turmas que receberam o comunicado
}

