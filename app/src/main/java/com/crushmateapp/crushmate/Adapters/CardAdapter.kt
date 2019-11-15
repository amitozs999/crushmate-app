package com.crushmateapp.crushmate.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.crushmateapp.crushmate.R
import com.crushmateapp.crushmate.util.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*

class CardAdapter(context: Context?, resourceId: Int, users: List<User>): ArrayAdapter<User>(
    context!!, resourceId, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var user = getItem(position)
        var finalView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false)

        var name = finalView.findViewById<TextView>(R.id.namecv)
        var image = finalView.findViewById<ImageView>(R.id.imagecv)


        name.text = "${user!!.name}, ${user!!.age}"
        Picasso.get().load(user?.imageurl).resize(300,350).into(image)



        return finalView
    }
}