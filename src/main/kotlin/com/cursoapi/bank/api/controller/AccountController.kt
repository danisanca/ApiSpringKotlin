package com.cursoapi.bank.api.controller

import com.cursoapi.bank.api.model.Account
import com.cursoapi.bank.api.repository.AccountRepository
import com.cursoapi.bank.api.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class AccountController(private val service:AccountService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody account: Account):Account = service.create(account)

    @GetMapping
    fun getAll():List<Account> = service.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long):ResponseEntity<Account> =
        service.getById(id).map{
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())

    @PutMapping("/{id}")
    fun update(@PathVariable id:Long, @RequestBody account : Account): ResponseEntity<Account> =
        service.update(id,account).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id:Long):ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity<Void>(HttpStatus.OK)
    }



}