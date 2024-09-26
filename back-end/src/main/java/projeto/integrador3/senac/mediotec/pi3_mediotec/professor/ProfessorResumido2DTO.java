package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;


import java.util.Set;

import lombok.Builder;
import lombok.Data;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;

@Builder
@Data
public class ProfessorResumido2DTO {
    private String cpf;         // CPF do Professor
    private String nomeProfessor;       
    private String email;
    private Set<Telefone> telefones;
}

