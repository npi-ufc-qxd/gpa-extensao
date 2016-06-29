package ufc.quixada.npi.gpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("authenticationProviderExtensao")
	private AuthenticationProvider provider;
	
//	@Autowired
//	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			//.antMatchers("/direcao/**").hasAuthority("DIRECAO")
			//.antMatchers("/resources/**").permitAll()
			.anyRequest().authenticated()//.fullyAuthenticated()
			.and()
				.formLogin().loginPage("/login").permitAll()
			.and()
				.logout().permitAll();
	            //.logout().logoutUrl("/logout").permitAll();
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
//		auth.userDetailsService(userDetailsService);
	}

}
