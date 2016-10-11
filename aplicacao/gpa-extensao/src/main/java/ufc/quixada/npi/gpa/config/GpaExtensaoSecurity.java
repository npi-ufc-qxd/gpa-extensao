package ufc.quixada.npi.gpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan
public class GpaExtensaoSecurity extends WebSecurityConfigurerAdapter {
	
	// Utilizado para autenticação via banco de dados
	/*@Autowired
	private UserDetailsService userDetailsService;*/
	
	// Utilizado para autenticação via ldap
	@Autowired
	@Qualifier("authenticationProviderExtensao")
	private AuthenticationProvider provider;
	
	private String login = "/login";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").authenticated().antMatchers("/public-resources/**").permitAll()
				.antMatchers("/admin/**").hasAuthority("ADMINISTRACAO")
				.antMatchers("/direcao/**").hasAuthority("DIRECAO")
				.anyRequest().authenticated()
				.and().formLogin()
				.loginProcessingUrl(login).loginPage(login).permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl(login);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Utilizado para autenticação via ldap
		auth.authenticationProvider(provider);
		
		// Utilizado para autenticação via banco de dados
		//auth.userDetailsService(userDetailsService);
	}
}