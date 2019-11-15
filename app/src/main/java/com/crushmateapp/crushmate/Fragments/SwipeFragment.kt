package com.crushmateapp.crushmate.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.crushmateapp.crushmate.Adapters.CardAdapter


import com.crushmateapp.crushmate.R
import com.crushmateapp.crushmate.activities.TinderCallback
import com.crushmateapp.crushmate.util.DATA_MATCHES
import com.crushmateapp.crushmate.util.DATA_SWIPES_LEFT
import com.crushmateapp.crushmate.util.DATA_SWIPES_RIGHT
import com.crushmateapp.crushmate.util.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import kotlinx.android.synthetic.main.fragment_swipe.*

/**
 * A simple [Fragment] subclass.
 */
class SwipeFragment : Fragment() {
    private lateinit var userId: String
    private lateinit var userDatabase: DatabaseReference
    private var callback: TinderCallback? = null

    private var cardAdapter: ArrayAdapter<User>? = null
    private var Itemslist = ArrayList<User>()

    private var preferredGender: String? = null
    private var userName: String? = null
    private var imageUrl: String? = null

    fun setCallback(callback: TinderCallback) {
        this.callback = callback
        userId = callback.onGetUserId()
        userDatabase = callback.getUserDatabase().child(userId)    //callback interface calls getuderdatabase() of this child userid
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


         //add listener for single change in dat at this loation (addlfse)
        // user to recieve change about data change (value eventlistener)

        userDatabase.child(userId).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                val user = p0.getValue(User::class.java)
                preferredGender = user?.preferredGender
                userName = user?.name
                imageUrl = user?.imageurl
                populateItems()

            }

        })
        cardAdapter = CardAdapter(context, R.layout.item, Itemslist)
        frame.adapter = cardAdapter
        frame.setFlingListener(object :SwipeFlingAdapterView.onFlingListener{
            override fun removeFirstObjectInAdapter() {

            }

            override fun onLeftCardExit(p0: Any?) {

            }

            override fun onRightCardExit(p0: Any?) {

            }

            override fun onAdapterAboutToEmpty(p0: Int) {

            }

            override fun onScroll(p0: Float) {

            }

        })
    }

    fun populateItems() {
        noUsersLay.visibility = View.GONE
        progressLay.visibility = View.VISIBLE
        val cardsQuery = userDatabase.orderByChild("gender").equalTo(preferredGender)
        cardsQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (ds in p0.getChildren()) {
                    val user=ds.getValue(User::class.java)


                }


//                p0.children.forEach { child->
//                    val user=child.getValue(User::class.java)
//
//                    if (user != null) {
//                        var showUser = true
//                        if (child.child(DATA_SWIPES_LEFT).hasChild(userId) ||
//                            child.child(DATA_SWIPES_RIGHT).hasChild(userId) ||
//                            child.child(DATA_MATCHES).hasChild(userId)
//                        ) {
//                            showUser = false
//                        }
//                        if (showUser) {
//                            Itemslist.add(user)
//                            cardAdapter?.notifyDataSetChanged()
//                        }
//                    }
//                }


                progressLay.visibility = View.GONE
                if (Itemslist.isEmpty()) {
                    noUsersLay.visibility = View.VISIBLE
                }
            }
        })
    }
}
