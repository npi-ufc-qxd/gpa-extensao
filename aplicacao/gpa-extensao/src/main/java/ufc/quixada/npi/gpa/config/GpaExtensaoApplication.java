package ufc.quixada.npi.gpa.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ufc.quixada.npi.gpa.model.DocumentoEntityListener;

@SpringBootApplication
@ComponentScan({ "br.ufc.quixada.npi.ldap", "ufc.quixada.npi.gpa" })
@EntityScan(basePackages = "ufc.quixada.npi.gpa.model")
@EnableJpaRepositories("ufc.quixada.npi.gpa.repository")
public class GpaExtensaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpaExtensaoApplication.class, args);
	}

	@Bean
	public DocumentoEntityListener getDocumentoEntityListener() {
		return new DocumentoEntityListener();
	}
}
