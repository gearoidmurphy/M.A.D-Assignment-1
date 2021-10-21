package org.wit.farm_treatment_tracker.console.main

import mu.KotlinLogging
import org.wit.farm_treatment_tracker.console.controllers.TreatmentController
import org.wit.farm_treatment_tracker.console.models.TreatmentMemStore
import org.wit.farm_treatment_tracker.console.models.TreatmentModel
import org.wit.farm_treatment_tracker.console.views.TreatmentView

private val logger = KotlinLogging.logger {}

val treatments = TreatmentMemStore()
val treatmentView = TreatmentView()
val controller = TreatmentController()



fun main(args: Array<String>) {
    logger.info { "Launching Farm Treatment Tracker Console App" }
    println("Treatment Kotlin App Version 2.0")

    var input: Int

    do {
        input = treatmentView.menu()
        when(input) {
            1 -> addTreatment()
            2 -> treatmentView.listTreatments(treatments)
            3 -> searchAnimalsTreatments()
//            4 -> deleteTreatment()
            5 -> updateTreatment()
            -99 -> dummyData()
            -1 -> println("Exiting App")
            else -> println("Invalid Option")
        }
        println()
    } while (input != -1)
    logger.info { "Shutting Down Farm Treatment Tracker Console App" }
}

fun addTreatment() {
    var aTreatment = TreatmentModel()

    if (treatmentView.addTreatmentData(aTreatment)) {
       treatments.create(aTreatment)
    }
    else
        logger.info("Treatment Not Added")
}

//fun listAllTreatment() {
//    println("Listing All Treatments on your Farm")
//    println()
//    treatments.findAll()
//}



fun searchAnimalsTreatments() {
    val aTreatment = search(treatmentView.getId())!!
    treatmentView.showTreatment(aTreatment)
}

fun search(id: Long) : TreatmentModel? {
    var foundTreatment = treatments.findOne(id)
    return foundTreatment
}

//fun deleteTreatment() {
//    listAllTreatment()
//    var deleteId = getId()
//    val aTreatment = search(deleteId)
//    treatments.delete(aTreatment.copy())
//
//}

fun updateTreatment() {
    treatmentView.listTreatments(treatments)
    var searchId = treatmentView.getId()
    val aTreatment = search(searchId)

    if(aTreatment != null) {
        if(treatmentView.updateTreatmentData(aTreatment)) {
            treatments.update(aTreatment)
            treatmentView.showTreatment(aTreatment)
            logger.info("Treatment Updated : [ $aTreatment ]")
        }
        else
            logger.info("Treatment Not Updated")
    }
    else
        println("Treatment Not Updated...")
}

fun dummyData() {
    treatments.create(TreatmentModel(0, 71461, "Tubes",100,30,"01-09-2021"))
    treatments.create(TreatmentModel(1, 61501, "Bovevac",500,20,"23-09-2021"))
    treatments.create(TreatmentModel(2, 42063, "aprilzol",25,12,"19-08-2021"))
}