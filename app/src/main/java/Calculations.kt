import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Calculations {
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
        fun getLifeStageMessage(daysAlive: Long): String {
            return when (daysAlive) {
                in 0..3652 -> "You're exploring and learning. Have fun, and follow your heart!" // 0 - 9 years
                in 3653..7304 -> "You're learning responsibility and finding out who you are. Be bold and free!" // 10 - 19 years
                in 7305..10957 -> "Enjoy your new freedoms and make plans for the future!" // 20 - 29 years
                in 10958..14609 -> "Friends, family, romance, and everything in between!" // 30 - 39 years
                in 14610..18262 -> "You inspire others with your success as you achieve your dreams!" // 40 - 49 years
                in 18263..21914 -> "Your wisdom drives your accomplishments, and your compassion fuels your relationships." // 50 - 59 years
                in 21915..25567 -> "Reflecting on a life well lived, with excitement for what's to come!" // 60 - 69 years
                in 25568..29219 -> "Never too late to try something new, while celebrating the special people you have loved for so long." // 70 - 79 years
                in 29220..32872 -> "You understand the secrets of life better than anyone, and your warmth and insight bring happiness to others." // 80 - 89 years
                in 32873..36524 -> "Celebrate your sphere of special friends and enjoy their wonderful company!" // 90 - 99 years
                else -> "Wow! You reached 100! You have had so many adventures, but why not have some more?" // 100+
            }
        }

    }
}

