package com.security.repository

import com.security.domain.Account
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<Account, Long> {
	fun findByUsername(username: String): Account
}
