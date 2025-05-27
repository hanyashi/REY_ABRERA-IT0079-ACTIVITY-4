package com.example.fredrick_abrera_ricardo_rey_act3

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val cancelBtn = findViewById<ImageButton>(R.id.cancelBtn)
        val continueBtn = findViewById<ImageButton>(R.id.continueBtn)

        // login notification (toast)
        val successLogin = "Successfully Logged in!"
        val failedLogin = "Incorrect Credentials"
        val duration = Toast.LENGTH_SHORT

        continueBtn.setOnClickListener{
            if(usernameInput.text.toString() == "username" && passwordInput.text.toString() == "password" ){
                val toast = Toast.makeText(this, successLogin, duration)
                toast.show()
                val intent = Intent(this, Menu::class.java)
                startActivity(intent)
            } else {
                val toast = Toast.makeText(this, failedLogin, duration)
                toast.show()
            }
        }

        cancelBtn.setOnClickListener{
            usernameInput.setText("")
            passwordInput.setText("")
        }
    }
}