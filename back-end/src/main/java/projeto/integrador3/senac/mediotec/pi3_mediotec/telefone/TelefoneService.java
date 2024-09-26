package projeto.integrador3.senac.mediotec.pi3_mediotec.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.Aluno;
import projeto.integrador3.senac.mediotec.pi3_mediotec.aluno.AlunoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.Coordenador;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.CoordenadorRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.ProfessorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TelefoneService {

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    @Transactional
    public TelefoneDTO salvarTelefone(Long alunoId, String professorCpf, String coordenadorCpf, Long coordenacaoId, TelefoneDTO telefoneDTO) {
        Telefone telefone = convertToEntity(telefoneDTO);

        // Associar o telefone às entidades corretas com base nos IDs/CPFs fornecidos
        if (alunoId != null) {
            telefone.setAluno(buscarAlunoPorId(alunoId));
        }
        if (professorCpf != null) {
            telefone.setProfessor(buscarProfessorPorCpf(professorCpf));
        }
        if (coordenadorCpf != null) {
            telefone.setCoordenador(buscarCoordenadorPorCpf(coordenadorCpf));
        }
        if (coordenacaoId != null) {
            telefone.setCoordenacao(buscarCoordenacaoPorId(coordenacaoId));
        }

        Telefone savedTelefone = telefoneRepository.save(telefone);
        return convertToDTO(savedTelefone);
    }

    @Transactional
    public TelefoneDTO atualizarTelefone(Long id, Long alunoId, String professorCpf, String coordenadorCpf, Long coordenacaoId, TelefoneDTO telefoneDTO) {
        Telefone telefone = telefoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Telefone não encontrado"));

        telefone.setDdd(telefoneDTO.getDdd());
        telefone.setNumero(telefoneDTO.getNumero());

        // Associar o telefone às entidades corretas com base nos IDs/CPFs fornecidos
        if (alunoId != null) {
            telefone.setAluno(buscarAlunoPorId(alunoId));
        }
        if (professorCpf != null) {
            telefone.setProfessor(buscarProfessorPorCpf(professorCpf));
        }
        if (coordenadorCpf != null) {
            telefone.setCoordenador(buscarCoordenadorPorCpf(coordenadorCpf));
        }
        if (coordenacaoId != null) {
            telefone.setCoordenacao(buscarCoordenacaoPorId(coordenacaoId));
        }

        Telefone updatedTelefone = telefoneRepository.save(telefone);
        return convertToDTO(updatedTelefone);
    }

    @Transactional(readOnly = true)
    public Optional<TelefoneDTO> buscarTelefonePorId(Long id, Long alunoId, String professorCpf, String coordenadorCpf, Long coordenacaoId) {
        Telefone telefone = telefoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Telefone não encontrado"));

        validarEntidade(telefone, alunoId, professorCpf, coordenadorCpf, coordenacaoId);

        return Optional.of(convertToDTO(telefone));
    }

    @Transactional(readOnly = true)
    public List<TelefoneDTO> listarTelefones(Long alunoId, String professorCpf, String coordenadorCpf, Long coordenacaoId) {
        List<Telefone> telefones = telefoneRepository.findAll(); 

        return telefones.stream()
                .filter(telefone -> validarEntidade(telefone, alunoId, professorCpf, coordenadorCpf, coordenacaoId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private boolean validarEntidade(Telefone telefone, Long alunoId, String professorCpf, String coordenadorCpf, Long coordenacaoId) {
        if (alunoId != null && telefone.getAluno() != null && !telefone.getAluno().getId().equals(alunoId)) {
            return false;
        }
        if (professorCpf != null && telefone.getProfessor() != null && !telefone.getProfessor().getCpf().equals(professorCpf)) {
            return false;
        }
        if (coordenadorCpf != null && telefone.getCoordenador() != null && !telefone.getCoordenador().getCpf().equals(coordenadorCpf)) {
            return false;
        }
        if (coordenacaoId != null && telefone.getCoordenacao() != null && !telefone.getCoordenacao().getId().equals(coordenacaoId)) {
            return false;
        }
        return true;
    }

    @Transactional
    public void deletarTelefone(Long id) {
        Telefone telefone = telefoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Telefone não encontrado"));
        telefoneRepository.delete(telefone);
    }

    private Aluno buscarAlunoPorId(Long alunoId) {
        return alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    private Professor buscarProfessorPorCpf(String professorCpf) {
        return professorRepository.findById(professorCpf)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    private Coordenador buscarCoordenadorPorCpf(String coordenadorCpf) {
        return coordenadorRepository.findById(coordenadorCpf)
                .orElseThrow(() -> new RuntimeException("Coordenador não encontrado"));
    }

    private Coordenacao buscarCoordenacaoPorId(Long coordenacaoId) {
        return coordenacaoRepository.findById(coordenacaoId)
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
    }

    private TelefoneDTO convertToDTO(Telefone telefone) {
        return TelefoneDTO.builder()
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

    private Telefone convertToEntity(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }
}
