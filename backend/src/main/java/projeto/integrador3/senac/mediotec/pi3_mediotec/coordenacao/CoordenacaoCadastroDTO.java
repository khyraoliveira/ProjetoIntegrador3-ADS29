package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;


import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class CoordenacaoCadastroDTO {
    private String nome;
    private String descricao;
    
    // Endereços e Telefones completos para criação/edição diretamente em coordenacao
    private Set<EnderecoDTO> enderecos;
    private Set<TelefoneDTO> telefones;
    
    // IDs dos coordenadores, turmas e professores
    private List<String> coordenadoresIds;  // IDs dos coordenadores (CPF)
    private Set<Long> turmasIds;            // IDs das turmas
    private Set<String> professoresIds;     // IDs dos professores (CPF)
}

