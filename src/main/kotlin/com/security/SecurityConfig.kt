package com.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

	@Override
	override fun configure(http: HttpSecurity) {
		http.authorizeRequests()
			.anyRequest().authenticated()

		http
			.formLogin()
//			.loginPage("/loginPage")
			.defaultSuccessUrl("/success", true)
			.failureUrl("/fail")
			.usernameParameter("user")
			.passwordParameter("pw")
			.loginProcessingUrl("/login_proc")
			.permitAll()
	}

}
