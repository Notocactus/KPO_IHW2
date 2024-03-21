package com.example.features.login

import com.example.entities.AuthenticationManager
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
class LoginModelResponse (val token: ULong)

@Serializable
data class LoginModelRequest (
    val login: String,
    val password: String,
)

fun Application.login() {
    routing {
        post("/login") {
            val result = call.receive<LoginModelRequest>()

            val authManager = AuthenticationManager
            var token: ULong = 0u
            try{
                token = authManager.loginUser(result.login, result.password)
            }  catch (e: ArrayStoreException) {
                call.respond(HttpStatusCode.Conflict, e.message.toString())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway, e.message.toString())
            }
            call.respond(HttpStatusCode.OK, LoginModelResponse(token))
        }
    }
}
