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
fun WatchmanAppRoot(viewModel: HambaaViewModel) {
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
                    301 -> WatchmanDashboardScreen(viewModel)
                    302 -> WatchmanAttendanceScreen(viewModel)
                    303 -> WatchmanVisitorsScreen(viewModel)
                    304 -> WatchmanRegisterVisitorScreen(viewModel)
                    305 -> WatchmanVisitorDetailsScreen(viewModel)
                    306 -> WatchmanLogsScreen(viewModel)
                    307 -> WatchmanVehiclesScreen(viewModel)
                    308 -> WatchmanDeliveriesScreen(viewModel)
                    309 -> WatchmanReportIncidentScreen(viewModel)
                    310 -> WatchmanIncidentSuccessScreen(viewModel)
                    311 -> WatchmanInstructionsScreen(viewModel)
                    312 -> WatchmanNotificationsScreen(viewModel)
                    313 -> WatchmanProfileScreen(viewModel)
                    314 -> WatchmanAttendanceHistoryScreen(viewModel)
                    else -> WatchmanDashboardScreen(viewModel)
                }
            }

            // Bottom Navigation Bar for Watchman (Home, Visitors, Logs, Profile)
            if (activeScreen in listOf(301, 303, 306, 313)) {
                WatchmanBottomNav(activeScreen = activeScreen, onNavigate = { viewModel.navigateTo(it) })
            }
        }
    }
}

