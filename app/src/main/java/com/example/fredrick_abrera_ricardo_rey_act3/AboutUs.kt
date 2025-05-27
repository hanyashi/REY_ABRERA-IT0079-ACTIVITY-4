package com.example.fredrick_abrera_ricardo_rey_act3

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AboutUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val backBtn = findViewById<ImageButton>(R.id.backBtn)

        backBtn.setOnClickListener{
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
    }
}