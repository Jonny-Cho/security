package com.security.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.*
import javax.persistence.Id

@Entity
class Account(
	@Id
	@GeneratedValue(strategy = IDENTITY)
	val id:Long = 0L,
	val username:String,
	var password:String,
	val email:String,
	val age:String,
	val role:String,
){
	constructor(dto: AccountDto) : this(dto.id, dto.username, dto.password, dto.email, dto.age, dto.role)
}
