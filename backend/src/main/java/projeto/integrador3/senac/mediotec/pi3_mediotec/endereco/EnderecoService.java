package projeto.integrador3.senac.mediotec.pi3_mediotec.endereco;

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
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional
    public EnderecoDTO salvarEndereco(Long alunoId, String professorCpf, String coordenadorCpf, Long coordenacaoId, EnderecoDTO enderecoDTO) {
        Endereco endereco = convertToEntity(enderecoDTO);

        // Associa o endereço à entidade correta com base nos IDs fornecidos
        if (alunoId != null) {
            endereco.setAluno(buscarAlunoPorId(alunoId));
        }
        if (professorCpf != null) {
            endereco.setProfessor(buscarProfessorPorCpf(professorCpf));
        }
        if (coordenadorCpf != null) {
            endereco.setCoordenador(buscarCoordenadorPorCpf(coordenadorCpf));
        }
        if (coordenacaoId != null) {
            endereco.setCoordenacao(buscarCoordenacaoPorId(coordenacaoId));
        }

        Endereco savedEndereco = enderecoRepository.save(endereco);
        return convertToDTO(savedEndereco);
    }

    @Transactional
    public EnderecoDTO atualizarEndereco(Long id, Long alunoId, String professorCpf, String coordenadorCpf, Long coordenacaoId, EnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        endereco.setCep(enderecoDTO.getCep());
        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());

        // Atualiza a associação com as entidades corretas, se fornecidas
        if (alunoId != null) {
            endereco.setAluno(buscarAlunoPorId(alunoId));
        }
        if (professorCpf != null) {
            endereco.setProfessor(buscarProfessorPorCpf(professorCpf));
        }
        if (coordenadorCpf != null) {
            endereco.setCoordenador(buscarCoordenadorPorCpf(coordenadorCpf));
        }
        if (coordenacaoId != null) {
            endereco.setCoordenacao(buscarCoordenacaoPorId(coordenacaoId));
        }

        Endereco updatedEndereco = enderecoRepository.save(endereco);
        return convertToDTO(updatedEndereco);
    }

    public List<EnderecoDTO> listarEnderecos() {
        return enderecoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<EnderecoDTO> buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    
    // Serviço para buscar endereço por Aluno ID
    @Transactional(readOnly = true)
    public Optional<EnderecoDTO> buscarEnderecoPorAlunoId(Long alunoId) {
        return enderecoRepository.findByAlunoId(alunoId).map(this::convertToDTO);
    }

    // Serviço para buscar endereço por Professor CPF
    @Transactional(readOnly = true)
    public Optional<EnderecoDTO> buscarEnderecoPorProfessorCpf(String professorCpf) {
        return enderecoRepository.findByProfessorCpf(professorCpf).map(this::convertToDTO);
    }

    // Serviço para buscar endereço por Coordenador CPF
    @Transactional(readOnly = true)
    public Optional<EnderecoDTO> buscarEnderecoPorCoordenadorCpf(String coordenadorCpf) {
        return enderecoRepository.findByCoordenadorCpf(coordenadorCpf).map(this::convertToDTO);
    }

    // Serviço para buscar endereço por Coordenação ID
    @Transactional(readOnly = true)
    public Optional<EnderecoDTO> buscarEnderecoPorCoordenacaoId(Long id) {
        return enderecoRepository.findByCoordenacaoId(id).map(this::convertToDTO);
    }

    

    public void deletarEndereco(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
        enderecoRepository.delete(endereco);
    }

    private EnderecoDTO convertToDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .build();
    }

    private Endereco convertToEntity(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .cep(enderecoDTO.getCep())
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .bairro(enderecoDTO.getBairro())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    // Função para buscar Aluno por ID
    private Aluno buscarAlunoPorId(Long alunoId) {
        return alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    // Função para buscar Professor por CPF
    private Professor buscarProfessorPorCpf(String professorCpf) {
        return professorRepository.findById(professorCpf)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    // Função para buscar Coordenador por CPF
    private Coordenador buscarCoordenadorPorCpf(String coordenadorCpf) {
        return coordenadorRepository.findById(coordenadorCpf)
                .orElseThrow(() -> new RuntimeException("Coordenador não encontrado"));
    }

    // Função para buscar Coordenacao por ID
    private Coordenacao buscarCoordenacaoPorId(Long id) {
        return coordenacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordenação não encontrada"));
    }

}
