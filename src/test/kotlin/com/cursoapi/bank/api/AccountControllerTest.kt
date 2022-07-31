package com.cursoapi.bank.api

import com.cursoapi.bank.api.model.Account
import com.cursoapi.bank.api.repository.AccountRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var accountRepository: AccountRepository


    @Test
    fun testeFindAll() {
        accountRepository.save(Account(name = "Test", document = "123", phone = "9999"))
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].name").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].document").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].phone").isString)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun testeFindById() {
        val account = accountRepository.save(Account(name = "Test", document = "123", phone = "9999"))

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/${account.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(account.id))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun testCreateAccount() {
        val account = Account(name = "Test create", document = "12345678910", phone = "12345678910")
        val json = ObjectMapper().writeValueAsString(account)
        accountRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
            .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(accountRepository.findAll().isEmpty())
    }

    @Test
    fun testUpdateAccount() {
        val account = accountRepository
            .save(Account(name = "Test", document = "123", phone = "9999"))
            .copy(name = "update")
        val json = ObjectMapper().writeValueAsString(account)
        mockMvc.perform(
            MockMvcRequestBuilders.put("/accounts/${account.id}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
            .andDo(MockMvcResultHandlers.print())

        val findById = accountRepository.findById(account.id!!)
        Assertions.assertTrue(findById.isPresent)
        Assertions.assertEquals(account.name, findById.get().name)
    }

    @Test
    fun testDeleteAccount() {
        val account = accountRepository
            .save(Account(name = "Test", document = "123", phone = "9999"))
        val json = ObjectMapper().writeValueAsString(account)

        mockMvc.perform(MockMvcRequestBuilders.delete("/accounts/${account.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())

        val findById = accountRepository.findById(account.id!!)
        Assertions.assertFalse(findById.isPresent)
    }

    //--- Validação de Dados
    @Test
    fun testValidationData_Name_Empty() {
        val account = Account(name = "", document = "123", phone = "9999")
        val json = ObjectMapper().writeValueAsString(account)
        accountRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("[Nome] não pode estar em branco!"))
            .andDo(MockMvcResultHandlers.print())

    }
    @Test
    fun testValidationData_Name_Small() {
        val account = Account(name = "adbs", document = "123", phone = "9999")
        val json = ObjectMapper().writeValueAsString(account)
        accountRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("[Nome] deve conter mais que 5 characters"))
            .andDo(MockMvcResultHandlers.print())

    }
    @Test
    fun testValidationData_Document_Empty() {
        val account = Account(name = "adbsuu", document = "", phone = "9999")
        val json = ObjectMapper().writeValueAsString(account)
        accountRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("[Document] não pode estar em branco!"))
            .andDo(MockMvcResultHandlers.print())

    }
    @Test
    fun testValidationData_Document_Small() {
        val account = Account(name = "adbsu", document = "123", phone = "9999")
        val json = ObjectMapper().writeValueAsString(account)
        accountRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("[Document] deve conter mais que 11 characters"))
            .andDo(MockMvcResultHandlers.print())

    }

    @Test
    fun testValidationData_Phone_Empty() {
        val account = Account(name = "adbsuu", document = "12345678910", phone = "")
        val json = ObjectMapper().writeValueAsString(account)
        accountRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("[Phone] não pode estar em branco!"))
            .andDo(MockMvcResultHandlers.print())

    }
    @Test
    fun testValidationData_Phone_Small() {
        val account = Account(name = "adbsu", document = "12345678910", phone = "9999")
        val json = ObjectMapper().writeValueAsString(account)
        accountRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("[Phone] deve conter mais que 11 characters"))
            .andDo(MockMvcResultHandlers.print())

    }

}