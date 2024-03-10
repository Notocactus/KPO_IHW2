package com.example.features.cookOrder

import com.example.entities.UserAdmin
import com.example.entities.AuthenticationManager
import com.example.entities.UserVisitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class StartCookingModelRequest(val token: ULong)


fun Application.cookOrder() {
    routing {
        post("/startCookingOrder") {
            val result = call.receive<StartCookingModelRequest>()

            val authManager = AuthenticationManager
            val username = authManager.checkToken(result.token)

            if (username == null) {
                call.respond(HttpStatusCode.Forbidden, "please log in first")
            }

            val user = authManager.getUserByLogin(username!!)

            if (user == null || user is UserAdmin) {
                call.respond(HttpStatusCode.Forbidden, "authorise as visitor first")
            }
            try {
//                if ((user as UserVisitor).orderBuilder.checkIfOrderIsEmpty()) {
//                    call.respond(HttpStatusCode.BadRequest, "order is empty")
//                }
//                else {
//                    (user as UserVisitor).orderBuilder.cookOrder()
//                }
                (user as UserVisitor).orderBuilder.cookOrder()
            } catch (e: NoSuchMethodException) {
                call.respond(HttpStatusCode.MethodNotAllowed, e.message.toString())
            }
            call.respond(HttpStatusCode.OK, "order started cooking")
        }
    }
}