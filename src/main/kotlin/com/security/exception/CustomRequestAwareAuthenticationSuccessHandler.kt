package com.security.exception

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomRequestAwareAuthenticationSuccessHandler : SavedRequestAwareAuthenticationSuccessHandler() {
	override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
		val requestCache = HttpSessionRequestCache()
		val savedRequest = requestCache.getRequest(request, response)
		println("saved redirectUrl = ${savedRequest.redirectUrl}")
		super.onAuthenticationSuccess(request, response, authentication)
	}
}
