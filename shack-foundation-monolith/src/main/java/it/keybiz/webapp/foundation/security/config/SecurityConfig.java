package it.keybiz.webapp.foundation.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import it.keybiz.webapp.foundation.model.Authority;
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
            .antMatchers("/application","/application/*").hasAnyAuthority(Authority.ROLE_USER.toString(), Authority.ROLE_ADMIN.toString())
            .antMatchers("/users","/users/*").hasAuthority(Authority.ROLE_ADMIN.toString())
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
