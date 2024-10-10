package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turma_disciplina_professor")
public class TurmaDisciplinaProfessor {

    // ============================= CHAVE PRIMÁRIA COMPOSTA =============================

    /**
     * A classe `TurmaDisciplinaProfessor` usa uma chave composta (representada pela classe `TurmaDisciplinaProfessorId`)
     * para identificar de maneira única a associação entre uma turma, uma disciplina e um professor.
     * O uso de `@EmbeddedId` permite a definição da chave composta diretamente na entidade.
     */
    @EmbeddedId
    private TurmaDisciplinaProfessorId id; // Chave composta para associar turma, disciplina e professor

    // ============================= RELACIONAMENTOS =============================

    /**
     * Relação ManyToOne com a entidade `Turma`, representando que uma turma pode estar associada
     * a várias combinações de disciplina-professor, mas cada associação de disciplina-professor pertence a uma única turma.
     * 
     * O `@MapsId("turmaId")` instrui o JPA a usar a parte `turmaId` da chave composta para fazer o mapeamento.
     * A anotação `@JoinColumn` define a coluna `id_turma` como o campo que referencia a chave estrangeira da entidade `Turma`.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("turmaId")
    @JoinColumn(name = "id_turma", nullable = false)
    private Turma turma; // Associa a turma à relação Turma-Disciplina-Professor

    /**
     * Relação ManyToOne com a entidade `Disciplina`, representando que uma disciplina pode fazer part	e de várias turmas,
     * mas em cada combinação de turma-professor, ela é única.
     * 
     * Assim como no caso de `Turma`, o `@MapsId("disciplinaId")` usa a parte `disciplinaId` da chave composta para o mapeamento.
     * A anotação `@JoinColumn` define a coluna `id_disciplina` como o campo que referencia a chave estrangeira da entidade `Disciplina`.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("disciplinaId")
    @JoinColumn(name = "id_disciplina", nullable = false)
    private Disciplina disciplina; // Associa a disciplina à relação Turma-Disciplina-Professor

    /**
     * Relação ManyToOne com a entidade `Professor`, representando que um professor pode estar associado a várias turmas e disciplinas,
     * mas cada professor está associado a uma única combinação de turma e disciplina.
     * 
     * O `@MapsId("professorId")` instrui o JPA a usar a parte `professorId` da chave composta para o mapeamento.
     * A anotação `@JoinColumn` define a coluna `id_professor` como o campo que referencia a chave estrangeira da entidade `Professor`.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("professorId")
    @JoinColumn(name = "id_professor", nullable = false)
    private Professor professor; // Associa o professor à relação Turma-Disciplina-Professor

    // ============================= MÉTODOS GETTERS E SETTERS =============================

    // Os getters e setters são gerados automaticamente pelas anotações @Getter e @Setter da biblioteca Lombok.
}
