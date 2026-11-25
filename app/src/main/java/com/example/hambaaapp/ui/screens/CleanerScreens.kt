package com.example.hambaaapp.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hambaaapp.data.*
import com.example.hambaaapp.ui.theme.*
import com.example.hambaaapp.viewmodel.HambaaViewModel

@Composable
fun CleanerAppRoot(viewModel: HambaaViewModel) {
    val activeScreen by viewModel.activeScreen.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        color = BgCream
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                when (activeScreen) {
                    201 -> CleanerDashboardScreen(viewModel)
                    202 -> CleanerTasksScreen(viewModel)
                    203 -> CleanerTaskDetailsScreen(viewModel)
                    204 -> CleanerAreasScreen(viewModel)
                    205 -> CleanerAreaDetailsScreen(viewModel)
                    206 -> CleanerScheduleScreen(viewModel)
                    207 -> CleanerReportIssueScreen(viewModel)
                    208 -> CleanerReportSuccessScreen(viewModel)
                    209 -> CleanerHistoryScreen(viewModel)
                    210 -> CleanerNotificationsScreen(viewModel)
                    211 -> CleanerProfileScreen(viewModel)
                    else -> CleanerDashboardScreen(viewModel)
                }
            }

            // Bottom Navigation Bar for Cleaner (Home, Tasks, Areas, Profile)
            if (activeScreen in listOf(201, 202, 204, 211)) {
                CleanerBottomNav(activeScreen = activeScreen, onNavigate = { viewModel.navigateTo(it) })
            }
        }
    }
}

