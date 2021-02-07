package com.security.security.token

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.SpringSecurityCoreVersion

class AjaxAuthenticationToken(private val principal: Any, private val credentials: Any?, authorities: List<GrantedAuthority>? = null) : AbstractAuthenticationToken(authorities) {

	private val serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID

	init{
		authorities?.also{
			super.setAuthenticated(true)
		}
	}

	override fun getCredentials(): Any? {
		return credentials
	}

	override fun getPrincipal(): Any {
		return principal
	}

}
