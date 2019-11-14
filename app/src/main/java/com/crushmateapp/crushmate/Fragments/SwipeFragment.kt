package com.crushmateapp.crushmate.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.crushmateapp.crushmate.R
import com.crushmateapp.crushmate.activities.TinderCallback
import com.google.firebase.database.DatabaseReference

/**
 * A simple [Fragment] subclass.
 */
class SwipeFragment : Fragment() {
    private lateinit var userId: String
    private lateinit var userDatabase: DatabaseReference
    private var callback: TinderCallback? = null

    fun setCallback(callback: TinderCallback) {
        this.callback = callback
        userId = callback.onGetUserId()
        userDatabase = callback.getUserDatabase().child(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }


}
