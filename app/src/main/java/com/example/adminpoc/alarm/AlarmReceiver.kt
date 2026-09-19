package com.example.adminpoc.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.adminpoc.data.AdminTaskRepository
import com.example.adminpoc.model.TaskSlot

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val repository = AdminTaskRepository(context)
        val todayKey = repository.getTodayDateKey()
        val allTasks = repository.getTasksForDate(todayKey)
        val pendingCount = allTasks.count { !it.isCompleted }
        val completedCount = allTasks.count { it.isCompleted }

        // Update sticky progress in notification drawer
        NotificationHelper.updateStickyProgressNotification(
            context = context,
            completed = completedCount,
            total = allTasks.size,
            pending = pendingCount
        )

        val slotName = intent.getStringExtra(AlarmScheduler.EXTRA_SLOT_TYPE) ?: TaskSlot.MORNING.name
        val isRepeat = intent.getBooleanExtra(AlarmScheduler.EXTRA_IS_REPEAT, false)
        val slot = try {
            TaskSlot.valueOf(slotName)
        } catch (e: Exception) {
            TaskSlot.MORNING
        }

        val slotPending = repository.getPendingTasksForSlot(todayKey, slot)

        if (isRepeat) {
            if (pendingCount > 0 && repository.isForcefulEnabled()) {
                NotificationHelper.showRepeatFollowup(
                    context,
                    "⚠️ তাগিদ: আপনার আজকের $pendingCount টি কাজ এখনও বাকি আছে! অনুগ্রহ করে দ্রুত সম্পন্ন করুন।"
                )
                // Schedule next forceful reminder in 15 minutes
                AlarmScheduler.scheduleRepeatFollowup(context, 15, slot)
            }
            return
        }

        when (slot) {
            TaskSlot.MORNING -> {
                NotificationHelper.showMorningReminder(context, slotPending.size)
                // If tasks are pending, schedule repeat reminder in 15 minutes
                if (slotPending.isNotEmpty() && repository.isForcefulEnabled()) {
                    AlarmScheduler.scheduleRepeatFollowup(context, 15, slot)
                }
            }
            TaskSlot.MIDDAY -> {
                NotificationHelper.showMiddayReminder(context, slotPending.size)
                if (slotPending.isNotEmpty() && repository.isForcefulEnabled()) {
                    AlarmScheduler.scheduleRepeatFollowup(context, 15, slot)
                }
            }
            TaskSlot.PRE_CLOSING, TaskSlot.EVENING -> {
                if (pendingCount > 0) {
                    val pendingPreview = allTasks
                        .filter { !it.isCompleted }
                        .take(4)
                        .joinToString("\n") { "• ${it.bengaliTitle}" }

                    NotificationHelper.showPreClosingReminder(
                        context = context,
                        pendingCount = pendingCount,
                        pendingListStr = pendingPreview
                    )
                    // High urgency: Repeat every 10 minutes until office leaves at 6 PM!
                    if (repository.isForcefulEnabled()) {
                        AlarmScheduler.scheduleRepeatFollowup(context, 10, slot)
                    }
                }
            }
        }
    }
}
