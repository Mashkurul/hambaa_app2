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
fun WorkerAppRoot(viewModel: HambaaViewModel) {
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
                    101 -> WorkerDashboardScreen(viewModel)
                    102 -> WorkerCowListScreen(viewModel)
                    103 -> WorkerCowDetailsScreen(viewModel)
                    104 -> WorkerTasksScreen(viewModel)
                    105 -> WorkerFeedingScreen(viewModel)
                    106 -> WorkerMilkScreen(viewModel)
                    107 -> WorkerHealthReportScreen(viewModel)
                    108 -> WorkerHistoryScreen(viewModel)
                    109 -> WorkerNotificationsScreen(viewModel)
                    110 -> WorkerProfileScreen(viewModel)
                    else -> WorkerDashboardScreen(viewModel)
                }
            }

            // Bottom Navigation Bar for Worker (Home, My Cows, Tasks, Profile)
            if (activeScreen in listOf(101, 102, 104, 110)) {
                WorkerBottomNav(activeScreen = activeScreen, onNavigate = { viewModel.navigateTo(it) })
            }
        }
    }
}

@Composable
fun WorkerBottomNav(activeScreen: Int, onNavigate: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = activeScreen == 101,
            onClick = { onNavigate(101) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 102,
            onClick = { onNavigate(102) },
            icon = { Icon(Icons.Default.Pets, contentDescription = "My Cows") },
            label = { Text("My Cows", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 104,
            onClick = { onNavigate(104) },
            icon = { Icon(Icons.Default.Task, contentDescription = "Tasks") },
            label = { Text("Tasks", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 110,
            onClick = { onNavigate(110) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
    }
}

// -----------------------------------------------------------------------------
// 1. WORKER DASHBOARD (Screen 101)
// -----------------------------------------------------------------------------
@Composable
fun WorkerDashboardScreen(viewModel: HambaaViewModel) {
    val workerTasks by viewModel.workerTasks.collectAsState()
    val workerCows by viewModel.workerCows.collectAsState()
    val completedCount = workerTasks.count { it.status == "Completed" }
    val progress = completedCount.toFloat() / workerTasks.size.toFloat()

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
                Text("COW WORKER SHIFT • EMP-104", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryFresh)
                Text("Welcome, Rahim Uddin", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = TextCharcoal)
                Text("North Dairy Shed A & B", fontSize = 11.sp, color = TextMuted)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = { viewModel.navigateTo(109) },
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White).border(1.dp, BorderGreen, CircleShape)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = TextCharcoal, modifier = Modifier.size(20.dp))
                        Badge(modifier = Modifier.align(Alignment.TopEnd)) { Text("3") }
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

        // Stats Row
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.weight(1f).clickable { viewModel.navigateTo(102) }
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Assigned Cows", fontSize = 11.sp, color = TextMuted)
                    Text("${workerCows.size} Head", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryForest)
                    Text("North Shed Bays 1–6", fontSize = 10.sp, color = SecondaryFresh)
                }
            }
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.weight(1f).clickable { viewModel.navigateTo(104) }
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Shift Tasks", fontSize = 11.sp, color = TextMuted)
                    Text("$completedCount / ${workerTasks.size}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = TextCharcoal)
                    Text("${(progress * 100).toInt()}% Done", fontSize = 10.sp, color = SecondaryFresh)
                }
            }
        }

        // Progress Card
        Surface(
            color = PrimaryForest,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Today's Task Progress", fontSize = 12.sp, color = Color(0xFFC8E6C9), fontWeight = FontWeight.Bold)
                    Text("$completedCount of ${workerTasks.size} Completed", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                    color = SecondaryFresh,
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
            }
        }

        // Quick Actions 4-Column Strip
        Column {
            Text("Worker Quick Actions", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                WorkerQuickActionTile("Feed Log", Icons.Default.Restaurant, PrimaryForest) { viewModel.navigateTo(105) }
                WorkerQuickActionTile("Record Milk", Icons.Default.WaterDrop, SecondaryFresh) { viewModel.navigateTo(106) }
                WorkerQuickActionTile("Health Rep.", Icons.Default.Healing, WarningAmber) { viewModel.navigateTo(107) }
                WorkerQuickActionTile("Work History", Icons.Default.History, Color.DarkGray) { viewModel.navigateTo(108) }
            }
        }

        // Assigned Tasks Preview
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Assigned Shift Tasks", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    TextButton(onClick = { viewModel.navigateTo(104) }) {
                        Text("View All", fontSize = 12.sp, color = PrimaryForest, fontWeight = FontWeight.Bold)
                    }
                }
                workerTasks.take(3).forEach { task ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(task.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                            Text("${task.time} • ${task.area}", fontSize = 10.sp, color = TextMuted)
                        }
                        CowStatusBadge(task.status)
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.WorkerQuickActionTile(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, onClick: () -> Unit) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, BorderGreen),
        modifier = Modifier.weight(1f).clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            }
            Text(label, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal, maxLines = 1)
        }
    }
}

