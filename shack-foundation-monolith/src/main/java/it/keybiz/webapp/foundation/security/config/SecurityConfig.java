package it.keybiz.webapp.foundation.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import it.keybiz.webapp.foundation.security.users.CustomUserDetailsService;

@Configuration
@ComponentScan(basePackages = { "it.keybiz.webapp.foundation.security" })
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(customUserDetailsService);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .authorizeRequests()
            .antMatchers("/login*", "/resources/**").permitAll()
            .antMatchers("/application","/application/*").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
            .antMatchers("/users","/users/*").hasAuthority("ROLE_ADMIN")
            .and()
        .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/application")
            .failureUrl("/login?error=true")
//            .successHandler(myAuthenticationSuccessHandler)
//            .failureHandler(authenticationFailureHandler)
//            .authenticationDetailsSource(authenticationDetailsSource)
        .permitAll()
        .and()
        .logout()
//            .logoutSuccessHandler(myLogoutSuccessHandler)
            .invalidateHttpSession(false)
            .logoutSuccessUrl("/logout.html?logSucc=true")
            .deleteCookies("JSESSIONID")
            .permitAll();
    }
	

}
