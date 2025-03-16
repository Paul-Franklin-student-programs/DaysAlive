package com.paulfranklin.daysalive

import android.app.Activity
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

    fun Toast.showCustomToast(message: String, activity: Activity): Toast {

            val layout = activity.layoutInflater.inflate(
                R.layout.toast_zodiac,
                activity.findViewById(R.id.toast_container)
            )

            // Set the text of the TextView of the message
            val textView = layout.findViewById<TextView>(R.id.toast_text)
            textView.text = message

            // Use the application extension function
            this.apply {
                setGravity(Gravity.BOTTOM, 0, 300)
                duration = Toast.LENGTH_LONG
                view = layout
                show()
            }

        return this;
    }
