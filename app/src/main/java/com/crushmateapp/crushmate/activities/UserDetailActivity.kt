package com.crushmateapp.crushmate.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crushmateapp.crushmate.R
import com.crushmateapp.crushmate.util.DATA_USERS
import com.crushmateapp.crushmate.util.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)


        val id = intent.getStringExtra("idpassed")
        if(id.isNullOrEmpty()) {
            finish()
        }
        val userDatabase = FirebaseDatabase.getInstance().reference.child(DATA_USERS)
        userDatabase.child(id).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)
                detailtv.text = user?.name

                if(user?.imageurl != null) {

                    Picasso.get().load(user.imageurl).into(detailiv)
                }
            }

        })


    }
}
