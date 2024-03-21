package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.swagger.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}


//fun Application.swagger() {
//    routing {
//        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
//    }
//}