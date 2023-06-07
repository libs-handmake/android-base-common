package common.hoangdz.lib.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class NotificationMaker constructor(
    private val context: Context,
    @DrawableRes private val smallIcon: Int,
    private val channelID: String,
    private val channelName: String = channelID
) {

    constructor(
        context: Context,
        @DrawableRes smallIcon: Int,
        channelID: String,
        channelName: String = channelID,
        priority: Int,
    ) : this(context, smallIcon, channelID, channelName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.priority = priority
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private var priority = NotificationManager.IMPORTANCE_HIGH

    private val notificationManager by lazy { context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    channelID,
                    channelName,
                    priority
                )
            )
        }
    }

    fun makeNotification(
        title: String,
        content: String,
        onNotificationBuilder: NotificationCompat.Builder.() -> Unit
    ) {
        val builder = NotificationCompat.Builder(context, channelID)
        builder.setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(smallIcon)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    101,
                    context.packageManager.getLaunchIntentForPackage(context.packageName),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            ).apply(onNotificationBuilder)
        notificationManager.notify(101, builder.build())
    }
}