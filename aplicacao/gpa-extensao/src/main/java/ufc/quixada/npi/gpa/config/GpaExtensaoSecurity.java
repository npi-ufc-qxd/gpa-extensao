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
@Configuration
@EnableWebSecurity
@ComponentScan
public class GpaExtensaoSecurity extends WebSecurityConfigurerAdapter {
//	@Autowired
//	private UserDetailsService userDetailsService;
	@Autowired
	@Qualifier("authenticationProviderExtensao")
	private AuthenticationProvider provider;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/direcao/**").hasAuthority("DIRECAO").anyRequest().authenticated()
			.antMatchers("/public/**").permitAll().anyRequest().permitAll()
			.and()
				.formLogin().loginProcessingUrl("/login").loginPage("/login").permitAll()
			.and()
	            .logout().logoutUrl("/logout").permitAll();
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
	}
}