package com.cursoapi.bank.api.repository

import com.cursoapi.bank.api.model.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository:JpaRepository<Account,Long> {
}