// -----------------------------------------------------------------------------
// 2. MY COWS SCREEN (Screen 102)
// -----------------------------------------------------------------------------
@Composable
fun WorkerCowListScreen(viewModel: HambaaViewModel) {
    val workerCows by viewModel.workerCows.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "My Assigned Cows", subtitle = "${workerCows.size} Cattle in North Shed A & B", onBack = { viewModel.navigateTo(101) })

        workerCows.forEach { cow ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth().clickable {
                    viewModel.setSelectedWorkerCow(cow.id)
                    viewModel.navigateTo(103)
                }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier.size(52.dp).clip(RoundedCornerShape(12.dp)).background(SoftGreenContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Pets, contentDescription = null, tint = PrimaryForest, modifier = Modifier.size(28.dp))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("#${cow.id} • ${cow.shedBay}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                            CowStatusBadge(cow.status)
                        }
                        Text("${cow.name} • ${cow.breed}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                        Text("Today Milk: ${cow.todayMilk} L • Weight: ${cow.weight}", fontSize = 11.sp, color = TextMuted)
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 3. WORKER COW DETAILS SCREEN (Screen 103)
// -----------------------------------------------------------------------------
@Composable
fun WorkerCowDetailsScreen(viewModel: HambaaViewModel) {
    val workerCows by viewModel.workerCows.collectAsState()
    val selectedId by viewModel.selectedWorkerCowId.collectAsState()
    val cow = workerCows.find { it.id == selectedId } ?: workerCows.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Cow Details", subtitle = "#${cow.id} • ${cow.shedBay}", onBack = { viewModel.navigateTo(102) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(68.dp).clip(RoundedCornerShape(18.dp)).background(SoftGreenContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Pets, contentDescription = null, tint = PrimaryForest, modifier = Modifier.size(36.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                CowStatusBadge(cow.status)
                Spacer(modifier = Modifier.height(6.dp))
                Text(cow.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                Text("${cow.breed} • #${cow.id}", fontSize = 12.sp, color = TextMuted)
            }
        }

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Biometrics & Feeding Note", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                DetailRow("Weight", cow.weight)
                DetailRow("Age", cow.age)
                DetailRow("Feeding Instruction", cow.feedingNote)
                DetailRow("Owner Note", cow.ownerNote)
            }
        }

        Surface(
            color = SoftGreenContainer,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFC8E6C9)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceAround) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Morning Milk", fontSize = 11.sp, color = PrimaryForest)
                    Text("${cow.morningMilk} L", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Evening Milk", fontSize = 11.sp, color = PrimaryForest)
                    Text("${cow.eveningMilk} L", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 4. DAILY TASKS SCREEN (Screen 104)
// -----------------------------------------------------------------------------
@Composable
fun WorkerTasksScreen(viewModel: HambaaViewModel) {
    val tasks by viewModel.workerTasks.collectAsState()
    var taskFilter by remember { mutableStateOf("All") }

    val filtered = tasks.filter {
        taskFilter == "All" || it.status == taskFilter
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Daily Shift Tasks", subtitle = "${tasks.count { it.status == "Completed" }} of ${tasks.size} Completed", onBack = { viewModel.navigateTo(101) })

        Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("All", "Pending", "In Progress", "Completed").forEach { filter ->
                FilterChip(
                    selected = taskFilter == filter,
                    onClick = { taskFilter = filter },
                    label = { Text(filter, fontSize = 11.sp) }
                )
            }
        }

        filtered.forEach { task ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(task.time, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                        CowStatusBadge(task.status)
                    }
                    Text(task.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    Text(task.desc, fontSize = 11.sp, color = TextMuted)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                        if (task.status != "Completed") {
                            Button(
                                onClick = { viewModel.updateTaskStatus(task.id, "Completed") },
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Mark Completed", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Text("Done ✓", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SecondaryFresh)
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 5. FEEDING MANAGEMENT SCREEN (Screen 105)
// -----------------------------------------------------------------------------
@Composable
fun WorkerFeedingScreen(viewModel: HambaaViewModel) {
    val periods by viewModel.feedingPeriods.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Feeding Management", subtitle = "Schedules & rations for assigned cows", onBack = { viewModel.navigateTo(101) })

        periods.forEach { period ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(period.period, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                        Text(period.timeWindow, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                    }
                    period.items.forEach { item ->
                        Surface(color = BgCream, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(item.group, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                                    CowStatusBadge(item.status)
                                }
                                Text(item.feed, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = PrimaryForest)
                                Text("Amount: ${item.amount} • Scheduled: ${item.time}", fontSize = 10.sp, color = TextMuted)
                            }
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 6. MILK PRODUCTION SCREEN (Screen 106)
// -----------------------------------------------------------------------------
@Composable
fun WorkerMilkScreen(viewModel: HambaaViewModel) {
    val workerCows by viewModel.workerCows.collectAsState()
    var selectedCowId by remember { mutableStateOf(workerCows.firstOrNull()?.id ?: "C-101") }
    var shift by remember { mutableStateOf("Morning") }
    var litersText by remember { mutableStateOf("12.0") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Record Milk Production", subtitle = "Log milking yield for assigned cows", onBack = { viewModel.navigateTo(101) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Column {
                    Text("Select Assigned Cow *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        workerCows.forEach { c ->
                            FilterChip(
                                selected = selectedCowId == c.id,
                                onClick = { selectedCowId = c.id },
                                label = { Text("${c.name} (#${c.id})", fontSize = 11.sp) }
                            )
                        }
                    }
                }

                Column {
                    Text("Milking Shift *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { shift = "Morning" },
                            colors = ButtonDefaults.buttonColors(containerColor = if (shift == "Morning") PrimaryForest else Color.LightGray),
                            modifier = Modifier.weight(1f)
                        ) { Text("Morning") }
                        Button(
                            onClick = { shift = "Evening" },
                            colors = ButtonDefaults.buttonColors(containerColor = if (shift == "Evening") PrimaryForest else Color.LightGray),
                            modifier = Modifier.weight(1f)
                        ) { Text("Evening") }
                    }
                }

                Column {
                    Text("Milk Quantity (Liters) *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(
                        value = litersText,
                        onValueChange = { litersText = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Button(
                    onClick = {
                        val l = litersText.toDoubleOrNull() ?: 12.0
                        viewModel.recordWorkerMilk(selectedCowId, shift, l)
                        viewModel.navigateTo(101)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Save Milk Record", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 7. HEALTH REPORTING SCREEN (Screen 107)
// -----------------------------------------------------------------------------
@Composable
fun WorkerHealthReportScreen(viewModel: HambaaViewModel) {
    val workerCows by viewModel.workerCows.collectAsState()
    var selectedCowId by remember { mutableStateOf(workerCows.firstOrNull()?.id ?: "C-102") }
    var urgency by remember { mutableStateOf("Needs Attention") }
    var notes by remember { mutableStateOf("Sluggish movement and lower feed intake during morning shift.") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Health Observation Report", subtitle = "Submit observations for Owner / Vet review", onBack = { viewModel.navigateTo(101) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Column {
                    Text("Select Cow *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        workerCows.forEach { c ->
                            FilterChip(
                                selected = selectedCowId == c.id,
                                onClick = { selectedCowId = c.id },
                                label = { Text("${c.name} (#${c.id})", fontSize = 11.sp) }
                            )
                        }
                    }
                }

                Column {
                    Text("Urgency Level *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Routine", "Needs Attention", "Urgent / Emergency").forEach { u ->
                            Button(
                                onClick = { urgency = u },
                                colors = ButtonDefaults.buttonColors(containerColor = if (urgency == u) ErrorRed else Color.LightGray),
                                modifier = Modifier.weight(1f)
                            ) { Text(u, fontSize = 10.sp) }
                        }
                    }
                }

                Column {
                    Text("Observation Notes *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false
                    )
                }

                Button(
                    onClick = {
                        viewModel.submitHealthReport(selectedCowId, listOf("Reduced Appetite"), urgency, notes)
                        viewModel.navigateTo(101)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Submit Health Report", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 8. WORK HISTORY SCREEN (Screen 108)
// -----------------------------------------------------------------------------
@Composable
fun WorkerHistoryScreen(viewModel: HambaaViewModel) {
    val history by viewModel.workHistory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Shift Work History", subtitle = "Completed tasks & recorded logs", onBack = { viewModel.navigateTo(101) })

        history.forEach { item ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.type, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                        Text(item.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                        Text("${item.target} • ${item.date} at ${item.time}", fontSize = 10.sp, color = TextMuted)
                    }
                    CowStatusBadge(item.status)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 9. NOTIFICATIONS SCREEN (Screen 109)
// -----------------------------------------------------------------------------
@Composable
fun WorkerNotificationsScreen(viewModel: HambaaViewModel) {
    val notifs by viewModel.workerNotifications.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Notifications & Alerts", subtitle = "Owner instructions & reminders", onBack = { viewModel.navigateTo(101) })

        notifs.forEach { n ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, if (n.unread) PrimaryForest else BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(n.category, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
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
// 10. WORKER PROFILE SCREEN (Screen 110)
// -----------------------------------------------------------------------------
@Composable
fun WorkerProfileScreen(viewModel: HambaaViewModel) {
    val profile by viewModel.workerProfile.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout from HAMBAA?") },
            text = { Text("Your shift records and task statuses are synced with Farm Owner.") },
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
        HambaaTopBar(title = "Worker Profile", subtitle = "Shift & role credentials")

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
                CowStatusBadge("COW WORKER")
                Spacer(modifier = Modifier.height(6.dp))
                Text(profile.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                Text("${profile.designation} • ${profile.empId}", fontSize = 12.sp, color = TextMuted)
            }
        }

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                DetailRow("Assigned Area", profile.assignedArea)
                DetailRow("Phone Number", profile.phone)
                DetailRow("Assigned Cows", "${profile.assignedCowCount} Head")
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
