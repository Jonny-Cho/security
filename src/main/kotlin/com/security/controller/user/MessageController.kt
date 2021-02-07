package com.security.controller.user

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MessageController {
	@GetMapping("/messages")
	fun message() = "user/messages"

	@GetMapping("/api/messages")
	@ResponseBody
	fun apiMessage() = "messages ok"
}
