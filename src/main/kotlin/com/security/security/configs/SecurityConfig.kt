package com.security.security.configs

import com.security.security.common.FormAuthenticationDetailsSource
import com.security.security.provider.CustomAuthenticationProvider
import com.security.security.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

@Configuration
@EnableWebSecurity
class SecurityConfig(val userDetailsService: CustomUserDetailsService, val authenticationDetailsSource: FormAuthenticationDetailsSource) : WebSecurityConfigurerAdapter() {

	@Autowired
	lateinit var customAuthenticationSuccessHandler: AuthenticationSuccessHandler

	@Bean
	fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

	@Bean
	fun authenticationProvider(userDetailsService: CustomUserDetailsService, passwordEncoder: PasswordEncoder): AuthenticationProvider {
		return CustomAuthenticationProvider(userDetailsService, passwordEncoder)
	}

	override fun configure(auth: AuthenticationManagerBuilder) {
		auth.authenticationProvider(authenticationProvider(userDetailsService, passwordEncoder()))
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
		.and()
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login_proc")
			.authenticationDetailsSource(authenticationDetailsSource)
			.defaultSuccessUrl("/")
			.successHandler(customAuthenticationSuccessHandler)
			.permitAll()

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
