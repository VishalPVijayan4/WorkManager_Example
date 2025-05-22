package com.vishalpvijayan.workmanager_example

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class ReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun doWork(): Result {
        // 1) Show the notification
        NotificationManagerCompat.from(applicationContext).notify(
            /*id=*/ System.currentTimeMillis().toInt(),  // unique ID each time
            NotificationCompat.Builder(applicationContext, NotificationHelper.getChannelId())
                .setSmallIcon(R.drawable.ic_dialog_info)
                .setContentTitle("🥤 Hydration Reminder")
                .setContentText("Time to drink water!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
        )

        // 2) Schedule the next run in 10 seconds
        scheduleNext()

        return Result.success()
    }

    private fun scheduleNext() {
        val next = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(10, TimeUnit.HOURS)
            .build()
        WorkManager
            .getInstance(applicationContext)
            .enqueueUniqueWork(
                /*uniqueName=*/ "reminder_work",
                ExistingWorkPolicy.REPLACE,
                next
            )
    }
}

