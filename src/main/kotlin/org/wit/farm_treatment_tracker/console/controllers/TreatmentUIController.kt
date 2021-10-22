package org.wit.farm_treatment_tracker.console.controllers

import mu.KotlinLogging
import org.wit.farm_treatment_tracker.console.models.TreatmentJSONStore
import org.wit.farm_treatment_tracker.console.models.TreatmentModel
import org.wit.farm_treatment_tracker.console.views.*

import tornadofx.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TreatmentUIController : Controller() {

    val treatments = TreatmentJSONStore()
    val logger = KotlinLogging.logger {}

    init {
        logger.info { "Launching Treatment TornadoFX UI App" }
    }
    fun add(_tagNumber : Int, _treatment : String, _amount : Int, _withdrawal : Int){
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatted = current.format(formatter)
        var aTreatment = TreatmentModel(tagNumber = _tagNumber, treatment = _treatment, amount = _amount, withdrawal = _withdrawal,date = formatted)
        treatments.create(aTreatment)
        logger.info("Treatment Added")
    }

    fun update(_id : Long, _tagNumber : Int, _treatment : String, _amount : Int, _withdrawal : Int, _date : String){

        var aTreatment = TreatmentModel(id = _id.toLong(), tagNumber = _tagNumber, treatment = _treatment, amount = _amount, withdrawal = _withdrawal, date = _date)
        treatments.update(aTreatment)
        logger.info("Treatment updated")
    }

    fun delete(_id: Long, _tagNumber : Int, _treatment : String, _amount : Int, _withdrawal : Int, _date : String){
        var aTreatment = TreatmentModel(id = _id.toLong(), tagNumber = _tagNumber, treatment = _treatment, amount = _amount, withdrawal = _withdrawal, date = _date)
        treatments.delete(aTreatment)
    }

    fun search(_tagNumber: Int ): MutableList<TreatmentModel?> {

        return treatments.searchTagNumber(_tagNumber).observable()
    }

    fun loadListScreen() {
        runLater {
            find(MenuScreen::class).replaceWith(ListTreatmentScreen::class, sizeToScene = true, centerOnScreen = true)
        }
        treatments.logAll()
    }

    fun loadDeleteScreen() {
        runLater {
            find(MenuScreen::class).replaceWith(DeleteTreatmentScreen::class, sizeToScene = true, centerOnScreen = true)
        }

    }

    fun loadUpdateScreen() {
        runLater {
            find(MenuScreen::class).replaceWith(UpdateTreatmentScreen::class, sizeToScene = true, centerOnScreen = true)
        }

    }

    fun loadAddScreen() {
        runLater {
            find(MenuScreen::class).replaceWith(AddTreatmentScreen::class, sizeToScene = true, centerOnScreen = true)
        }
    }

    fun closeAdd() {
        runLater {
            find(AddTreatmentScreen::class).replaceWith(MenuScreen::class, sizeToScene = true, centerOnScreen = true)
        }
    }
    fun closeList() {
        runLater {
            find(ListTreatmentScreen::class).replaceWith(MenuScreen::class, sizeToScene = true, centerOnScreen = true)
        }
    }
    fun closeDelete() {
        runLater {
            find(DeleteTreatmentScreen::class).replaceWith(MenuScreen::class, sizeToScene = true, centerOnScreen = true)
        }
    }
    fun closeUpdate() {
        runLater {
            find(UpdateTreatmentScreen::class).replaceWith(MenuScreen::class, sizeToScene = true, centerOnScreen = true)
        }
    }

}