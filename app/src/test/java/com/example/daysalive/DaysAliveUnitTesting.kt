package com.example.daysalive

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class DaysAliveUnitTesting {

    @Test // T1
    fun testDaysAliveCalculation() {
        // Given: A birthdate
        val birthDate = LocalDate.of(1950, 1, 1)

        // When: We calculate days alive
        val today = LocalDate.now()
        val expectedDays = ChronoUnit.DAYS.between(birthDate, today)

        // Then: Verify the output
        assertEquals(
            expectedDays,
            Calculations.birthdayCalc(
                birthDate.dayOfMonth.toString(),
                birthDate.monthValue.toString(),
                birthDate.year.toString()
            )
        )
    }

    @Test // T2
    fun testDaysAliveCalculation2() {
        // Given: A birthdate
        val birthDate = LocalDate.of(1975, 10, 26)

        // When: We calculate days alive
        val today = LocalDate.now()
        val expectedDays = ChronoUnit.DAYS.between(birthDate, today)

        // Then: Verify the output
        assertEquals(
            expectedDays,
            Calculations.birthdayCalc(
                birthDate.dayOfMonth.toString(),
                birthDate.monthValue.toString(),
                birthDate.year.toString()
            )
        )
    }


    @Test // T3
    fun testZodiacSignDeterminationPisces() {
        val day = 10
        val month = 3
        val result = "Pisces"

        assertEquals(result, Calculations.determineSign(day, month))
    }


    @Test // T4
    fun testZodiacSignDeterminationScorpio() {
        val day = 26
        val month = 10
        val result = "Scorpio"

        assertEquals(result, Calculations.determineSign(day, month))
    }

    @Test // T5
    fun testDecadeMessage90_100() {
        val day = 1
        val month = 10
        val year = 1900
        val message =
            "Wow! You reached 100! You have had so many adventures, but why not have some more?"

        assertEquals(
            message,
            Calculations.getLifeStageMessage(
                ChronoUnit.DAYS.between(
                    LocalDate.of(year, month, day),
                    LocalDate.now()
                )
            )
        )
    }

    @Test // T6
    fun testDecadeMessageAge0_10() {
        val day = 15
        val month = 2
        val year = 2024
        val message = "You're exploring and learning. Have fun, and follow your heart!"

        assertEquals(
            message,
            Calculations.getLifeStageMessage(
                ChronoUnit.DAYS.between(
                    LocalDate.of(year, month, day),
                    LocalDate.now()
                )
            )
        )
    }

    @Test // T7
    fun testPlanetPluto() {
        val sign = "Scorpio"
        val planet = "Pluto"

        assertEquals(planet, Calculations.determinePlanet(sign))

    }

    @Test // T8
    fun testPlanetMars() {
        val sign = "Capricorn"
        val planet = "Saturn"

        assertEquals(planet, Calculations.determinePlanet(sign))

    }

    @Test // T9
    fun testLeapYearHandling() {
        val day = "29"
        val month = "2"
        val year = "1951"
        val message = "Error: The date you entered does not exist!"
        assertEquals(message, Calculations2.dateExceptionTest(day,month,year))

    }

    @Test // T10
    fun testInvalidDayHandling() {
        val day = "31"
        val month = "6"
        val year = "2000"
        val message = "Error: The date you entered does not exist!"
        assertEquals(message, Calculations2.dateExceptionTest(day,month,year))

    }

    @Test // T11
    fun testInvalidEntryHandling() {
        val day = "1"
        val month = "4"
        val year = "123456789"
        val message = "Error: Please enter valid birthday information"
        assertEquals(message, Calculations2.dateExceptionTest(day,month,year))

    }

    @Test // T12
    fun testSingleBlankFieldHandling() {
        val day = "1"
        val month = "4"
        val year = null
        val message = "Error: Do not leave fields blank"
        assertEquals(message, Calculations2.dateExceptionTest(day,month,year))

    }

    @Test // T13
    fun testTwoBlankFieldsHandling() {
        val day = "1"
        val month = null
        val year = null
        val message = "Error: Do not leave fields blank"
        assertEquals(message, Calculations2.dateExceptionTest(day,month,year))

    }

    @Test // T14
    fun testAllFieldsBlankHandling() {
        val day = null
        val month = null
        val year = null
        val message = "Error: Do not leave fields blank"
        assertEquals(message, Calculations2.dateExceptionTest(day,month,year))

    }

    @Test // T15
    fun testDaysAliveReturnedString() {
        val day = 26
        val month = 10
        val year = 1991
        val birthDate = LocalDate.of(year,month,day)
        val today = LocalDate.now()
        val expectedDays = ChronoUnit.DAYS.between(birthDate, today)
        val message = "You have been alive for... $expectedDays days!"
        assertEquals(message, Calculations2.dateExceptionTest2(day.toString(),month.toString(),year.toString()))
    }

    @Test // T15
    fun testDaysAliveReturnedString2() {
        val day = 19
        val month = 5
        val year = 1946
        val birthDate = LocalDate.of(year,month,day)
        val today = LocalDate.now()
        val expectedDays = ChronoUnit.DAYS.between(birthDate, today)
        val message = "You have been alive for... $expectedDays days!"
        assertEquals(message, Calculations2.dateExceptionTest2(day.toString(),month.toString(),year.toString()))

    }
}









