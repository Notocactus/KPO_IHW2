package com.example.entities

import entities.DBAdapter
import java.text.SimpleDateFormat
import java.util.*

class PaymentManager() {
    private val manager = DBAdapter
    fun checkOut(user: User, order: Order): Boolean {
        try {
            manager.addSum(user.id.toInt(), "", order.getPrice())

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            manager.addUserActivity(user.id.toInt(), "payment", currentDate)
        } catch (e: NullPointerException) {
            throw NullPointerException("something went wrong with database")
        } catch (e: Exception) {
            throw Exception("something went wrong")
        }
        return true
    }

}