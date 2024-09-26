package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ComunicadoSimplesDTO {
    private Long id;
    private String conteudo;
    private LocalDateTime dataEnvio;
}
