package com.example.gestiondetelephone

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_main.loginButton
import kotlinx.android.synthetic.main.activity_main.numTel
import kotlinx.android.synthetic.main.activity_main.signUpButton
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {


    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()


        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))

        }



        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser


        if (currentUser != null) {
            startActivity(Intent(applicationContext, homeActivity::class.java))
            finish()
        }

    loginButton.setOnClickListener {
        var number = numTel.text.toString()
        when {

            TextUtils.isEmpty(number.trim { it <= ' ' }) -> {
                Toast.makeText(this,
                    "please enter mobile numner",
                    Toast.LENGTH_SHORT).show()

            }
            else -> {
                val num = number.trim { it <= ' ' }
                // log in with firebaseAuth
                    login()

            }
        }
    }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

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




    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    Toast.makeText(
                        this,
                        "You are logged in successfully",
                    Toast.LENGTH_SHORT).show()

                    startActivity(Intent(applicationContext, homeActivity::class.java))
                    finish()
// ...
                } else {
                        Toast.makeText(
                            this,
                            task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT).show()

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
// The verification code entered was invalid
                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }



    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUi(currentUser)
    }

    private fun updateUi(currentUser: FirebaseUser?) {

    }


    private fun login() {

        var number = numTel.text.toString().trim()

        if (number != null) {

                number = "+216" + number
                sendVerificationcode(number)
            } else {
                Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
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


}




