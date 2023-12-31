package com.example.shoppingapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val imageView = findViewById<ImageView>(R.id.splash_image)
        val textView = findViewById<TextView>(R.id.iconTxt)
        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation)
        val zoomInAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in_animation)

        // Animate simultaneously
        imageView.startAnimation(zoomInAnimation)
        textView.startAnimation(rotateAnimation)
        // Navigate to the main activity after animations finish
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2500) // Adjust delay as needed
    }
}
