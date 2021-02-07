package com.security.security.configs

import com.security.security.common.AjaxLoginAuthenticationEntryPoint
import com.security.security.common.FormAuthenticationDetailsSource
import com.security.security.filter.AjaxLoginProcessingFilter
import com.security.security.handler.AjaxAccessDeniedHandler
import com.security.security.handler.AjaxAuthenticationFailureHandler
import com.security.security.handler.AjaxAuthenticationSuccessHandler
import com.security.security.provider.AjaxAuthenticationProvider
import com.security.security.service.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@Order(0)
class AjaxSecurityConfig(val userDetailsService: CustomUserDetailsService, val authenticationDetailsSource: FormAuthenticationDetailsSource) : WebSecurityConfigurerAdapter() {

	@Bean
	fun ajaxAuthenticationProvider() = AjaxAuthenticationProvider(userDetailsService)

	@Bean
	fun ajaxAuthenticationSuccessHandler() = AjaxAuthenticationSuccessHandler()

	@Bean
	fun ajaxAuthenticationFailureHandler() = AjaxAuthenticationFailureHandler()

	@Bean
	fun ajaxLoginProcessingFilter() : AjaxLoginProcessingFilter {
		val ajaxLoginProcessingFilter = AjaxLoginProcessingFilter()
		ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean())
		ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler())
		ajaxLoginProcessingFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler())
		return ajaxLoginProcessingFilter
	}

	@Bean
	fun ajaxAccessDeniedHandler() = AjaxAccessDeniedHandler()

	override fun configure(auth: AuthenticationManagerBuilder) {
		auth.authenticationProvider(ajaxAuthenticationProvider())
	}

	override fun configure(http: HttpSecurity) {
		http
			.antMatcher("/api/**")
			.authorizeRequests()
			.antMatchers("/api/messages").hasRole("MANAGER")
			.anyRequest().authenticated()
		.and()
			.addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter::class.java)

		http
			.exceptionHandling()
			.authenticationEntryPoint(AjaxLoginAuthenticationEntryPoint())
			.accessDeniedHandler(ajaxAccessDeniedHandler())

		http.csrf().disable()
	}
}
