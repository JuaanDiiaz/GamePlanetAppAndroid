package com.example.gameplanet.Tools

class Constants {
    companion object{
        const val LOG_TAG = "UdelP"
        const val user = "usr"
        const val pass = "pass"
        const val id_user = "id_user"
        const val ID = "id"
        const val PREFS_NAME = "MyConfigurations"
        const val url = "https://d721-2806-2f0-90a0-1083-3ccb-f81c-fed2-22ed.ngrok.io"
        val LIST_PERMISSIONS = arrayOf<String>(android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.WAKE_LOCK)
        val LIST_PERMISSIONS_UBI = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}