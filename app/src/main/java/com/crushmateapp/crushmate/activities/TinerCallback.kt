package com.crushmateapp.crushmate.activities

import com.google.firebase.database.DatabaseReference

interface TinerCallback {

    fun onSignout()
    fun onGetUserId(): String
    fun getUserDatabase(): DatabaseReference

}