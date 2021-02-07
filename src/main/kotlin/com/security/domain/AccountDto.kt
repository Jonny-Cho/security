package com.security.domain

data class AccountDto (
	val id:Long = 0L,
	val username:String,
	val password:String,
	val email:String? = null,
	val age:String? = null,
	val role:String? = null
)
