package com.example.daysalive

import Calculations.Companion.getLifeStageMessage
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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        // Age-Specific Images
        val ageImages = listOf(
            findViewById<ImageView>(R.id.age_0_9),
            findViewById<ImageView>(R.id.age_10_19),
            findViewById<ImageView>(R.id.age_20_29),
            findViewById<ImageView>(R.id.age_30_39),
            findViewById<ImageView>(R.id.age_40_49),
            findViewById<ImageView>(R.id.age_50_59),
            findViewById<ImageView>(R.id.age_60_69),
            findViewById<ImageView>(R.id.age_70_79),
            findViewById<ImageView>(R.id.age_80_89),
            findViewById<ImageView>(R.id.age_90_99),
            findViewById<ImageView>(R.id.age_100)
        )

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
            // Hide all images first
            ageImages.forEach { it.visibility = View.GONE }

            when (daysAlive) {
                in 0..3652 -> ageImages[0].visibility = View.VISIBLE
                in 3653..7304 -> ageImages[1].visibility = View.VISIBLE
                in 7305..10957 -> ageImages[2].visibility = View.VISIBLE
                in 10958..14609 -> ageImages[3].visibility = View.VISIBLE
                in 14610..18262 -> ageImages[4].visibility = View.VISIBLE
                in 18263..21914 -> ageImages[5].visibility = View.VISIBLE
                in 21915..25567 -> ageImages[6].visibility = View.VISIBLE
                in 25568..29219 -> ageImages[7].visibility = View.VISIBLE
                in 29220..32872 -> ageImages[8].visibility = View.VISIBLE
                in 32873..36524 -> ageImages[9].visibility = View.VISIBLE
                else -> ageImages[10].visibility = View.VISIBLE
            }
        }

        calculateButton.setOnClickListener {
            try {

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
                    return@setOnClickListener
                }
                // If result is a valid day count, continue
                playSound(R.raw.daysalive_sound_correct)
                val dayCountRaw = result as Long
                val dayCountStyled = NumberFormat.getNumberInstance(Locale.US).format(dayCountRaw)
                val dayCountStyledFinal = "You have been alive for... \n$dayCountStyled days!"
                val daysAliveMessage = getLifeStageMessage(dayCountRaw)

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
                if (e is NumberFormatException || e is DateTimeException) {
                    playSound(R.raw.dayalive_sound_wrong)
                    // If error is related to parsing or date validation, show the correct message
                    errorTextViewMessage.text = "Error: The date you entered does not exist!"
                } else {
                    playSound(R.raw.dayalive_sound_wrong)
                    // For truly unexpected errors, show general error message
                    errorTextViewMessage.text = "An unexpected error occurred.\nPlease try again."
                }
                errorTextViewMessage.visibility = View.VISIBLE
            }

            tryAgainButton.setOnClickListener {
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

                // Hide all age images
                ageImages.forEach { it.visibility = View.GONE }
            }
        }
    }
}