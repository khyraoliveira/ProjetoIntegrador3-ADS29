package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comunicado {

    // ============================= ATRIBUTOS =============================

    // Identificador único do comunicado, gerado automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Conteúdo do comunicado
    @Column(nullable = false)
    private String conteudo;

    // Data de envio do comunicado
    @Column(nullable = false)
    private LocalDateTime dataEnvio;

    // ============================= RELACIONAMENTOS =============================

    // Relacionamento com o Professor que enviou o comunicado (opcional)
    @ManyToOne
    @JoinColumn(name = "remetente_professor_id")
    private Professor remetenteProfessor;

    // Relacionamento com a Coordenação que enviou o comunicado (opcional)
    @ManyToOne
    @JoinColumn(name = "remetente_coordenacao_id")
    private Coordenacao remetenteCoordenacao;

    // ============================= DESTINATÁRIOS =============================

    // Lista de IDs dos alunos que receberam o comunicado
    @ElementCollection
    @CollectionTable(name = "comunicado_receptor_alunos", joinColumns = @JoinColumn(name = "comunicado_id"))
    @Column(name = "aluno_id")
    private List<Long> receptorAlunos;

    // Lista de IDs das turmas que receberam o comunicado
    @ElementCollection
    @CollectionTable(name = "comunicado_receptor_turmas", joinColumns = @JoinColumn(name = "comunicado_id"))
    @Column(name = "turma_id")
    private List<Long> receptorTurmas;
}
