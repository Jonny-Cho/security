package com.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.savedrequest.HttpSessionRequestCache

@Configuration
@EnableWebSecurity
class SecurityConfig(val userDetailsService: UserDetailsService) : WebSecurityConfigurerAdapter() {

	override fun configure(auth: AuthenticationManagerBuilder) {
		auth.inMemoryAuthentication()
			.withUser("user")
			.password("{noop}1234")
			.roles("USER")
		auth.inMemoryAuthentication()
			.withUser("sys")
			.password("{noop}1234")
			.roles("SYS", "USER")
		auth.inMemoryAuthentication()
			.withUser("admin")
			.password("{noop}1234")
			.roles("ADMIN", "USER")
	}

	override fun configure(http: HttpSecurity) {
		http.authorizeRequests()
			.antMatchers("/customLogin")
			.permitAll()
			.antMatchers("/user")
			.hasRole("USER")
			.antMatchers("/admin/pay")
			.hasRole("ADMIN")
			.antMatchers("/admin/**")
			.access("hasRole('ADMIN') or hasRole('SYS')")
			.anyRequest()
			.authenticated()

		http.formLogin()
			.successHandler { request, response, authentication ->
				val requestCache = HttpSessionRequestCache()
				val savedRequest = requestCache.getRequest(request, response)
				response.sendRedirect(savedRequest.redirectUrl)
			} // 로그인 실패했을 때 어디로 가려고 했는지 세션에서 알아와서 로그인 성공했을 때 다시 보내기

		http.rememberMe()
			.rememberMeParameter("remember")
			.tokenValiditySeconds(3600)
			//			.alwaysRemember(true)
			.userDetailsService(userDetailsService)

		http.sessionManagement()
			.maximumSessions(1) // -1 -> 무제한 로그인 세션 허용
			.maxSessionsPreventsLogin(false) // 동시 로그인 차단 default: false -> 기존 세션 만료 / true -> 기존 세션 유지 새 로그인 실패
			.expiredUrl("/expired")
			.and()
			.sessionFixation()
			.changeSessionId()
			.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 기본값 IF_REQUIRED. JWT등을 사용할때는 Stateless로 설정

		http.exceptionHandling()
//			.authenticationEntryPoint { request, response, authException -> response.sendRedirect("/customLogin") }
			.accessDeniedHandler { request, response, accessDeniedException -> response.sendRedirect("/denied") }
	}

}
