package com.example.fredrick_abrera_ricardo_rey_act3

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val calculatorDesktopBtn = findViewById<ImageButton>(R.id.calculatorDesktopBtn)
        val txtBtn = findViewById<ImageButton>(R.id.txtBtn)
        val backBtn = findViewById<ImageButton>(R.id.backBtn)
        val dateTextView = findViewById<TextView>(R.id.date_textview)

        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
        val formattedDate = currentDate.format(formatter)

        dateTextView.text = formattedDate

        calculatorDesktopBtn.setOnClickListener{
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }

        txtBtn.setOnClickListener{
            val intent = Intent(this, AboutUs::class.java)
            startActivity(intent)
        }

        backBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}