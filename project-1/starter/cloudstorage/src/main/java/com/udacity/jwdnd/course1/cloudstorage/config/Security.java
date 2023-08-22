package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter{

	@Autowired
	private AuthService authService;

	@Override
	protected void configure(AuthenticationManagerBuilder authManager) throws Exception{
		authManager.authenticationProvider(this.authService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests(requests -> requests.antMatchers("/signup", "/css/**", "/js/&&").permitAll().anyRequest().authenticated());

        http.formLogin(login -> login.loginPage("/login").permitAll());

        http.formLogin(login -> login.defaultSuccessUrl("/home", true));

        http.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout=1"));
	}
}
