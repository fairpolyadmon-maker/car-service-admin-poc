package com.example.adminpoc.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.adminpoc.model.TaskSlot
import java.util.Calendar

object AlarmScheduler {

    const val EXTRA_SLOT_TYPE = "extra_slot_type"
    const val EXTRA_IS_REPEAT = "extra_is_repeat"

    private const val REQUEST_CODE_MORNING = 2001
    private const val REQUEST_CODE_MIDDAY = 2002
    private const val REQUEST_CODE_PRE_CLOSING = 2003
    private const val REQUEST_CODE_EVENING = 2004
    private const val REQUEST_CODE_REPEAT = 2005

    fun scheduleDailyAlarms(context: Context) {
        scheduleSlotAlarm(context, TaskSlot.MORNING, 9, 0, REQUEST_CODE_MORNING)
        scheduleSlotAlarm(context, TaskSlot.MIDDAY, 13, 0, REQUEST_CODE_MIDDAY)
        scheduleSlotAlarm(context, TaskSlot.PRE_CLOSING, 17, 15, REQUEST_CODE_PRE_CLOSING)
        scheduleSlotAlarm(context, TaskSlot.EVENING, 17, 45, REQUEST_CODE_EVENING)
    }

    private fun scheduleSlotAlarm(
        context: Context,
        slot: TaskSlot,
        hour: Int,
        minute: Int,
        requestCode: Int
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // If time has already passed today, schedule for tomorrow
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "com.example.adminpoc.ACTION_CHECK_TASKS"
            putExtra(EXTRA_SLOT_TYPE, slot.name)
            putExtra(EXTRA_IS_REPEAT, false)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        } catch (e: SecurityException) {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    fun scheduleRepeatFollowup(context: Context, delayMinutes: Int = 15, slot: TaskSlot) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val triggerTime = System.currentTimeMillis() + (delayMinutes * 60 * 1000L)

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "com.example.adminpoc.ACTION_CHECK_TASKS"
            putExtra(EXTRA_SLOT_TYPE, slot.name)
            putExtra(EXTRA_IS_REPEAT, true)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_REPEAT,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            } else {
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun triggerTestAlarmNow(context: Context, slot: TaskSlot) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "com.example.adminpoc.ACTION_CHECK_TASKS"
            putExtra(EXTRA_SLOT_TYPE, slot.name)
            putExtra(EXTRA_IS_REPEAT, false)
        }
        context.sendBroadcast(intent)
    }
}
