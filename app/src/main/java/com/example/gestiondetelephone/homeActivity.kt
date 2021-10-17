package com.example.gestiondetelephone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiondetelephone.listeTel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_liste_tel.*

class homeActivity : AppCompatActivity() {

private lateinit var  user: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

         user = FirebaseAuth.getInstance()

        ajouterTel.setOnClickListener {
            var i = Intent(this,ajouterTelephone::class.java)
            startActivity(i)
            finish()
        }

        listeTel.setOnClickListener {
            var i = Intent(this,com.example.gestiondetelephone.listeTel::class.java)
            startActivity(i)
            finish()
        }

        signOutbtn.setOnClickListener {
            user.signOut()
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
            Toast.makeText(this, "user signed out successfullly", Toast.LENGTH_SHORT).show()
            finish()
        }

    }



}












