package uni.umons.ratingwebapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import uni.umons.ratingwebapp.security.LoggingAccessDeniedHandler;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.EnumSet;


@Configuration
@ComponentScan("uni.umons.ratingwebapp.security")
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService customUserDetailsService;

	@Autowired
	@Qualifier("accessDeniedHandler")
	private LoggingAccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		// static resources
		httpSecurity.authorizeRequests()
		.antMatchers("/css/**", "/js/**", "/images/**", "/resources/**", "/webjars/**").permitAll();

		httpSecurity.authorizeRequests()
						.antMatchers("/signin").anonymous()
						.anyRequest()
						.authenticated()
					.and()
						.logout()
						.clearAuthentication(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/signin?logout")
					.and()
						.formLogin()
						.loginPage("/signin")
						.loginProcessingUrl("/sign-in-process.html")
						.failureUrl("/signin?error")
						.usernameParameter("username")
						.passwordParameter("password")
						.defaultSuccessUrl("/home", false)

					.and()
						.rememberMe()
						.rememberMeServices(rememberMeServices())
						.tokenRepository(persistentTokenRepository())
						.key("AppKey")
						.alwaysRemember(true)
						.rememberMeParameter("remember-me")
						.tokenValiditySeconds(24 * 60 * 60)
					.and()
						.csrf().disable()
						.headers().frameOptions().disable();


		httpSecurity.exceptionHandling().accessDeniedPage("/error/401");
		httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		httpSecurity.sessionManagement().invalidSessionUrl("/signin");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService);
	}
	
	@Bean
	public FilterRegistrationBean getSpringSecurityFilterChainBindedToError(
	                @Qualifier("springSecurityFilterChain") Filter springSecurityFilterChain) {

	        FilterRegistrationBean registration = new FilterRegistrationBean();
	        registration.setFilter(springSecurityFilterChain);
	        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
	        return registration;
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Bean
	public AbstractRememberMeServices rememberMeServices() {
		PersistentTokenBasedRememberMeServices rememberMeServices =
				new PersistentTokenBasedRememberMeServices("posc",customUserDetailsService,persistentTokenRepository());
		rememberMeServices.setAlwaysRemember(true);
		return rememberMeServices;
	}
}
