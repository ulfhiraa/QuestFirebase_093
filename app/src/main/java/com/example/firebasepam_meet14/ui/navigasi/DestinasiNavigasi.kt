package com.example.firebasepam_meet14.ui.navigasi

//untuk mendefinisikan route dan title, serta mengimplementasikan objek DestinasiHome dan DestinasiInsert dengan properti route dan title masing-masing
interface DestinasiNavigasi {
    val route:String
    val titleRes: String
}

object DestinasiHome : DestinasiNavigasi {
    override val route: String = "home"
    override val titleRes: String = "home"
}

object DestinasiInsert : DestinasiNavigasi {
    override val route: String = "insert"
    override val titleRes: String = "insert"
}
