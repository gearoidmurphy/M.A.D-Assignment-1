package org.wit.farm_treatment_tracker.console.views

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.scene.layout.GridPane
import org.wit.farm_treatment_tracker.console.controllers.TreatmentUIController
import org.wit.farm_treatment_tracker.console.models.TreatmentModel

import tornadofx.*

class ListTreatmentScreen : View("List Treatments") {
    val model = ViewModel()
    val treatmentUIController: TreatmentUIController by inject()
    val tableContent = treatmentUIController.treatments.findAll()
    var data = tableContent.observable()
    val search = model.bind { SimpleStringProperty() }


    override val root = form {
        setPrefSize(700.0, 200.0)
        fieldset(labelPosition = Orientation.HORIZONTAL) {
            field("Search") {
                textfield(search).required()
            }
            button("Search") {
                action {
                    runAsyncWithProgress {
                        data.setAll(treatmentUIController.search(search.value.toInt()) as ObservableList<TreatmentModel>)

                    }
                }
            }
        }
        tableview(data) {
            readonlyColumn("ID", TreatmentModel::id)
            readonlyColumn("TAG NUMBER", TreatmentModel::tagNumber)
            readonlyColumn("TREATMENT", TreatmentModel::treatment)
            readonlyColumn("AMOUNT", TreatmentModel::amount)
            readonlyColumn("WITHDRAWAL", TreatmentModel::withdrawal)
            readonlyColumn("DATE", TreatmentModel::date)
        }
        button("Close") {
            useMaxWidth = true
            action {
                data.setAll(treatmentUIController.treatments.findAll().observable())
                runAsyncWithProgress {
                    treatmentUIController.closeList()

                }
            }
        }
    }

}

