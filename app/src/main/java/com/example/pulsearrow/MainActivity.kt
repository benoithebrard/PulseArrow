package com.example.pulsearrow

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewPropertyAnimator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var previousAnimation: ViewPropertyAnimator? = null
    private var previousAnimatedArrow: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pulseButton: AppCompatButton = findViewById(R.id.pulse_button)
        val statusText: TextView = findViewById(R.id.status_text)
        val pulseArrowUp: AppCompatImageView = findViewById(R.id.pulse_arrow_up)
        val pulseArrowDown: AppCompatImageView = findViewById(R.id.pulse_arrow_down)

        pulseButton.setOnClickListener {
            val pulseCount: Int = (1..5).random()
            val isUp: Boolean = Random.nextBoolean()
            Toast.makeText(this, "pulse $pulseCount times (up=$isUp)", Toast.LENGTH_SHORT).show()

            statusText.animateCrossFade(isUp)
            previousAnimation?.cancel()
            previousAnimatedArrow?.alpha = 0f

            if (isUp) {
                previousAnimation = pulseArrowUp.animatePulse(
                    isUp = true,
                    pulseCount = pulseCount - 1
                )
                previousAnimatedArrow = pulseArrowUp
            } else {
                previousAnimation = pulseArrowDown.animatePulse(
                    isUp = false,
                    pulseCount = pulseCount - 1
                )
                previousAnimatedArrow = pulseArrowDown
            }
        }
    }

    private fun TextView.animateCrossFade(
        isUp: Boolean,
    ) {
        animate().setDuration(500L).alpha(0f).withEndAction {
            background = ColorDrawable(if (isUp) ContextCompat.getColor(context,
                android.R.color.holo_green_dark) else
                ContextCompat.getColor(context, android.R.color.holo_red_dark))
            text = if (isUp) "UP" else "DOWN"
            animate().setDuration(500L).alpha(1f)
        }
    }

    private fun ImageView.animatePulse(
        isUp: Boolean,
        pulseCount: Int,
    ): ViewPropertyAnimator {
        val verticalTranslation = if (isUp) -20f else 20f
        return animate().alpha(1f).xBy(20f).yBy(verticalTranslation).withStartAction {
            translationX = 0f
            translationY = 0f
        }.withEndAction {
            animate().setStartDelay(500L).alpha(0f).xBy(-20f).yBy(-verticalTranslation)
                .withStartAction {
                    translationX = 20f
                    translationY = verticalTranslation
                }.withEndAction {
                    if (pulseCount > 0) {
                        animatePulse(
                            isUp = isUp,
                            pulseCount = pulseCount - 1
                        )
                    }
                }
        }
    }
}