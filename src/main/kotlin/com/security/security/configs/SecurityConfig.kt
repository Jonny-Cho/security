package com.security.security.configs

import org.springframework.boot.autoconfigure.security.StaticResourceLocation
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(val userDetailsService:UserDetailsService) : WebSecurityConfigurerAdapter() {

	@Bean
	fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

	override fun configure(auth: AuthenticationManagerBuilder) {
		auth.userDetailsService(userDetailsService)
	}

	override fun configure(web: WebSecurity) {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
	}

	override fun configure(http: HttpSecurity) {
		http.authorizeRequests()
			.antMatchers("/", "/users").permitAll()
			.antMatchers("/mypage").hasRole("USER")
			.antMatchers("/messages").hasRole("MANAGER")
			.antMatchers("/config").hasRole("ADMIN")
			.anyRequest().authenticated()

		.and().formLogin()
			.defaultSuccessUrl("/mypage")

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
