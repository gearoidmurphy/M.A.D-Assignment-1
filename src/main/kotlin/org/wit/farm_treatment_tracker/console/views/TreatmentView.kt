package org.wit.farm_treatment_tracker.console.views

import org.wit.farm_treatment_tracker.console.models.TreatmentMemStore
import org.wit.farm_treatment_tracker.console.models.TreatmentModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TreatmentView {

    fun menu() : Int {

        var option : Int
        val RESET = "\u001b[0m" // Text Reset
        val PURPLE_BOLD = "\u001b[1;35m" // PURPLE
        val CYAN = "\u001b[0;36m"
        var input: String?

        println( PURPLE_BOLD +"Main Menu" + RESET)
        println(CYAN + " 1. " + RESET +"Add Treatment")
        println(CYAN + " 2. " + RESET +"List All Treatments")
        println(CYAN + " 3. " + RESET +"Search Animal Treatment History")
        println(CYAN + " 4. " + RESET +"Delete Animal Treatment")
        println(CYAN + " 5. " + RESET +"Update treatment")
        println(CYAN + "-1. " + RESET +"Exit")
        println()
        print("Enter Option : ")
        input = readLine()!!
        option = if (input.toIntOrNull() != null && !input.isEmpty())
            input.toInt()
        else
            -9
        return option
    }

    fun listTreatments(treatments : TreatmentMemStore) {
        println("List All Treatments")
        println()
        treatments.printList()
        println()
    }

    fun showTreatment(treatment : TreatmentModel) {
        if(treatment != null) {
            println("Animal ${treatment.tagNumber} Treatment Details : ")
            println("- On ${treatment.date} the following was given : ${treatment.treatment}, ${treatment.amount}ml, ${treatment.withdrawal} Day withdrawal period ")
        }else
            println("Tag Number Not Found...")
    }

    fun addTreatmentData(treatment : TreatmentModel) : Boolean {

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatted = current.format(formatter)
        println("Add Treatment")
        println()
        print("Enter the Animals Tag Number : ")
        treatment.tagNumber = readLine()!!.toInt()
        print("Enter Treatment : ")
        treatment.treatment = readLine()!!
        print("Enter the amount given (eg. 100) : ")
        treatment.amount = readLine()!!.toInt()
        print("Enter Withdrawal Period (in days) : ")
        treatment.withdrawal = readLine()!!.toInt()
        treatment.date = formatted

        return treatment.treatment.isNotEmpty() && treatment.date.isNotEmpty()
    }

    fun updateTreatmentData(treatment : TreatmentModel) : Boolean {

        var tempTagNumber : Int?
        var tempTreatment : String?
        var tempAmount : Int?
        var tempWithdrawal : Int?
        var tempDate : String?

        if(treatment != null) {
            print("Enter New Tag Number Previously was [ " + treatment.tagNumber + " ] : ")
            tempTagNumber = readLine()!!.toInt()
            print("Enter a New Treatment Previously was [ " + treatment.treatment + " ] : ")
            tempTreatment = readLine()!!
            print("Enter New Amount Given Previously was [ " + treatment.amount + " ] : ")
            tempAmount = readLine()!!.toInt()
            print("Enter New Withdrawal Period Previously was [ " + treatment.withdrawal + " ] : ")
            tempWithdrawal = readLine()!!.toInt()
            print("Enter New Date Previously was [ " + treatment.date + " ] (dd-MM-yyyy): ")
            tempDate = readLine()!!

            if (!tempTreatment.isNullOrEmpty()) {
                treatment.tagNumber = tempTagNumber
                treatment.treatment = tempTreatment
                treatment.amount = tempAmount
                treatment.withdrawal = tempWithdrawal
                treatment.date = tempDate
                return true
            }
        }
        return false
    }

    fun getId() : Long {
        var strId : String? // String to hold user input
        var searchId : Long // Long to hold converted id
        print("Enter id to Search/Update : ")
        strId = readLine()!!
        searchId = if (strId.toLongOrNull() != null && !strId.isEmpty())
            strId.toLong()
        else
            -9
        return searchId
    }
}