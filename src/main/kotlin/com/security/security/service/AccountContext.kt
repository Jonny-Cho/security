package com.security.security.service

import com.security.domain.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class AccountContext(val account: Account, authorities: List<GrantedAuthority>) : User(account.username, account.password, authorities)
