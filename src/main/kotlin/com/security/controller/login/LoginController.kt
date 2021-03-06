package com.security.controller.login

import com.security.domain.Account
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class LoginController{

	@GetMapping("/login", "/api/login")
	fun login(
		@RequestParam(value = "error", required = false) error:String?,
		@RequestParam(value = "exception", required = false) exception:String?,
		model: Model,
	): String {
		model.addAttribute("error", error)
		model.addAttribute("exception", exception)
		return "login2"
	}

	@GetMapping("/logout")
	fun logout(request:HttpServletRequest, response:HttpServletResponse): String {
		val authentication: Authentication? = SecurityContextHolder.getContext().authentication

		authentication?.let{
			SecurityContextLogoutHandler().logout(request, response, authentication)
		}

		return "redirect:/login"
	}

	@GetMapping("/denied", "/api/denied")
	fun accessDenied(
		@RequestParam(value = "exception", required = false) exception:String?,
		model: Model,
	):String{
		val authentication = SecurityContextHolder.getContext().authentication
		val account = authentication.principal as Account
		model.addAttribute("username", account.username)
		model.addAttribute("exception", exception)
		return "user/login/denied"
	}
}
