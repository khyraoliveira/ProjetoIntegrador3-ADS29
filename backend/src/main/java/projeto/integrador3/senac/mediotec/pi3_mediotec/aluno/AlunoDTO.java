package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel.Responsavel;
import projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel.ResponsavelDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;

import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class AlunoDTO {
	private Long id;
    private String nome;
    private String ultimoNome;
    private String genero;
    private Date data_nascimento;
    private boolean status;
    private String cpf;
    private String email;
    private Set<EnderecoDTO> enderecos;
    private Set<TelefoneDTO> telefones;
    private Set<TurmaResumidaDTO> turmas; // Turmas com disciplinas e coordenadores
    private Set<ResponsavelDTO> responsaveis;
}
