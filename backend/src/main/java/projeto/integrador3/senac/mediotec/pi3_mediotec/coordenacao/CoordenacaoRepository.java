package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;

public interface CoordenacaoRepository extends JpaRepository<Coordenacao, Long> {
	Optional<Coordenacao> findById(Long id);
	boolean existsByIdAndCoordenadores_Cpf(Long coordenacaoId, String cpf);
}

