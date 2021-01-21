package com.security.service.impl

import com.security.domain.Account
import com.security.repository.UserRepository
import com.security.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("userService")
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

	@Transactional
	override fun createUser(account: Account) {
		userRepository.save(account)
	}
}
