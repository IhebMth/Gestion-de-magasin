package com.example.gestiondetelephone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestiondetelephone.listeTel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_liste_tel.*

class homeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        ajouterTel.setOnClickListener {
            var i = Intent(this,ajouterTelephone::class.java)
            startActivity(i)
            finish()
        }

        listeTel.setOnClickListener {
            var i = Intent(this,homeActivity::class.java)
            startActivity(i)
            finish()
        }

    }



}












