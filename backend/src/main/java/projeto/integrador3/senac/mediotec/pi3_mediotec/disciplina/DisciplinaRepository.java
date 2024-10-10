package projeto.integrador3.senac.mediotec.pi3_mediotec.disciplina;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
	Optional<Disciplina> findById(Long id);
	
}

