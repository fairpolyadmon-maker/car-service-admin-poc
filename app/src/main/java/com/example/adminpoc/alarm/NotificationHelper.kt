package com.example.adminpoc.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.adminpoc.MainActivity
import com.example.adminpoc.R

object NotificationHelper {

    const val CHANNEL_ID_CRITICAL = "admin_poc_critical_alerts"
    const val CHANNEL_ID_STICKY = "admin_poc_sticky_progress"
    const val CHANNEL_ID_CLOSING = "admin_poc_pre_closing_push"

    const val NOTIFICATION_ID_STICKY = 1001
    const val NOTIFICATION_ID_ALARM = 1002
    const val NOTIFICATION_ID_CLOSING = 1003

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val alarmSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM) ?: defaultSoundUri

            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()

            // 1. Critical Channel
            val criticalChannel = NotificationChannel(
                CHANNEL_ID_CRITICAL,
                "Admin POC Duty & Inspection Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Morning and mid-day daily inspection task alarms"
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 200, 500, 200, 500)
                setSound(alarmSoundUri, audioAttributes)
            }

            // 2. Sticky Progress Channel
            val stickyChannel = NotificationChannel(
                CHANNEL_ID_STICKY,
                "Admin POC Sticky Duty Tracker",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows today's live task completion progress"
                setShowBadge(false)
            }

            // 3. Pre-Closing Channel
            val closingChannel = NotificationChannel(
                CHANNEL_ID_CLOSING,
                "Admin POC Pre-Closing Enforcement",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Forceful reminders before leaving office at 6:00 PM"
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 700, 300, 700, 300, 700)
                setSound(alarmSoundUri, audioAttributes)
            }

            notificationManager.createNotificationChannels(
                listOf(criticalChannel, stickyChannel, closingChannel)
            )
        }
    }

    private fun getAppOpenIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun showMorningReminder(context: Context, pendingCount: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_CRITICAL)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("🌅 অফিসে স্বাগতম! সকালের ওপেনিং ইনস্পেকশন শুরু করুন")
            .setContentText("সার্ভিস বে, জেনারেটর, কাস্টমার লাউঞ্জ ও সিকিউরিটি চেক এখনই সম্পন্ন করুন। ($pendingCount টি কাজ বাকি)")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("ডিউটি শুরু হয়েছে! আপনার সকালের চেকলিস্ট ওপেন করুন:\n• গেট ও শাটার ইনস্পেকশন\n• পাওয়ার সাপ্লাই, জেনারেটর ও ইউপিএস চেক\n• সার্ভিস বে হাউসকিপিং ও সুইপার তদারকি\n• কাস্টমার ওয়েটিং এরিয়া ও প্যান্ট্রি উপস্থিতি\n\nকাজগুলো শেষ করে টিক দিন!")
            )
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setContentIntent(getAppOpenIntent(context))
            .build()

        manager.notify(NOTIFICATION_ID_ALARM, notification)
    }

    fun showMiddayReminder(context: Context, pendingCount: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_CRITICAL)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("☀️ দুপুর: ওয়ার্কশপ সাপোর্ট ও সেফটি ইনস্পেকশন")
            .setContentText("টেকনিশিয়ান পরিকাঠামো, ফায়ার সেফটি ও ভেন্ডর মনিটরিং করুন। ($pendingCount টি কাজ বাকি)")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("দুপুরের চেকলিস্ট:\n• ফায়ার এক্সটিঙ্গুইশার ও ইমার্জেন্সি এক্সিট ক্লিয়ারেন্স\n• ওয়ার্কশপ ড্রেনেজ, লাইটিং ও এয়ার প্রেশার\n• ভেন্ডর/কন্ট্রাক্টর ওয়ার্ক পারমিট চেক\n• ব্যবহৃত বর্জ্য ও মবিল ড্রাম স্টোরেজ")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(getAppOpenIntent(context))
            .build()

        manager.notify(NOTIFICATION_ID_ALARM, notification)
    }

    fun showPreClosingReminder(context: Context, pendingCount: Int, pendingListStr: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_CLOSING)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("⚠️ অফিস ত্যাগের পূর্বে তাগিদ! ($pendingCount টি কাজ বাকি)")
            .setContentText("নরমালি ৬টায় অফিস শেষ। আপনার এখনো $pendingCount টি কাজ পেন্ডিং আছে!")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("⚠️ জরুরি তাগিদ:\nঅফিস ত্যাগ করার আগে নিশ্চিত করুন আপনি আজকের সব কাজ সম্পন্ন করেছেন।\n\nপেন্ডিং কাজসমূহ:\n$pendingListStr\n\nদ্রুত সম্পন্ন করে ম্যানেজমেন্টের কাছে ডেলি রিপোর্ট সাবমিট করুন!")
            )
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setContentIntent(getAppOpenIntent(context))
            .build()

        manager.notify(NOTIFICATION_ID_CLOSING, notification)
    }

    fun showRepeatFollowup(context: Context, message: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_CRITICAL)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("🔔 তাগিদ: অসমাপ্ত দৈনন্দিন কাজ সম্পন্ন করুন")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(getAppOpenIntent(context))
            .build()

        manager.notify(NOTIFICATION_ID_ALARM, notification)
    }

    fun updateStickyProgressNotification(context: Context, completed: Int, total: Int, pending: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val percent = if (total > 0) (completed * 100 / total) else 0

        val title = if (pending == 0) {
            "✅ আজকের সব কাজ সম্পন্ন ($completed/$total)"
        } else {
            "📋 অ্যাডমিন ডিউটি রানিং: $completed/$total সম্পন্ন ($pending বাকি)"
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID_STICKY)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText("দৈনিক অগ্রগতি: $percent% সম্পন্ন | বিস্তারিত দেখতে ট্যাপ করুন")
            .setProgress(total, completed, false)
            .setOngoing(pending > 0) // Cannot dismiss if tasks are pending
            .setAutoCancel(false)
            .setContentIntent(getAppOpenIntent(context))
            .build()

        manager.notify(NOTIFICATION_ID_STICKY, notification)
    }
}
