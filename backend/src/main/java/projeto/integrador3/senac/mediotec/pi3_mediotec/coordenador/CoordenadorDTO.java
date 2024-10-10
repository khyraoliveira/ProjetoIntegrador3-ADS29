package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder 
@Data
public class CoordenadorDTO {
    private String cpf;
    private String nome;
    private String ultimoNome;
    private String genero;
    private Date data_nascimento;
    private String email;
    private boolean status;
    private Set<EnderecoDTO> enderecos;
    private Set<TelefoneDTO> telefones;
    private Long idCoordenacao;
}
