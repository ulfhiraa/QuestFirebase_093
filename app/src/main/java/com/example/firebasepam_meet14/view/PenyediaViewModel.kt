package com.example.firebasepam_meet14.view

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.firebasepam_meet14.MahasiswaApplications
import com.example.firebasepam_meet14.ui.viewmodel.HomeViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        // HOME
        initializer {
            HomeViewModel(
                MahasiswaApplications()
                    .container
                    .mahasiswaRepository
            )
        }
    }

    fun CreationExtras.MahasiswaApplications(): MahasiswaApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplications)
}