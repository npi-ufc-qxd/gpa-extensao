package ufc.quixada.npi.gpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import ufc.quixada.npi.gpa.model.DocumentoEntityListener;

@SpringBootApplication
@ComponentScan({ "br.ufc.quixada.npi.ldap", "ufc.quixada.npi.gpa" })
@EnableScheduling
public class GpaExtensaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpaExtensaoApplication.class, args);
	}

	@Bean
	public DocumentoEntityListener getDocumentoEntityListener() {
		return new DocumentoEntityListener();
	}
}
