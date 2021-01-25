package com.security.controller.user

import com.security.domain.Account
import com.security.domain.AccountDto
import com.security.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class UserController(@Autowired private val passwordEncoder: PasswordEncoder, val userService: UserService) {
	@GetMapping("/mypage")
	fun myPage() = "user/mypage"

	@GetMapping("/users")
	fun createUser() = "user/login/register"

	@PostMapping("/users")
	fun createUser(accountDto: AccountDto):String{
		println("accountDto $accountDto")

		val account = Account(accountDto)
		account.password = passwordEncoder.encode(accountDto.password)
		userService.createUser(account)
		return "redirect:/"
	}

}
