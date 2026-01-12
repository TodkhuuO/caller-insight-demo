package com.example.callerinsight

import android.content.Intent
import android.telecom.Call
import android.telecom.CallScreeningService

class CallScreenService : CallScreeningService() {

    override fun onScreenCall(details: Call.Details) {
        val number = details.handle?.schemeSpecificPart ?: "Unknown"

        val intent = Intent(this, OverlayService::class.java)
        intent.putExtra("PHONE_NUMBER", number)
        startService(intent)

        respondToCall(details, CallResponse.Builder().build())
    }
}