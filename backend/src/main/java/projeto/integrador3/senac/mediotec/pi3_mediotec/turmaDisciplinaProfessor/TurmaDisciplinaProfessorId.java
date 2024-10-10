package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TurmaDisciplinaProfessorId implements Serializable {

    private static final long serialVersionUID = 1L;

    // ============================= CHAVES PRIMÁRIAS =============================

    /**
     * O campo `turmaId` representa a chave estrangeira que referencia a tabela `Turma`.
     * Ele é mapeado diretamente para a coluna `id_turma` da tabela `turma_disciplina_professor`.
     */
    @Column(name = "id_turma")
    private Long turmaId; // Chave primária associada à entidade `Turma`

    /**
     * O campo `disciplinaId` representa a chave estrangeira que referencia a tabela `Disciplina`.
     * Ele é mapeado diretamente para a coluna `id_disciplina` da tabela `turma_disciplina_professor`.
     */
    @Column(name = "id_disciplina")
    private Long disciplinaId; // Chave primária associada à entidade `Disciplina`

    /**
     * O campo `professorId` representa a chave estrangeira que referencia a tabela `Professor`.
     * Ele é mapeado diretamente para a coluna `id_professor` da tabela `turma_disciplina_professor`.
     */
    @Column(name = "id_professor")
    private String professorId; // Chave primária associada à entidade `Professor`

    // ============================= MÉTODOS AUXILIARES =============================

    /**
     * Método `equals` para comparar dois objetos `TurmaDisciplinaProfessorId`.
     * Este método garante que dois objetos sejam considerados iguais se seus atributos `turmaId`,
     * `disciplinaId` e `professorId` forem iguais.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurmaDisciplinaProfessorId that = (TurmaDisciplinaProfessorId) o;
        return Objects.equals(turmaId, that.turmaId) &&
               Objects.equals(disciplinaId, that.disciplinaId) &&
               Objects.equals(professorId, that.professorId);
    }

    /**
     * Método `hashCode` para gerar o código hash do objeto.
     * Este método utiliza os campos `turmaId`, `disciplinaId` e `professorId`
     * para garantir a unicidade do objeto em estruturas de dados baseadas em hash, como `HashSet`.
     */
    @Override
    public int hashCode() {
        return Objects.hash(turmaId, disciplinaId, professorId);
    }
}
