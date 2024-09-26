package projeto.integrador3.senac.mediotec.pi3_mediotec.endereco;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
    Optional<Endereco> findByAlunoId(Long id);
    Optional<Endereco> findByProfessorCpf(String professorCpf);
    Optional<Endereco> findByCoordenadorCpf(String coordenadorCpf);
    Optional<Endereco> findByCoordenacaoId(Long id);
    
}

