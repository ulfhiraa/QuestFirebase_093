package com.example.firebasepam_meet14.model

data class Mahasiswa(
    val nim: String,
    val nama: String,
    val alamat: String,
    val jenisKelamin: String,
    val kelas: String,
    val angkatan: String,
    val judulSkripsi: String,
    val dospemSatu: String,
    val dospemDua: String
) {
    constructor() :
            this("",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
}

