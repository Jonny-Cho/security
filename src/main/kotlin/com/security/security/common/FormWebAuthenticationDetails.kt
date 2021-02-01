package com.security.security.common

import org.springframework.security.web.authentication.WebAuthenticationDetails
import javax.servlet.http.HttpServletRequest

class FormWebAuthenticationDetails(request:HttpServletRequest) : WebAuthenticationDetails(request) {
	val secretkey = request.getParameter("secret_key")
}
