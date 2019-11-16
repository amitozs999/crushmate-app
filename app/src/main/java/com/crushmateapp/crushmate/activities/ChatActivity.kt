package com.crushmateapp.crushmate.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.crushmateapp.crushmate.Adapters.MessageAdapter
import com.crushmateapp.crushmate.R
import com.crushmateapp.crushmate.util.DATA_CHATS
import com.crushmateapp.crushmate.util.DATA_MESSAGES
import com.crushmateapp.crushmate.util.Message
import com.crushmateapp.crushmate.util.User
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {


    private var chatId: String? = null
    private var userId: String? = null
    private var imageUrl: String? = null
    private var otherUserId: String? = null

    private lateinit var chatDatabase: DatabaseReference

    private lateinit var messagesAdapter: MessageAdapter

    private val chatMessagesListener = object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val message = p0.getValue(Message::class.java)
            if(message != null) {
                messagesAdapter.addMessage(message)
                messagesRV.post {
                    messagesRV.smoothScrollToPosition(messagesAdapter.itemCount - 1)
                }
            }
        }

        override fun onChildRemoved(p0: DataSnapshot) {
        }

    }

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


        chatDatabase = FirebaseDatabase.getInstance().reference.child(DATA_CHATS)
        messagesAdapter = MessageAdapter(ArrayList(), userId!!)
        messagesRV.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = messagesAdapter
        }


        chatDatabase.child(chatId!!).child(DATA_MESSAGES).addChildEventListener(chatMessagesListener)

        chatDatabase.child(chatId!!).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach { value ->
                    val key = value.key
                    val user = value.getValue(User::class.java)
                    if(!key.equals(userId)) {
                        chattername.text = user?.name
                        if((user!!.imageurl).isNullOrEmpty()) {
                            chatterimage.setImageResource(R.drawable.default_pic)

                        }
                        else{
                            Picasso.get()
                                .load(user?.imageurl)
                                .into(chatterimage)
                        }

                        chatterimage.setOnClickListener {
                            val intent= Intent(this@ChatActivity,UserDetailActivity::class.java)
                            intent.putExtra("idpassed",otherUserId)
                            startActivity(intent)

                        }
                    }
                }
            }

        })

        sendButton.setOnClickListener {
            val message = Message(userId, messageET.text.toString(), Calendar.getInstance().time.toString())
            val key = chatDatabase.child(chatId!!).child(DATA_MESSAGES).push().key
            if(!key.isNullOrEmpty()) {
                chatDatabase.child(chatId!!).child(DATA_MESSAGES).child(key).setValue(message)
            }
            messageET.setText("", TextView.BufferType.EDITABLE)
        }
    }

    }


