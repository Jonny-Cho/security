package com.security.security.provider

import com.security.security.common.FormWebAuthenticationDetails
import com.security.security.service.AccountContext
import com.security.security.service.CustomUserDetailsService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

open class FormAuthenticationProvider(private val userDetailsService: CustomUserDetailsService, private val passwordEncoder: PasswordEncoder) : AuthenticationProvider {

	@Transactional
	override fun authenticate(authentication: Authentication): Authentication {
		val username = authentication.name
		val password = authentication.credentials.toString()

		val accountContext = userDetailsService.loadUserByUsername(username) as AccountContext

		if(!passwordEncoder.matches(password, accountContext.password)){
			throw BadCredentialsException("password가 일치하지 않습니다.")
		}

		val formWebAuthenticationDetails = authentication.details as FormWebAuthenticationDetails
		val secretkey = formWebAuthenticationDetails.secretkey

		if(secretkey != "secret"){
			throw IllegalArgumentException("Invalid Password")
		}

		return UsernamePasswordAuthenticationToken(accountContext.account, null, accountContext.authorities)
	}

	override fun supports(authentication: Class<*>): Boolean {
		return authentication == UsernamePasswordAuthenticationToken::class.java
	}
}
