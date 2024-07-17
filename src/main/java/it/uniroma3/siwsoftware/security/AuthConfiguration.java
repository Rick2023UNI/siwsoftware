package it.uniroma3.siwsoftware.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfiguration implements WebMvcConfigurer {

	private static final String ADMIN_ROLE = "admin";

	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.authoritiesByUsernameQuery("SELECT username, ruolo from utente WHERE username=?")
				.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM utente WHERE username=?");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().and().cors().disable().authorizeHttpRequests()
				// .requestMatchers("/**").permitAll()
				// chiunque (autenticato o no) può accedere alle pagine index, login, register,
				// ai css e alle immagini
				.requestMatchers(HttpMethod.GET, "/", "/index", "/register", "/css/**", "/js/**", "/images/**",
						"/favicon.ico", "/software/**", "/image/logo.png", "/softwareHouse/**", "/image/favicon.ico")
				.permitAll()
				// chiunque (autenticato o no) può mandare richieste POST al punto di accesso
				// per login e register
				.requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()
				.requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
				.requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
				// tutti gli utenti autenticati possono accedere alle pagine rimanenti
				.anyRequest().authenticated()
				// LOGIN: qui definiamo il login
				.and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/success", true)
				// .failureUrl("/login?error=true")
				.failureUrl("/login-error")
				// LOGOUT: qui definiamo il logout
				.and().logout()
				// il logout è attivato con una richiesta GET a "/logout"
				.logoutUrl("/logout")
				// in caso di successo, si viene reindirizzati alla home
				.logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).clearAuthentication(true).permitAll();
		return httpSecurity.build();
	}

}
