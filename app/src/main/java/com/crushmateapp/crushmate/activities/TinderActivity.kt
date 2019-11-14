package com.crushmateapp.crushmate.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.crushmateapp.crushmate.R
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import kotlinx.android.synthetic.main.activity_main.*

class TinderActivity : AppCompatActivity() {

    private var mylist = ArrayList<String>()
    private var arrayAdapter: ArrayAdapter<String>? = null
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mylist.add("kylie jenner")
        mylist.add("kim kardesian")
        mylist.add("nicki minaj")

        mylist.add("taylor swift")


        //choose your favorite adapter
        arrayAdapter = ArrayAdapter<String>(this,
            R.layout.item,
            R.id.helloText, mylist)

        //set the listener and the adapter

    }
}