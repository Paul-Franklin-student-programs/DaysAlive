package com.example.daysalive

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Access UI elements
        val tryAgainButton = findViewById<TextView>(R.id.btn_try_again)
        val titleImage = findViewById<ImageView>(R.id.myTitle)
        val logoImage = findViewById<ImageView>(R.id.myLogo)
        val promptTextView = findViewById<TextView>(R.id.et_prompt)
        val monthField = findViewById<EditText>(R.id.et_month)
        val dayField = findViewById<EditText>(R.id.et_day)
        val yearField = findViewById<EditText>(R.id.et_year)
        val calculateButton = findViewById<Button>(R.id.btn_calculate)
        val resultTextView = findViewById<TextView>(R.id.tv_result)




        // Initially hide the result TextView
        resultTextView.visibility = View.GONE

        fun formatWithCommas(number: Long): String {
            return NumberFormat.getNumberInstance(Locale.US).format(number)
        }

        // Set click listener on the button
        calculateButton.setOnClickListener {
            // Get user input
            val month = monthField.text.toString().toIntOrNull()
            val day = dayField.text.toString().toIntOrNull()
            val year = yearField.text.toString().toIntOrNull()

            // Determine the message to display
            val daysAliveMessage: String = if (day != null && month != null && year != null) {
                try {
                    // Calculate days alive using DaysCalculator
                    val daysAliveRaw: Long = Calculations.birthdayCalc(day, month, year)
                    val daysAliveMessage = Calculations.getLifeStageMessage(daysAliveRaw)
                    val daysAlive = formatWithCommas(daysAliveRaw)
                    "You have been alive for $daysAlive days.\n\n$daysAliveMessage"
                } catch (e: Exception) {
                    "Error: Invalid Date Entry"
                }
            } else {
                "Error: Please fill in all fields with valid numbers."
            }

            promptTextView.visibility = View.GONE
            monthField.visibility = View.GONE
            yearField.visibility = View.GONE
            dayField.visibility = View.GONE
            calculateButton.visibility = View.GONE

            tryAgainButton.visibility = View.VISIBLE
            resultTextView.text = daysAliveMessage
            resultTextView.visibility = View.VISIBLE
        }
    }
}