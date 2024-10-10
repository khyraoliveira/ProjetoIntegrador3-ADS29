package projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenador.Coordenador;
import projeto.integrador3.senac.mediotec.pi3_mediotec.endereco.Endereco;
import projeto.integrador3.senac.mediotec.pi3_mediotec.professor.Professor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.telefone.Telefone;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turma.Turma;

@Data
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coordenacao")
public class Coordenacao implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // ============================= ATRIBUTOS =============================

    // Chave primária para a entidade Coordenacao
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coordenacao")
    private Long id;

    // Nome da coordenação - Obrigatório com restrição de tamanho
    @NotNull(message = "{coordenacao.nome.notnull}")
    @Size(min = 3, max = 50, message = "{coordenacao.nome.size}")
    @Column(nullable = false)
    private String nome;

    // Descrição adicional da coordenação - Não obrigatória, com limite de caracteres
    @Size(max = 100)
    @Column
    private String descricao;

    // ============================= RELACIONAMENTOS =============================

    // Relação com Endereços - Uma Coordenação pode ter vários endereços
    @Builder.Default
    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Endereco> enderecos = new HashSet<>();

    // Relação com Telefones - Uma Coordenação pode ter vários telefones
    @Builder.Default
    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Telefone> telefones = new HashSet<>();

    // Relação com Coordenadores - Uma Coordenação pode ter vários coordenadores
    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private Set<Coordenador> coordenadores;

    // Relação com Turmas - Uma Coordenação pode ter várias turmas
    @JsonIgnore
    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Turma> turmas;

    // Relação com Professores - Uma Coordenação pode ter vários professores
    @JsonIgnore
    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Professor> professores;

    // ============================= MÉTODOS AUXILIARES =============================

    // Adiciona um Endereço e configura a relação bidirecional
    public void addEndereco(Endereco endereco) {
        endereco.setCoordenacao(this); 
        this.enderecos.add(endereco);
    }

    // Adiciona um Telefone e configura a relação bidirecional
    public void addTelefone(Telefone telefone) {
        telefone.setCoordenacao(this); 
        this.telefones.add(telefone);
    }

    // Adiciona uma Turma e configura a relação bidirecional
    public void addTurma(Turma turma) {
        turma.setCoordenacao(this);
        this.turmas.add(turma);
    }

    // Adiciona um Professor e configura a relação bidirecional
    public void addProfessor(Professor professor) {
        professor.setCoordenacao(this);
        this.professores.add(professor);
    }

    // Adiciona um Coordenador e configura a relação bidirecional
    public void addCoordenador(Coordenador coordenador) {
        coordenador.setCoordenacao(this);
        this.coordenadores.add(coordenador);
    }

    // ============================= MÉTODOS EQUALS E HASHCODE =============================

    @Override
    public int hashCode() {
        return Objects.hash(id); // Gera o hash baseado no ID
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordenacao that = (Coordenacao) o;
        return Objects.equals(id, that.id); // Compara as entidades pelo ID
    }
}
