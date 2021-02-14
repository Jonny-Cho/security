package com.security.security.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.security.domain.AccountDto
import com.security.security.token.AjaxAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AjaxLoginProcessingFilter : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/api/login")) {

	val objectMapper = jacksonObjectMapper()

	override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
		if(isNotAjax(request)){
			throw IllegalStateException("Authentication is not supported")
		}

		val reader = request.reader
		val accountDto = this.objectMapper.readValue(reader, AccountDto::class.java)
		println("accountDto== $accountDto")
		if(accountDto.username.isEmpty() || accountDto.password.isEmpty()){
			throw IllegalArgumentException("Username or Password is empty")
		}

		val ajaxAuthenticationToken = AjaxAuthenticationToken(accountDto.username, accountDto.password)

		return authenticationManager.authenticate(ajaxAuthenticationToken)
	}

	private fun isNotAjax(request: HttpServletRequest): Boolean {
		return request.getHeader("X-Requested-With") != "XMLHttpRequest"
	}
}
