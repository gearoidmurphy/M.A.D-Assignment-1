package org.wit.farm_treatment_tracker.console.views

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import org.wit.farm_treatment_tracker.console.controllers.TreatmentUIController

import tornadofx.*

class AddTreatmentScreen : View("Add Treatment") {
    val model = ViewModel()
    val _tagNumber = model.bind { SimpleIntegerProperty() }
    val _treatment = model.bind { SimpleStringProperty() }
    val _amount = model.bind { SimpleIntegerProperty() }
    val _withdrawal = model.bind { SimpleIntegerProperty() }
    val treatmentUIController: TreatmentUIController by inject()

    override val root = form {
        setPrefSize(600.0, 200.0)
        fieldset(labelPosition = Orientation.VERTICAL) {
            field("Animal Tag Number") {
                textfield(_tagNumber).required()
            }
            field("Treatment Name") {
                textarea(_treatment).required()
            }
            field("The Dosage given (ml)") {
                textfield(_amount).required()
            }
            field("Withdrawal Period") {
                textfield(_withdrawal).required()
            }
            button("Add") {
                enableWhen(model.valid)
                isDefaultButton = true
                useMaxWidth = true
                action {
                    runAsyncWithProgress {
                        treatmentUIController.add(_tagNumber.intValue(),_treatment.value,_amount.intValue(),_withdrawal.intValue())

                    }
                }
            }
            button("Close") {
                useMaxWidth = true
                action {
                    runAsyncWithProgress {
                        treatmentUIController.closeAdd()
                    }
                }
            }
        }
    }

    override fun onDock() {
        _tagNumber.value = 0
        _treatment.value = ""
        _amount.value = 0
        _withdrawal.value = 0
        model.clearDecorators()
    }
}
