package com.uandme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.uandme.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()



        Log.e("TAG", "onCreate: ", )

    }

    private fun initView() {
        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.loginBtn.setOnClickListener {
            if (validation()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.emailEt.text.toString().trim(),
                    binding.passwordEt.text.toString().trim()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "successfull Login", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validation(): Boolean {
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
        Log.e("TAG", "onStart: ", )
    }
}