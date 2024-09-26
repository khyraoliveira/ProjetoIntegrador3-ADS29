package projeto.integrador3.senac.mediotec.pi3_mediotec.usuario;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * A classe abstrata Usuario serve como superclasse para outras entidades que herdam suas
 * propriedades e comportamentos. Ela contém atributos comuns a todas as entidades que
 * representam usuários no sistema, como nome, sobrenome, gênero, data de nascimento e email.
 *
 * A classe é anotada com @MappedSuperclass para indicar que seus atributos serão herdados
 * por outras classes, mas ela própria não será mapeada para uma tabela no banco de dados.
 */
@Data // Gera automaticamente getters, setters, equals, hashCode, toString, etc.
@NoArgsConstructor // Construtor sem parâmetros gerado automaticamente
@AllArgsConstructor // Construtor com todos os parâmetros gerado automaticamente
@Getter // Gera getters automaticamente para todos os atributos
@Setter // Gera setters automaticamente para todos os atributos
@SuperBuilder // Facilita a criação de objetos complexos utilizando o padrão Builder, mantendo a herança
@MappedSuperclass // Indica que esta classe será uma superclasse mapeada no JPA
public abstract class Usuario implements Serializable {

    // Serial Version UID para garantir a compatibilidade durante o processo de serialização
    private static final long serialVersionUID = 1L;

    // O nome do usuário é obrigatório e deve ter entre 3 e 50 caracteres
    @NotNull(message = "{usuario.nome.notnull}") // Valida que o campo não pode ser nulo
    @Size(min = 3, max = 50, message = "{usuario.nome.size}") // Define o tamanho mínimo e máximo
    @Column(nullable = false) // Especifica que a coluna 'nome' no banco de dados não pode ser nula
    private String nome;
    
    // O sobrenome do usuário é obrigatório e deve ter entre 3 e 50 caracteres
    @NotNull(message = "{usuario.ultimoNome.notnull}") // Valida que o campo não pode ser nulo
    @Size(min = 3, max = 50, message = "{usuario.ultimoNome.size}") // Define o tamanho mínimo e máximo
    @Column(nullable = false) // A coluna 'ultimoNome' no banco de dados também não pode ser nula
    private String ultimoNome;

    // O gênero do usuário é obrigatório (Ex: "Masculino", "Feminino", etc.)
    @NotNull(message = "{usuario.genero.notnull}") // O campo gênero não pode ser nulo
    @Column(nullable = false) // A coluna 'genero' não pode ser nula no banco de dados
    private String genero;

    // A data de nascimento do usuário é obrigatória e armazenada no formato de data
    @NotNull(message = "{usuario.data_nascimento.notnull}") // O campo data de nascimento não pode ser nulo
    @Temporal(TemporalType.DATE) // Define que o tipo de dado será tratado como uma data
    @Column(nullable = false) // A coluna 'data_nascimento' no banco de dados não pode ser nula
    private Date data_nascimento;

    // O email do usuário é obrigatório e deve estar em um formato válido
    @NotNull(message = "{usuario.email.notnull}") // O campo email não pode ser nulo
    @Email(message = "{usuario.email.email}") // Valida que o campo segue um formato de email válido
    @Column(nullable = false, unique = true) // A coluna 'email' no banco de dados também não pode ser nula
    private String email;
    
}
