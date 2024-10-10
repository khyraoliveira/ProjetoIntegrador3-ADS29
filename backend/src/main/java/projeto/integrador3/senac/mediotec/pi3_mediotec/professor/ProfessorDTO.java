package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorDTO;

import java.sql.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class ProfessorDTO {
    private String cpf;
    private String nome;
    private String ultimoNome;
    private String genero;
    private Date data_nascimento;
    private String email;
    private boolean status;
    private Set<EnderecoDTO> enderecos;
    private Set<TelefoneDTO> telefones;
    private Long coordenacaoId;
    private Set<TurmaDisciplinaDTO> turmasDisciplinas;
}

