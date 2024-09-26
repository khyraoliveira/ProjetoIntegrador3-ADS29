package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.presenca.Presenca;
import projeto.integrador3.senac.mediotec.pi3_mediotec.responsavel.Responsavel;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;
import projeto.integrador3.senac.mediotec.pi3_mediotec.usuario.Usuario;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "aluno")
public class Aluno extends Usuario {

    private static final long serialVersionUID = 1L;

    // ============================= PRIMARY KEY =============================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matricula_aluno")
    private Long id;

    // ============================= ATTRIBUTES =============================

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column
    private boolean status;

    // ============================= RELATIONSHIPS =============================

    // Endereços associados ao aluno (One-to-Many)
    @Builder.Default
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Endereco> enderecos = new HashSet<>();

    // Telefones associados ao aluno (One-to-Many)
    @Builder.Default
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Telefone> telefones = new HashSet<>();

    // Turmas nas quais o aluno está matriculado (Many-to-Many)
    @Builder.Default
    @ManyToMany(mappedBy = "alunos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Turma> turmas = new HashSet<>();

    // Presenças do aluno (One-to-Many)
    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Presenca> presencas = new HashSet<>();

    // Coordenação relacionada ao aluno (Many-to-One)
    @ManyToOne
    private Coordenacao coordenacao;

    // Responsáveis pelo aluno (One-to-Many)
    @Builder.Default
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Responsavel> responsaveis = new HashSet<>();

    // ============================= AUXILIARY METHODS =============================

    /**
     * Adiciona um endereço ao aluno e configura a relação bidirecional.
     */
    public void addEndereco(Endereco endereco) {
        endereco.setAluno(this);
        this.enderecos.add(endereco);
    }

    /**
     * Adiciona um telefone ao aluno e configura a relação bidirecional.
     */
    public void addTelefone(Telefone telefone) {
        telefone.setAluno(this);
        this.telefones.add(telefone);
    }

    /**
     * Adiciona uma turma ao aluno e configura a relação bidirecional.
     */
    public void addTurma(Turma turma) {
        this.turmas.add(turma);
        turma.getAlunos().add(this);
    }

    /**
     * Adiciona um responsável ao aluno e configura a relação bidirecional.
     */
    public void addResponsavel(Responsavel responsavel) {
        responsavel.setAluno(this);
        this.responsaveis.add(responsavel);
    }
}
