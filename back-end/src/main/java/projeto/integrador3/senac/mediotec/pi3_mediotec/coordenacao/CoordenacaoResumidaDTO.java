package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;

@Builder
@Data
public class CoordenacaoResumidaDTO {
    private String nome;
    private List<CoordenadorResumidoDTO> coordenadores; 
}
