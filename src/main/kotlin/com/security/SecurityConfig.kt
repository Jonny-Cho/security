package com.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
@EnableWebSecurity
class SecurityConfig(val userDetailsService:UserDetailsService) : WebSecurityConfigurerAdapter() {

	@Override
	override fun configure(http: HttpSecurity) {
		http
			.authorizeRequests()
			.anyRequest().authenticated()

		http
			.formLogin()
//			.loginPage("/loginPage")
			.defaultSuccessUrl("/success")
			.failureUrl("/fail")
			.usernameParameter("user")
			.passwordParameter("pw")
			.loginProcessingUrl("/login_proc")
			.permitAll()

		http
			.rememberMe()
			.rememberMeParameter("remember")
			.tokenValiditySeconds(3600)
//			.alwaysRemember(true)
			.userDetailsService(userDetailsService)
	}

}
