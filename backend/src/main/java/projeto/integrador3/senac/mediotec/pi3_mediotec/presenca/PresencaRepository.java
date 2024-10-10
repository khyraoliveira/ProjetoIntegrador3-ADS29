package projeto.integrador3.senac.mediotec.pi3_mediotec.presenca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina.Disciplina;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;

import java.util.List;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {
    List<Presenca> findByAluno(Aluno aluno);
    List<Presenca> findByAlunoAndTurmaDisciplinaProfessor_Disciplina(Aluno aluno, Disciplina disciplina);
    List<Presenca> findByTurmaDisciplinaProfessor_Professor(Professor professor);
}
