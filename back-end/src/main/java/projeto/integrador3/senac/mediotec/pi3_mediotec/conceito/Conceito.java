package projeto.integrador3.senac.mediotec.pi3_mediotec.conceito;

import jakarta.persistence.*;
import lombok.*;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conceito")
public class Conceito implements Serializable {

    private static final long serialVersionUID = 1L;

    // ============================= ATRIBUTOS =============================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_conceito;

    // Notas de cada unidade
    @Column
    private Float notaUnidade1;

    @Column
    private Float notaUnidade2;

    @Column
    private Float notaUnidade3;

    @Column
    private Float notaUnidade4;

    // Notas de novas oportunidades de aprendizado (NOA)
    @Column
    private Float noa1; // Nova Oportunidade de Aprendizado (NOA) - 1º semestre

    @Column
    private Float noa2; // Nova Oportunidade de Aprendizado (NOA) - 2º semestre

    // Nota da recuperação final
    @Column
    private Float noaFinal; // Nota da recuperação final

    // Média final das notas
    @Column
    private Float mediaFinal;

    // Aprovação ou reprovação do aluno
    @Column
    private Boolean aprovado;

    // Conceitos exibidos aos alunos (descrições não numéricas das notas)
    private String conceitoNota1;
    private String conceitoNota2;
    private String conceitoNota3;
    private String conceitoNota4;
    private String conceitoNoa1;
    private String conceitoNoa2;
    private String conceitoNoaFinal;
    private String conceitoFinal;

    // ============================= RELACIONAMENTOS =============================

    // Relacionamento com TurmaDisciplinaProfessor (turma, disciplina, professor)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "id_turma", referencedColumnName = "id_turma"),
        @JoinColumn(name = "id_disciplina", referencedColumnName = "id_disciplina"),
        @JoinColumn(name = "id_professor", referencedColumnName = "id_professor")
    })
    private TurmaDisciplinaProfessor turmaDisciplinaProfessor;

    // Relacionamento com a entidade Aluno
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

}
