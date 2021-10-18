package com.example.gestiondetelephone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_sign_up.loginBtn
import kotlinx.android.synthetic.main.activity_update_user.*

class UpdateUser : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    lateinit var number: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        number = intent.getStringExtra("NumT").toString()
        NumTel.text = number



        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser


    }


}
