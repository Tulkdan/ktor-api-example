package com.example.services

import io.ktor.server.config.*
import io.ktor.util.date.getTimeMillis
import kotlin.text.toLong
import kotlin.text.toLongOrNull
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.*

class TokenProviderService(private val config: HoconApplicationConfig) {

    suspend fun createToken(applicationUsername: String): String = coroutineScope {
	val accessToken: String
	val createdAt = getTimeMillis()
	val expireInMs = config.tryGetString("jwt.expireInMs")?.toLong() ?: 1_200_000L

	try {
	    async {
		val secret = config.property("jwt.secret").getString()
		val issuer = config.property("jwt.issuer").getString()

		JWT.create()
		    .withIssuer(issuer)
		    .withExpiresAt(Date(createdAt + expireInMs))
		    .withClaim("username", applicationUsername)
		    .sign(Algorithm.HMAC512(secret))
	    }.also {
		accessToken = it.await()
	    }
	} catch (ex: Exception) {
	    throw ex
	}

	accessToken
    }
}
