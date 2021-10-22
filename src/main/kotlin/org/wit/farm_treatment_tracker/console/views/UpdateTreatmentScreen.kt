package org.wit.farm_treatment_tracker.console.views

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.scene.control.SelectionMode
import org.wit.farm_treatment_tracker.console.controllers.TreatmentUIController
import org.wit.farm_treatment_tracker.console.models.TreatmentJSONStore
import org.wit.farm_treatment_tracker.console.models.TreatmentModel

import tornadofx.*

class UpdateTreatmentScreen : View("Update Treatment") {
    val treatments = TreatmentJSONStore()
    val model = ViewModel()
    var treatmentId : Long = 0
    val _id = model.bind { SimpleLongProperty() }
    val _tagNumber = model.bind { SimpleIntegerProperty() }
    val _treatment = model.bind { SimpleStringProperty() }
    val _amount = model.bind { SimpleIntegerProperty() }
    val _withdrawal = model.bind { SimpleIntegerProperty() }
    val _date = model.bind { SimpleStringProperty() }
    val treatmentUIController: TreatmentUIController by inject()
    val tableContent = treatmentUIController.treatments.findAll()
    val data = tableContent.observable()

    override val root = form {
        setPrefSize(600.0, 300.0)
        tableview(data) {
            readonlyColumn("ID", TreatmentModel::id)
            readonlyColumn("TAG NUMBER", TreatmentModel::tagNumber)
            readonlyColumn("TREATMENT", TreatmentModel::treatment)
            readonlyColumn("AMOUNT", TreatmentModel::amount)
            readonlyColumn("WITHDRAWAL", TreatmentModel::withdrawal)
            readonlyColumn("DATE", TreatmentModel::date)
            selectionModel.selectionMode = SelectionMode.SINGLE
            onDoubleClick {
                treatmentId = this.selectedItem!!.id
            }
        }.setMinSize(500.0,200.0)
        fieldset(labelPosition = Orientation.VERTICAL) {
            field("Animal Tag Number") {
                textfield(_tagNumber).required()
            }
            field("Treatment Name") {
                textfield(_treatment).required()
            }
            field("The Dosage given (ml)") {
                textfield(_amount).required()
            }
            field("Withdrawal Period") {
                textfield(_withdrawal).required()
            }
            field("Date(dd-MM-yyyy") {
                textfield(_date).required()
            }
            button("Update") {
                enableWhen(model.valid)
                isDefaultButton = true
                useMaxWidth = true
                action {
                    runAsyncWithProgress {
                        treatmentUIController.update(treatmentId,_tagNumber.intValue(),_treatment.value.toString(),_amount.intValue(),_withdrawal.intValue(),_date.value.toString())

                    }
                }
            }
            button("Close") {
                useMaxWidth = true
                action {
                    runAsyncWithProgress {
                        treatmentUIController.closeUpdate()
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
