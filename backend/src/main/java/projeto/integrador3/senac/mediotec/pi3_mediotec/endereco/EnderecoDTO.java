package projeto.integrador3.senac.mediotec.pi3_mediotec.endereco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;


@Builder 
@Data
@JsonIgnoreProperties({"aluno", "professor", "coordenador", "coordenacao"})
public class EnderecoDTO {
    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
}

