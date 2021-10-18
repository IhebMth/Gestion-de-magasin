package com.example.gestiondetelephone

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.EditText

import android.widget.Toast
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_verify.*

class Verify : AppCompatActivity(), TextWatcher {
    val userArrayList = mutableListOf<User>()
    lateinit var auth: FirebaseAuth
    lateinit var number: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)


        auth = FirebaseAuth.getInstance()
        val storedVerificationId = intent.getStringExtra("storedVerificationId")
        number = intent.getStringExtra("NumTel").toString()


        verifyBtn.setOnClickListener {
            var otp1 = codeOne.text.toString().trim()
            var otp2 = codeTwo.text.toString().trim()
            var otp3 = codeThree.text.toString().trim()
            var otp4 = codeFour.text.toString().trim()
            var otp5 = codeFive.text.toString().trim()
            var otp6 = codeSix.text.toString().trim()

            var otp = otp1 + otp2 + otp3 + otp4 + otp5 + otp6
            if (!otp.isEmpty()) {
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp
                )
                signInWithPhoneAuthCredential(credential)
            }
            // el if hedhi lkolha zeyda ya iheb el storedVerificationId 7aja o5ra mahiech nafsha el otp
            /*else if (otp != storedVerificationId)
            {
                Toast.makeText(this, "Enter a valid OTP", Toast.LENGTH_SHORT).show()
            }

            */ else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }

        }

        init()
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Log.d(TAG, "onVerificationCompleted:$credential")
                    Toast.makeText(
                        this,
                        "You are logged in successfully",
                        Toast.LENGTH_SHORT).show()
                    getUserData()

// ...
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
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


    private fun getUserData() {

        //table users mahiech child mta3 table Tel
        var dbref = FirebaseDatabase.getInstance().getReference("Users")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    userArrayList.clear()

                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)

                    }


                    if (userArrayList.isEmpty()) {

                        var intent = Intent(applicationContext, UpdateUser::class.java)
                        startActivity(intent)
                        Toast.makeText(applicationContext,
                            "you have missing info you need to need complete it here",
                            Toast.LENGTH_SHORT).show()

                    } else {

                        for (user in userArrayList) {
                            if (user.numTel!!.toString() == number) {
                                var intent = Intent(applicationContext, listeTel::class.java)
                                startActivity(intent)
                                finishAffinity()
                            }
                        }

                        var intent = Intent(applicationContext, UpdateUser::class.java)
                        startActivity(intent)
                        Toast.makeText(applicationContext,
                            "you have missing info you need to need complete it here",
                            Toast.LENGTH_SHORT).show()
                    }


                }
            }


            override fun onCancelled(error: DatabaseError) {
                if (error != null) {

                }
            }


        })

    }


    private fun init() {
        codeOne.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        codeOne.addTextChangedListener(this)
        codeTwo.addTextChangedListener(this)
        codeThree.addTextChangedListener(this)
        codeFour.addTextChangedListener(this)
        codeFive.addTextChangedListener(this)
        codeSix.addTextChangedListener(this)

        codeSix.tag = true

    }


    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            when {
                codeTwo.hasFocus() -> {
                    getBackFocus(codeOne)
                    codeTwo.isFocusableInTouchMode = false

                }
                codeThree.hasFocus() -> {
                    getBackFocus(codeTwo)
                    codeThree.isFocusableInTouchMode = false
                }
                codeFour.hasFocus() -> {
                    getBackFocus(codeThree)
                    codeFour.isFocusableInTouchMode = false
                }
                codeFive.hasFocus() -> {
                    getBackFocus(codeFour)
                    codeFour.isFocusableInTouchMode = false
                }
                codeSix.hasFocus() -> {
                    if (codeSix.tag as Boolean) {
                        getBackFocus(codeFive)
                        codeSix.isFocusableInTouchMode = false
                    } else {
                        codeSix.tag = true
                    }
                }
            }
        }
        return super.onKeyUp(keyCode, event)
    }


    //    Text change listener, whenever user will enter or remove anything from the editText this listener will be called
    override fun afterTextChanged(s: Editable?) {
        if (s != null) {
            if (!!s.isNotEmpty()) {
                when {
                    codeSix.hasFocus() -> {
                        codeSix.tag = false

                    }
                    codeFive.hasFocus() -> {
                        getNextFocus(codeSix)
                        codeFive.isFocusableInTouchMode = false
                    }
                    codeFour.hasFocus() -> {
                        getNextFocus(codeFive)
                        codeFour.isFocusableInTouchMode = false
                    }
                    codeThree.hasFocus() -> {
                        getNextFocus(codeFour)
                        codeThree.isFocusableInTouchMode = false
                    }
                    codeTwo.hasFocus() -> {
                        getNextFocus(codeThree)
                        codeTwo.isFocusableInTouchMode = false
                    }
                    codeOne.hasFocus() -> {
                        getNextFocus(codeTwo)
                        codeOne.isFocusableInTouchMode = false
                    }
                }
            }
        }
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    //    When user enters the code and we move the focus from one editText to the next one.
    private fun getNextFocus(editText: EditText) {
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
    }


    //    When user presses the delete button to remove the code and we move the focus back to the previous editText.
    private fun getBackFocus(editText: EditText) {
        editText.isFocusableInTouchMode = true
        editText.setText("")
        editText.requestFocus()
    }

    fun logIn(view: android.view.View) {

        startActivity(Intent(this, MainActivity::class.java))
    }


}