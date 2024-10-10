package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoResumidaDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorResumidoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;

@Builder
@Data
public class ProfessorResumidoDTO {
    private String cpf;         // CPF do Professor
    private String nome;        // Nome do Professor
    private String ultimoNome;  // Sobrenome do Professor
    private String email;
    private CoordenacaoResumidaDTO coordenacao;
    private Set<TurmaDisciplinaResumidaDTO> turmaDisciplinaProfessores;
    private Set<EnderecoDTO> enderecos = new HashSet<>();
    private Set<TelefoneDTO> telefones = new HashSet<>();
}

