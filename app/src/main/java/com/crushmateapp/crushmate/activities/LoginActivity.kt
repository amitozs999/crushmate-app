package com.crushmateapp.crushmate.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.crushmateapp.crushmate.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser
        if(user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {
            if(!emaillogin.text.toString().isNullOrEmpty() && !passwordlogin.text.toString().isNullOrEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(emaillogin.text.toString(), passwordlogin.text.toString())
                    .addOnCompleteListener { task ->
                        if(!task.isSuccessful) {
                            Toast.makeText(this, "Login error ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                    }

            }

        }
    }
    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }
    fun onlogin(v: View) {

        startActivity(LoginActivity.newIntent(this))
    }

    companion object {
        fun newIntent(context: Context?) = Intent(context, LoginActivity::class.java)
    }
}
