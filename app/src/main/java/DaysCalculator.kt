import android.os.Build
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class DaysCalculator {
   var day: Int = 1
   var month: Int = 1
   var year: Int = 1980

   public fun birthdayCalc (day: Int, month: Int, year: Int): Long {
       val userBorn = LocalDate.of(year, month, day)
       val todayDate = LocalDate.now()
       return ChronoUnit.DAYS.between(userBorn, todayDate)
   }

}