@Composable
fun CleanerBottomNav(activeScreen: Int, onNavigate: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = activeScreen == 201,
            onClick = { onNavigate(201) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 202,
            onClick = { onNavigate(202) },
            icon = { Icon(Icons.Default.Task, contentDescription = "Tasks") },
            label = { Text("Tasks", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 204,
            onClick = { onNavigate(204) },
            icon = { Icon(Icons.Default.CleaningServices, contentDescription = "Areas") },
            label = { Text("Areas", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 211,
            onClick = { onNavigate(211) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
    }
}

// -----------------------------------------------------------------------------
// 1. CLEANER DASHBOARD (Screen 201)
// -----------------------------------------------------------------------------
@Composable
fun CleanerDashboardScreen(viewModel: HambaaViewModel) {
    val tasks by viewModel.cleaningTasks.collectAsState()
    val areas by viewModel.cleaningAreas.collectAsState()
    val completedCount = tasks.count { it.status == "Completed" }
    val pendingCount = tasks.size - completedCount

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("FARM SANITATION SHIFT • CLN-001", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryFresh)
                Text("Welcome, Rahim Ahmed", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = TextCharcoal)
                Text("Green Valley Dairy Farm", fontSize = 11.sp, color = TextMuted)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = { viewModel.navigateTo(210) },
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White).border(1.dp, BorderGreen, CircleShape)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = TextCharcoal, modifier = Modifier.size(20.dp))
                        Badge(modifier = Modifier.align(Alignment.TopEnd)) { Text("2") }
                    }
                }
                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(PrimaryForest),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }
        }

        // 3 Summary Cards
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DashboardKpiCard(modifier = Modifier.weight(1f), title = "Total", value = "${tasks.size}", badge = "Shift", icon = { Icon(Icons.Default.Assignment, contentDescription = null, tint = PrimaryForest) }, onClick = { viewModel.navigateTo(202) })
            DashboardKpiCard(modifier = Modifier.weight(1f), title = "Completed", value = "$completedCount", badge = "Done", icon = { Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SecondaryFresh) }, onClick = { viewModel.navigateTo(202) })
            DashboardKpiCard(modifier = Modifier.weight(1f), title = "Pending", value = "$pendingCount", badge = "Queue", icon = { Icon(Icons.Default.Schedule, contentDescription = null, tint = WarningAmber) }, onClick = { viewModel.navigateTo(202) })
        }

        // Quick Actions 4-Column Strip
        Column {
            Text("Cleaner Quick Actions", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                WorkerQuickActionTile("All Tasks", Icons.Default.Task, PrimaryForest) { viewModel.navigateTo(202) }
                WorkerQuickActionTile("Shed Areas", Icons.Default.CleaningServices, SecondaryFresh) { viewModel.navigateTo(204) }
                WorkerQuickActionTile("Schedule", Icons.Default.CalendarToday, WarningAmber) { viewModel.navigateTo(206) }
                WorkerQuickActionTile("Report Issue", Icons.Default.ReportProblem, ErrorRed) { viewModel.navigateTo(207) }
            }
        }

        // Priority Cleaning Alert Card
        Surface(
            color = Color(0xFFFEF3C7),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFFDE68A)),
            modifier = Modifier.fillMaxWidth().clickable { viewModel.navigateTo(202) }
        ) {
            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(38.dp).clip(RoundedCornerShape(10.dp)).background(WarningAmber),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Priority: Disinfect Cow Shed B", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E))
                    Text("Deadline 01:00 PM • Isolation Bay 1 disinfection", fontSize = 11.sp, color = Color(0xFFB45309))
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = WarningAmber)
            }
        }

        // Today's Schedule Preview
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Today's Cleaning Schedule", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    TextButton(onClick = { viewModel.navigateTo(206) }) {
                        Text("View Timeline", fontSize = 12.sp, color = PrimaryForest, fontWeight = FontWeight.Bold)
                    }
                }
                tasks.take(3).forEach { t ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(t.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                            Text("${t.scheduledTime} • ${t.area}", fontSize = 10.sp, color = TextMuted)
                        }
                        CowStatusBadge(t.status)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 2. CLEANER TASKS SCREEN (Screen 202)
// -----------------------------------------------------------------------------
@Composable
fun CleanerTasksScreen(viewModel: HambaaViewModel) {
    val tasks by viewModel.cleaningTasks.collectAsState()
    var filter by remember { mutableStateOf("All") }

    val filtered = tasks.filter {
        filter == "All" || it.status == filter
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Cleaning Tasks", subtitle = "${tasks.count { it.status == "Completed" }} of ${tasks.size} Completed", onBack = { viewModel.navigateTo(201) })

        Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("All", "Pending", "In Progress", "Completed").forEach { f ->
                FilterChip(
                    selected = filter == f,
                    onClick = { filter = f },
                    label = { Text(f, fontSize = 11.sp) }
                )
            }
        }

        filtered.forEach { task ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth().clickable {
                    viewModel.setSelectedCleanerTaskId(task.id)
                    viewModel.navigateTo(203)
                }
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("${task.scheduledTime} (Due ${task.deadline})", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                        CowStatusBadge(task.status)
                    }
                    Text(task.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    Text("Area: ${task.area} • Priority: ${task.priority}", fontSize = 11.sp, color = TextMuted)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 3. CLEANER TASK DETAILS SCREEN (Screen 203)
// -----------------------------------------------------------------------------
@Composable
fun CleanerTaskDetailsScreen(viewModel: HambaaViewModel) {
    val tasks by viewModel.cleaningTasks.collectAsState()
    val selectedId by viewModel.selectedCleanerTaskId.collectAsState()
    val task = tasks.find { it.id == selectedId } ?: tasks.first()
    var notesText by remember { mutableStateOf(task.notes) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Task Details", subtitle = "#${task.id} • ${task.area}", onBack = { viewModel.navigateTo(202) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Priority: ${task.priority}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ErrorRed)
                    CowStatusBadge(task.status)
                }
                Text(task.title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                Text(task.desc, fontSize = 12.sp, color = TextMuted)

                Divider(color = BorderGreen)

                Text("Hygiene Instructions:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                task.instructions.forEach { ins ->
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("•", color = PrimaryForest, fontWeight = FontWeight.Bold)
                        Text(ins, fontSize = 11.sp, color = TextCharcoal)
                    }
                }
            }
        }

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Completion Notes", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                HambaaOutlinedTextField(
                    value = notesText,
                    onValueChange = { notesText = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = { viewModel.updateCleaningTaskStatus(task.id, "In Progress", notesText) },
                colors = ButtonDefaults.buttonColors(containerColor = WarningAmber),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Text("Start Task", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Button(
                onClick = { viewModel.updateCleaningTaskStatus(task.id, "Completed", notesText); viewModel.navigateTo(202) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Text("Mark as Completed", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 4. CLEANING AREAS SCREEN (Screen 204)
// -----------------------------------------------------------------------------
@Composable
fun CleanerAreasScreen(viewModel: HambaaViewModel) {
    val areas by viewModel.cleaningAreas.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Assigned Shed Areas", subtitle = "${areas.size} Farm Hygiene Zones", onBack = { viewModel.navigateTo(201) })

        areas.forEach { area ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth().clickable {
                    viewModel.setSelectedCleanerAreaId(area.id)
                    viewModel.navigateTo(205)
                }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(SoftGreenContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CleaningServices, contentDescription = null, tint = PrimaryForest, modifier = Modifier.size(24.dp))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(area.zoneCode, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                            CowStatusBadge(area.status)
                        }
                        Text(area.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                        Text("Last Cleaned: ${area.lastCleaned}", fontSize = 11.sp, color = TextMuted)
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 5. AREA DETAILS SCREEN (Screen 205)
// -----------------------------------------------------------------------------
@Composable
fun CleanerAreaDetailsScreen(viewModel: HambaaViewModel) {
    val areas by viewModel.cleaningAreas.collectAsState()
    val selectedId by viewModel.selectedCleanerAreaId.collectAsState()
    val area = areas.find { it.id == selectedId } ?: areas.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = area.name, subtitle = area.zoneCode, onBack = { viewModel.navigateTo(204) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Sanitation Status", fontSize = 11.sp, color = TextMuted)
                    CowStatusBadge(area.status)
                }
                Text("Last Cleaned: ${area.lastCleanedDate}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)

                Divider(color = BorderGreen)

                Text("Standard Hygiene Instructions:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                Text(area.instructions, fontSize = 11.sp, color = TextCharcoal)

                Spacer(modifier = Modifier.height(4.dp))
                Text("Sanitation Reminders:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                area.reminders.forEach { r ->
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("•", color = SecondaryFresh, fontWeight = FontWeight.Bold)
                        Text(r, fontSize = 11.sp, color = TextMuted)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 6. CLEANING SCHEDULE SCREEN (Screen 206)
// -----------------------------------------------------------------------------
@Composable
fun CleanerScheduleScreen(viewModel: HambaaViewModel) {
    val tasks by viewModel.cleaningTasks.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Cleaning Schedule", subtitle = "Today's timeline (Morning, Afternoon, Evening)", onBack = { viewModel.navigateTo(201) })

        listOf("Morning", "Afternoon", "Evening").forEach { period ->
            val periodTasks = tasks.filter { it.period == period }
            if (periodTasks.isNotEmpty()) {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, BorderGreen),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("$period Shift", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                        periodTasks.forEach { t ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(t.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                                    Text("${t.scheduledTime} • ${t.area}", fontSize = 10.sp, color = TextMuted)
                                }
                                CowStatusBadge(t.status)
                            }
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 7. REPORT ISSUE SCREEN (Screen 207)
// -----------------------------------------------------------------------------
@Composable
fun CleanerReportIssueScreen(viewModel: HambaaViewModel) {
    var category by remember { mutableStateOf("Damaged Equipment") }
    var area by remember { mutableStateOf("Cow Shed B") }
    var description by remember { mutableStateOf("Pressure washer hose has a leak near nozzle connection.") }
    var priority by remember { mutableStateOf("Medium") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Report Sanitation Issue", subtitle = "Notify owner of equipment or hygiene problems", onBack = { viewModel.navigateTo(201) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Column {
                    Text("Issue Category *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = category, onValueChange = { category = it }, modifier = Modifier.fillMaxWidth())
                }

                Column {
                    Text("Farm Area *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = area, onValueChange = { area = it }, modifier = Modifier.fillMaxWidth())
                }

                Column {
                    Text("Priority Level *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Low", "Medium", "High").forEach { p ->
                            Button(
                                onClick = { priority = p },
                                colors = ButtonDefaults.buttonColors(containerColor = if (priority == p) WarningAmber else Color.LightGray),
                                modifier = Modifier.weight(1f)
                            ) { Text(p, fontSize = 11.sp) }
                        }
                    }
                }

                Column {
                    Text("Description *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false
                    )
                }

                Button(
                    onClick = {
                        viewModel.submitCleaningReport(category, area, description, priority)
                        viewModel.navigateTo(208)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Submit Issue Report", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 8. REPORT SUCCESS SCREEN (Screen 208)
// -----------------------------------------------------------------------------
@Composable
fun CleanerReportSuccessScreen(viewModel: HambaaViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(80.dp).clip(CircleShape).background(SoftGreenContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryForest, modifier = Modifier.size(48.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Report Submitted Successfully!", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
        Text("Farm Owner and supervisors have been notified of this sanitation issue.", fontSize = 12.sp, color = TextMuted, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { viewModel.navigateTo(201) },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Back to Dashboard", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// -----------------------------------------------------------------------------
// 9. WORK HISTORY SCREEN (Screen 209)
// -----------------------------------------------------------------------------
@Composable
fun CleanerHistoryScreen(viewModel: HambaaViewModel) {
    val history by viewModel.cleanerWorkHistory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Sanitation History", subtitle = "${history.size} Completed cleaning logs", onBack = { viewModel.navigateTo(201) })

        history.forEach { item ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(item.area, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                        CowStatusBadge(item.status)
                    }
                    Text(item.taskName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    Text("Notes: ${item.notes}", fontSize = 11.sp, color = TextMuted)
                    Text("${item.date} at ${item.time}", fontSize = 10.sp, color = TextMuted)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 10. NOTIFICATIONS SCREEN (Screen 210)
// -----------------------------------------------------------------------------
@Composable
fun CleanerNotificationsScreen(viewModel: HambaaViewModel) {
    val notifs by viewModel.cleanerNotifications.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Notifications", subtitle = "Owner instructions & deadline alerts", onBack = { viewModel.navigateTo(201) })

        notifs.forEach { n ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, if (n.unread) PrimaryForest else BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(n.type, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                        Text(n.time, fontSize = 10.sp, color = TextMuted)
                    }
                    Text(n.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    Text(n.body, fontSize = 11.sp, color = TextMuted)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 11. CLEANER PROFILE SCREEN (Screen 211)
// -----------------------------------------------------------------------------
@Composable
fun CleanerProfileScreen(viewModel: HambaaViewModel) {
    val profile by viewModel.cleanerProfile.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout from HAMBAA?") },
            text = { Text("Your sanitation logs and issue reports are synced with the farm.") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    viewModel.logout()
                }) {
                    Text("Logout", color = ErrorRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Cleaner Profile", subtitle = "Shift & role credentials")

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(64.dp).clip(RoundedCornerShape(18.dp)).background(PrimaryForest),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                CowStatusBadge("CLEANER")
                Spacer(modifier = Modifier.height(6.dp))
                Text(profile.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                Text("${profile.roleTitle} • ${profile.empId}", fontSize = 12.sp, color = TextMuted)
            }
        }

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                DetailRow("Email / Username", profile.email)
                DetailRow("Phone Number", profile.phone)
                DetailRow("Assigned Shift", profile.shift)
                DetailRow("Farm Name", profile.farmName)
            }
        }

        Button(
            onClick = { showLogoutDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Icon(Icons.Default.Logout, contentDescription = null, tint = ErrorRed)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Logout from HAMBAA", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ErrorRed)
        }
    }
}
