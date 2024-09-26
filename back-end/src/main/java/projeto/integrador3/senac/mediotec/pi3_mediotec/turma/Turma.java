package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "turma")
public class Turma implements Serializable {

    private static final long serialVersionUID = 1L;

    // ============================= PRIMARY KEY =============================
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turma")
    private Long id;

    // ============================= ATTRIBUTES =============================
    
    @Column
    private String nome;

    @NotNull(message = "{turma.ano.notnull}")
    @Column(nullable = false)
    private int anoLetivo;

    @Column
    private String anoEscolar;

    @Column
    private String turno;

    @Column
    private boolean status;

    // ============================= RELATIONSHIPS =============================
    
    // Relacionamento ManyToMany com Aluno
    @JsonIgnore
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "aluno_turma",
        joinColumns = @JoinColumn(name = "turma_id"),
        inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> alunos = new ArrayList<>();

    // Relacionamento ManyToOne com Coordenacao
    @ManyToOne
    @JoinColumn(name = "id_coordenacao")
    private Coordenacao coordenacao;

    // Relacionamento OneToMany com TurmaDisciplinaProfessor
    @Builder.Default
    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<TurmaDisciplinaProfessor> turmaDisciplinaProfessores = new HashSet<>();

    // ============================= AUXILIARY METHODS =============================

    /**
     * Adiciona uma relação entre a turma e TurmaDisciplinaProfessor.
     */
    public void addTurmaDisciplinaProfessor(TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        this.turmaDisciplinaProfessores.add(turmaDisciplinaProfessor);
        turmaDisciplinaProfessor.setTurma(this);
    }

    /**
     * Remove uma relação entre a turma e TurmaDisciplinaProfessor.
     */
    public void removeTurmaDisciplinaProfessor(TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        this.turmaDisciplinaProfessores.remove(turmaDisciplinaProfessor);
        turmaDisciplinaProfessor.setTurma(null);
    }

    /**
     * Adiciona um aluno à turma e configura a relação bidirecional.
     */
    public void addAluno(Aluno aluno) {
        this.alunos.add(aluno);
        aluno.getTurmas().add(this);
    }

    /**
     * Remove um aluno da turma e configura a relação bidirecional.
     */
    public void removeAluno(Aluno aluno) {
        this.alunos.remove(aluno);
        aluno.getTurmas().remove(this);
    }
}