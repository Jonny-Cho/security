package com.security.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {

	@RequestMapping("/")
	fun main() = "home2"

}
