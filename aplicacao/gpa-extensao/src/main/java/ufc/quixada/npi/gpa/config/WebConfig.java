package ufc.quixada.npi.gpa.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import ufc.quixada.npi.gpa.generation.pdf.PdfViewResolver;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
	
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer
		.defaultContentType(MediaType.APPLICATION_JSON)
		.favorPathExtension(true);
		
	}
	
	/*
	 * Configuração do ContentNegotiatingViewResolver
	 */
	
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager){
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		
		//Definindo todos os possíveis view resolvers
		
		List<ViewResolver> resolvers = new ArrayList<>();
		
		resolvers.add(pdfViewResolver());
		
		resolver.setViewResolvers(resolvers);
		
		return resolver;
	}
	
	@Bean
	public ViewResolver pdfViewResolver(){
		return new PdfViewResolver();
	}
	

}
