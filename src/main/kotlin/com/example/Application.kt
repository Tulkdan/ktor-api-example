package com.example

import io.ktor.server.netty.EngineMain
import io.ktor.server.application.Application
import io.ktor.server.config.HoconApplicationConfig

import com.example.plugins.configureAuthentication
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.services.TokenProviderService
import com.typesafe.config.ConfigFactory

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureAuthentication()
    configureSerialization()

    val config = HoconApplicationConfig(ConfigFactory.load())

    val tokenProviderService = TokenProviderService(config)

    configureRouting(tokenProviderService)
}
