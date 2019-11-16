package com.crushmateapp.crushmate.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.crushmateapp.crushmate.R

import com.crushmateapp.crushmate.util.Message


class MessageAdapter( private var messagelist: ArrayList<Message>,val userId: String) : RecyclerView.Adapter<MessageAdapter.myviewHolder>() {

    fun addMessage(message: Message) {
        messagelist.add(message)
        notifyDataSetChanged()
    }

    companion object {
        val MESSAGE_CURRENT_USER = 1
        val MESSAGE_OTHER_USER = 2
    }

    class myviewHolder( val view: View): RecyclerView.ViewHolder(view)



    override fun onCreateViewHolder(parent: ViewGroup, itemViewType: Int):myviewHolder {

        var li =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if(itemViewType == MESSAGE_CURRENT_USER) {

            val view = li.inflate(R.layout.user1_message, parent, false)
            return myviewHolder(view)
        }
        else{
            val view = li.inflate(R.layout.user2_message, parent, false)
            return myviewHolder(view)
        }


    }
    override fun getItemViewType(position: Int): Int {
        if(messagelist[position].sentBy.equals(userId)) {
            return MESSAGE_CURRENT_USER
        } else {
            return MESSAGE_OTHER_USER
        }
    }
    override fun getItemCount() = messagelist.size

    override fun onBindViewHolder(holder: myviewHolder, position: Int) {
        val item1=this.messagelist[position]

        var mess = holder.view.findViewById<TextView>(R.id.usermessage)
       mess.text=item1.message





    }

}