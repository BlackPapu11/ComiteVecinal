package mx.edu.utez.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SimpleAuthenticationSuccessHandler successHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT correo, contrasena, habilitado FROM users WHERE correo = ?")
				.authoritiesByUsernameQuery("SELECT u.correo, r.authority FROM user_role AS ur "
						+ "INNER JOIN users AS u ON u.id = ur.user_id "
						+ "INNER JOIN roles AS r ON r.id = ur.role_id WHERE u.correo  = ?");
	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers(
				// Los recursos estaticos no requieren autenticacion
				"/css/**", "/js/**", "/image/**", "/imagenes/**").permitAll()
				// Las URL publicas no requieren autenticacion
				.antMatchers("/", "/signup", "/encriptar/**").permitAll()

				// Asignar permisos a las URL de acuerdo a los roles
				.antMatchers("/enlaces/**","/administrador/**").hasAnyAuthority("ROL_ADMINISTRADOR")
				.antMatchers("/colonias/**","/enlace/**").hasAnyAuthority("ROL_ENLACE")
				.antMatchers("/presidente/**").hasAnyAuthority("ROL_PRESIDENTE")

				// Las demas URL requieren autenticacion
				.anyRequest().authenticated()

				// Formulario de inicio de sesion no requiere autenticacion
				.and().formLogin().successHandler(successHandler).loginPage("/login").permitAll();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}