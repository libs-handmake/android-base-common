package common.hoangdz.lib.notification

import android.app.Notification
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

    companion object {
        const val DEFAULT_ID = 101
    }

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

    fun createNotification(
        title: String,
        content: String,
        onNotificationBuilder: (NotificationCompat.Builder.() -> Unit)? = null
    ): Notification {
        val builder = NotificationCompat.Builder(context, channelID)
        return builder.setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(smallIcon)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    101,
                    context.packageManager.getLaunchIntentForPackage(context.packageName),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            ).apply {
                onNotificationBuilder?.invoke(this)
            }.build()
    }

    fun showNotification(
        title: String,
        content: String,
        notificationID: Int = DEFAULT_ID,
        onNotificationBuilder: (NotificationCompat.Builder.() -> Unit)? = null
    ) {
        notificationManager.notify(
            notificationID,
            createNotification(title, content, onNotificationBuilder)
        )
    }

    fun clearNotification(notificationID: Int = DEFAULT_ID) {
        notificationManager.cancel(notificationID)
    }

    fun clearAllNotifications() = notificationManager.cancelAll()
}