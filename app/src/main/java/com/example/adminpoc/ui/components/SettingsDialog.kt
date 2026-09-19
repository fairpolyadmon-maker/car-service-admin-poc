package com.example.adminpoc.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.adminpoc.alarm.AlarmScheduler
import com.example.adminpoc.alarm.NotificationHelper
import com.example.adminpoc.data.AdminTaskRepository
import com.example.adminpoc.model.TaskSlot

@Composable
fun SettingsDialog(
    repository: AdminTaskRepository,
    onDismiss: () -> Unit,
    onSaved: () -> Unit
) {
    val context = LocalContext.current
    var officerName by remember { mutableStateOf(repository.getOfficerName()) }
    var centerName by remember { mutableStateOf(repository.getServiceCenterName()) }
    var headPhone by remember { mutableStateOf(repository.getHeadPhone()) }
    var forcefulEnabled by remember { mutableStateOf(repository.isForcefulEnabled()) }
    var smsEscalationEnabled by remember { mutableStateOf(repository.isSmsEscalationEnabled()) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(vertical = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⚙️ সেটিংস ও রিমাইন্ডার কনফিগ",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = officerName,
                    onValueChange = { officerName = it },
                    label = { Text("অ্যাডমিন পিওসি নাম ও পদবী") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = centerName,
                    onValueChange = { centerName = it },
                    label = { Text("কার সার্ভিস সেন্টার লোকেশন") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = headPhone,
                    onValueChange = { headPhone = it },
                    label = { Text("হেড অব অ্যাডমিন ফোন / SMS নম্বর") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(14.dp))

                // Forceful notification toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "ফোর্সফুল নোটিফিকেশন ও বারবার তাগিদ",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "কাজ অসমাপ্ত থাকলে প্রতি ১০-১৫ মিনিট পর পর সাউন্ড সহ রিমাইন্ডার বাজবে",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = forcefulEnabled,
                        onCheckedChange = { forcefulEnabled = it }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // SMS Escalation toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "এসএমএস এসকেলেশন সাপোর্ট",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "জরুরি ব্রেকডাউন বা প্রি-ক্লোজিং এ হেড অফিসে এসএমএস সতর্কবার্তা পাঠানোর অনুমতি",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = smsEscalationEnabled,
                        onCheckedChange = { smsEscalationEnabled = it }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(14.dp))

                // Instant Alarm Testing Section
                Text(
                    text = "🔔 তাৎক্ষণিক অ্যালার্ম ও নোটিফিকেশন টেস্ট:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            AlarmScheduler.triggerTestAlarmNow(context, TaskSlot.MORNING)
                            Toast.makeText(context, "সকালের ওপেনিং অ্যালার্ম টেস্ট ট্রিগার হয়েছে", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("🌅 সকাল টেস্ট")
                    }

                    OutlinedButton(
                        onClick = {
                            AlarmScheduler.triggerTestAlarmNow(context, TaskSlot.PRE_CLOSING)
                            Toast.makeText(context, "৫:১৫ প্রি-ক্লোজিং পুশ টেস্ট ট্রিগার হয়েছে", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("⚠️ ৫:১৫ টেস্ট")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = {
                        val pending = repository.getPendingCount()
                        val done = repository.getCompletedCount()
                        val total = pending + done
                        NotificationHelper.updateStickyProgressNotification(context, done, total, pending)
                        Toast.makeText(context, "স্টিকি প্রগ্রেস বার নোটিফিকেশন ট্রাফিকে পাঠানো হয়েছে", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.NotificationsActive, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("📌 স্টিকি প্রগ্রেস নোটিফিকেশন টেস্ট")
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Save button
                Button(
                    onClick = {
                        repository.setOfficerName(officerName)
                        repository.setServiceCenterName(centerName)
                        repository.setHeadPhone(headPhone)
                        repository.setForcefulEnabled(forcefulEnabled)
                        repository.setSmsEscalationEnabled(smsEscalationEnabled)
                        Toast.makeText(context, "সেটিংস সফলভাবে সংরক্ষিত হয়েছে", Toast.LENGTH_SHORT).show()
                        onSaved()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("সেটিংস সংরক্ষণ করুন")
                }
            }
        }
    }
}
