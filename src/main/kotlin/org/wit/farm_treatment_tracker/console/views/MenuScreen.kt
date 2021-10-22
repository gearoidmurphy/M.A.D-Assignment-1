package org.wit.farm_treatment_tracker.console.views

import javafx.application.Platform
import javafx.geometry.Orientation
import org.wit.farm_treatment_tracker.console.controllers.TreatmentUIController

import tornadofx.*

class MenuScreen : View("Treatment Main Menu") {

    val treatmentUIController: TreatmentUIController by inject()

    override val root = form {
        setPrefSize(400.0, 200.0)
        fieldset(labelPosition = Orientation.VERTICAL) {
            text("")
            button("Add Treatment") {

                isDefaultButton = true
                useMaxWidth = true
                action {
                    runAsyncWithProgress {
                        treatmentUIController.loadAddScreen()
                    }
                }
            }
            text("")
            button("List Treatments") {

                isDefaultButton = true
                useMaxWidth = true
                action {
                    runAsyncWithProgress {
                        treatmentUIController.loadListScreen()
                    }
                }
            }
            text("")
            button("Update Treatments") {

                isDefaultButton = true
                useMaxWidth = true
                action {
                    runAsyncWithProgress {
                        treatmentUIController.loadUpdateScreen()
                    }
                }
            }
            text("")
            button("Delete Treatments") {

                isDefaultButton = true
                useMaxWidth = true
                action {
                    runAsyncWithProgress {
                        treatmentUIController.loadDeleteScreen()
                    }
                }
            }
            text("")
            button("Exit") {

                isDefaultButton = true
                useMaxWidth = true
                action {
                    runAsyncWithProgress {
                        Platform.exit();
                        System.exit(0);
                    }
                }
            }
        }

    }
}