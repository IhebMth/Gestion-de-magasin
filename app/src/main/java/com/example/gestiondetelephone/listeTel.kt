package com.example.gestiondetelephone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_liste_tel.*

class listeTel : AppCompatActivity() {

    val userArrayList = mutableListOf<Tel>()
    private  val MyAdapter = MyAdapter(userArrayList)
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_tel)
        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userRecyclerview.adapter = MyAdapter

        getTelData()

        homeSc.setOnClickListener{
            var i = Intent(this,homeActivity::class.java)
            startActivity(i)
            finish()
        }

        ajouterT.setOnClickListener{
            var i = Intent(this,ajouterTelephone::class.java)
            startActivity(i)
            finish()
        }


    }

    private fun getTelData() {

        dbref = FirebaseDatabase.getInstance().getReference("Tel")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    userArrayList.clear()
                    MyAdapter.notifyDataSetChanged()
                    for (telSnapshot in snapshot.children){


                        val tel = telSnapshot.getValue(Tel::class.java)
                        userArrayList.add(tel!!)

                    }

                    MyAdapter.notifyDataSetChanged()


                }

            }

            override fun onCancelled(error: DatabaseError){
                if (error != null )
                {

                }
            }


        })

    }
}

