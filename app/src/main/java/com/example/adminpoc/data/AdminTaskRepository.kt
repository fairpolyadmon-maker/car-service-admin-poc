package com.example.adminpoc.data

import android.content.Context
import android.content.SharedPreferences
import com.example.adminpoc.model.DefaultTasks
import com.example.adminpoc.model.Priority
import com.example.adminpoc.model.TaskItem
import com.example.adminpoc.model.TaskSlot
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AdminTaskRepository(private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("admin_poc_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TASKS_PREFIX = "tasks_"
        private const val KEY_OFFICER_NAME = "officer_name"
        private const val KEY_CENTER_NAME = "center_name"
        private const val KEY_HEAD_PHONE = "head_phone"
        private const val KEY_HEAD_WHATSAPP = "head_whatsapp"
        private const val KEY_FORCEFUL_ENABLED = "forceful_enabled"
        private const val KEY_SMS_ESCALATION_ENABLED = "sms_escalation_enabled"
    }

    fun getTodayDateKey(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getDisplayDate(dateKey: String = getTodayDateKey()): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM, yyyy (EEEE)", Locale.getDefault())
        return try {
            val date = inputFormat.parse(dateKey) ?: Date()
            outputFormat.format(date)
        } catch (e: Exception) {
            dateKey
        }
    }

    fun getTasksForDate(dateKey: String = getTodayDateKey()): List<TaskItem> {
        val rawJson = prefs.getString(KEY_TASKS_PREFIX + dateKey, null)
        if (rawJson.isNullOrEmpty()) {
            val defaultList = DefaultTasks.createDefaultTasks()
            saveTasksForDate(dateKey, defaultList)
            return defaultList
        }

        return try {
            val array = JSONArray(rawJson)
            val list = mutableListOf<TaskItem>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    TaskItem(
                        id = obj.getString("id"),
                        title = obj.getString("title"),
                        bengaliTitle = obj.getString("bengaliTitle"),
                        category = obj.getString("category"),
                        description = obj.getString("description"),
                        slot = TaskSlot.valueOf(obj.getString("slot")),
                        priority = Priority.valueOf(obj.getString("priority")),
                        isCompleted = obj.getBoolean("isCompleted"),
                        completedAt = if (obj.has("completedAt") && !obj.isNull("completedAt")) obj.getLong("completedAt") else null,
                        remarks = if (obj.has("remarks")) obj.getString("remarks") else "",
                        isEscalated = if (obj.has("isEscalated")) obj.getBoolean("isEscalated") else false
                    )
                )
            }
            list
        } catch (e: Exception) {
            DefaultTasks.createDefaultTasks()
        }
    }

    fun saveTasksForDate(dateKey: String = getTodayDateKey(), tasks: List<TaskItem>) {
        val array = JSONArray()
        for (item in tasks) {
            val obj = JSONObject().apply {
                put("id", item.id)
                put("title", item.title)
                put("bengaliTitle", item.bengaliTitle)
                put("category", item.category)
                put("description", item.description)
                put("slot", item.slot.name)
                put("priority", item.priority.name)
                put("isCompleted", item.isCompleted)
                put("completedAt", item.completedAt ?: JSONObject.NULL)
                put("remarks", item.remarks)
                put("isEscalated", item.isEscalated)
            }
            array.put(obj)
        }
        prefs.edit().putString(KEY_TASKS_PREFIX + dateKey, array.toString()).apply()
    }

    fun updateTask(dateKey: String = getTodayDateKey(), updatedItem: TaskItem) {
        val current = getTasksForDate(dateKey).toMutableList()
        val index = current.indexOfFirst { it.id == updatedItem.id }
        if (index != -1) {
            current[index] = updatedItem
            saveTasksForDate(dateKey, current)
        }
    }

    fun toggleTaskCompletion(dateKey: String = getTodayDateKey(), taskId: String): TaskItem? {
        val current = getTasksForDate(dateKey).toMutableList()
        val index = current.indexOfFirst { it.id == taskId }
        if (index != -1) {
            val old = current[index]
            val newCompleted = !old.isCompleted
            val updated = old.copy(
                isCompleted = newCompleted,
                completedAt = if (newCompleted) System.currentTimeMillis() else null
            )
            current[index] = updated
            saveTasksForDate(dateKey, current)
            return updated
        }
        return null
    }

    fun getPendingCount(dateKey: String = getTodayDateKey()): Int {
        return getTasksForDate(dateKey).count { !it.isCompleted }
    }

    fun getCompletedCount(dateKey: String = getTodayDateKey()): Int {
        return getTasksForDate(dateKey).count { it.isCompleted }
    }

    fun getPendingTasks(dateKey: String = getTodayDateKey()): List<TaskItem> {
        return getTasksForDate(dateKey).filter { !it.isCompleted }
    }

    fun getPendingTasksForSlot(dateKey: String = getTodayDateKey(), slot: TaskSlot): List<TaskItem> {
        return getTasksForDate(dateKey).filter { it.slot == slot && !it.isCompleted }
    }

    fun getOfficerName(): String = prefs.getString(KEY_OFFICER_NAME, "Admin POC Officer") ?: "Admin POC Officer"
    fun setOfficerName(name: String) = prefs.edit().putString(KEY_OFFICER_NAME, name).apply()

    fun getServiceCenterName(): String = prefs.getString(KEY_CENTER_NAME, "Mirpur Car Service Center") ?: "Mirpur Car Service Center"
    fun setServiceCenterName(name: String) = prefs.edit().putString(KEY_CENTER_NAME, name).apply()

    fun getHeadPhone(): String = prefs.getString(KEY_HEAD_PHONE, "+8801700000000") ?: "+8801700000000"
    fun setHeadPhone(phone: String) = prefs.edit().putString(KEY_HEAD_PHONE, phone).apply()

    fun getHeadWhatsApp(): String = prefs.getString(KEY_HEAD_WHATSAPP, "+8801700000000") ?: "+8801700000000"
    fun setHeadWhatsApp(phone: String) = prefs.edit().putString(KEY_HEAD_WHATSAPP, phone).apply()

    fun isForcefulEnabled(): Boolean = prefs.getBoolean(KEY_FORCEFUL_ENABLED, true)
    fun setForcefulEnabled(enabled: Boolean) = prefs.edit().putBoolean(KEY_FORCEFUL_ENABLED, enabled).apply()

    fun isSmsEscalationEnabled(): Boolean = prefs.getBoolean(KEY_SMS_ESCALATION_ENABLED, true)
    fun setSmsEscalationEnabled(enabled: Boolean) = prefs.edit().putBoolean(KEY_SMS_ESCALATION_ENABLED, enabled).apply()

    fun generateDailyReportText(dateKey: String = getTodayDateKey()): String {
        val tasks = getTasksForDate(dateKey)
        val total = tasks.size
        val done = tasks.count { it.isCompleted }
        val pending = total - done
        val officer = getOfficerName()
        val center = getServiceCenterName()
        val displayDate = getDisplayDate(dateKey)

        val sb = StringBuilder()
        sb.append("📋 *CAR SERVICE CENTER - DAILY ADMIN REPORT*\n")
        sb.append("━━━━━━━━━━━━━━━━━━━━\n")
        sb.append("🏢 *Service Center:* $center\n")
        sb.append("👤 *Admin POC:* $officer\n")
        sb.append("📅 *Date:* $displayDate\n")
        sb.append("📊 *Status:* $done/$total Done (${if (total > 0) (done * 100 / total) else 0}% Completed)\n")
        if (pending > 0) {
            sb.append("⚠️ *Pending Tasks:* $pending items\n")
        } else {
            sb.append("✅ *All Daily Tasks 100% Completed!*\n")
        }
        sb.append("━━━━━━━━━━━━━━━━━━━━\n\n")

        TaskSlot.values().forEach { slot ->
            val slotTasks = tasks.filter { it.slot == slot }
            sb.append("🔹 *${slot.label}* (${slot.timeSlot}):\n")
            slotTasks.forEach { t ->
                val check = if (t.isCompleted) "✅" else "❌"
                sb.append("  $check ${t.bengaliTitle}\n")
                if (t.remarks.isNotBlank()) {
                    sb.append("     ↳ নোট: ${t.remarks}\n")
                }
            }
            sb.append("\n")
        }

        val escalations = tasks.filter { it.isEscalated }
        if (escalations.isNotEmpty()) {
            sb.append("🚨 *CRITICAL ESCALATIONS / ISSUES:*\n")
            escalations.forEach { e ->
                sb.append("• ${e.bengaliTitle}: ${e.remarks.ifBlank { "Immediate management attention required" }}\n")
            }
            sb.append("\n")
        }

        sb.append("━━━━━━━━━━━━━━━━━━━━\n")
        sb.append("Reported via *Admin POC Routine App*")
        return sb.toString()
    }
}
