package common.hoangdz.lib.utils.user.feedback

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build


object FeedbackSender {
    fun sendFeedback(context: Context, feedbackData: FeedbackData): Boolean {
        val i = Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse("mailto:")
//            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(feedbackData.email))
            putExtra(Intent.EXTRA_SUBJECT, feedbackData.title)
            putExtra(Intent.EXTRA_TEXT, feedbackData.body)
//            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return try {
            context.startActivity(i)
            true
        } catch (ex: ActivityNotFoundException) {
            false
        }
    }

    fun makeFeedbackData(title: String, email: String, content: String): FeedbackData {
        val nContent =
            "SDK level: ${Build.VERSION.SDK_INT}\nDevice: ${Build.DEVICE}\nModel: ${Build.MODEL} \nProduct: ${Build.PRODUCT}\n\n---------------\n\n\n $content"
        return FeedbackData(email, title, nContent)
    }
}