package projeto.integrador3.senac.mediotec.pi3_mediotec.endereco;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.Coordenador;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;

/**
 * Classe que representa a entidade Endereço.
 * Um endereço pode estar associado a um Aluno, Professor, Coordenador ou Coordenação.
 */
@Data
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "endereco")
public class Endereco {

    /**
     * ID do endereço. Chave primária gerada automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_endereco;

    /**
     * CEP do endereço.
     * Deve conter entre 8 e 9 caracteres.
     */
    @NotNull(message = "{usuario.endereco.cep.notnull}")
    @Size(min = 8, max = 9, message = "{usuario.endereco.cep.size}")
    @Column(name = "cep", nullable = false)
    private String cep;

    /**
     * Rua do endereço.
     * Deve conter entre 3 e 100 caracteres.
     */
    @NotNull(message = "{usuario.endereco.rua.notnull}")
    @Size(min = 3, max = 100, message = "{usuario.endereco.rua.size}")
    @Column(name = "rua", nullable = false)
    private String rua;

    /**
     * Número do endereço.
     * Deve conter entre 1 e 10 caracteres.
     */
    @NotNull(message = "{usuario.endereco.numero.notnull}")
    @Size(min = 1, max = 10, message = "{usuario.endereco.numero.size}")
    @Column(name = "numero", nullable = false)
    private String numero;

    /**
     * Bairro do endereço.
     * Deve conter entre 3 e 50 caracteres.
     */
    @NotNull(message = "{usuario.endereco.bairro.notnull}")
    @Size(min = 3, max = 50, message = "{usuario.endereco.bairro.size}")
    @Column(name = "bairro", nullable = false)
    private String bairro;

    /**
     * Cidade do endereço.
     * Deve conter entre 3 e 50 caracteres.
     */
    @NotNull(message = "{usuario.endereco.cidade.notnull}")
    @Size(min = 3, max = 50, message = "{usuario.endereco.cidade.size}")
    @Column(name = "cidade", nullable = false)
    private String cidade;

    /**
     * Estado do endereço.
     * Deve conter entre 2 e 20 caracteres.
     */
    @NotNull(message = "{usuario.endereco.estado.notnull}")
    @Size(min = 2, max = 20, message = "{usuario.endereco.estado.size}")
    @Column(name = "estado", nullable = false)
    private String estado;

    /**
     * Associação com a entidade Aluno.
     * Relacionamento ManyToOne, ignorado na serialização JSON.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    /**
     * Associação com a entidade Professor.
     * Relacionamento ManyToOne, ignorado na serialização JSON.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    /**
     * Associação com a entidade Coordenador.
     * Relacionamento ManyToOne, ignorado na serialização JSON.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "coordenador_id")
    private Coordenador coordenador;

    /**
     * Associação com a entidade Coordenação.
     * Relacionamento ManyToOne, ignorado na serialização JSON.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "coordenacao_id")
    private Coordenacao coordenacao;

    /**
     * Método `hashCode` baseado apenas no ID do endereço para evitar ciclos em associações.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id_endereco); // Baseado no ID
    }

    /**
     * Método `equals` para comparar instâncias de Endereço pelo ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(id_endereco, endereco.id_endereco); // Comparação pelo ID
    }
}
