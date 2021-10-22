package org.wit.farm_treatment_tracker.console.models

import mu.KotlinLogging
import org.wit.farm_treatment_tracker.console.helpers.write


private val logger = KotlinLogging.logger {}
var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class TreatmentMemStore : TreatmentStore {

    val treatments = ArrayList<TreatmentModel>()

    override fun findAll(): List<TreatmentModel> {
        return treatments
    }

    override fun findOne(id: Long) : TreatmentModel? {
        var foundTreatment: TreatmentModel? = treatments.find { p -> p.id == id }
        return foundTreatment
    }

    override fun create(treatment: TreatmentModel) {
        treatment.id = getId()
        treatments.add(treatment)
        logAll()
        write("storage.txt",treatment.id.toString() + " " + treatment.tagNumber.toString() + " " + treatment.treatment + " " + treatment.amount.toString() + " " + treatment.withdrawal.toString() + " " + treatment.date )
    }

    override fun delete(treatment: TreatmentModel) {
        treatments.remove(treatment)
    }

    override fun update(treatment: TreatmentModel) {
        var foundTreatment = findOne(treatment.id!!)
        if (foundTreatment != null) {
            foundTreatment.tagNumber = treatment.tagNumber
            foundTreatment.treatment = treatment.treatment
            foundTreatment.amount = treatment.amount
            foundTreatment.withdrawal = treatment.withdrawal
            foundTreatment.date = treatment.date
        }
    }

    internal fun logAll() {
        treatments.forEach { logger.info("${it}") }
    }

    internal fun printList() {
        treatments.forEach {
            println("${it.id}: ${it.tagNumber}, ${it.treatment}, ${it.amount}ml, ${it.withdrawal} Days, ${it.date}")
        }
    }
}