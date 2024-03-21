package com.example

import com.example.features.addMealToMenu.addMealToMenu
import com.example.features.cancelOrder.cancelOrder
import com.example.features.changeMealPrice.changeMealPrice
import com.example.features.getIncome.getIncome
import com.example.features.getMenu.getMenu
import com.example.features.getUserActivity.getUserActivity
//import com.example.features.getUserActivity.getUserActivity
import com.example.features.changeMealAmount.changeMealAmount
import com.example.features.login.login
import com.example.features.logout.logout
import com.example.features.addMealToOrder.addMealToOrder
import com.example.features.payForOrder.payForOrder
import com.example.features.cookOrder.cookOrder
import com.example.features.registration.register
import com.example.features.removeMealFromMenu.removeMealFromMenu
import com.example.features.removeMealFromOrder.removeMealFromOrder
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*



fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)

//    val manager = DatabaseManager()
//    val database = DBAdapter()
//
//    println(manager.performExecute(database.createUsersTableRequest))
//    println(manager.performExecute(database.createMealsTableRequest))
//    println(manager.performExecute(database.createUsersActiveTableRequest))
//    println(manager.performExecute(database.createIncomeTableRequest))

}

fun Application.module() {
//    swagger()
    configureSerialization()
    configureRouting()
    addMealToMenu()
    cancelOrder()
    getIncome()
    getMenu()
    getUserActivity()
    changeMealAmount()
    login()
    logout()
    addMealToOrder()
    payForOrder()
    cookOrder()
    register()
    removeMealFromMenu()
    removeMealFromOrder()
    changeMealPrice()
}
