package com.example.gestiondetelephone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils.formatNumber
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.EditText

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_verify.*
import java.lang.StringBuilder
import java.text.DecimalFormat

class Verify : AppCompatActivity(), TextWatcher {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)



        auth = FirebaseAuth.getInstance()
        val storedVerificationId = intent.getStringExtra("storedVerificationId")

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
            } else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }

        }

        init()
    }




    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(applicationContext, homeActivity::class.java))
                    finish()
// ...
                } else {
// Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
// The verification code entered was invalid
                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }


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


}