package com.security.security.handler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AjaxAuthenticationFailureHandler : AuthenticationFailureHandler{

	val objectMapper = jacksonObjectMapper()

	override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
		var errorMessage = "Invalid Username or Password"

		response.status = HttpStatus.UNAUTHORIZED.value()
		response.contentType = MediaType.APPLICATION_JSON_VALUE

		if(exception is BadCredentialsException){
			errorMessage = "Invalid Username or Password"
		} else if (exception is DisabledException){
			errorMessage = "Locked"
		} else if (exception is CredentialsExpiredException){
			errorMessage = "Expired password"
		}

		objectMapper.writeValue(response.writer, errorMessage)
	}
}
