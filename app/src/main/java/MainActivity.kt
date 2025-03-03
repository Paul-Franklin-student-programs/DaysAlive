package com.example.daysalive

import Calculations.Companion.getLifeStageMessage
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
        /*val titleImage = findViewById<ImageView>(R.id.myTitle)
        val logoImage = findViewById<ImageView>(R.id.myLogo)*/
        val promptTextView = findViewById<TextView>(R.id.et_prompt)
        val monthField = findViewById<EditText>(R.id.et_month)
        val dayField = findViewById<EditText>(R.id.et_day)
        val yearField = findViewById<EditText>(R.id.et_year)
        val calculateButton = findViewById<Button>(R.id.btn_calculate)
        val resultTextViewYears = findViewById<TextView>(R.id.tv_result)
        val resultTextViewMessage = findViewById<TextView>(R.id.tv_result_two)

        // Set click listener on the button
        calculateButton.setOnClickListener {
            // Get user input
            val month = monthField.text.toString().toIntOrNull()
            val day = dayField.text.toString().toIntOrNull()
            val year = yearField.text.toString().toIntOrNull()

            var dayCountRaw: Long = 0L
            var dayCountStyled: String = ""
            var daysAliveMessage: String = ""

            fun  getQuantity() {
                if (day != null && month != null && year != null) {
                    dayCountRaw = Calculations.birthdayCalc(day, month, year)
                }
            }

            fun formatQuantity(quantity: Long) {
                daysAliveMessage = NumberFormat.getNumberInstance(Locale.US).format(quantity)
            }

            fun getMessage(quantity: Long) {
                daysAliveMessage = getLifeStageMessage(quantity)
            }

            fun mainFunction() {
                getQuantity()
                formatQuantity(dayCountRaw)
                getMessage(dayCountRaw)
            }

            calculateButton.setOnClickListener {
                // Call the function when the button is clicked
                mainFunction()
            }


            promptTextView.visibility = View.GONE
            monthField.visibility = View.GONE
            yearField.visibility = View.GONE
            dayField.visibility = View.GONE
            calculateButton.visibility = View.GONE
            resultTextViewYears.visibility = View.GONE

            tryAgainButton.visibility = View.VISIBLE
            resultTextViewYears.text = dayCountStyled
            resultTextViewYears.visibility = View.VISIBLE
            resultTextViewMessage.text = daysAliveMessage
            resultTextViewMessage.visibility = View.VISIBLE
        }
    }
}