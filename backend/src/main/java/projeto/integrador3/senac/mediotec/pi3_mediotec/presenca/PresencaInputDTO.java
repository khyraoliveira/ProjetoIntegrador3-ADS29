package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class PresencaInputDTO {
    private Date data;
    private Boolean presenca;
}
