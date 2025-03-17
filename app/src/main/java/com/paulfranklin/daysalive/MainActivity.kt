package com.paulfranklin.daysalive
import com.paulfranklin.daysalive.Calculations.Companion.getLifeStageMessage
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Matrix
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.time.DateTimeException
import java.util.Locale
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import java.time.LocalDate
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var dayCountStyledFinal: String
    private lateinit var errorMessage: String

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var activeToast: Toast? = null

        val myLogo = findViewById<ImageView>(R.id.myLogo)
        // Create a Matrix object for zooming
        val matrix2 = Matrix()

        // Scale the image (increase the zoom by setting values greater than 1)
        val scaleXX = .25f // Increase horizontal scale (adjust as needed)
        val scaleYY = .25f // Increase vertical scale (adjust as needed)

        matrix2.setScale(scaleXX, scaleYY)

        // Translate the image to reposition it after zooming
        val translateXX = -27.75f // Move image left (adjust as needed)
        val translateYY = -28f // Move image up (adjust as needed)
        matrix2.postTranslate(translateXX, translateYY)

        // Apply the transformation to the ImageView
        myLogo.imageMatrix = matrix2


        // Access UI elements
        val tryAgainButton = findViewById<TextView>(R.id.btn_try_again)
        val promptTextView = findViewById<TextView>(R.id.et_prompt)
        val monthField = findViewById<EditText>(R.id.et_month)
        val dayField = findViewById<EditText>(R.id.et_day)
        val yearField = findViewById<EditText>(R.id.et_year)
        val calculateButton = findViewById<Button>(R.id.btn_calculate)
        val resultTextViewYears = findViewById<TextView>(R.id.tv_result)
        val resultTextViewMessage = findViewById<TextView>(R.id.tv_result_two)
        val errorTextViewMessage = findViewById<TextView>(R.id.tv_result_three)
        val firstZodiacMessage = findViewById<TextView>(R.id.tv_result_four)
        val secondZodiacMessage = findViewById<TextView>(R.id.tv_result_five)
        val planetJupiter = findViewById<ImageView>(R.id.planet_jupiter)
        val planetMars = findViewById<ImageView>(R.id.planet_mars)
        val planetMercury = findViewById<ImageView>(R.id.planet_mercury)
        val planetNeptune = findViewById<ImageView>(R.id.planet_neptune)
        val planetPluto = findViewById<ImageView>(R.id.planet_pluto)
        val planetSaturn = findViewById<ImageView>(R.id.planet_saturn)
        val planetTheMoon = findViewById<ImageView>(R.id.planet_the_moon)
        val planetTheSun = findViewById<ImageView>(R.id.planet_the_sun)
        val planetUranus = findViewById<ImageView>(R.id.planet_uranus)
        val planetVenus = findViewById<ImageView>(R.id.planet_venus)


        // Age-Specific Images
        val ageInfant = findViewById<ImageView>(R.id.age_0)
        val ageChild = findViewById<ImageView>(R.id.age_0_9)
        val ageTeenager = findViewById<ImageView>(R.id.age_10_19)
        val ageYoungAdult = findViewById<ImageView>(R.id.age_20_29)
        val ageAdult = findViewById<ImageView>(R.id.age_30_39)
        val ageMiddleAge = findViewById<ImageView>(R.id.age_40_49)
        val ageUpperAge = findViewById<ImageView>(R.id.age_50_59)
        val ageRetire = findViewById<ImageView>(R.id.age_60_69)
        val ageWisdom = findViewById<ImageView>(R.id.age_70_79)
        val ageSenior = findViewById<ImageView>(R.id.age_80_89)
        val ageElder = findViewById<ImageView>(R.id.age_90_99)
        val ageCentury = findViewById<ImageView>(R.id.age_100)

        calculateButton.isSoundEffectsEnabled = false
        tryAgainButton.isSoundEffectsEnabled = false

        fun playSound(resourceId: Int) {
            val mediaPlayer = MediaPlayer.create(this, resourceId)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { mp ->
                mp.release() // Free memory after playback
            }
        }

        fun determineImage(daysAlive: Long) {
            when (daysAlive) {
                in 0..364 -> ageInfant.visibility = View.VISIBLE
                in 365..3652 -> ageChild.visibility = View.VISIBLE
                in 3653..7304 -> ageTeenager.visibility = View.VISIBLE
                in 7305..10957 -> ageYoungAdult.visibility = View.VISIBLE
                in 10958..14609 -> ageAdult.visibility = View.VISIBLE
                in 14610..18262 -> ageMiddleAge.visibility = View.VISIBLE
                in 18263..21914 -> ageUpperAge.visibility = View.VISIBLE
                in 21915..25567 -> ageRetire.visibility = View.VISIBLE
                in 25568..29219 -> ageWisdom.visibility = View.VISIBLE
                in 29220..32872 -> ageSenior.visibility = View.VISIBLE
                in 32873..36524 -> ageElder.visibility = View.VISIBLE
                else -> ageCentury.visibility = View.VISIBLE
            }
        }

        val glassBowl = View.OnClickListener {

            activeToast?.cancel()  // Manually dismiss the toast
            activeToast = null

            playSound(R.raw.daysalive_sound_zodiac)

            dayCountStyledFinal = dayCountStyledFinal.drop(28)
            resultTextViewYears.text = dayCountStyledFinal

            resultTextViewMessage.visibility = View.GONE
            ageInfant.visibility = View.GONE
            ageChild.visibility = View.GONE
            ageTeenager.visibility = View.GONE
            ageYoungAdult.visibility = View.GONE
            ageAdult.visibility = View.GONE
            ageMiddleAge.visibility = View.GONE
            ageUpperAge.visibility = View.GONE
            ageRetire.visibility = View.GONE
            ageWisdom.visibility = View.GONE
            ageSenior.visibility = View.GONE
            ageElder.visibility = View.GONE
            ageCentury.visibility = View.GONE

            val day2 = dayField.text.toString()
            val month2 = monthField.text.toString()
            val dayInt2 = day2.toInt()
            val monthInt2 = month2.toInt()
            val userBorn2 = LocalDate.of(1900, monthInt2, dayInt2)
            val sign = when {
                (userBorn2.monthValue == 3 && userBorn2.dayOfMonth >= 21) || (userBorn2.monthValue == 4 && userBorn2.dayOfMonth <= 19) -> "Aries♈"
                (userBorn2.monthValue == 4 && userBorn2.dayOfMonth >= 20) || (userBorn2.monthValue == 5 && userBorn2.dayOfMonth <= 20) -> "Taurus♉"
                (userBorn2.monthValue == 5 && userBorn2.dayOfMonth >= 21) || (userBorn2.monthValue == 6 && userBorn2.dayOfMonth <= 20) -> "Gemini♊"
                (userBorn2.monthValue == 6 && userBorn2.dayOfMonth >= 21) || (userBorn2.monthValue == 7 && userBorn2.dayOfMonth <= 22) -> "Cancer♋"
                (userBorn2.monthValue == 7 && userBorn2.dayOfMonth >= 23) || (userBorn2.monthValue == 8 && userBorn2.dayOfMonth <= 22) -> "Leo♌"
                (userBorn2.monthValue == 8 && userBorn2.dayOfMonth >= 23) || (userBorn2.monthValue == 9 && userBorn2.dayOfMonth <= 22) -> "Virgo♍"
                (userBorn2.monthValue == 9 && userBorn2.dayOfMonth >= 23) || (userBorn2.monthValue == 10 && userBorn2.dayOfMonth <= 22) -> "Libra♎"
                (userBorn2.monthValue == 10 && userBorn2.dayOfMonth >= 23) || (userBorn2.monthValue == 11 && userBorn2.dayOfMonth <= 21) -> "Scorpio♏"
                (userBorn2.monthValue == 11 && userBorn2.dayOfMonth >= 22) || (userBorn2.monthValue == 12 && userBorn2.dayOfMonth <= 21) -> "Sagittarius♐"
                (userBorn2.monthValue == 12 && userBorn2.dayOfMonth >= 22) || (userBorn2.monthValue == 1 && userBorn2.dayOfMonth <= 19) -> "Capricorn♑"
                (userBorn2.monthValue == 1 && userBorn2.dayOfMonth >= 20) || (userBorn2.monthValue == 2 && userBorn2.dayOfMonth <= 18) -> "Aquarius♒"
                (userBorn2.monthValue == 2 && userBorn2.dayOfMonth >= 19) || (userBorn2.monthValue == 3 && userBorn2.dayOfMonth <= 20) -> "Pisces♓"
                else -> "Error: Unable to find sign"  // Error case (invalid date)
            }

            val signQuote = when (sign) {
                "Aries♈" -> "\"Courage, Drive, and Strength\""
                "Taurus♉" -> "\"Patience, Stability, and Endurance\""
                "Gemini♊" -> "\"Curiosity, Wit, and Adaptability\""
                "Cancer♋" -> "\"Empathy, Protection, and Devotion\""
                "Leo♌" -> "\"Confidence, Passion, and Leadership\""
                "Virgo♍" -> "\"Precision, Logic, and Diligence\""
                "Libra♎" -> "\"Harmony, Charm, and Fairness\""
                "Scorpio♏" -> "\"Intensity, Mystery, and Power\""
                "Sagittarius♐" -> "\"Adventure, Optimism, and Wisdom\""
                "Capricorn♑" -> "\"Discipline, Ambition, and Resilience\""
                "Aquarius♒" -> "\"Innovation, Vision, and Rebellion\""
                "Pisces♓" -> "\"Imagination, Compassion, and Dreaming\""
                else -> "\"Unknown, Undefined, and Uncharted\""
            }

            when (sign) {
                "Aries♈" -> planetMars.visibility = View.VISIBLE
                "Taurus♉" -> planetVenus.visibility = View.VISIBLE
                "Gemini♊" -> planetMercury.visibility = View.VISIBLE
                "Cancer♋" -> planetTheMoon.visibility = View.VISIBLE
                "Leo♌" -> planetTheSun.visibility = View.VISIBLE
                "Virgo♍" -> planetMercury.visibility = View.VISIBLE
                "Libra♎" -> planetVenus.visibility = View.VISIBLE
                "Scorpio♏" -> planetPluto.visibility = View.VISIBLE
                "Sagittarius♐" -> planetJupiter.visibility = View.VISIBLE
                "Capricorn♑" -> planetSaturn.visibility = View.VISIBLE
                "Aquarius♒" -> planetUranus.visibility = View.VISIBLE
                "Pisces♓" -> planetNeptune.visibility = View.VISIBLE
            }

            firstZodiacMessage.visibility = View.VISIBLE
            firstZodiacMessage.text = sign
            secondZodiacMessage.visibility = View.VISIBLE
            secondZodiacMessage.text = signQuote

        }




                val paperCup = View.OnClickListener {
                        view ->
                    try {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val activity = view.context as Activity

                            // ✅ Create the Toast first, then call `showCustomToast()` on it
                            activeToast = Toast.makeText(activity, "Follow the signs... 🔮", Toast.LENGTH_LONG)
                                .showCustomToast("Follow the signs... 🔮", activity) // Call on Toast instance
                        }, 3000)


                        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(window.decorView.windowToken, 0)

                        // Hide unnecessary elements
                        planetMars.visibility = View.GONE
                        planetVenus.visibility = View.GONE
                        planetMercury.visibility = View.GONE
                        planetTheMoon.visibility = View.GONE
                        planetTheSun.visibility = View.GONE
                        planetPluto.visibility = View.GONE
                        planetJupiter.visibility = View.GONE
                        planetSaturn.visibility = View.GONE
                        planetUranus.visibility = View.GONE
                        planetNeptune.visibility = View.GONE
                        firstZodiacMessage.visibility = View.GONE
                        secondZodiacMessage.visibility = View.GONE
                        errorTextViewMessage.visibility = View.GONE

                        // Get user input
                        val month = monthField.text.toString()
                        val day = dayField.text.toString()
                        val year = yearField.text.toString()

                        // Call birthdayCalc and store the result
                        val result = Calculations.birthdayCalc(day, month, year)

                        if (result is String) {
                            // If result is an error message, display it
                            playSound(R.raw.dayalive_sound_wrong)
                            errorTextViewMessage.text = result
                            errorTextViewMessage.visibility = View.VISIBLE
                            return@OnClickListener // Stop execution here
                        }

                        // Ensure result is a valid number before casting
                        if (result !is Long) {
                            throw IllegalStateException("Unexpected result type from birthdayCalc()")
                        }

                        val dayCountRaw = result
                        val dayCountStyled = NumberFormat.getNumberInstance(Locale.US).format(dayCountRaw)
                        val daysAliveMessage = getLifeStageMessage(dayCountRaw)

                        dayCountStyledFinal = "You have been alive for... \n$dayCountStyled days!"

                        if (promptTextView.visibility == View.VISIBLE) {
                            playSound(R.raw.daysalive_sound_correct)
                        }

                        // Hide input fields and show results
                        promptTextView.visibility = View.GONE
                        monthField.visibility = View.GONE
                        yearField.visibility = View.GONE
                        dayField.visibility = View.GONE
                        calculateButton.visibility = View.GONE

                        resultTextViewYears.text = dayCountStyledFinal
                        resultTextViewYears.visibility = View.VISIBLE

                        resultTextViewMessage.text = daysAliveMessage
                        resultTextViewMessage.visibility = View.VISIBLE

                        determineImage(dayCountRaw)
                        tryAgainButton.visibility = View.VISIBLE

                    } catch (e: Exception) {
                        playSound(R.raw.dayalive_sound_wrong)

                        // Handle specific errors first
                        when (e) {
                            is NumberFormatException, is DateTimeException -> {
                                errorTextViewMessage.text = "Error: The date you entered does not exist!"
                            }
                            else -> {
                                errorTextViewMessage.text = "An unexpected error occurred.\nPlease try again."
                            }
                        }

                        errorTextViewMessage.visibility = View.VISIBLE
                    }
                }

                calculateButton.setOnClickListener(paperCup)

        /*
        planetMars.setOnClickListener(paperCup)
        planetMercury.setOnClickListener(paperCup)
        planetNeptune.setOnClickListener(paperCup)
        planetPluto.setOnClickListener(paperCup)
        planetSaturn.setOnClickListener(paperCup)
        planetTheMoon.setOnClickListener(paperCup)
        planetTheSun.setOnClickListener(paperCup)
        planetUranus.setOnClickListener(paperCup)
        planetVenus.setOnClickListener(paperCup)
        planetJupiter.setOnClickListener(paperCup)*/

                ageInfant.setOnClickListener(glassBowl)
                ageChild.setOnClickListener(glassBowl)
                ageTeenager.setOnClickListener(glassBowl)
                ageYoungAdult.setOnClickListener(glassBowl)
                ageAdult.setOnClickListener(glassBowl)
                ageMiddleAge.setOnClickListener(glassBowl)
                ageUpperAge.setOnClickListener(glassBowl)
                ageRetire.setOnClickListener(glassBowl)
                ageWisdom.setOnClickListener(glassBowl)
                ageSenior.setOnClickListener(glassBowl)
                ageElder.setOnClickListener(glassBowl)
                ageCentury.setOnClickListener(glassBowl)







                tryAgainButton.setOnClickListener {

                    firstZodiacMessage.visibility = View.GONE
                    secondZodiacMessage.visibility = View.GONE
                    playSound(R.raw.daysalive_sound_return)
                    // Reset input fields
                    monthField.text.clear()
                    yearField.text.clear()
                    dayField.text.clear()

                    // Restore visibility
                    promptTextView.visibility = View.VISIBLE
                    monthField.visibility = View.VISIBLE
                    yearField.visibility = View.VISIBLE
                    dayField.visibility = View.VISIBLE
                    calculateButton.visibility = View.VISIBLE

                    tryAgainButton.visibility = View.GONE
                    resultTextViewYears.visibility = View.GONE
                    resultTextViewMessage.visibility = View.GONE

                    planetMars.visibility = View.GONE
                    planetVenus.visibility = View.GONE
                    planetMercury.visibility = View.GONE
                    planetTheMoon.visibility = View.GONE
                    planetTheSun.visibility = View.GONE
                    planetMercury.visibility = View.GONE
                    planetVenus.visibility = View.GONE
                    planetPluto.visibility = View.GONE
                    planetJupiter.visibility = View.GONE
                    planetSaturn.visibility = View.GONE
                    planetUranus.visibility = View.GONE
                    planetNeptune.visibility = View.GONE

                    ageInfant.visibility = View.GONE
                    ageChild.visibility = View.GONE
                    ageTeenager.visibility = View.GONE
                    ageYoungAdult.visibility = View.GONE
                    ageAdult.visibility = View.GONE
                    ageMiddleAge.visibility = View.GONE
                    ageUpperAge.visibility = View.GONE
                    ageRetire.visibility = View.GONE
                    ageWisdom.visibility = View.GONE
                    ageSenior.visibility = View.GONE
                    ageElder.visibility = View.GONE
                    ageCentury.visibility = View.GONE

                }
            }
        }
