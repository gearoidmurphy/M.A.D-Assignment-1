package org.wit.farm_treatment_tracker.console.views

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.SelectionMode
import org.wit.farm_treatment_tracker.console.controllers.TreatmentUIController
import org.wit.farm_treatment_tracker.console.models.TreatmentJSONStore
import org.wit.farm_treatment_tracker.console.models.TreatmentModel
import tornadofx.*

class DeleteTreatmentScreen : View("Delete treatment") {
    val treatments = TreatmentJSONStore()
    val model = ViewModel()
    var treatmentId: Long = 0
    var tagNumber : Int = 0
    var treatment : String = ""
    var amount : Int = 0
    var withdrawal : Int = 0
    var date : String = ""
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
                tagNumber = this.selectedItem!!.tagNumber
                treatment = this.selectedItem!!.treatment
                amount = this.selectedItem!!.amount
                withdrawal = this.selectedItem!!.withdrawal
                date = this.selectedItem!!.date

            }
        }
        button("Delete") {
            enableWhen(model.valid)
            isDefaultButton = true
            useMaxWidth = true
            action {
                runAsyncWithProgress {
                    treatmentUIController.delete(treatmentId,tagNumber,treatment,amount,withdrawal,date)

                }
            }
        }
        button("Close") {
            useMaxWidth = true
            action {
                runAsyncWithProgress {
                    treatmentUIController.closeDelete()
                }
            }
        }
    }
}
