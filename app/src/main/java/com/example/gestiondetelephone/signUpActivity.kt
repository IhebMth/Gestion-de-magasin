package com.example.gestiondetelephone

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.R
import androidx.core.app.BundleCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.gestiondetelephone.databinding.ActivityHomeBinding
import com.example.gestiondetelephone.databinding.ActivityMainBinding
import com.example.gestiondetelephone.databinding.ActivitySignUpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.concurrent.TimeUnit


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {

            val nom = binding.nom.text.toString()
            val prenom = binding.prenom.text.toString()
            val nomUtilisateur = binding.nomUtilisateur.text.toString()
            val email = binding.email.text.toString()
            val numTel = binding.numTel.text.toString().toInt()

            database = FirebaseDatabase.getInstance().getReference("Users")
            val User = User(nom, prenom, nomUtilisateur, email, numTel)
            database.child(nom).setValue(User).addOnSuccessListener {

                binding.nom.text.clear()
                binding.prenom.text.clear()
                binding.nomUtilisateur.text.clear()
                binding.nomUtilisateur.text.clear()
                binding.email.text.clear()
                binding
            }


        }


    }

    fun signUp(view: View) {
        val intent = Intent(this, homeActivity::class.java)
        intent.putExtra("mobile", numTel.getText().toString())
        startActivity(intent)
        Toast.makeText(this, "hello", Toast.LENGTH_LONG).show()
    }
    }
