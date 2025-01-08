package com.example.firebasepam_meet14

import android.app.Application
import com.example.firebasepam_meet14.di.AppContainer
import com.example.firebasepam_meet14.di.MahasiswaContainer

//   untuk inisialisasi mengatur komponen yang akan dipakai di seluruh aplikasi
class MahasiswaApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container= MahasiswaContainer()
    }
}