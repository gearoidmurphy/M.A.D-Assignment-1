package org.wit.farm_treatment_tracker.console.controllers

import mu.KotlinLogging
import org.wit.farm_treatment_tracker.console.helpers.read
import org.wit.farm_treatment_tracker.console.models.TreatmentMemStore
import org.wit.farm_treatment_tracker.console.models.TreatmentModel
import org.wit.farm_treatment_tracker.console.views.TreatmentView

class TreatmentController {

    val treatments = TreatmentMemStore()
    val treatmentView = TreatmentView()
    val logger = KotlinLogging.logger {}



    init {
        logger.info { "Launching Treatment Console App" }
        read("storage.txt")
    }

    fun start() {
        var input: Int

        do {
            input = menu()
            when (input) {
                1 -> addTreatment()
                2 -> list()
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


    fun menu(): Int {
        return treatmentView.menu()
    }

    fun addTreatment() {
        var aTreatment = TreatmentModel()

        if (treatmentView.addTreatmentData(aTreatment)) {
            treatments.create(aTreatment)
        } else
            logger.info("Treatment Not Added")
    }

    fun list() {
        treatmentView.listTreatments(treatments)
    }


    fun searchAnimalsTreatments() {
        val aTreatment = search(treatmentView.getId())!!
        treatmentView.showTreatment(aTreatment)
    }

    fun search(id: Long): TreatmentModel? {
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

        if (aTreatment != null) {
            if (treatmentView.updateTreatmentData(aTreatment)) {
                treatments.update(aTreatment)
                treatmentView.showTreatment(aTreatment)
                logger.info("Treatment Updated : [ $aTreatment ]")
            } else
                logger.info("Treatment Not Updated")
        } else
            println("Treatment Not Updated...")
    }

    fun dummyData() {
        treatments.create(TreatmentModel(0, 71461, "Tubes", 100, 30, "01-09-2021"))
        treatments.create(TreatmentModel(1, 61501, "Bovevac", 500, 20, "23-09-2021"))
        treatments.create(TreatmentModel(2, 42063, "aprilzol", 25, 12, "19-08-2021"))
    }
}