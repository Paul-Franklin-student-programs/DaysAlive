import java.time.LocalDate
import java.time.temporal.ChronoUnit

class DaysCalculator {
    companion object {
        // Method to calculate days alive
        fun birthdayCalc(day: Int, month: Int, year: Int): Long {
            // Create a LocalDate object for the user's birthday
            val userBorn = LocalDate.of(year, month, day)
            // Get the current date
            val todayDate = LocalDate.now()
            // Calculate and return the difference in days
            return ChronoUnit.DAYS.between(userBorn, todayDate)
        }
    }
}