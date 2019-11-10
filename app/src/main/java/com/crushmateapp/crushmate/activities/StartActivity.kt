package com.crushmateapp.crushmate.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.crushmateapp.crushmate.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
//        button2.setOnClickListener {
//            val intent= Intent(this,LoginActivity::class.java)
//            startActivity(intent)
//        }


    }

        fun onlogin(v: View) {

            startActivity(LoginActivity.newIntent(this))
        }

        fun onsignup(v: View) {
            startActivity(SignupActivity.newIntent(this))
        }



    companion object{
        fun newIntent(context: Context?) = Intent(context, StartActivity::class.java)
    }
}
