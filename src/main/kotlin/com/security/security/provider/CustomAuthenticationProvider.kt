package com.security.security.provider

import com.security.security.service.AccountContext
import com.security.security.service.CustomUserDetailsService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder

class CustomAuthenticationProvider(private val userDetailsService: CustomUserDetailsService, private val passwordEncoder: PasswordEncoder) : AuthenticationProvider {

	override fun authenticate(authentication: Authentication): Authentication {
		val username = authentication.name
		val password = authentication.credentials.toString()

		val accountContext = userDetailsService.loadUserByUsername(username) as AccountContext

		if(!passwordEncoder.matches(password, accountContext.password)){
			throw BadCredentialsException("password가 일치하지 않습니다.")
		}

		return UsernamePasswordAuthenticationToken(accountContext.account, null, accountContext.authorities)
	}

	override fun supports(authentication: Class<*>): Boolean {
		return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
	}
}
