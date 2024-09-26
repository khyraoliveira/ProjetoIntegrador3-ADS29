package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel.Responsavel;
import projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel.ResponsavelDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class AlunoResumidoDTO2 {
    private String nome;
    private String ultimoNome;
    private String genero;
    private Date data_nascimento;
    private String cpf;
    private String email;
    private Long coordenacaoId; 
    private Set<Long> turmasIds; // Apenas IDs das turmas
    private Set<EnderecoDTO> enderecos;
    private Set<TelefoneDTO> telefones;
    private Set<ResponsavelDTO> responsaveis;
}
