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
import java.util.Locale

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

        // Age-Specific Images
        val age_0_9: ImageView = findViewById(R.id.age_0_9);
        val age_10_19: ImageView = findViewById(R.id.age_10_19)
        val age_20_29: ImageView = findViewById(R.id.age_20_29)
        val age_30_39: ImageView = findViewById(R.id.age_30_39)
        val age_40_49: ImageView = findViewById(R.id.age_40_49)
        val age_50_59: ImageView = findViewById(R.id.age_50_59)
        val age_60_69: ImageView = findViewById(R.id.age_60_69)
        val age_70_79: ImageView = findViewById(R.id.age_70_79)
        val age_80_89: ImageView = findViewById(R.id.age_80_89)
        val age_90_99: ImageView = findViewById(R.id.age_90_99)
        val age_100: ImageView = findViewById(R.id.age_100)

        fun determineImage(daysAlive: Long) {
            when (daysAlive) {
                in 0..3652 -> age_0_9.visibility = View.VISIBLE
                in 3653..7304 -> age_10_19.visibility = View.VISIBLE
                in 7305..10957 -> age_20_29.visibility = View.VISIBLE
                in 10958..14609 -> age_30_39.visibility = View.VISIBLE
                in 14610..18262 -> age_40_49.visibility = View.VISIBLE
                in 18263..21914 -> age_50_59.visibility = View.VISIBLE
                in 21915..25567 -> age_60_69.visibility = View.VISIBLE
                in 25568..29219 -> age_70_79.visibility = View.VISIBLE
                in 29220..32872 -> age_80_89.visibility = View.VISIBLE
                in 32873..36524 -> age_90_99.visibility = View.VISIBLE
                else -> age_100.visibility = View.VISIBLE
            }
        }

        calculateButton.setOnClickListener {

            val month: Int = monthField.text.toString().toInt()
            val day: Int = dayField.text.toString().toInt()
            val year: Int = yearField.text.toString().toInt()

            val dayCountRaw: Long = Calculations.birthdayCalc(day, month, year)
            val dayCountStyled: String =
                NumberFormat.getNumberInstance(Locale.US).format(dayCountRaw)
            val dayCountStyledFinal = "You have been alive for... \n\n$dayCountStyled days!"
            val daysAliveMessage: String = getLifeStageMessage(dayCountRaw)

            promptTextView.visibility = View.GONE
            monthField.visibility = View.GONE
            yearField.visibility = View.GONE
            dayField.visibility = View.GONE
            calculateButton.visibility = View.GONE
            resultTextViewYears.visibility = View.GONE

            resultTextViewYears.text = dayCountStyledFinal
            resultTextViewYears.visibility = View.VISIBLE
            resultTextViewMessage.text = daysAliveMessage
            resultTextViewMessage.visibility = View.VISIBLE

            determineImage(dayCountRaw)
            tryAgainButton.visibility = View.VISIBLE
        }

        tryAgainButton.setOnClickListener {

            monthField.text.clear()
            yearField.text.clear()
            dayField.text.clear()


            promptTextView.visibility = View.VISIBLE
            monthField.visibility = View.VISIBLE
            yearField.visibility = View.VISIBLE
            dayField.visibility = View.VISIBLE
            calculateButton.visibility = View.VISIBLE
            resultTextViewYears.visibility = View.VISIBLE

            tryAgainButton.visibility = View.GONE
            resultTextViewYears.visibility = View.GONE
            resultTextViewMessage.visibility = View.GONE
            age_0_9.visibility = View.GONE
            age_10_19.visibility = View.GONE
            age_20_29.visibility = View.GONE
            age_30_39.visibility = View.GONE
            age_40_49.visibility = View.GONE
            age_50_59.visibility = View.GONE
            age_60_69.visibility = View.GONE
            age_70_79.visibility = View.GONE
            age_80_89.visibility = View.GONE
            age_90_99.visibility = View.GONE
            age_100.visibility = View.GONE
        }





    }
}





