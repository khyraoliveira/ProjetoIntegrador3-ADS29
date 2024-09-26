package projeto.integrador3.senac.mediotec.pi3_mediotec.telefone;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.Coordenador;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel.Responsavel;

@Data
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "telefone")
public class Telefone {

    // ========================= Identificação da Entidade =========================
    
    /** 
     * Chave primária (ID) gerada automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ========================= Campos Obrigatórios (DDD e Número) =========================
    
    /**
     * Código de área (DDD) do telefone, deve ter 2 dígitos.
     * Validação: Campo não pode ser nulo, e o tamanho deve ser exatamente 2.
     */
    @NotNull(message = "{usuario.telefone.ddd.notnull}")
    @Size(min = 2, max = 2, message = "{usuario.telefone.ddd.size}")
    @Column(name = "ddd", nullable = false)
    private String ddd;

    /**
     * Número do telefone, deve ter entre 8 e 9 dígitos.
     * Validação: Campo não pode ser nulo, e o tamanho deve estar entre 8 e 9 caracteres.
     */
    @NotNull(message = "{usuario.telefone.numero.notnull}")
    @Size(min = 8, max = 9, message = "{usuario.telefone.numero.size}")
    @Column(name = "numero", nullable = false)
    private String numero;

    // ========================= Relacionamentos com Outras Entidades =========================
    
    /** 
     * Relacionamento com a entidade `Aluno` (muitos para um).
     * O campo `aluno_id` no banco de dados representa essa relação.
     * A anotação `@JsonIgnore` impede a serialização desse relacionamento na resposta JSON.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    /**
     * Relacionamento com a entidade `Professor` (muitos para um).
     * O campo `professor_id` no banco de dados representa essa relação.
     * Anotação `@JsonIgnore` evita ciclos de serialização.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    /**
     * Relacionamento com a entidade `Coordenador` (muitos para um).
     * O campo `coordenador_id` no banco de dados representa essa relação.
     * `@JsonIgnore` evita a inclusão dessa relação nas respostas JSON.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "coordenador_id")
    private Coordenador coordenador;
    
    /**
     * Relacionamento com a entidade `Coordenacao` (muitos para um).
     * O campo `coordenacao_id` no banco de dados representa essa relação.
     * `@JsonIgnore` evita que a relação apareça nas respostas JSON.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "coordenacao_id")
    private Coordenacao coordenacao;
    
    /**
     * Relacionamento com a entidade `Responsavel` (muitos para um).
     * O campo `responsavel_id` no banco de dados representa essa relação.
     * `@JsonIgnore` evita que o relacionamento seja serializado.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Responsavel responsavel;

    // ========================= Métodos Auxiliares =========================

    /**
     * Método para configurar o relacionamento bilateral com a entidade `Responsavel`.
     * @param responsavel - Instância do objeto `Responsavel` que será associada.
     */
    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    // ========================= Métodos de Igualdade e Hash =========================

    /**
     * Método `hashCode` baseado no ID para evitar ciclos de referência e garantir integridade.
     * @return Código hash baseado no campo `id`.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id); // Baseado apenas no ID
    }

    /**
     * Método `equals` para comparar dois objetos `Telefone` com base no campo `id`.
     * @param o - Objeto a ser comparado.
     * @return `true` se os objetos têm o mesmo ID, `false` caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefone telefone = (Telefone) o;
        return Objects.equals(id, telefone.id); // Comparando apenas o ID
    }
}
