package com.security.security

import org.springframework.boot.autoconfigure.security.StaticResourceLocation
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories

@Configuration
@EnableWebSecurity
class SecurityConfig(val userDetailsService: UserDetailsService) : WebSecurityConfigurerAdapter() {

	override fun configure(web: WebSecurity) {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
	}

	override fun configure(auth: AuthenticationManagerBuilder) {
//		val password = "{noop}1234"
		val password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("1234")

		println("password $password")

		auth.inMemoryAuthentication()
			.withUser("user").password(password).roles("USER")
		auth.inMemoryAuthentication()
			.withUser("manager").password(password).roles("USER", "MANAGER")
		auth.inMemoryAuthentication()
			.withUser("admin").password(password).roles("USER", "MANAGER", "ADMIN")
	}

	override fun configure(http: HttpSecurity) {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/mypage").hasRole("USER")
			.antMatchers("/messages").hasRole("MANAGER")
			.antMatchers("/config").hasRole("ADMIN")
			.anyRequest().authenticated()

		.and().formLogin()
			.defaultSuccessUrl("/")

//		http.rememberMe()
//			.rememberMeParameter("remember")
//			.tokenValiditySeconds(3600)
//			//			.alwaysRemember(true)
//			.userDetailsService(userDetailsService)
//
//		http.sessionManagement()
//			.maximumSessions(1) // -1 -> 무제한 로그인 세션 허용
//			.maxSessionsPreventsLogin(false) // 동시 로그인 차단 default: false -> 기존 세션 만료 / true -> 기존 세션 유지 새 로그인 실패
//			.expiredUrl("/expired")
//			.and()
//			.sessionFixation()
//			.changeSessionId()
//			.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 기본값 IF_REQUIRED. JWT등을 사용할때는 Stateless로 설정
//
//		http.exceptionHandling()
//			.authenticationEntryPoint { request, response, authException -> response.sendRedirect("/customLogin") }
//			.accessDeniedHandler { request, response, accessDeniedException -> response.sendRedirect("/denied") }
	}

}
