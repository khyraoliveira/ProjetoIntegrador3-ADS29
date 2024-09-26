package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ComunicadoDTO {
    private String conteudo;
    private LocalDateTime dataEnvio;
    private List<Long> alunoIds;
    private List<Long> turmaIds;
}
