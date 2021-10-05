package com.example.gestiondetelephone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gestiondetelephone.databinding.ActivityAjouterTelephoneBinding
import com.example.gestiondetelephone.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_ajouter_telephone.*
import kotlinx.android.synthetic.main.activity_home.*

class ajouterTelephone : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityAjouterTelephoneBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_telephone)
        binding = ActivityAjouterTelephoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listeTel.setOnClickListener {
            startActivity(Intent(this, listeTel::class.java))
        }

        binding.ajouterButton.setOnClickListener {
            if (nomTel.text.toString().isEmpty()) {
                Toast.makeText(
                    this,
                    "entrez  le nom de téléphone s'il vous plait",
                    Toast.LENGTH_SHORT
                ).show()
                nomTel.requestFocus()

            }

            else if (sysEx.text.toString().isEmpty()) {
                Toast.makeText(
                    this,
                    "entrez  le système d'exploiataion s'il vous plait",
                    Toast.LENGTH_SHORT
                ).show()
                sysEx.requestFocus()


            } else if (ram.text.toString().isEmpty()) {
                Toast.makeText(this, "entrez  la mémoire vive  s'il vous plait", Toast.LENGTH_SHORT)
                    .show()
                ram.requestFocus()

            } else if (cap.text.toString().isEmpty()) {
                Toast.makeText(
                    this,
                    "entrez la capacité du téléphone  s'il vous plait",
                    Toast.LENGTH_SHORT
                ).show()
                cap.requestFocus()

            } else  {
                val nomTel = binding.nomTel.text.toString()
                val sysEx = binding.sysEx.text.toString()
                val ram = binding.ram.text.toString()
                val cap = binding.cap.text.toString()




                database = FirebaseDatabase.getInstance().getReference("Tel")
                val Tel1 = Tel( nomTel,sysEx, ram, cap)
                database.child(nomTel).setValue(Tel1).addOnSuccessListener {

                    binding.nomTel.text?.clear()
                    binding.sysEx.text?.clear()
                    binding.ram.text?.clear()
                    binding.cap.text?.clear()


                }
            }
            }
        }




        override fun onBackPressed() {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }
    }






