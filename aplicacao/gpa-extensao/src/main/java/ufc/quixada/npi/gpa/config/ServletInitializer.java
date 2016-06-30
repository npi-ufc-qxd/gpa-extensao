package ufc.quixada.npi.gpa.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;

@EnableAutoConfiguration
@ComponentScan
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GpaExtensaoApplication.class);
	}
	@Bean
	 public MultipartConfigElement multipartConfigElement() {
	     return new MultipartConfigElement("");
	 }

	 @Bean
	 public MultipartResolver multipartResolver() {
	     org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
	     multipartResolver.setMaxUploadSize(1000000);
	     return multipartResolver;
	 }
}
