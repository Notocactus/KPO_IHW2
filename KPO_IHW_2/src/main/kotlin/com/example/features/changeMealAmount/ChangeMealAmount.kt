package com.example.features.changeMealAmount

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
data class ChangeMealModerRequest(val token: ULong, val meal: String, val amount: UInt = 1u)


fun Application.changeMealAmount() {
    routing {
        post("/changeMealAmount") {
            val result = call.receive<ChangeMealModerRequest>()

            val authManager = AuthenticationManager

            val username = authManager.checkToken(result.token)

            if (username == null) {
                call.respond(HttpStatusCode.Forbidden, "please log in first")
            }

            val user = authManager.getUserByLogin(username!!)

            if (user == null || user is UserVisitor) {
                call.respond(HttpStatusCode.Forbidden, "please authorise as admin")
            }

            try {
                (user as UserAdmin).dbAdapter.changeMealAmount(result.meal, result.amount)
            } catch (e: NullPointerException) {
                call.respond(HttpStatusCode.BadGateway, "something went wrong")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway)
            }
            call.respond(HttpStatusCode.OK, "amount changed successfully")
        }
    }
}