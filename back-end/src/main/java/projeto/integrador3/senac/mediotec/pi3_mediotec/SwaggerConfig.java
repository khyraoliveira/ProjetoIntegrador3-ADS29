package projeto.integrador3.senac.mediotec.pi3_mediotec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SGE - Sistema de Gerenciamento Escolar")
                        .version("1.0.0")
                        .description("Faculdade Senac Pernambuco - Projeto Integrador III")
                        .contact(new Contact()
                                .name("Rafael Vitor de Oliveira")
                                .url("https://www.linkedin.com/in/rafaelvitor2/")
                                .email("rafaelvd2@hotmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/rafaeldgdf/PI3-mediotec.git"));
    }
}
