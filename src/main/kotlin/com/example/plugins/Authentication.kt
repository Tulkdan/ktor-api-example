package com.example.plugins

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.http.HttpStatusCode
import java.util.*

fun Application.configureAuthentication() {
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    install(Authentication) {
	jwt("auth-jwt") {
	    realm = myRealm

	    verifier(
		JWT.require(Algorithm.HMAC512(secret))
		.withIssuer(issuer)
		.build()
	    )

	    validate { credential ->
		if (credential.payload.getClaim("username").asString() != "") {
		    JWTPrincipal(credential.payload)
		} else {
		    null
		}
	    }

	    challenge { _, _ ->
		call.respond(
		    HttpStatusCode.Unauthorized,
		    "Token is not valid or has expired"
		)
	    }
	}
    }
}

