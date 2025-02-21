package com.example.daysalive

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Access UI elements
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
                    val daysAliveRaw: Long = DaysCalculator.birthdayCalc(day, month, year)
                    val daysAlive = formatWithCommas(daysAliveRaw)
                    "You have been alive for $daysAlive days."
                } catch (e: Exception) {
                    "Error: Invalid Date Entry"
                }
            } else {
                "Error: Please fill in all fields with valid numbers."
            }

            // Update the TextView with the message
            resultTextView.text = daysAliveMessage
            resultTextView.visibility = View.VISIBLE
        }
    }
}