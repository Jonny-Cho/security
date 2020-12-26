package com.security.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CtlMain {

	@GetMapping("/")
	fun main() = "hello world!"

	@GetMapping("/user")
	fun user() = "user"

	@GetMapping("/admin/pay")
	fun adminPay() = "adminPay"

	@GetMapping("/admin/**")
	fun admin() = "admin"

	@GetMapping("/customLogin")
	fun customLogin() = "customLogin"

	@GetMapping("/denied")
	fun denied() = "denied"
}
