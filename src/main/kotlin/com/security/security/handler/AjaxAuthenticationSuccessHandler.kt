package com.security.security.handler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.security.domain.Account
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AjaxAuthenticationSuccessHandler : AuthenticationSuccessHandler{

	val objectMapper = jacksonObjectMapper()

	override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
		val account = authentication.principal as Account
		response.status = HttpStatus.OK.value()
		response.contentType = MediaType.APPLICATION_JSON_VALUE
		objectMapper.writeValue(response.writer, account)
	}
}
