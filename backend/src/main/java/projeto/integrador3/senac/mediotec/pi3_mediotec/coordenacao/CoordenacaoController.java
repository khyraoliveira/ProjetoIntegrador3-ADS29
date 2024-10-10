package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/coordenacoes")
@Tag(name = "Coordenação", description = "Gerenciamento das Coordenações")  // Configuração para documentação do Swagger
public class CoordenacaoController {

    @Autowired
    private CoordenacaoService coordenacaoService;

    // ============================= MÉTODOS HTTP =============================

    // Lista todas as coordenações
    @Operation(summary = "Listar todas as coordenações", description = "Retorna uma lista de todas as coordenações cadastradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de coordenações retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao listar as coordenações")
    })
    @GetMapping
    public ResponseEntity<List<CoordenacaoDTO>> getAllCoordenacoes() {
        try {
            List<CoordenacaoDTO> coordenacoes = coordenacaoService.getAllCoordenacoes(); // Busca todas as coordenações
            return new ResponseEntity<>(coordenacoes, HttpStatus.OK);  // Retorna lista com status 200
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar coordenações", e);  // Lança exceção em caso de erro
        }
    }

    // Busca uma coordenação por ID
    @Operation(summary = "Buscar coordenação por ID", description = "Retorna os detalhes de uma coordenação específica com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Coordenação retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Coordenação não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao buscar coordenação")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CoordenacaoDTO> getCoordenacaoById(@PathVariable Long id) {
        try {
            Optional<CoordenacaoDTO> coordenacao = coordenacaoService.getCoordenacaoById(id);  // Busca coordenação pelo ID
            return coordenacao.map(ResponseEntity::ok)
                              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenação não encontrada"));  // Lança erro 404 se não for encontrada
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar coordenação", e);  // Lança erro 500 em caso de falha no servidor
        }
    }

    // Cria uma nova coordenação
    @Operation(summary = "Criar nova coordenação", description = "Cria uma nova coordenação com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Coordenação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação ao criar a coordenação"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao criar coordenação")
    })
    @PostMapping
    public ResponseEntity<CoordenacaoDTO> createCoordenacao(@RequestBody CoordenacaoCadastroDTO coordenacaoCadastroDTO) {
        try {
            CoordenacaoDTO savedCoordenacao = coordenacaoService.saveCoordenacao(coordenacaoCadastroDTO);  // Salva a nova coordenação
            return new ResponseEntity<>(savedCoordenacao, HttpStatus.CREATED);  // Retorna status 201 após criar
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar coordenação", e);  // Lança erro 400 para falha de validação
        }
    }

    // Atualiza uma coordenação existente
    @Operation(summary = "Atualizar coordenação", description = "Atualiza os dados de uma coordenação existente com base no ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Coordenação atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Coordenação não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao atualizar coordenação")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CoordenacaoDTO> updateCoordenacao(@PathVariable Long id, @RequestBody CoordenacaoCadastroDTO coordenacaoCadastroDTO) {
        try {
            CoordenacaoDTO updatedCoordenacao = coordenacaoService.updateCoordenacao(id, coordenacaoCadastroDTO);  // Atualiza a coordenação existente
            return new ResponseEntity<>(updatedCoordenacao, HttpStatus.OK);  // Retorna status 200 após sucesso
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenação não encontrada", e);  // Lança erro 404 se não encontrada
        }
    }

    // Deleta uma coordenação por ID
    @Operation(summary = "Deletar coordenação", description = "Deleta uma coordenação existente com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Coordenação deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Coordenação não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao deletar coordenação")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordenacao(@PathVariable Long id) {
        try {
            coordenacaoService.deleteCoordenacao(id);  // Deleta a coordenação pelo ID
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Retorna status 204 após deletar
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenação não encontrada", e);  // Lança erro 404 se não encontrada
        }
    }
}
