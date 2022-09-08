package com.uandme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.uandme.PhoneNumber.Signup_with_moblile_numberActivity
import com.uandme.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        initView()
    }

    private fun initView() {
        // ...
// Initialize Firebase Auth
        auth = Firebase.auth

        binding.loginBtn.setOnClickListener {
            if (valiadtion()) {
                auth.createUserWithEmailAndPassword(
                    binding.emailEt.text.toString().trim(),
                    binding.passwordEt.text.toString().trim()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "successfull registered", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.signupViaNumberTv.setOnClickListener {
            startActivity(Intent(this, Signup_with_moblile_numberActivity::class.java))
        }
    }


    private fun valiadtion(): Boolean {
        if (binding.emailEt.text?.trim()!!.isEmpty()) {
            Toast.makeText(this, "Please enter the email", Toast.LENGTH_LONG).show()

            return false
        } else if (binding.passwordEt.text?.trim()!!.isEmpty()) {
            Toast.makeText(this, "Please enter the password", Toast.LENGTH_LONG).show()

            return false
        } else {
            return true
        }

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //reload();
        }
    }

}