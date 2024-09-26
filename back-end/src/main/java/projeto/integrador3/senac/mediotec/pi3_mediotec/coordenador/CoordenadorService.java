package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.EnderecoDTO;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.TelefoneDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CoordenadorService {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository; // Para associar a Coordenacao

    // ================== MÉTODOS DE BUSCA ==================

    // Lista todos os coordenadores e retorna uma lista de CoordenadorDTO
    public List<CoordenadorDTO> getAllCoordenadores() {
        return coordenadorRepository.findAll().stream()
                .map(this::convertToDto) // Converte cada coordenador para DTO
                .collect(Collectors.toList());
    }

    // Busca coordenador por ID (CPF) e retorna CoordenadorDTO
    public Optional<CoordenadorDTO> getCoordenadorById(String id) {
        return coordenadorRepository.findById(id)
                .map(this::convertToDto); // Converte para DTO se encontrado
    }

    // ================== MÉTODOS DE CRIAÇÃO/ATUALIZAÇÃO ==================

    // Cria um novo coordenador
    public CoordenadorDTO saveCoordenador(CoordenadorDTO coordenadorDTO) {
        // Verifica se o CPF já existe
        if (coordenadorRepository.existsByCpf(coordenadorDTO.getCpf())) {
            throw new RuntimeException("Coordenador com CPF " + coordenadorDTO.getCpf() + " já existe");
        }

        // Converte DTO diretamente para entidade
        Coordenador coordenador = new Coordenador();
        coordenador.setCpf(coordenadorDTO.getCpf());
        coordenador.setNome(coordenadorDTO.getNome());
        coordenador.setUltimoNome(coordenadorDTO.getUltimoNome());
        coordenador.setGenero(coordenadorDTO.getGenero());
        coordenador.setData_nascimento(coordenadorDTO.getData_nascimento());
        coordenador.setEmail(coordenadorDTO.getEmail());
        coordenador.setStatus(true); // Define o status inicial como ativo

        // Associa a coordenação, se o idCoordenacao estiver presente no DTO
        if (coordenadorDTO.getIdCoordenacao() != null) {
            Coordenacao coordenacao = coordenacaoRepository.findById(coordenadorDTO.getIdCoordenacao())
                    .orElseThrow(() -> new RuntimeException("Coordenação com ID " + coordenadorDTO.getIdCoordenacao() + " não encontrada"));

            // Associa a coordenação ao coordenador
            coordenador.setCoordenacao(coordenacao);
            coordenacao.getCoordenadores().add(coordenador); // Atualiza a relação bidirecional

            // Salva a coordenação com a nova associação
            coordenacaoRepository.save(coordenacao);
        }

        // Adiciona e associa endereços ao coordenador
        if (coordenadorDTO.getEnderecos() != null && !coordenadorDTO.getEnderecos().isEmpty()) {
            coordenador.getEnderecos().clear();
            coordenadorDTO.getEnderecos().forEach(enderecoDTO -> {
                Endereco endereco = Endereco.builder()
                    .cep(enderecoDTO.getCep())
                    .rua(enderecoDTO.getRua())
                    .numero(enderecoDTO.getNumero())
                    .bairro(enderecoDTO.getBairro())
                    .cidade(enderecoDTO.getCidade())
                    .estado(enderecoDTO.getEstado())
                    .coordenador(coordenador) // Associação bidirecional
                    .build();
                coordenador.getEnderecos().add(endereco);
            });
        }

        // Adiciona e associa telefones ao coordenador
        if (coordenadorDTO.getTelefones() != null && !coordenadorDTO.getTelefones().isEmpty()) {
            coordenador.getTelefones().clear();
            coordenadorDTO.getTelefones().forEach(telefoneDTO -> {
                Telefone telefone = Telefone.builder()
                    .ddd(telefoneDTO.getDdd())
                    .numero(telefoneDTO.getNumero())
                    .coordenador(coordenador) // Associação bidirecional
                    .build();
                coordenador.getTelefones().add(telefone);
            });
        }

        // Salva o coordenador e retorna o DTO correspondente
        Coordenador savedCoordenador = coordenadorRepository.save(coordenador);
        return convertToDto(savedCoordenador);
    }

    // Atualiza um coordenador existente
    public CoordenadorDTO updateCoordenador(String id, CoordenadorDTO coordenadorDTO) {
        // Busca o coordenador existente no banco
        Coordenador coordenador = coordenadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Coordenador não encontrado"));

        // Atualiza os dados do coordenador diretamente a partir do DTO
        coordenador.setNome(coordenadorDTO.getNome());
        coordenador.setUltimoNome(coordenadorDTO.getUltimoNome());
        coordenador.setGenero(coordenadorDTO.getGenero());
        coordenador.setData_nascimento(coordenadorDTO.getData_nascimento());
        coordenador.setEmail(coordenadorDTO.getEmail());
        coordenador.setStatus(coordenadorDTO.isStatus());

        // Atualiza a coordenação, se o idCoordenacao for passado no DTO
        if (coordenadorDTO.getIdCoordenacao() != null) {
            Coordenacao coordenacao = coordenacaoRepository.findById(coordenadorDTO.getIdCoordenacao())
                    .orElseThrow(() -> new RuntimeException("Coordenação com ID " + coordenadorDTO.getIdCoordenacao() + " não encontrada"));
            coordenador.setCoordenacao(coordenacao); // Atualiza a relação com coordenação
        } else {
            coordenador.setCoordenacao(null); // Se idCoordenacao for null, remove a associação
        }

        // Atualiza os endereços do coordenador
        if (coordenadorDTO.getEnderecos() != null && !coordenadorDTO.getEnderecos().isEmpty()) {
            coordenador.getEnderecos().clear();
            coordenadorDTO.getEnderecos().forEach(enderecoDTO -> {
                Endereco endereco = Endereco.builder()
                    .cep(enderecoDTO.getCep())
                    .rua(enderecoDTO.getRua())
                    .numero(enderecoDTO.getNumero())
                    .bairro(enderecoDTO.getBairro())
                    .cidade(enderecoDTO.getCidade())
                    .estado(enderecoDTO.getEstado())
                    .coordenador(coordenador) // Associação bidirecional
                    .build();
                coordenador.getEnderecos().add(endereco);
            });
        }

        // Atualiza os telefones do coordenador
        if (coordenadorDTO.getTelefones() != null && !coordenadorDTO.getTelefones().isEmpty()) {
            coordenador.getTelefones().clear();
            coordenadorDTO.getTelefones().forEach(telefoneDTO -> {
                Telefone telefone = Telefone.builder()
                    .ddd(telefoneDTO.getDdd())
                    .numero(telefoneDTO.getNumero())
                    .coordenador(coordenador) // Associação bidirecional
                    .build();
                coordenador.getTelefones().add(telefone);
            });
        }

        // Salva a entidade e retorna o DTO atualizado
        Coordenador updatedCoordenador = coordenadorRepository.save(coordenador);
        return convertToDto(updatedCoordenador);
    }

    // ================== MÉTODO DE REMOÇÃO ==================

    // Deleta um coordenador por ID (CPF)
    public void deleteCoordenador(String id) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordenador não encontrado"));
        coordenadorRepository.delete(coordenador);
    }

    // ================== MÉTODOS AUXILIARES ==================

    // Converte Coordenador para CoordenadorDTO
    private CoordenadorDTO convertToDto(Coordenador coordenador) {
        return CoordenadorDTO.builder()
                .cpf(coordenador.getCpf())
                .nome(coordenador.getNome())
                .ultimoNome(coordenador.getUltimoNome())
                .genero(coordenador.getGenero())
                .data_nascimento(coordenador.getData_nascimento())
                .email(coordenador.getEmail())
                .status(coordenador.isStatus())
                .enderecos(coordenador.getEnderecos().stream()
                        .map(this::convertEnderecoToDto)
                        .collect(Collectors.toSet()))
                .telefones(coordenador.getTelefones().stream()
                        .map(this::convertTelefoneToDto)
                        .collect(Collectors.toSet()))
                .idCoordenacao(coordenador.getCoordenacao() != null ? coordenador.getCoordenacao().getId() : null) // Converte a coordenação para o DTO
                .build();
    }

    // Converte Endereco (entidade) para EnderecoDTO
    private EnderecoDTO convertEnderecoToDto(Endereco endereco) {
        return EnderecoDTO.builder()
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .build();
    }

    // Converte Telefone (entidade) para TelefoneDTO
    private TelefoneDTO convertTelefoneToDto(Telefone telefone) {
        return TelefoneDTO.builder()
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }
}
