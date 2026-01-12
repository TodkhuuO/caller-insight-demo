package com.example.callerinsight

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView

class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: View

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val phone = intent?.getStringExtra("PHONE_NUMBER") ?: "Unknown"

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Fix: Use a simple FrameLayout as parent container
        val container = FrameLayout(this)
        overlayView = LayoutInflater.from(this)
            .inflate(R.layout.over_view, container, false)

        val text = overlayView.findViewById<TextView>(R.id.textResult)
        // Use string resource with placeholder
        text.text = getString(R.string.caller_info, phone)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        windowManager.addView(overlayView, params)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::overlayView.isInitialized) {
            windowManager.removeView(overlayView)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}