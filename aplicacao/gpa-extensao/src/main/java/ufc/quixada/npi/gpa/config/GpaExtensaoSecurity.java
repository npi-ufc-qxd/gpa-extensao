package ufc.quixada.npi.gpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
	@Autowired
	private UserDetailsService userDetailsService;
	
//	@Autowired
//	@Qualifier("authenticationProviderExtensao")
//	private AuthenticationProvider provider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").authenticated()
			.antMatchers("/public-resources/**").permitAll()
			.antMatchers("/direcao/**").hasAuthority("DIRECAO").anyRequest().authenticated()
			.antMatchers("/admin/**").hasAuthority("ADMINISTRACAO").anyRequest().authenticated()
			.and()
				.formLogin().loginProcessingUrl("/login").loginPage("/login").permitAll()
			.and()
	            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(provider);
		auth.userDetailsService(userDetailsService);
	}
}