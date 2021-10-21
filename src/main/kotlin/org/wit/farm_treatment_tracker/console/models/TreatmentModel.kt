package org.wit.farm_treatment_tracker.console.models

data class TreatmentModel( var id: Long = 0,
                           var tagNumber: Int = 0,
                           var treatment: String = "",
                           var amount: Int = 0,
                           var withdrawal: Int = 0,
                           var date: String = "")