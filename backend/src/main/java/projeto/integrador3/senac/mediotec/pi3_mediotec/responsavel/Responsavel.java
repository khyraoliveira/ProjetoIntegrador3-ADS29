package projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel;

import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import java.util.HashSet;

/**
 * A classe Responsavel representa a entidade responsável, 
 * relacionada a alunos e contendo informações específicas, 
 * como nome, CPF, grau de parentesco e associação com telefones.
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "responsavel")
public class Responsavel implements Serializable {

    // Serial version UID para a serialização da entidade
    private static final long serialVersionUID = 1L;

    // ============================= ATRIBUTOS =============================

    /**
     * Chave primária da entidade Responsavel.
     * O valor é gerado automaticamente (auto-incremento).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * CPF do responsável, deve ser único e não pode ser nulo.
     */
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpfResponsavel;

    /**
     * Nome do responsável, não pode ser nulo.
     */
    @Column(name = "nome", nullable = false)
    private String nome;

    /**
     * Último nome (sobrenome) do responsável, não pode ser nulo.
     */
    @Column(name = "ultimo_nome", nullable = false)
    private String ultimoNome;

    /**
     * Grau de parentesco do responsável com o aluno, não pode ser nulo.
     */
    @Column(name = "grau_parentesco", nullable = false)
    private String grauParentesco;

    // ============================= RELACIONAMENTOS =============================

    /**
     * Relação ManyToOne (muitos responsáveis podem estar associados a um aluno).
     * O campo aluno_id será usado para mapear essa relação no banco de dados.
     */
    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    /**
     * Relação OneToMany (um responsável pode ter muitos telefones).
     * Telefones são removidos em cascata e orphanRemoval garante a exclusão.
     * Inicializamos como um HashSet para garantir que sempre tenha uma coleção pronta.
     */
    @Builder.Default
    @OneToMany(mappedBy = "responsavel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Telefone> telefones = new HashSet<>();

    // ============================= MÉTODOS AUXILIARES =============================

    /**
     * Adiciona um telefone ao responsável e configura a relação bidirecional
     * entre o responsável e o telefone.
     * 
     * @param telefone O telefone a ser associado ao responsável.
     */
    public void addTelefone(Telefone telefone) {
        telefone.setResponsavel(this);  // Configura a relação bidirecional
        this.telefones.add(telefone);   // Adiciona o telefone ao conjunto
    }
}
