package org.wit.farm_treatment_tracker.console.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import mu.KotlinLogging

import org.wit.farm_treatment_tracker.console.helpers.*
import java.util.*

private val logger = KotlinLogging.logger {}

val JSON_FILE = "treatments.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<TreatmentModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class TreatmentJSONStore : TreatmentStore {

    var treatments = mutableListOf<TreatmentModel>()

    init {
        if (exists(JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<TreatmentModel> {
        return treatments
    }

    override fun findOne(id: Long) : TreatmentModel? {
        var foundTreatment: TreatmentModel? = treatments.find { p -> p.id == id }
        return foundTreatment
    }

    override fun create(treatment: TreatmentModel) {
        treatment.id = generateRandomId()
        treatments.add(treatment)
        serialize()
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
        serialize()
    }

    internal fun logAll() {
        treatments.forEach { logger.info("${it}") }
    }

    internal fun printList() {
        treatments.forEach {
            println("${it.id}: ${it.tagNumber}, ${it.treatment}, ${it.amount}ml, ${it.withdrawal} Days, ${it.date}")
        }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(treatments, listType)
        write(JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(JSON_FILE)
        treatments = Gson().fromJson(jsonString, listType)
    }
}
