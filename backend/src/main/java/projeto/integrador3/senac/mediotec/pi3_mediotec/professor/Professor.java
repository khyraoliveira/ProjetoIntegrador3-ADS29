package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.usuario.Usuario;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "professor")
public class Professor extends Usuario {

    private static final long serialVersionUID = 1L;

    // ============================= PRIMARY KEY =============================

    @Id
    @NotNull(message = "{usuario.cpf.notnull}")
    @Column(nullable = false, unique = true)
    private String cpf;

    // ============================= ATTRIBUTES =============================

    @Column
    private boolean status;

    // ============================= RELATIONSHIPS =============================

    // Coordenacao associada ao professor (Many-to-One)
    @ManyToOne
    @JoinColumn(name = "id_coordenacao")
    private Coordenacao coordenacao;

    // Endereços associados ao professor (One-to-Many)
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Endereco> enderecos = new HashSet<>();

    // Telefones associados ao professor (One-to-Many)
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Telefone> telefones = new HashSet<>();

    // Relação com TurmaDisciplinaProfessor (One-to-Many)
    @JsonIgnore
    @OneToMany(mappedBy = "professor")
    private Set<TurmaDisciplinaProfessor> turmaDisciplinaProfessores = new HashSet<>();

    // ============================= AUXILIARY METHODS =============================

    /**
     * Adiciona um endereço ao professor e configura a relação bidirecional.
     * 
     * @param endereco O endereço a ser adicionado.
     */
    public void addEndereco(Endereco endereco) {
        endereco.setProfessor(this);
        this.enderecos.add(endereco);
    }

    /**
     * Adiciona um telefone ao professor e configura a relação bidirecional.
     * 
     * @param telefone O telefone a ser adicionado.
     */
    public void addTelefone(Telefone telefone) {
        telefone.setProfessor(this);
        this.telefones.add(telefone);
    }

    /**
     * Adiciona uma associação entre o professor e uma turma-disciplina-professor.
     * 
     * @param tdp A entidade que associa o professor à turma e disciplina.
     */
    public void addTurmaDisciplinaProfessor(TurmaDisciplinaProfessor tdp) {
        if (this.turmaDisciplinaProfessores == null) {
            this.turmaDisciplinaProfessores = new HashSet<>();
        }
        tdp.setProfessor(this);
        this.turmaDisciplinaProfessores.add(tdp);
    }

}
