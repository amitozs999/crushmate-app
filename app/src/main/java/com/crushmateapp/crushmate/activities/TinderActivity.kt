package com.crushmateapp.crushmate.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.crushmateapp.crushmate.Fragments.MatchesFragment
import com.crushmateapp.crushmate.Fragments.ProfileFragment
import com.crushmateapp.crushmate.Fragments.SwipeFragment
import com.crushmateapp.crushmate.R
import com.crushmateapp.crushmate.util.DATA_USERS
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import kotlinx.android.synthetic.main.activity_main.*

class TinderActivity : AppCompatActivity(),TinerCallback {



    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userId = firebaseAuth.currentUser?.uid
    private lateinit var userDatabase: DatabaseReference
    private var mylist = ArrayList<String>()
    private var arrayAdapter: ArrayAdapter<String>? = null
    private var i = 0

    private var profileFragment: ProfileFragment? = null
    private var swipeFragment: SwipeFragment? = null
    private var matchesFragment: MatchesFragment? = null

    private var profileTab: TabLayout.Tab? = null
    private var swipeTab: TabLayout.Tab? = null
    private var matchesTab: TabLayout.Tab? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(userId.isNullOrEmpty()) {
            onSignout()
        }

        userDatabase = FirebaseDatabase.getInstance().reference.child(DATA_USERS)

        profileTab = navigationTabs.newTab()
        swipeTab = navigationTabs.newTab()
        matchesTab = navigationTabs.newTab()

        profileTab?.icon = ContextCompat.getDrawable(this, R.drawable.tab_profile)
        swipeTab?.icon = ContextCompat.getDrawable(this, R.drawable.tab_swipe)
        matchesTab?.icon = ContextCompat.getDrawable(this, R.drawable.tab_matches)

        navigationTabs.addTab(profileTab!!)
        navigationTabs.addTab(swipeTab!!)
        navigationTabs.addTab(matchesTab!!)

        navigationTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                onTabSelected(tab)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab) {
                    profileTab -> {
                        if (profileFragment == null) {
                            profileFragment = ProfileFragment()

                        }
                        replaceFragment(profileFragment!!)
                    }
                    swipeTab -> {
                        if (swipeFragment == null) {
                            swipeFragment = SwipeFragment()

                        }
                        replaceFragment(swipeFragment!!)
                    }
                    matchesTab -> {
                        if (matchesFragment == null) {
                            matchesFragment = MatchesFragment()

                        }
                        replaceFragment(matchesFragment!!)
                    }
                }
            }

        })

        profileTab?.select()  //bydefault

    }

    override fun onSignout() {
        firebaseAuth.signOut()



        val intent=Intent(this,StartActivity::class.java)
         startActivity(intent)
        finish()

    }

    override fun onGetUserId(): String {
        return userId!!

    }

    override fun getUserDatabase(): DatabaseReference {
        return userDatabase

    }
    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

}