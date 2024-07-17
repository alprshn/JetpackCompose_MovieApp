package com.example.movieapp

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApp: Application(){
    override fun onCreate() {
        super.onCreate()
    }
}