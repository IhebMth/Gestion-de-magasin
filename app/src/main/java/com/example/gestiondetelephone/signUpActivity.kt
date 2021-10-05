package com.example.gestiondetelephone

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log

import android.widget.Toast

import com.example.gestiondetelephone.databinding.ActivitySignUpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.loginButton
import kotlinx.android.synthetic.main.activity_main.signUpButton
import kotlinx.android.synthetic.main.activity_sign_up.*

import java.util.concurrent.TimeUnit



class SignUpActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loginBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
           // onBackPressed()
        }


        binding.signUpButton.setOnClickListener {


            if (nom.text.toString().isEmpty())
            {
                Toast.makeText(
                    this,
                    "entrez  votre nom  s'il vous plait",
                    Toast.LENGTH_SHORT
                ).show()
                nom.requestFocus()

            }

            else  if (prenom.text.toString().isEmpty())
            {
                Toast.makeText(
                    this,
                    "entrez  votre prénom  s'il vous plait",
                    Toast.LENGTH_SHORT
                ).show()
                prenom.requestFocus()

            }

            else if (nomUtilisateur.text.toString().isEmpty())
            {
                Toast.makeText(
                    this,
                    "entrez  un nom d'utilisateur s'il vous plait",
                    Toast.LENGTH_SHORT
                ).show()
                nomUtilisateur.requestFocus()

            }

            else if (email.text.toString().isEmpty())
            {
                Toast.makeText(
                    this,
                    "entrez  votre email s'il vous plait",
                    Toast.LENGTH_SHORT
                ).show()
                email.requestFocus()

            }else {
                val nom = binding.nom.text.toString()
                val prenom = binding.prenom.text.toString()
                val nomUtilisateur = binding.nomUtilisateur.text.toString()
                val email = binding.email.text.toString()
                var numTel = binding.numTel.text.toString().toInt()


                database = FirebaseDatabase.getInstance().getReference("Users")
                val User = User(prenom, nomUtilisateur, email, numTel)
                database.child(nom).setValue(User).addOnSuccessListener {

                    binding.nom.text?.clear()
                    binding.prenom.text?.clear()
                    binding.nomUtilisateur.text?.clear()
                    binding.email.text?.clear()
                    binding.numTel.text?.clear()


                }
            }
            val intent = Intent(this, Verify::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser


        if (currentUser != null) {
            startActivity(Intent(applicationContext, homeActivity::class.java))
            finish()
        }

        signUpButton.setOnClickListener{
            login()
        }


        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //auth.signInWithCredential(credential).addOnCompleteListener() {
                Log.d(TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)



                startActivity(Intent(applicationContext, homeActivity::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "Please enter a valid number", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG", "onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token

                var intent = Intent(applicationContext, Verify::class.java)
                intent.putExtra("storedVerificationId", storedVerificationId)
                startActivity(intent)
            }
        }
    }



    private fun login() {
        var number = binding.numTel.text.toString().trim()
        if (!number.isEmpty()) {
            number = "+216" + number
            sendVerificationcode(number)
        } else {
            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    startActivity(Intent (this, MainActivity::class.java))
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                   Toast.makeText(baseContext, "Sign up failed.", Toast.LENGTH_LONG).show()
                    }
                    // Update UI
                }
            }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
    }


}