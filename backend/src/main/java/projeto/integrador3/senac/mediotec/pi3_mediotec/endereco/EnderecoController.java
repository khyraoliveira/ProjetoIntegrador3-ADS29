package projeto.integrador3.senac.mediotec.pi3_mediotec.endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/*
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;
    
    
    // GET endereço de um Aluno
    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<EnderecoDTO> buscarEnderecoPorAlunoId(@PathVariable Long alunoId) {
        Optional<EnderecoDTO> enderecoDTO = enderecoService.buscarEnderecoPorAlunoId(alunoId);
        return enderecoDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET endereço de um Professor
    @GetMapping("/professor/{professorCpf}")
    public ResponseEntity<EnderecoDTO> buscarEnderecoPorProfessorCpf(@PathVariable String professorCpf) {
        Optional<EnderecoDTO> enderecoDTO = enderecoService.buscarEnderecoPorProfessorCpf(professorCpf);
        return enderecoDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET endereço de um Coordenador
    @GetMapping("/coordenador/{coordenadorCpf}")
    public ResponseEntity<EnderecoDTO> buscarEnderecoPorCoordenadorCpf(@PathVariable String coordenadorCpf) {
        Optional<EnderecoDTO> enderecoDTO = enderecoService.buscarEnderecoPorCoordenadorCpf(coordenadorCpf);
        return enderecoDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET endereço de uma Coordenação
    @GetMapping("/coordenacao/{coordenacaoId}")
    public ResponseEntity<EnderecoDTO> buscarEnderecoPorCoordenacaoId(@PathVariable Long coordenacaoId) {
        Optional<EnderecoDTO> enderecoDTO = enderecoService.buscarEnderecoPorCoordenacaoId(coordenacaoId);
        return enderecoDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> criarEndereco(@RequestParam(value = "alunoId", required = false) Long alunoId,
                                                     @RequestParam(value = "professorCpf", required = false) String professorCpf,
                                                     @RequestParam(value = "coordenadorCpf", required = false) String coordenadorCpf,
                                                     @RequestParam(value = "coordenacaoId", required = false) Long coordenacaoId,
                                                     @RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO novoEndereco = enderecoService.salvarEndereco(alunoId, professorCpf, coordenadorCpf, coordenacaoId, enderecoDTO);
        return ResponseEntity.ok(novoEndereco);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarEnderecoPorId(@PathVariable Long id) {
        Optional<EnderecoDTO> enderecoDTO = enderecoService.buscarEnderecoPorId(id);
        return enderecoDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> listarTodosOsEnderecos() {
        List<EnderecoDTO> enderecos = enderecoService.listarEnderecos();
        return ResponseEntity.ok(enderecos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@PathVariable Long id,
                                                         @RequestParam(value = "alunoId", required = false) Long alunoId,
                                                         @RequestParam(value = "professorCpf", required = false) String professorCpf,
                                                         @RequestParam(value = "coordenadorCpf", required = false) String coordenadorCpf,
                                                         @RequestParam(value = "coordenacaoId", required = false) Long coordenacaoId,
                                                         @RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO enderecoAtualizado = enderecoService.atualizarEndereco(id, alunoId, professorCpf, coordenadorCpf, coordenacaoId, enderecoDTO);
        return ResponseEntity.ok(enderecoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
        enderecoService.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
*/