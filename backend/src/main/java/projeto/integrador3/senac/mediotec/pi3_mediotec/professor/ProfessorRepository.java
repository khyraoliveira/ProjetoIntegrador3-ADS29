package projeto.integrador3.senac.mediotec.pi3_mediotec.professor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
	boolean existsByCpf(String cpf);
	Optional<Professor> findByCpf(String cpf); 
}
