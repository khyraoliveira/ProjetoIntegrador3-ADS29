package projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel;


import java.util.Set;
import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;

@Data
@Builder
public class ResponsavelDTO {
    private String nome;
    private String ultimoNome;
    private String cpfResponsavel;
    private Set<TelefoneDTO> telefones;  
    private String grauParentesco;  
}