@Composable
fun WatchmanBottomNav(activeScreen: Int, onNavigate: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = activeScreen == 301,
            onClick = { onNavigate(301) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 303,
            onClick = { onNavigate(303) },
            icon = { Icon(Icons.Default.People, contentDescription = "Visitors") },
            label = { Text("Visitors", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 306,
            onClick = { onNavigate(306) },
            icon = { Icon(Icons.Default.ListAlt, contentDescription = "Logs") },
            label = { Text("Logs", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 313,
            onClick = { onNavigate(313) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
    }
}

// -----------------------------------------------------------------------------
// 1. WATCHMAN DASHBOARD (Screen 301)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanDashboardScreen(viewModel: HambaaViewModel) {
    val profile by viewModel.watchmanProfile.collectAsState()
    val visitors by viewModel.visitors.collectAsState()
    val vehicles by viewModel.vehicles.collectAsState()
    val notifs by viewModel.watchmanNotifications.collectAsState()

    val insideCount = visitors.count { it.status == "Inside" }

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
                Text("MAIN GATE SECURITY • WTM-001", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryFresh)
                Text("Welcome, ${profile.name}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = TextCharcoal)
                Text(profile.gatePost, fontSize = 11.sp, color = TextMuted)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = { viewModel.navigateTo(312) },
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White).border(1.dp, BorderGreen, CircleShape)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = TextCharcoal, modifier = Modifier.size(20.dp))
                        Badge(modifier = Modifier.align(Alignment.TopEnd)) { Text("${notifs.size}") }
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

        // 4 Gate KPI Cards
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DashboardKpiCard(modifier = Modifier.weight(1f), title = "Total Today", value = "${visitors.size}", badge = "Visitors", icon = { Icon(Icons.Default.People, contentDescription = null, tint = PrimaryForest) }, onClick = { viewModel.navigateTo(303) })
            DashboardKpiCard(modifier = Modifier.weight(1f), title = "Inside", value = "$insideCount", badge = "Active", icon = { Icon(Icons.Default.Login, contentDescription = null, tint = SecondaryFresh) }, onClick = { viewModel.navigateTo(303) })
            DashboardKpiCard(modifier = Modifier.weight(1f), title = "Vehicles", value = "${vehicles.size}", badge = "Gate", icon = { Icon(Icons.Default.LocalShipping, contentDescription = null, tint = WarningAmber) }, onClick = { viewModel.navigateTo(307) })
            DashboardKpiCard(modifier = Modifier.weight(1f), title = "Alerts", value = "1", badge = "Sec", icon = { Icon(Icons.Default.Warning, contentDescription = null, tint = ErrorRed) }, onClick = { viewModel.navigateTo(312) })
        }

        // Quick Actions 4-Column Strip
        Column {
            Text("Gate Quick Actions", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                WorkerQuickActionTile("Attendance", Icons.Default.Fingerprint, PrimaryForest) { viewModel.navigateTo(302) }
                WorkerQuickActionTile("New Visitor", Icons.Default.PersonAdd, SecondaryFresh) { viewModel.navigateTo(304) }
                WorkerQuickActionTile("Vehicle Log", Icons.Default.LocalShipping, WarningAmber) { viewModel.navigateTo(307) }
                WorkerQuickActionTile("Report Inc.", Icons.Default.ReportProblem, ErrorRed) { viewModel.navigateTo(309) }
            }
        }

        // Recent Visitors Preview
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Currently Inside Visitors", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    TextButton(onClick = { viewModel.navigateTo(303) }) {
                        Text("View All", fontSize = 12.sp, color = PrimaryForest, fontWeight = FontWeight.Bold)
                    }
                }
                visitors.filter { it.status == "Inside" }.forEach { v ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable {
                            viewModel.setSelectedVisitorId(v.id)
                            viewModel.navigateTo(305)
                        },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(v.name, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                            Text("${v.purpose} • Met: ${v.personToMeet}", fontSize = 10.sp, color = TextMuted)
                        }
                        CowStatusBadge(v.status)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 2. ATTENDANCE SCREEN (Screen 302)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanAttendanceScreen(viewModel: HambaaViewModel) {
    val profile by viewModel.watchmanProfile.collectAsState()
    val history by viewModel.attendanceHistory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Shift Attendance", subtitle = "Main Gate Duty Timestamps", onBack = { viewModel.navigateTo(301) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CowStatusBadge(profile.attendanceStatus)
                Text(profile.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                Text(profile.assignedShift, fontSize = 12.sp, color = TextMuted)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Check-In", fontSize = 11.sp, color = TextMuted)
                        Text(profile.checkInTime, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Check-Out", fontSize = 11.sp, color = TextMuted)
                        Text(profile.checkOutTime, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = ErrorRed)
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = { viewModel.checkInAttendance() },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(44.dp)
                    ) {
                        Text("Check In", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { viewModel.checkOutAttendance() },
                        colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(44.dp)
                    ) {
                        Text("Check Out", fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Attendance History", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    TextButton(onClick = { viewModel.navigateTo(314) }) {
                        Text("View Monthly", fontSize = 11.sp, color = PrimaryForest, fontWeight = FontWeight.Bold)
                    }
                }
                history.forEach { ah ->
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text(ah.date, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                            Text("In: ${ah.checkIn} • Out: ${ah.checkOut}", fontSize = 10.sp, color = TextMuted)
                        }
                        CowStatusBadge(ah.status)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 3. VISITORS SCREEN (Screen 303)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanVisitorsScreen(viewModel: HambaaViewModel) {
    val visitors by viewModel.visitors.collectAsState()
    var filter by remember { mutableStateOf("All") }

    val filtered = visitors.filter {
        filter == "All" || it.status == filter
    }

    Scaffold(
        topBar = {
            HambaaTopBar(title = "Visitor Management", subtitle = "${visitors.count { it.status == "Inside" }} currently inside gate", onBack = { viewModel.navigateTo(301) })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.navigateTo(304) },
                containerColor = PrimaryForest,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.PersonAdd, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Register Visitor", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = BgCream
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("All", "Inside", "Exited").forEach { f ->
                    FilterChip(
                        selected = filter == f,
                        onClick = { filter = f },
                        label = { Text(f, fontSize = 11.sp) }
                    )
                }
            }

            filtered.forEach { v ->
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, BorderGreen),
                    modifier = Modifier.fillMaxWidth().clickable {
                        viewModel.setSelectedVisitorId(v.id)
                        viewModel.navigateTo(305)
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
                            Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryForest, modifier = Modifier.size(24.dp))
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("#${v.id} • ${v.visitorType}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                                CowStatusBadge(v.status)
                            }
                            Text(v.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                            Text("Purpose: ${v.purpose} • Met: ${v.personToMeet}", fontSize = 11.sp, color = TextMuted)
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                    }
                }
            }
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

// -----------------------------------------------------------------------------
// 4. REGISTER VISITOR SCREEN (Screen 304)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanRegisterVisitorScreen(viewModel: HambaaViewModel) {
    var name by remember { mutableStateOf("Nazmul Haque") }
    var phone by remember { mutableStateOf("+880 1812-334455") }
    var address by remember { mutableStateOf("Dhanmondi, Dhaka") }
    var purpose by remember { mutableStateOf("Farm Equipment Inspection") }
    var personToMeet by remember { mutableStateOf("Farm Owner") }
    var visitorType by remember { mutableStateOf("Business") }
    var vehicleInfo by remember { mutableStateOf("Dhaka Metro-Ha 12-3456") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Register Visitor", subtitle = "Main Gate Access Control", onBack = { viewModel.navigateTo(303) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Column {
                    Text("Visitor Full Name *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = name, onValueChange = { name = it }, modifier = Modifier.fillMaxWidth())
                }
                Column {
                    Text("Phone Number *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = phone, onValueChange = { phone = it }, modifier = Modifier.fillMaxWidth())
                }
                Column {
                    Text("Address / Organization", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = address, onValueChange = { address = it }, modifier = Modifier.fillMaxWidth())
                }
                Column {
                    Text("Visit Purpose *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = purpose, onValueChange = { purpose = it }, modifier = Modifier.fillMaxWidth())
                }
                Column {
                    Text("Person to Meet *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = personToMeet, onValueChange = { personToMeet = it }, modifier = Modifier.fillMaxWidth())
                }
                Column {
                    Text("Vehicle Info (if any)", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = vehicleInfo, onValueChange = { vehicleInfo = it }, modifier = Modifier.fillMaxWidth())
                }

                Button(
                    onClick = {
                        viewModel.registerVisitor(name, phone, address, purpose, personToMeet, visitorType, vehicleInfo)
                        viewModel.navigateTo(303)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Issue Gate Pass & Enter", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 5. VISITOR DETAILS SCREEN (Screen 305)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanVisitorDetailsScreen(viewModel: HambaaViewModel) {
    val visitors by viewModel.visitors.collectAsState()
    val selectedId by viewModel.selectedVisitorId.collectAsState()
    val visitor = visitors.find { it.id == selectedId } ?: visitors.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Visitor Gate Pass", subtitle = "#${visitor.id} • ${visitor.visitorType}", onBack = { viewModel.navigateTo(303) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CowStatusBadge(visitor.status)
                Text(visitor.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                Text(visitor.phone, fontSize = 13.sp, color = PrimaryForest, fontWeight = FontWeight.SemiBold)

                Divider(color = BorderGreen)

                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DetailRow("Organization / Address", visitor.address)
                    DetailRow("Visit Purpose", visitor.purpose)
                    DetailRow("Person to Meet", visitor.personToMeet)
                    DetailRow("Vehicle Info", visitor.vehicleInfo)
                    DetailRow("Entry Timestamp", "${visitor.entryDate} at ${visitor.entryTime}")
                    DetailRow("Exit Timestamp", visitor.exitTime)
                    DetailRow("Gate Notes", visitor.notes)
                }

                if (visitor.status == "Inside") {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            viewModel.recordVisitorExit(visitor.id)
                            viewModel.navigateTo(303)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("Record Visitor Exit & Return Pass", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 6. ENTRY EXIT LOGS SCREEN (Screen 306)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanLogsScreen(viewModel: HambaaViewModel) {
    val logs by viewModel.entryExitLogs.collectAsState()
    var categoryFilter by remember { mutableStateOf("All") }

    val filtered = logs.filter {
        categoryFilter == "All" || it.category == categoryFilter
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Gate Movement Logs", subtitle = "Chronological visitor, vehicle & delivery log", onBack = { viewModel.navigateTo(301) })

        Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("All", "Visitors", "Vehicles", "Deliveries").forEach { cat ->
                FilterChip(
                    selected = categoryFilter == cat,
                    onClick = { categoryFilter = cat },
                    label = { Text(cat, fontSize = 11.sp) }
                )
            }
        }

        filtered.forEach { log ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("${log.category} • Action: ${log.action}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                        Text(log.ident, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                        Text("${log.date} at ${log.time}", fontSize = 10.sp, color = TextMuted)
                    }
                    CowStatusBadge(log.status)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 7. VEHICLE MANAGEMENT SCREEN (Screen 307)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanVehiclesScreen(viewModel: HambaaViewModel) {
    val vehicles by viewModel.vehicles.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Vehicle Gate Tracker", subtitle = "${vehicles.size} Farm registered vehicles", onBack = { viewModel.navigateTo(301) })

        vehicles.forEach { veh ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(veh.type, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                        CowStatusBadge(veh.status)
                    }
                    Text(veh.regNo, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    Text("Driver: ${veh.driverName} (${veh.driverPhone})", fontSize = 11.sp, color = TextMuted)
                    Text("Purpose: ${veh.purpose}", fontSize = 11.sp, color = TextMuted)
                    Text("Entry: ${veh.entryTime} • Exit: ${veh.exitTime}", fontSize = 10.sp, color = TextMuted)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 8. DELIVERY RECORDS SCREEN (Screen 308)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanDeliveriesScreen(viewModel: HambaaViewModel) {
    val deliveries by viewModel.deliveries.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Supplier Deliveries", subtitle = "Gate arrival & departure logs", onBack = { viewModel.navigateTo(301) })

        deliveries.forEach { del ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(del.deliveryType, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                        CowStatusBadge(del.status)
                    }
                    Text(del.supplier, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    Text("Vehicle: ${del.vehicleNo}", fontSize = 11.sp, color = TextMuted)
                    Text("Purpose: ${del.purpose}", fontSize = 11.sp, color = TextMuted)
                    Text("Arrival Time: ${del.arrivalTime}", fontSize = 10.sp, color = TextMuted)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 9. REPORT INCIDENT SCREEN (Screen 309)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanReportIncidentScreen(viewModel: HambaaViewModel) {
    var category by remember { mutableStateOf("Perimeter Security Breach") }
    var location by remember { mutableStateOf("South Fodder Gate") }
    var description by remember { mutableStateOf("Gate latch found unsecured during routine watch round.") }
    var priority by remember { mutableStateOf("High") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Report Security Incident", subtitle = "Instant alert to Farm Owner", onBack = { viewModel.navigateTo(301) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Column {
                    Text("Incident Category *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = category, onValueChange = { category = it }, modifier = Modifier.fillMaxWidth())
                }

                Column {
                    Text("Location / Gate *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = location, onValueChange = { location = it }, modifier = Modifier.fillMaxWidth())
                }

                Column {
                    Text("Priority Level *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Low", "Medium", "High", "Critical").forEach { p ->
                            Button(
                                onClick = { priority = p },
                                colors = ButtonDefaults.buttonColors(containerColor = if (priority == p) ErrorRed else Color.LightGray),
                                modifier = Modifier.weight(1f)
                            ) { Text(p, fontSize = 10.sp) }
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
                        viewModel.submitSecurityIncident(category, location, description, priority)
                        viewModel.navigateTo(310)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Submit Incident Report", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 10. INCIDENT SUCCESS SCREEN (Screen 310)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanIncidentSuccessScreen(viewModel: HambaaViewModel) {
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
        Text("Incident Report Submitted", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
        Text("Farm Owner has been instantly alerted of this security incident.", fontSize = 12.sp, color = TextMuted, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { viewModel.navigateTo(301) },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Back to Dashboard", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// -----------------------------------------------------------------------------
// 11. SECURITY INSTRUCTIONS SCREEN (Screen 311)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanInstructionsScreen(viewModel: HambaaViewModel) {
    val instructions by viewModel.securityInstructions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Security Instructions", subtitle = "Mandatory Farm Owner gate protocols", onBack = { viewModel.navigateTo(301) })

        instructions.forEach { ins ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(ins.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                    Text(ins.desc, fontSize = 11.sp, color = TextCharcoal)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 12. NOTIFICATIONS SCREEN (Screen 312)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanNotificationsScreen(viewModel: HambaaViewModel) {
    val notifs by viewModel.watchmanNotifications.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Notifications & Alerts", subtitle = "Visitor alerts & owner instructions", onBack = { viewModel.navigateTo(301) })

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
// 13. WATCHMAN PROFILE SCREEN (Screen 313)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanProfileScreen(viewModel: HambaaViewModel) {
    val profile by viewModel.watchmanProfile.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout from HAMBAA?") },
            text = { Text("Your gate entry logs and attendance status are safely synced.") },
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
        HambaaTopBar(title = "Watchman Profile", subtitle = "Shift & security credentials")

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
                CowStatusBadge("WATCHMAN")
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
                DetailRow("Gate Post", profile.gatePost)
                DetailRow("Phone Number", profile.phone)
                DetailRow("Assigned Shift", profile.assignedShift)
                DetailRow("Farm Name", profile.farmName)
            }
        }

        Button(
            onClick = { viewModel.navigateTo(314) },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("View Attendance History", fontSize = 13.sp, fontWeight = FontWeight.Bold)
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

// -----------------------------------------------------------------------------
// 14. ATTENDANCE HISTORY SCREEN (Screen 314)
// -----------------------------------------------------------------------------
@Composable
fun WatchmanAttendanceHistoryScreen(viewModel: HambaaViewModel) {
    val history by viewModel.attendanceHistory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Attendance History", subtitle = "Monthly duty logs & working hours", onBack = { viewModel.navigateTo(313) })

        history.forEach { ah ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(ah.date, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                        CowStatusBadge(ah.status)
                    }
                    Text("Check-In: ${ah.checkIn} • Check-Out: ${ah.checkOut}", fontSize = 11.sp, color = TextMuted)
                    Text("Total Shift Hours: ${ah.hours}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = PrimaryForest)
                }
            }
        }
    }
}
