package me.sithiramunasinghe.flutter.flutter_radio_player.core.services.support

import android.app.Notification
import android.os.Build
import android.content.pm.ServiceInfo
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import io.flutter.Log
import me.sithiramunasinghe.flutter.flutter_radio_player.core.services.FRPCoreService

class FRPPlayerNotificationListener(private val frpCoreService: FRPCoreService) :
    PlayerNotificationManager.NotificationListener {

    companion object {
        private const val TAG = "FRPPlayerNotificationListener"
    }

    override fun onNotificationCancelled(
        notificationId: Int,
        dismissedByUser: Boolean
    ) {
        if (dismissedByUser) {
            Log.i(TAG, ":::: onNotificationCancelled ::::")
            frpCoreService.stopSelf()
        }
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        Log.i(TAG, "Attaching player to foreground...")
        if (Build.VERSION.SDK_INT < 34) {
            frpCoreService.startForeground(notificationId, notification)
        } else {
            frpCoreService.startForeground(notificationId, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
        }
    }
}