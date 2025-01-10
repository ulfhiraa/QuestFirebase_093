package com.example.firebasepam_meet14.repository

import com.example.firebasepam_meet14.model.Mahasiswa
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NetworkMahasiswaRepository (private val firestore: FirebaseFirestore)
    : MahasiswaRepository{

    override suspend fun insertMahasiswa(mahasiswa: Mahasiswa) {
        try {
            firestore.collection("Mahasiswa").add(mahasiswa).await()
        } catch (e: Exception) {
            throw Exception("Gagal menambahkan Data Mahasiswa: ${e.message}")
        }
    }

    override suspend fun getMahasiswa(): Flow<List<Mahasiswa>> = callbackFlow { // flow supaya datanya tetap urut meskipun operasinya asinkron
        val mhsCollection = firestore.collection("Mahasiswa") // nama collection, case sensitive
//            .orderBy("nama", Query.Direction.ASCENDING) // mengurutkan nama dari dari kecil ke besar atau dari A ke Z untuk teks
            .orderBy("nama", Query.Direction.DESCENDING) // mengurutkan nama dari dari besar ke kecil atau dari Z ke A untuk teks
            .addSnapshotListener { value, error ->  // agar datanya real time

                if (value != null) {
                    val mhsList = value.documents.mapNotNull {
                        it.toObject(Mahasiswa::class.java)!! // data dalam document dimasukkan ke dalam model
                    }
                    trySend(mhsList) // try send memberikan fungsi untuk mengirim data ke flow
                }
            }
        awaitClose{
            mhsCollection.remove()
        }
    }

    override suspend fun getMahasiswaByNim(nim: String): Flow<Mahasiswa> = callbackFlow {
        val mhsDocument = firestore.collection("Mahasiswa")
            .document(nim)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    val mhs = value.toObject(Mahasiswa::class.java)!!
                    trySend(mhs)
                }
            }

        awaitClose {
            mhsDocument.remove()
        }
    }

    override suspend fun updateMahasiswa(mahasiswa: Mahasiswa) {
        try {
            firestore.collection("Mahasiswa")
                .document(mahasiswa.nim)
                .set(mahasiswa)
                .await()
        } catch (e: Exception) {
            throw Exception("Gagal mengupdate Data Mahasiswa: ${e.message}")
        }
    }

    override suspend fun deleteMahasiswa(mahasiswa: Mahasiswa) {
        try {
            firestore.collection("Mahasiswa")
                .document(mahasiswa.nim)
                .delete()
                .await()
        } catch (e: Exception) {
            throw Exception("Gagal menghapus Data Mahasiswa: ${e.message}")
        }
    }
}