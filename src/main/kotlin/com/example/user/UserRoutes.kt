package com.example.user.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

import com.example.user.models.User
import com.example.user.models.LoginResponse
import com.example.services.TokenProviderService

fun Route.loginRouting(tokenProviderService: TokenProviderService) {
    route("/login") {
	post {
	    val user = call.receive<User>()

	    val accessToken = tokenProviderService.createToken(user.username)

	    val message = LoginResponse(accessToken)

	    call.respond(message = message, status = HttpStatusCode.OK)
	}
	authenticate("auth-jwt") {
	    get("/validate") {
		val principal = call.principal<JWTPrincipal>()
		val username = principal!!.payload.getClaim("username").asString()
		val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
		call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
	    }
	}
    }
}
