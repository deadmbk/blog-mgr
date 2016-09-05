package pl.edu.agh.blog.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ImportResource({"/WEB-INF/acl-context.xml"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {

//		auth.ldapAuthentication()
//			.userDetailsContextMapper(userDetailsContextMapper())
//			.userDnPatterns("uid={0},ou=people")
//			.groupSearchBase("ou=groups")
//			.groupSearchFilter("(member={0})")
//			.contextSource()
//				.url("ldap://localhost:10389/dc=example,dc=com");
		
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("SELECT username, password, 1 FROM users WHERE username=?")
			.authoritiesByUsernameQuery("SELECT username, name FROM users JOIN roles ON roles.id = users.role_id WHERE username=?")
			.passwordEncoder(passwordEncoder());
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		 * users	- URL-based security (only role ADMIN allowed)
		 * articles - URL-based security and later method security to determine author
		 * comments - URL-based security and later method security to determine author
		 */
		
		http.authorizeRequests()
				.antMatchers("/user/register", "/login").not().authenticated()	// not working for /login*
				.antMatchers("/user/**").access("hasRole('ROLE_ADMIN')") // dostêp do userów ma tylko admin
				.antMatchers("/article/list", "/article/show/*").permitAll() // ka¿dy widzi listê artyku³ów i mo¿e je
				.antMatchers("/article/add", "/article/edit", "/article/edit/*", "/article/delete/*").access("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
				 // dostêp do edycji i usuwania ma tylko admin i edytor - przy edytorze nast¹pi póŸniej service-based authorization
				.antMatchers("/article/*/comment/**").authenticated()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/", "/index").permitAll()
				.anyRequest().denyAll()
				.and()
			.formLogin()
	            .loginPage("/login") .failureUrl("/login?error")
	            .permitAll()
	            .and()
        	.logout()
        		.logoutSuccessUrl("/?logout")
        		.permitAll()
        		.and()
    		.exceptionHandling()
    			.accessDeniedPage("/access-denied");
					
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Bean
	public UserDetailsContextMapper userDetailsContextMapper() {
		return new CustomUserDetailsContextMapper();
	}
}
