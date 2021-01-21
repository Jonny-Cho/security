package com.security.controller.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ConfigController {
	@GetMapping("/config")
	fun config() = "admin/config"
}
