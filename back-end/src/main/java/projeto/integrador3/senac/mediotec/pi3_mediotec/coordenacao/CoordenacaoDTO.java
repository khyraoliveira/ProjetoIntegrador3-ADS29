package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumido2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumido2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumida2DTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.TurmaResumidaDTO;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class CoordenacaoDTO {
	private Long id;
    private String nome;
    private String descricao;
    private Set<EnderecoDTO> enderecos;
    private Set<TelefoneDTO> telefones;
    private List<CoordenadorResumido2DTO> coordenadores;  // Resumo dos coordenadores
    private Set<TurmaResumida2DTO> turmas;  // Resumo das turmas
    private Set<ProfessorResumido2DTO> professores;  // Resumo dos professores
}

