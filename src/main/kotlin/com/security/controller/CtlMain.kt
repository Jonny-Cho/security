package com.security.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class CtlMain {

	@GetMapping("/")
	@ResponseBody
	fun main():String {
		return "hello world!"
	}
}
