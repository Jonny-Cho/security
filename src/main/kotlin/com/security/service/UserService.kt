package com.security.service

import com.security.domain.Account

interface UserService {
	fun createUser(account: Account)
}
