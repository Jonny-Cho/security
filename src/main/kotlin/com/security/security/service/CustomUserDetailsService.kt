package com.security.security.service

import com.security.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service("userDetailsService")
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

	override fun loadUserByUsername(username: String): UserDetails {
		val account = userRepository.findByUsername(username)

		val roles = listOf(SimpleGrantedAuthority(account.role))

		val accountContext = AccountContext(account, roles)
		return accountContext
	}
}
