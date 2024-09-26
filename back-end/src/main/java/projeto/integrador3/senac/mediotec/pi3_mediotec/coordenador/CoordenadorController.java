package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/coordenadores")
@Tag(name = "Coordenador", description = "Gerenciamento dos Coordenadores")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    // ================== LISTAR COORDENADORES ==================
    // Método GET para listar todos os coordenadores
    // O método retorna uma lista de coordenadores como DTOs
    @Operation(summary = "Listar todos os coordenadores", description = "Retorna uma lista de todos os coordenadores cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de coordenadores retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao listar coordenadores")
    })
    @GetMapping
    public ResponseEntity<List<CoordenadorDTO>> getAllCoordenadores() {
        try {
            // Chama o serviço para buscar todos os coordenadores
            List<CoordenadorDTO> coordenadores = coordenadorService.getAllCoordenadores();
            return new ResponseEntity<>(coordenadores, HttpStatus.OK);
        } catch (Exception e) {
            // Lança exceção em caso de erro no servidor
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar coordenadores", e);
        }
    }

    // ================== BUSCAR COORDENADOR POR ID ==================
    // Método GET para buscar um coordenador por CPF (ID)
    // O método retorna os detalhes do coordenador em formato DTO
    @Operation(summary = "Buscar coordenador por ID", description = "Retorna os detalhes de um coordenador específico com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Coordenador retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Coordenador não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao buscar coordenador")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<CoordenadorDTO> getCoordenadorById(@PathVariable String cpf) {
        try {
            // Chama o serviço para buscar o coordenador pelo ID
            Optional<CoordenadorDTO> coordenador = coordenadorService.getCoordenadorById(cpf);
            return coordenador.map(ResponseEntity::ok)
                              // Lança exceção caso o coordenador não seja encontrado
                              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenador não encontrado"));
        } catch (Exception e) {
            // Lança exceção em caso de erro no servidor
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar coordenador", e);
        }
    }

    // ================== CRIAR NOVO COORDENADOR ==================
    // Método POST para criar um novo coordenador
    // O método recebe um DTO do coordenador para criação
    @Operation(summary = "Criar novo coordenador", description = "Cria um novo coordenador com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Coordenador criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação ao criar o coordenador"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao criar coordenador")
    })
    @PostMapping
    public ResponseEntity<CoordenadorDTO> createCoordenador(@RequestBody CoordenadorDTO coordenadorDTO) {
        try {
            // Chama o serviço para salvar o coordenador
            CoordenadorDTO savedCoordenador = coordenadorService.saveCoordenador(coordenadorDTO);
            return new ResponseEntity<>(savedCoordenador, HttpStatus.CREATED);
        } catch (Exception e) {
            // Lança exceção caso haja erro ao criar o coordenador
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar coordenador", e);
        }
    }

    // ================== ATUALIZAR COORDENADOR EXISTENTE ==================
    // Método PUT para atualizar os dados de um coordenador existente
    // O método recebe um DTO atualizado do coordenador
    @Operation(summary = "Atualizar coordenador", description = "Atualiza os dados de um coordenador existente com base no ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Coordenador atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Coordenador não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao atualizar coordenador")
    })
    @PutMapping("/{cpf}")
    public ResponseEntity<CoordenadorDTO> updateCoordenador(@PathVariable String cpf, @RequestBody CoordenadorDTO coordenadorDTO) {
        try {
            // Chama o serviço para atualizar o coordenador
            CoordenadorDTO updatedCoordenador = coordenadorService.updateCoordenador(cpf, coordenadorDTO);
            return new ResponseEntity<>(updatedCoordenador, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Lança exceção caso o coordenador não seja encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenador não encontrado", e);
        }
    }

    // ================== DELETAR COORDENADOR ==================
    // Método DELETE para remover um coordenador por ID (CPF)
    @Operation(summary = "Deletar coordenador", description = "Deleta um coordenador existente com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Coordenador deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Coordenador não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao deletar coordenador")
    })
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteCoordenador(@PathVariable String cpf) {
        try {
            // Chama o serviço para deletar o coordenador
            coordenadorService.deleteCoordenador(cpf);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            // Lança exceção caso o coordenador não seja encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenador não encontrado", e);
        }
    }
}
