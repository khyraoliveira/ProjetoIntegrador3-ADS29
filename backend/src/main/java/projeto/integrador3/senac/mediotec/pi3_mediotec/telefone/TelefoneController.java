package projeto.integrador3.senac.mediotec.pi3_mediotec.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/*
@RestController
@RequestMapping("/telefones")
public class TelefoneController {

    @Autowired
    private TelefoneService telefoneService;

    @PostMapping
    public ResponseEntity<TelefoneDTO> criarTelefone(
            @RequestParam(required = false) Long alunoId,
            @RequestParam(required = false) String professorCpf,
            @RequestParam(required = false) String coordenadorCpf,
            @RequestParam(required = false) Long coordenacaoId,
            @RequestBody TelefoneDTO telefoneDTO) {
        TelefoneDTO novoTelefone = telefoneService.salvarTelefone(alunoId, professorCpf, coordenadorCpf, coordenacaoId, telefoneDTO);
        return ResponseEntity.ok(novoTelefone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelefoneDTO> atualizarTelefone(
            @PathVariable Long id,
            @RequestParam(required = false) Long alunoId,
            @RequestParam(required = false) String professorCpf,
            @RequestParam(required = false) String coordenadorCpf,
            @RequestParam(required = false) Long coordenacaoId,
            @RequestBody TelefoneDTO telefoneDTO) {
        TelefoneDTO telefoneAtualizado = telefoneService.atualizarTelefone(id, alunoId, professorCpf, coordenadorCpf, coordenacaoId, telefoneDTO);
        return ResponseEntity.ok(telefoneAtualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelefoneDTO> buscarTelefonePorId(
            @PathVariable Long id,
            @RequestParam(required = false) Long alunoId,
            @RequestParam(required = false) String professorCpf,
            @RequestParam(required = false) String coordenadorCpf,
            @RequestParam(required = false) Long coordenacaoId) {

        Optional<TelefoneDTO> telefoneDTO = telefoneService.buscarTelefonePorId(id, alunoId, professorCpf, coordenadorCpf, coordenacaoId);
        return telefoneDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TelefoneDTO>> listarTelefones(
            @RequestParam(required = false) Long alunoId,
            @RequestParam(required = false) String professorCpf,
            @RequestParam(required = false) String coordenadorCpf,
            @RequestParam(required = false) Long coordenacaoId) {

        List<TelefoneDTO> telefones = telefoneService.listarTelefones(alunoId, professorCpf, coordenadorCpf, coordenacaoId);
        return ResponseEntity.ok(telefones);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTelefone(@PathVariable Long id) {
        telefoneService.deletarTelefone(id);
        return ResponseEntity.noContent().build();
    }
}
*/