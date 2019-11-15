package com.crushmateapp.crushmate.activities

import com.google.firebase.database.DatabaseReference

interface CallbackInterace {

    fun onSignout()
    fun onGetUserId(): String
    fun getUserDatabase(): DatabaseReference
    fun profileComplete()
    fun ActivityForPhoto()
    fun getChatDatabase(): DatabaseReference
}