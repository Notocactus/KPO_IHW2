package com.example.features.payForOrder

import com.example.entities.UserAdmin
import com.example.entities.AuthenticationManager
import com.example.entities.PaymentManager
import com.example.entities.UserVisitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(val token: ULong)


fun Application.payForOrder() {
    routing {
        post("/payment") {
            val result = call.receive<PaymentRequest>()

            val authManager = AuthenticationManager

            val username = authManager.checkToken(result.token)

            if (username == null) {
                call.respond(HttpStatusCode.Forbidden, "please log in first")
            }

            val user = authManager.getUserByLogin(username!!)

            if (user == null || user is UserAdmin){
                call.respond(HttpStatusCode.Forbidden, "authorise as visitor first")
            }
            val userOrder = (user as UserVisitor).orderBuilder.getOrder()
            if (userOrder == null) {
                call.respond(HttpStatusCode.BadRequest, "order is not cooked")
            }
            val paymentManager = PaymentManager()
            var success = false
            try {
                success = paymentManager.checkOut(user, userOrder!!)
            } catch (e: NullPointerException) {
                call.respond(HttpStatusCode.BadGateway, e.message.toString())
            }catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway, e.message.toString())
            }
            if (!success) {
                call.respond(HttpStatusCode.BadRequest, "incorrect card number")
            }

            call.respond(HttpStatusCode.OK)
        }
    }
}