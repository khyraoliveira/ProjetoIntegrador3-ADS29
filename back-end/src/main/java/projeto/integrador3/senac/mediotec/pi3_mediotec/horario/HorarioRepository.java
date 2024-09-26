// HorarioRepository.java
package projeto.integrador3.senac.mediotec.pi3_mediotec.horario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;

import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
    // Método customizado para buscar horários por ID da turma, disciplina e professor
    List<Horario> findByTurmaDisciplinaProfessor_Id(TurmaDisciplinaProfessorId turmaDisciplinaProfessorId);
}
