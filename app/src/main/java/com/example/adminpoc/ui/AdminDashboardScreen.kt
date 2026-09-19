package com.example.adminpoc.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.adminpoc.alarm.AlarmScheduler
import com.example.adminpoc.alarm.NotificationHelper
import com.example.adminpoc.data.AdminTaskRepository
import com.example.adminpoc.model.TaskItem
import com.example.adminpoc.model.TaskSlot
import com.example.adminpoc.ui.components.DailyReportDialog
import com.example.adminpoc.ui.components.SettingsDialog
import com.example.adminpoc.ui.components.TaskCard
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val repository = remember { AdminTaskRepository(context) }

    var taskList by remember { mutableStateOf<List<TaskItem>>(emptyList()) }
    var selectedSlotFilter by remember { mutableStateOf<TaskSlot?>(null) }
    var showReportDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    // Request Notification Permission for Android 13+
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            AlarmScheduler.scheduleDailyAlarms(context)
        }
    }

    LaunchedEffect(Unit) {
        NotificationHelper.createNotificationChannels(context)
        AlarmScheduler.scheduleDailyAlarms(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        // Load tasks
        taskList = repository.getTasksForDate()

        // Initialize sticky notification
        val completed = taskList.count { it.isCompleted }
        NotificationHelper.updateStickyProgressNotification(
            context,
            completed = completed,
            total = taskList.size,
            pending = taskList.size - completed
        )
    }

    val totalCount = taskList.size
    val completedCount = taskList.count { it.isCompleted }
    val pendingCount = totalCount - completedCount
    val progressPercent = if (totalCount > 0) (completedCount * 100 / totalCount) else 0

    // Check if it is currently afternoon/pre-closing (past 17:00)
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val isNearClosing = currentHour >= 17 && pendingCount > 0

    val filteredTasks = remember(taskList, selectedSlotFilter) {
        if (selectedSlotFilter == null) taskList
        else taskList.filter { it.slot == selectedSlotFilter }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Admin POC Daily Task",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = repository.getServiceCenterName(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showSettingsDialog = true }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 12.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { showReportDialog = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.Assessment, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("ডেইলি রিপোর্ট", fontSize = 13.sp)
                    }

                    Button(
                        onClick = {
                            val headPhone = repository.getHeadPhone()
                            val uri = Uri.parse("smsto:$headPhone")
                            val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
                                putExtra(
                                    "sms_body",
                                    "⚠️ জরুরি অ্যালার্ট [${repository.getServiceCenterName()}]: ওয়ার্কশপ/ইউটিলিটিতে একটি ক্রিটিক্যাল সমস্যা পাওয়া গেছে। অনুগ্রহ করে অবিলম্বে দৃষ্টি আকর্ষণ করুন। - ${repository.getOfficerName()}"
                                )
                            }
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "SMS খোলা সম্ভব হয়নি", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                    ) {
                        Icon(Icons.Default.ReportProblem, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("এসওএস SMS", fontSize = 13.sp, color = Color.White)
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 14.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            // 1. Officer Info & Date Banner
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = repository.getOfficerName(),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = repository.getDisplayDate(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Surface(
                        color = if (pendingCount == 0) Color(0xFFE8F5E9) else Color(0xFFFFF3E0),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (pendingCount == 0) "সব কাজ সম্পন্ন ✅" else "ডিউটি চলমান ⚡",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (pendingCount == 0) Color(0xFF2E7D32) else Color(0xFFE65100),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // 2. Pre-Closing Urgent Alert Banner
            if (isNearClosing) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.NotificationImportant,
                                contentDescription = "Urgent",
                                tint = Color(0xFFC62828),
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "⚠️ অফিস ছুটির পূর্বে তাগিদ (৫:১৫ PM+)",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFC62828),
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "৬টায় অফিস ত্যাগ করার আগে আপনার এখনও $pendingCount টি কাজ বাকি আছে! দ্রুত সম্পন্ন করুন।",
                                    fontSize = 12.sp,
                                    color = Color(0xFFB71C1C)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }

            // 3. Progress Summary Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "দৈনিক কাজের অগ্রগতি",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "$progressPercent%",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { if (totalCount > 0) completedCount.toFloat() / totalCount else 0f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$totalCount",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text("মোট টাস্ক", style = MaterialTheme.typography.bodySmall)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$completedCount",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E7D32),
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text("সম্পন্ন", style = MaterialTheme.typography.bodySmall)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$pendingCount",
                                    fontWeight = FontWeight.Bold,
                                    color = if (pendingCount > 0) Color(0xFFC62828) else Color.Gray,
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text("বাকি আছে", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // 4. Slot Filter Chips
            item {
                Text(
                    text = "সময় অনুযায়ী চেকলিস্ট ফিল্টার:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        FilterChip(
                            selected = selectedSlotFilter == null,
                            onClick = { selectedSlotFilter = null },
                            label = { Text("সকল (${taskList.size})") },
                            colors = FilterChipDefaults.filterChipColors()
                        )
                    }
                    items(TaskSlot.values()) { slot ->
                        val slotCount = taskList.count { it.slot == slot }
                        val slotDone = taskList.count { it.slot == slot && it.isCompleted }
                        FilterChip(
                            selected = selectedSlotFilter == slot,
                            onClick = { selectedSlotFilter = slot },
                            label = { Text("${slot.label} ($slotDone/$slotCount)") },
                            colors = FilterChipDefaults.filterChipColors()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // 5. Tasks List
            items(filteredTasks, key = { it.id }) { task ->
                TaskCard(
                    task = task,
                    onToggleCompletion = {
                        val updated = repository.toggleTaskCompletion(taskId = task.id)
                        if (updated != null) {
                            taskList = repository.getTasksForDate()
                            val done = taskList.count { it.isCompleted }
                            val pending = taskList.size - done
                            NotificationHelper.updateStickyProgressNotification(
                                context,
                                completed = done,
                                total = taskList.size,
                                pending = pending
                            )
                        }
                    },
                    onUpdateRemarks = { note ->
                        val current = task.copy(remarks = note)
                        repository.updateTask(updatedItem = current)
                        taskList = repository.getTasksForDate()
                    },
                    onToggleEscalation = {
                        val current = task.copy(isEscalated = !task.isEscalated)
                        repository.updateTask(updatedItem = current)
                        taskList = repository.getTasksForDate()
                        Toast.makeText(
                            context,
                            if (current.isEscalated) "সমস্যাটি জরুরি হিসেবে ফ্ল্যাগ করা হয়েছে" else "ফ্ল্যাগ সরানো হয়েছে",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }

            if (filteredTasks.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "কোনো টাস্ক নেই",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }

    // Dialogs
    if (showReportDialog) {
        DailyReportDialog(
            reportText = repository.generateDailyReportText(),
            headPhone = repository.getHeadPhone(),
            onDismiss = { showReportDialog = false }
        )
    }

    if (showSettingsDialog) {
        SettingsDialog(
            repository = repository,
            onDismiss = { showSettingsDialog = false },
            onSaved = {
                taskList = repository.getTasksForDate()
            }
        )
    }
}
