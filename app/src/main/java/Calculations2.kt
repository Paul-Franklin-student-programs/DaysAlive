package com.example.daysalive

import Calculations.Companion.getLifeStageMessage
import android.view.View
import java.text.NumberFormat
import java.time.DateTimeException
import java.util.Locale


class Calculations2 {
    companion object {
        fun dateExceptionTest(day: String?, month: String?, year: String?): Any {
            try {

                // Call birthdayCalc and store the result
                val result = Calculations.birthdayCalc(day, month, year)

                if (result is String) {
                    return result
                }

                // Ensure result is a valid number before casting
                if (result !is Long) {
                    return "Unexpected result type from birthdayCalc()"
                }

                val dayCountRaw = result
                val dayCountStyled = NumberFormat.getNumberInstance(Locale.US).format(dayCountRaw)

                return "You have been alive for... \n$dayCountStyled days!"

            } catch (e: Exception) {

                when (e) {
                    is NumberFormatException, is DateTimeException -> {
                        return "Error: The date you entered does not exist!"
                    }

                    else -> {
                        return "An unexpected error occurred.\nPlease try again."
                    }
                }


            }
        }


    }
}