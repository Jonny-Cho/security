package com.security.security.handler

import org.springframework.security.core.Authentication
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.SavedRequest
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationSuccessHandler : SimpleUrlAuthenticationSuccessHandler() {

	private val requestCache = HttpSessionRequestCache()
	private val redirectStrategy = DefaultRedirectStrategy()

	override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
		defaultTargetUrl = "/"

		val savedRequest : SavedRequest? = requestCache.getRequest(request,response)
		savedRequest?.also{
			val targetUrl = it.redirectUrl
			redirectStrategy.sendRedirect(request,response,targetUrl)
			super.onAuthenticationSuccess(request, response, authentication)
		} ?: also{
			redirectStrategy.sendRedirect(request,response,defaultTargetUrl)
		}
	}
}
