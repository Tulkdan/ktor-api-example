package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*

import com.example.customer.routes.customerRouting
import com.example.order.routes.listOrdersRoute
import com.example.order.routes.getOrderRoute
import com.example.order.routes.totalizeOrderRoute
import com.example.user.routes.loginRouting

import com.example.services.TokenProviderService

fun Application.configureRouting(tokenProviderService: TokenProviderService) {
    routing {
	customerRouting()
	loginRouting(tokenProviderService)
	listOrdersRoute()
	getOrderRoute()
	totalizeOrderRoute()
    }
}
