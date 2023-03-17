package com.example

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*

import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.models.Customer

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/customer").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("No customers found", bodyAsText())
        }
    }

    @Test
    fun testPostCustomer() = testApplication {
        application {
            configureRouting()
	    configureSerialization()
        }

        val response = client.post("/customer") {
	    contentType(ContentType.Application.Json)
	    setBody(Customer("3", "Hello", "World", "teste@gmail.com").toString())
	}

	assertEquals("", response.bodyAsText())
    }
}
