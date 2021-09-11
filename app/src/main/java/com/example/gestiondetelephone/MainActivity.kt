package com.example.gestiondetelephone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.sax.StartElementListener
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userName = findViewById<EditText>(R.id.userName)

    }

    fun SIGNUP(view: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "hello", Toast.LENGTH_LONG).show()
    }

    fun LOGIN(view: View) {
        val intent = Intent(this, homeActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "let's do this", Toast.LENGTH_LONG).show()

    }

    }
