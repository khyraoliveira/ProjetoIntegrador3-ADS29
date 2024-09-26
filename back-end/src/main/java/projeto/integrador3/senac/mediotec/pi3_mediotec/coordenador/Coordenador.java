package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.usuario.Usuario;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coordenador")
public class Coordenador extends Usuario {
    
    // ========================== CONSTANTES ==========================
    
    private static final long serialVersionUID = 1L;

    // ============================= CAMPOS =============================
    
    // CPF como chave primária (herdada de Usuario)
    @Id
    @NotNull(message = "{usuario.cpf.notnull}")
    @Size(min = 1, max = 50, message = "{usuario.cpf.size}")
    @Column(nullable = false, unique = true)
    private String cpf;

    // Status do coordenador (ativo/inativo)
    @Column
    private boolean status;

    // Relação One-to-Many com Endereços
    @Builder.Default
    @OneToMany(mappedBy = "coordenador", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Endereco> enderecos = new HashSet<>();

    // Relação One-to-Many com Telefones
    @Builder.Default
    @OneToMany(mappedBy = "coordenador", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Telefone> telefones = new HashSet<>();

    // Relação Many-to-One com Coordenacao
    @ManyToOne
    @JoinColumn(name = "id_coordenacao") // Chave estrangeira para a tabela coordenacao
    private Coordenacao coordenacao;

    // ======================== MÉTODOS AUXILIARES =======================
    
    // Adiciona um Endereço ao Coordenador e configura a relação bidirecional
    public void addEndereco(Endereco endereco) {
        endereco.setCoordenador(this); // Configura o coordenador no endereço
        this.enderecos.add(endereco);
    }

    // Adiciona um Telefone ao Coordenador e configura a relação bidirecional
    public void addTelefone(Telefone telefone) {
        telefone.setCoordenador(this); // Configura o coordenador no telefone
        this.telefones.add(telefone);
    }
}
