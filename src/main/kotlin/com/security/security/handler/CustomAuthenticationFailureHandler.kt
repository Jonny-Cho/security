package com.security.security.handler

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {

	private val requestCache = HttpSessionRequestCache()
	private val redirectStrategy = DefaultRedirectStrategy()

	override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
		var errorMessage = "Invalid Username or Password"

		if(exception is BadCredentialsException){
			errorMessage = "Invalid Username or Password"
		} else if (exception is InsufficientAuthenticationException){
			errorMessage = "Invalid Secret Key"
		}

		setDefaultFailureUrl("/login?error=true&exception=$errorMessage")

		super.onAuthenticationFailure(request, response, exception)
	}
}
