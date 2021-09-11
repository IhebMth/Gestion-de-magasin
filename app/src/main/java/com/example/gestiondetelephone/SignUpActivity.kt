package com.example.gestiondetelephone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

    }

    fun SIGNUP(view: View) {
        val intent = Intent(this, homeActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "let's do this", Toast.LENGTH_LONG).show()


    }
}