package com.security.security.provider

import com.security.security.service.AccountContext
import com.security.security.service.CustomUserDetailsService
import com.security.security.token.AjaxAuthenticationToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional

open class AjaxAuthenticationProvider(private val userDetailsService: CustomUserDetailsService) : AuthenticationProvider{

	@Autowired
	lateinit var passwordEncoder: PasswordEncoder

	@Transactional
	override fun authenticate(authentication: Authentication): Authentication {
		val username = authentication.name
		val password = authentication.credentials.toString()

		val accountContext = userDetailsService.loadUserByUsername(username) as AccountContext

		if(!passwordEncoder.matches(password, accountContext.password)){
			throw BadCredentialsException("password가 일치하지 않습니다.")
		}

		return AjaxAuthenticationToken(accountContext.account, null, accountContext.authorities.toList())
	}

	override fun supports(authentication: Class<*>): Boolean {
		return authentication == AjaxAuthenticationToken::class.java
	}
}
