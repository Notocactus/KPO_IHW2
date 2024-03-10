package com.example.entities

import entities.DBAdapter

class PaymentManager() {
    private val manager = DBAdapter
    fun checkOut(user: User, order: Order): Boolean {
        try {
            manager.addSum(user.id.toInt(), "", order.getPrice())
        } catch (e: NullPointerException) {
            throw NullPointerException("something went wrong with database")
        } catch (e: Exception) {
            throw Exception("something went wrong")
        }
        return true
    }

}