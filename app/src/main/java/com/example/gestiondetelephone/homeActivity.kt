package com.example.gestiondetelephone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class homeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun BackToLogin(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "hello", Toast.LENGTH_LONG).show()
    }
}