package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumido3DTO;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class RemetenteResumidoDTO {
    private ProfessorResumido3DTO professor;        // Objeto de professor resumido
    private CoordenacaoResumidaDTO coordenacao;     // Objeto de coordenacao resumido
}
