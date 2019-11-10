package com.crushmateapp.crushmate.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.crushmateapp.crushmate.R

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }



    fun onsignup(v: View) {
        startActivity(SignupActivity.newIntent(this))
    }
    companion object{
        fun newIntent(context: Context?) = Intent(context, SignupActivity::class.java)
    }
}
