package com.cursoapi.bank.api.service

import com.cursoapi.bank.api.model.Account
import com.cursoapi.bank.api.repository.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import java.util.*

@Service
class AccountServiceImpl(private val repository:AccountRepository) : AccountService {
    override fun create(account: Account): Account {
        Assert.hasLength(account.name,"[Nome] não pode estar em branco!")
        Assert.isTrue(account.name.length >= 5,"[Nome] deve conter mais que 5 characters")

        Assert.hasLength(account.document,"[Document] não pode estar em branco!")
        Assert.isTrue(account.document.length >= 11,"[Document] deve conter mais que 11 characters")

        Assert.hasLength(account.phone,"[Phone] não pode estar em branco!")
        Assert.isTrue(account.phone.length >= 11,"[Phone] deve conter mais que 11 characters")
        return repository.save(account)
    }

    override fun getAll(): List<Account> {
       return repository.findAll()
    }

    override fun getById(id: Long): Optional<Account> {
        return repository.findById(id)
    }

    override fun update(id: Long, account: Account): Optional<Account> {
        val optional = getById(id)
        if(!optional.isPresent){
            return optional
        }

        return optional.map{
            val accountToUpdate = it.copy(
                name = account.name,
                document = account.document,
                phone = account.phone
            )
            repository.save(accountToUpdate)
        }
    }

    override fun delete(id: Long) {
        repository.findById(id).map{
            repository.delete(it)
        }.orElseThrow{throw RuntimeException("Id not found")}
    }
}