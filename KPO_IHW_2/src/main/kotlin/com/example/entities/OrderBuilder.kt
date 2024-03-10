package com.example.entities

import entities.DBAdapter

class OrderBuilder {
    private var order: Order = Order()
    private val dbAdapter: DBAdapter = DBAdapter
    fun addMeal(mealName: String){
        val meal: Meal
        if (!dbAdapter.isMealInMenu(mealName)) {
            throw NullPointerException("no such meal")
        }
        meal = dbAdapter.getMeal(mealName)
        dbAdapter.takeMealAway(meal.id)
        order.addMeal(meal)
    }

    fun removeMeal(mealName: String) {
        try{
            order.removeMeal(dbAdapter.getMeal(mealName));
        } catch (e: NullPointerException) {
            throw NoSuchMethodException("can't remove meal")
        } catch (e: IndexOutOfBoundsException) {
            throw e
        } catch (e:NoSuchMethodException) {
            throw e
        } catch (e: Exception) {
            throw Exception("something went wrong")
        }
    }

    fun cookOrder(): Unit { //
        order.cook()
    }

    fun cancelOrder() {
        order.cancel()
        order = Order()
    }

    fun getOrder(): Order? {
        if (order.state is Cooked) return order
        return null
    }

//    fun checkIfOrderIsEmpty(): Boolean{
//        return order.meals.isEmpty()
//    }

}