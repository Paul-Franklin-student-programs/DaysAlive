package com.example.daysalive

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class DaysAliveUnitTesting {

        @Test
        fun testDaysAliveCalculation() {
            // Given: A birthdate
            val birthDate = LocalDate.of(1950, 1, 1)

            // When: We calculate days alive
            val today = LocalDate.now()
            val expectedDays = ChronoUnit.DAYS.between(birthDate, today)

            // Then: Verify the output
            assertEquals(expectedDays, Calculations.birthdayCalc(birthDate.dayOfMonth.toString(), birthDate.monthValue.toString(), birthDate.year.toString()))
        }

        @Test
        fun testZodiacSignDetermination() {




        }
    }




