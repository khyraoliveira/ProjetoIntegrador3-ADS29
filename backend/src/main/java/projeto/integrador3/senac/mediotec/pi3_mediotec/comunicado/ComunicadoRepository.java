package projeto.integrador3.senac.mediotec.pi3_mediotec.comunicado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComunicadoRepository extends JpaRepository<Comunicado, Long> {

    // Buscar comunicados enviados por um professor específico (remetenteProfessor)
    List<Comunicado> findByRemetenteProfessor_Cpf(String cpf);

    // Buscar comunicados enviados por uma coordenação específica (remetenteCoordenacao)
    List<Comunicado> findByRemetenteCoordenacao_Id(Long coordenacaoId);

    List<Comunicado> findByReceptorAlunosContaining(Long alunoId);
    List<Comunicado> findByReceptorTurmasContaining(Long turmaId);

}
