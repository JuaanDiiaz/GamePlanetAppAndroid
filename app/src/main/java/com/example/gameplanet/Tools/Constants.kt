package com.example.gameplanet.Tools

import android.util.Patterns
import java.util.regex.Pattern

class Constants {
    companion object{
        const val LOG_TAG = "UdelP"
        const val user = "usr"
        const val pass = "pass"
        const val id_user = "id_user"
        const val ID = "id"
        const val PREFS_NAME = "MyConfigurations"
        const val url = "https://7d4d-2806-2f0-90a0-1083-2460-6962-2cfd-f3a0.ngrok.io"
        val LIST_PERMISSIONS = arrayOf<String>(android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.WAKE_LOCK)
        val LIST_PERMISSIONS_UBI = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION)

        fun checkEmail(email: String): Boolean {
            val pattern: Pattern = Patterns.EMAIL_ADDRESS
            return pattern.matcher(email).matches()
        }
    }
}