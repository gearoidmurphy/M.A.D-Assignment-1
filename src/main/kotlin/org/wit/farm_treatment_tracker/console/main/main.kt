package org.wit.farm_treatment_tracker.console.main

import mu.KotlinLogging
import org.wit.farm_treatment_tracker.console.models.TreatmentModel

private val logger = KotlinLogging.logger {}
var treatment = TreatmentModel()
val treatments = ArrayList<TreatmentModel>()

fun main(args: Array<String>) {
    logger.info { "Launching Farm Treatment Tracker Console App" }
    println("Treatment Kotlin App Version 1.0")

    var input: Int

    do {
        input = menu()
        when(input) {
            1 -> addTreatment()
            2 -> listAllTreatment()
            -1 -> println("Exiting App")
            else -> println("Invalid Option")
        }
        println()
    } while (input != -1)
    logger.info { "Shutting Down Farm Treatment Tracker Console App" }
}

fun menu() : Int {

    var option : Int
    var input: String? = null

    println("Main Menu")
    println(" 1. Add Treatment")
    println(" 2. List All Treatments")
    println("-1. Exit")
    println()
    print("Enter an integer : ")
    input = readLine()!!
    option = if (input.toIntOrNull() != null && !input.isEmpty())
        input.toInt()
    else
        -9
    return option
}
fun addTreatment() {
    var aTreatment = TreatmentModel()
    println("Add Treatment")
    println()
    print("Enter the Animals Tag Number : ")
    aTreatment.tagNumber = readLine()!!
    print("Enter Treatment : ")
    aTreatment.treatment = readLine()!!
    print("Enter the amount given (eg. 100ml) : ")
    aTreatment.amount = readLine()!!
    print("Enter Withdrawal Period (in days) : ")
    aTreatment.withdrawal = readLine()!!
    print("Enter Date : ")
    aTreatment.date = readLine()!!

    if (aTreatment.tagNumber.isNotEmpty() && aTreatment.treatment.isNotEmpty() && aTreatment.amount.isNotEmpty() && aTreatment.withdrawal.isNotEmpty() && aTreatment.date.isNotEmpty()) {
        aTreatment.id = treatments.size.toLong()
        treatments.add(aTreatment.copy())
        logger.info("Treatment Added : [ $aTreatment ]")
    }
    else
        logger.info("Treatment Not Added")
}

fun listAllTreatment() {
    println("Listing All Treatments on your Farm")
    println()
    treatments.forEach { logger.info("${it}") }
}
