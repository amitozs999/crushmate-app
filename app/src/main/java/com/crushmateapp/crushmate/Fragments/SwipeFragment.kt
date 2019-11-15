package com.crushmateapp.crushmate.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.crushmateapp.crushmate.Adapters.CardAdapter


import com.crushmateapp.crushmate.R
import com.crushmateapp.crushmate.activities.TinderCallback
import com.crushmateapp.crushmate.util.*
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

    private var callback: TinderCallback? = null
    private lateinit var userId: String
    private lateinit var userDatabase: DatabaseReference

    private var cardAdapter: ArrayAdapter<User>? = null
    private var Itemlist = ArrayList<User>()

    private var preferredGender: String? = null
    private var userName: String? = null
    private var imageUrl: String? = null

    fun setCallback(callback: TinderCallback) {
        this.callback = callback
        userId = callback.onGetUserId()
        userDatabase = callback.getUserDatabase()  ///owhole database not single user database databa

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDatabase.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
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

        cardAdapter = CardAdapter(context, R.layout.item, Itemlist)

        frame.adapter = cardAdapter
        frame.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                Itemlist.removeAt(0)
                cardAdapter?.notifyDataSetChanged()

            }

            override fun onLeftCardExit(p0: Any?) {
                var user = p0 as User
                userDatabase.child(user.uid.toString()).child(DATA_SWIPES_LEFT).child(userId).setValue(true)

                //   p0 users
                //   |_id2(swiped left)
                //              |__lefts=id1(user id ho swiped)=true


            }

            override fun onRightCardExit(p0: Any?) {

                val selectedUser = p0 as User
                val selectedUserId = selectedUser.uid
                if (!selectedUserId.isNullOrEmpty()) {
                    userDatabase.child(userId).child(DATA_SWIPES_RIGHT)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {

                                //there are two cases if both have swiped or just one swiped right
                                //if both swiped right then make matches value true for both
                                //else just make swiped right true
                                if (p0.hasChild(selectedUserId)) {
                                    Toast.makeText(context, "Match!", Toast.LENGTH_SHORT).show()


                                    //both siwiped right bcoz p0 has child seleteduserid
                                        userDatabase.child(userId).child(DATA_SWIPES_RIGHT).child(selectedUserId).removeValue()
                                        userDatabase.child(userId).child(DATA_MATCHES).child(selectedUserId).setValue(true)
                                        userDatabase.child(selectedUserId).child(DATA_MATCHES).child(userId).setValue(true)



                                } else {
                                    userDatabase.child(selectedUserId).child(DATA_SWIPES_RIGHT).child(userId)
                                        .setValue(true)
                                }
                            }
                        })
                }

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
                p0.children.forEach {

                    //   p0 is whole users database
                    //   users are its children
                    //    we loop for every children
                    //  get user using  val user = child.getValue(User::class.java)   as child
                    //   check if already (left swiped )(right wiped )or (matches) is done
                    //  check child
                    //           |->swiped left
                    //                    |->userid
                        child ->
                    val user = child.getValue(User::class.java)
                    if (user != null) {
                        var showUser = true
                        if (child.child(DATA_SWIPES_LEFT).hasChild(userId) ||
                            child.child(DATA_SWIPES_RIGHT).hasChild(userId) ||
                            child.child(DATA_MATCHES).hasChild(userId)
                        ) {
                            showUser = false
                        }
                        if (showUser) {
                            Itemlist.add(user)
                            cardAdapter?.notifyDataSetChanged()
                        }
                    }
                }
                progressLay.visibility = View.GONE
                if (Itemlist.isEmpty()) {
                    noUsersLay.visibility = View.VISIBLE
                }
            }
        })
    }

}