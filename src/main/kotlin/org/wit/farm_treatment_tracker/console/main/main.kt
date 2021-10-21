package org.wit.farm_treatment_tracker.console.main

import mu.KotlinLogging
import org.wit.farm_treatment_tracker.console.models.TreatmentModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
            3 -> searchAnimalsTreatments()
            4 -> deleteTreatment()
            -99 -> dummyData()
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
    println(" 3. Search Animal Treatment History")
    println(" 4. Delete Animal Treatment")
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
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val formatted = current.format(formatter)
    println("Add Treatment")
    println()
    print("Enter the Animals Tag Number : ")
    aTreatment.tagNumber = readLine()!!.toInt()
    print("Enter Treatment : ")
    aTreatment.treatment = readLine()!!
    print("Enter the amount given (eg. 100ml) : ")
    aTreatment.amount = readLine()!!.toInt()
    print("Enter Withdrawal Period (in days) : ")
    aTreatment.withdrawal = readLine()!!.toInt()
    aTreatment.date = formatted

    if (aTreatment.treatment.isNotEmpty() && aTreatment.date.isNotEmpty()) {
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
    treatments.forEach { println("${it.id}: ${it.tagNumber}, ${it.treatment}, ${it.amount}ml, ${it.withdrawal} Days, ${it.date}") }
}

fun getId() : Long {
    var strId : String? // String to hold user input
    var searchId : Long // Long to hold converted id
    print("Enter id to Search/Update : ")
    strId = readLine()!!
    searchId = if (strId.toLongOrNull() != null && !strId.isEmpty())
        strId.toLong()
    else
        -9
    return searchId
}

fun search(id: Long) : TreatmentModel? {
    var foundAnimal: TreatmentModel? = treatments.find { p -> p.id == id }
    return foundAnimal
}

fun searchAnimalsTreatments() {
    listAllTreatment()
    var searchId = getId()
    val aTreatment = search(searchId)

    if(aTreatment != null) {
        println("Animal ${aTreatment.tagNumber} Treatment Details : ")
        println("- On ${aTreatment.date} the following was given : ${aTreatment.treatment}, ${aTreatment.amount}ml, ${aTreatment.withdrawal} Day withdrawal period ")
    }else
        println("Tag Number Not Found...")
}

fun deleteTreatment() {
    listAllTreatment()
    var deleteId = getId()
    val aTreatment = search(deleteId)
    treatments.remove(aTreatment)
    println("Treatment has been removed!")
}

fun dummyData() {
    treatments.add(TreatmentModel(0, 71461, "Tubes",100,30,"01-09-2021"))
    treatments.add(TreatmentModel(1, 61501, "Bovevac",500,20,"23-09-2021"))
    treatments.add(TreatmentModel(2, 42063, "aprilzol",25,12,"19-08-2021"))
}