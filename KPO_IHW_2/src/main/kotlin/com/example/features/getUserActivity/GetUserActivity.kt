package com.example.features.getUserActivity

import com.example.entities.UserAdmin
import com.example.entities.AuthenticationManager
import com.example.entities.UserActivity
import com.example.entities.UserVisitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class UserActivityModelRequest(val token: ULong)

@Serializable
data class UserActivityModelResponse(val activity: List<UserActivity>)


fun Application.getUserActivity() {
    routing {
        post("/userActivity") {
            val result = call.receive<UserActivityModelRequest>()

            val authManager = AuthenticationManager

            val username = authManager.checkToken(result.token)

            if (username == null) {
                call.respond(HttpStatusCode.Forbidden, "please log in first")
            }

            val user = authManager.getUserByLogin(username!!)

            if (user == null || user is UserVisitor) {
                call.respond(HttpStatusCode.Forbidden, "authorise as admin first")
            }
            var response: List<UserActivity>? = null
            try {
                response = (user as UserAdmin).dbAdapter.getUserActivity()
            } catch (e: NullPointerException) {
                call.respond(HttpStatusCode.BadGateway, "something went wrong")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway)
            }
            call.respond(HttpStatusCode.OK, UserActivityModelResponse(response!!))
        }
    }
}