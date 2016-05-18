package ufc.quixada.npi.gpa.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"br.ufc.quixada.npi.ldap", "ufc.quixada.npi.gpa"})
@EntityScan(basePackages="ufc.quixada.npi.gpa.model")
public class GpaExtensaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpaExtensaoApplication.class, args);
	}
}
