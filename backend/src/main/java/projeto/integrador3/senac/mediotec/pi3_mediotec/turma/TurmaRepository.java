package projeto.integrador3.senac.mediotec.pi3_mediotec.turma;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
	Optional<Turma> findById(Long id);
	
    @Query("SELECT t.id FROM Turma t")
    List<Long> findAllIds();
}

