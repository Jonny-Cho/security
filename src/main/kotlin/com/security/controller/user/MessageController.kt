package com.security.controller.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MessageController {
	@GetMapping("/messages")
	fun message() = "user/messages"

	@PostMapping("/api/messages")
	@ResponseBody
	fun apiMessage():ResponseEntity<String> {
		return ResponseEntity("ok", HttpStatus.OK)
	}
}
