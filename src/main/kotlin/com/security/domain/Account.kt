package com.security.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
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
