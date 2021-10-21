package org.wit.farm_treatment_tracker.console.models

interface TreatmentStore {
    fun findAll(): List<TreatmentModel>
    fun findOne(id: Long): TreatmentModel?
    fun create(treatment: TreatmentModel)
    fun update(treatment: TreatmentModel)
}
