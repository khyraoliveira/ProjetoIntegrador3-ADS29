package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador;

import java.util.Set;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;

@Builder
@Data
public class CoordenadorResumido2DTO {
    private String cpf;
    private String nomeCoordenador;
    private String email;
    private Set<Telefone> telefones;
}
