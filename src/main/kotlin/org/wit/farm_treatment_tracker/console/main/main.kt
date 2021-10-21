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
    TreatmentController().start()

}