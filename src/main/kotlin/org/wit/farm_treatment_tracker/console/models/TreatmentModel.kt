package org.wit.farm_treatment_tracker.console.models

data class TreatmentModel( var id: Long = 0,
                           var tagNumber: String = "",
                           var treatment: String = "",
                           var amount: String = "",
                           var withdrawal: String = "",
                           var date: String = "")