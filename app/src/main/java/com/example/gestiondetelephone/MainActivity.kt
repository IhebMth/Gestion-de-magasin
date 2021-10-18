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
    private  val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {


        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(applicationContext, "Please enter a valid number", Toast.LENGTH_LONG)
                .show()
        }


        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {

            Log.d("TAG", "onCodeSent:$verificationId")
            storedVerificationId = verificationId
            resendToken = token

            var intent = Intent(applicationContext, Verify::class.java)
            intent.putExtra("NumTel", numTel.text.toString().trim())
            intent.putExtra("storedVerificationId", storedVerificationId)
            startActivity(intent)
            finishAffinity()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))

        }




        auth = FirebaseAuth.getInstance()





        loginButton.setOnClickListener {


            var number = numTel.text.toString()
            when {

                TextUtils.isEmpty(number.trim { it <= ' ' }) -> {
                    Toast.makeText(this,
                        "please enter mobile number",
                        Toast.LENGTH_SHORT).show()

                }
                else -> {
                    // log in with firebaseAuth
                    login()

                }
            }
        }



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




