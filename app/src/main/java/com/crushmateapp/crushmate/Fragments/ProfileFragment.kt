package com.crushmateapp.crushmate.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.crushmateapp.crushmate.R
import com.crushmateapp.crushmate.activities.CallbackInterace
import com.crushmateapp.crushmate.util.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    private lateinit var userId: String
    private lateinit var userDatabase: DatabaseReference
    private var callback: CallbackInterace? = null

    fun setCallback(callback: CallbackInterace) {
        this.callback = callback
        userId = callback.onGetUserId()
        userDatabase = callback.getUserDatabase().child(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progresslay.setOnTouchListener { view, event -> true }

        populateInfo()

        image1.setOnClickListener{
            callback?.ActivityForPhoto()   // activityforphoto is a function of interface callback which is implemented inside tinderactivity
        }
        applyButton.setOnClickListener { onApply() }
        signoutButton.setOnClickListener { callback?.onSignout() }
    }


    fun populateInfo() {
        progresslay.visibility = View.VISIBLE
        userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                progresslay.visibility = View.GONE
            }

            override fun onDataChange(p0: DataSnapshot) {
                //return true if fragment is added to its activity
                if (isAdded) {

                    val user = p0.getValue(User::class.java)
                    nameet.setText(user?.name, TextView.BufferType.EDITABLE)
                    emailet.setText(user?.email, TextView.BufferType.EDITABLE)
                    ageet.setText(user?.age, TextView.BufferType.EDITABLE)
                    if (user?.gender == "male") {
                        radioMan1.isChecked = true
                    }
                    if (user?.gender == "female") {
                        radioWoman1.isChecked = true
                    }
                    if (user?.preferredGender == "male") {
                        radioMan2.isChecked = true
                    }
                    if (user?.preferredGender == "female") {
                        radioWoman2.isChecked = true
                    }
                    if(!user?.imageurl.isNullOrEmpty()) {
                        populateImage(user?.imageurl!!)
                    }
                    progresslay.visibility = View.GONE
                }
            }

        })
    }

    fun onApply() {
        if (nameet.text.toString().isNullOrEmpty() ||
            emailet.text.toString().isNullOrEmpty() ||
            radioGroup1.checkedRadioButtonId == -1 ||
            radioGroup2.checkedRadioButtonId == -1
        ) {
            Toast.makeText(context, "incomplete", Toast.LENGTH_SHORT).show()
        } else {

            val name1 = nameet.text.toString()
            val age1 = ageet.text.toString()
            val email1 = emailet.text.toString()
            val gender1 =
                if (radioMan1.isChecked) "male"
                else "female"
            val preferredGender1 =
                if (radioMan2.isChecked) "male"
                else "female"

            userDatabase.child("name").setValue(name1)
            userDatabase.child("age").setValue(age1)
            userDatabase.child("email").setValue(email1)
            userDatabase.child("gender").setValue(gender1)
            userDatabase.child("preferredGender").setValue(preferredGender1)

            callback?.profileComplete()
        }
    }
    fun updateImageUri(uri: String) {
        userDatabase.child("imageurl").setValue(uri)
        populateImage(uri)
    }
    fun populateImage(uri: String){
        Picasso.get().load(uri).resize(200,250).into(image1)

    }

}
