package projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaDisciplinaProfessorRepository extends JpaRepository<TurmaDisciplinaProfessor, TurmaDisciplinaProfessorId> {
    List<TurmaDisciplinaProfessor> findByTurmaId(Long turmaId);
    List<TurmaDisciplinaProfessor> findByDisciplinaId(Long disciplinaId);
    List<TurmaDisciplinaProfessor> findById_ProfessorId(String professorId);
    List<TurmaDisciplinaProfessor> findById_TurmaIdAndId_DisciplinaId(Long turmaId, Long disciplinaId);
    void deleteByTurmaId(Long turmaId);
    void deleteByDisciplina_Id(Long disciplinaId);
    
    // Verificar se existe uma turma com base no ID da turma
    boolean existsByTurma_Id(Long idTurma);
    
    // Verificar se existe uma disciplina com base no ID da disciplina
    boolean existsByDisciplina_Id(Long idDisciplina);
    
    // Verificar se existe um professor com base no ID do professor
    boolean existsByProfessor_Cpf(String cpf);

}




