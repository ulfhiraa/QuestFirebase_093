package com.example.firebasepam_meet14.repository

import com.example.firebasepam_meet14.model.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface MahasiswaRepository {
    suspend fun getMahasiswa(): Flow<List<Mahasiswa>>

    suspend fun getMahasiswaByNim(nim: String):Flow<Mahasiswa>

    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

//    suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa)
    suspend fun updateMahasiswa(mahasiswa: Mahasiswa)

//    suspend fun deleteMahasiswa(nim: String )
    suspend fun deleteMahasiswa(mahasiswa: Mahasiswa )
}

