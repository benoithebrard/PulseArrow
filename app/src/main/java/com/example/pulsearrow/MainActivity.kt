package com.example.pulsearrow

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pulseButton: AppCompatButton = findViewById(R.id.pulse_button)
        val statusText: TextView = findViewById(R.id.status_text)
        val pulseArrowUp: AppCompatImageView = findViewById(R.id.pulse_arrow_up)
        val pulseArrowDown: AppCompatImageView = findViewById(R.id.pulse_arrow_down)

        statusText.background = ColorDrawable(Color.GRAY)

        pulseButton.setOnClickListener {
            val pulseCount: Int = (0..5).random()
            val isUp: Boolean = Random.nextBoolean()
            Toast.makeText(this, "pulse $pulseCount times (up=$isUp)", Toast.LENGTH_SHORT).show()

            statusText.animate().setDuration(500L).alpha(0f).withEndAction {
                statusText.background = ColorDrawable(if (isUp) Color.GREEN else Color.RED)
                statusText.text = if (isUp) "UP" else "DOWN"
                statusText.animate().setDuration(500L).alpha(1f)
            }

            if (isUp) {
                pulseArrowUp.animate().alpha(1f).xBy(20f).yBy(-20f).withEndAction {
                    pulseArrowUp.animate().setStartDelay(500L).alpha(0f).xBy(-20f).yBy(20f)
                }
            } else {
                pulseArrowDown.animate().alpha(1f).xBy(20f).yBy(20f).withEndAction {
                    pulseArrowDown.animate().setStartDelay(500L).alpha(0f).xBy(-20f).yBy(-20f)
                }
            }
        }


    }
}