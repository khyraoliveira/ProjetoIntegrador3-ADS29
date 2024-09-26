package projeto.integrador3.senac.mediotec.pi3_mediotec.aluno;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	boolean existsByCpf(String cpf);
	Optional<Aluno> findById(Long id);	
	
    @Query("SELECT a.id FROM Aluno a")
    List<Long> findAllIds();
}

