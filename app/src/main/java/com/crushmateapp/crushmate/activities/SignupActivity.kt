package com.crushmateapp.crushmate.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.crushmateapp.crushmate.R
import com.crushmateapp.crushmate.util.DATA_USERS
import com.crushmateapp.crushmate.util.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private val firebaseDatabase = FirebaseDatabase.getInstance().reference
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser
        if(user != null) {
            val intent = Intent(this, TinderActivity::class.java)
            startActivity(intent)

            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupButton.setOnClickListener {
            if(!emailsu.text.toString().isNullOrEmpty() && !passwordsu.text.toString().isNullOrEmpty()) {

                firebaseAuth.createUserWithEmailAndPassword(emailsu.text.toString(), passwordsu.text.toString())
                    .addOnCompleteListener { task ->
                        if(!task.isSuccessful) {
                            Toast.makeText(this, "Signup error ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                        else{


                            val email = emailsu.text.toString()
                            val userId = firebaseAuth.currentUser?.uid ?: ""
                            val user = User(userId, "", "", email, "", "","")
                            firebaseDatabase.child(DATA_USERS).child(userId).setValue(user)

//                             users
//                               |
//                            userid
//                               |_name
//                               |_email
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

    fun onsignup(v: View) {
        startActivity(SignupActivity.newIntent(this))
    }
    companion object{
        fun newIntent(context: Context?) = Intent(context, SignupActivity::class.java)
    }
}
