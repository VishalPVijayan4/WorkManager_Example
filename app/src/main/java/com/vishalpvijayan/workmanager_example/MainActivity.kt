package com.vishalpvijayan.workmanager_example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1) Create channel & ask permission
        NotificationHelper.createNotificationChannel(this)
        NotificationHelper.requestNotificationPermissionIfNeeded(this)

        // 2) Kick off the first Worker (immediate run)
        val initialRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(0, TimeUnit.SECONDS)
            .build()

        WorkManager
            .getInstance(this)
            .enqueueUniqueWork(
                "reminder_work",
                ExistingWorkPolicy.KEEP,
                initialRequest
            )

        // 3) If user has disabled the channel, prompt them (after a small delay)
        Handler(Looper.getMainLooper()).postDelayed({
            if (NotificationHelper.isNotificationChannelDisabled(this)) {
                NotificationHelper.showNotificationSettingsDialog(this)
            }
        }, 1_000)
    }

    // (Optional) handle the POST_NOTIFICATIONS permission result:
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission granted — you could re-enqueue the worker here if you like.
        }
    }
}
