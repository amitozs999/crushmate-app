package com.crushmateapp.crushmate.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.crushmateapp.crushmate.R
import com.google.firebase.database.DatabaseReference

class ChatActivity : AppCompatActivity() {


    private var chatId: String? = null
    private var userId: String? = null
    private var imageUrl: String? = null
    private var otherUserId: String? = null

    private lateinit var chatDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatId = intent.getStringExtra("chatid")
        userId = intent.getStringExtra("userid")
        imageUrl = intent.getStringExtra("imageurl")
        otherUserId = intent.getStringExtra("otheruserid")
        if(chatId.isNullOrEmpty() || userId.isNullOrEmpty() || imageUrl.isNullOrEmpty() || otherUserId.isNullOrEmpty()) {
           
            finish()
        }
    }

}
