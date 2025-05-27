package com.example.fredrick_abrera_ricardo_rey_act3

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val calculatorTaskbarBtn = findViewById<ImageButton>(R.id.calculatorTaskbarBtn)
        val calculatorDesktopBtn = findViewById<ImageButton>(R.id.calculatorDesktopBtn)
        val backBtn = findViewById<ImageButton>(R.id.backBtn)

        calculatorTaskbarBtn.setOnClickListener{
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }

        calculatorDesktopBtn.setOnClickListener{
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }

        backBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}