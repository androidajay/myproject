package com.uandme.PhoneNumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.uandme.R
import com.uandme.databinding.ActivitySignupWithMoblileNumberBinding
import java.util.concurrent.TimeUnit

class Signup_with_moblile_numberActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupWithMoblileNumberBinding
    private lateinit var auth: FirebaseAuth
    var number: String = ""

    // we will use this to match the sent otp from firebase
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_with_moblile_number)
        initView()
    }

    private fun initView() {

        // Initialize Firebase Auth
        auth = Firebase.auth
        // start verification on click of the button
        binding.getOtpBtn.setOnClickListener {
            login()
        }

        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // This method is called when the verification is completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // startActivity(Intent(applicationContext, MainActivity::class.java))
                // finish()
                Log.d("Msg", "onVerificationCompleted Success")
            }

            // Called when verification is failed add log statement to see the exception
            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("MSg", "onVerificationFailed  $e")
            }

            // On code is sent by the firebase this method is called
            // in here we start a new activity where user can enter the OTP
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("Msg", "onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token

                // Start a new activity using intent
                // also send the storedVerificationId using intent
                // we will use this id to send the otp back to firebase
                // val intent = Intent(applicationContext, OTPActivity::class.java)
                //  intent.putExtra("storedVerificationId", storedVerificationId)
                //   startActivity(intent)
                //  finish()
            }
        }
    }

    private fun login() {
        number = binding.moblieEt.text.toString()

        // get the phone number from edit text and append the country cde with it
        if (number.isNotEmpty()) {
            number = "+91$number"
            sendVerificationCode(number)
        } else {
            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }

    // this method sends the verification code
    // and starts the callback of verification
    // which is implemented above in onCreate
    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("Msg", "Auth started")
    }


    override fun onStart() {
        super.onStart()


    }
}