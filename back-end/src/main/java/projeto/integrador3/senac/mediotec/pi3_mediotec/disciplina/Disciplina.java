package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;

/**
 * Entidade Disciplina - Responsável por representar uma disciplina no sistema.
 * Cada disciplina possui uma carga horária e pode estar associada a várias turmas e professores
 * por meio da entidade de relacionamento `TurmaDisciplinaProfessor`.
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "disciplina")
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    // Identificador único da disciplina
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disciplina")
    private Long id;

    // Nome da disciplina, não nulo e com restrição de tamanho
    @NotNull(message = "{disciplina.nome.notnull}")
    @Size(min = 3, max = 100, message = "{disciplina.nome.size}")
    @Column(nullable = false)
    private String nome;

    // Carga horária da disciplina (não nula)
    @NotNull(message = "{disciplina.ch.notnull}")
    @Column(nullable = false)
    private int carga_horaria;
    
    // Relacionamento com TurmaDisciplinaProfessor para definir quais turmas e professores lecionam essa disciplina
    @JsonIgnore
    @OneToMany(mappedBy = "disciplina")
    private Set<TurmaDisciplinaProfessor> turmaDisciplinaProfessores;
}
