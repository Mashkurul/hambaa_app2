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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hambaaapp.ui.theme.*
import com.example.hambaaapp.data.UserRole
import com.example.hambaaapp.viewmodel.HambaaViewModel

@Composable
fun HambaaOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        textStyle = TextStyle(color = TextCharcoal, fontSize = 14.sp),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextCharcoal,
            unfocusedTextColor = TextCharcoal,
            disabledTextColor = TextCharcoal,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = PrimaryForest,
            unfocusedBorderColor = BorderGreen
        )
    )
}

@Composable
fun OwnerAppRoot(viewModel: HambaaViewModel) {
    val activeScreen by viewModel.activeScreen.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            when (activeScreen) {
                3 -> DashboardScreen(viewModel)
                4 -> CowListScreen(viewModel)
                5 -> CowDetailsScreen(viewModel)
                6 -> AddCowScreen(viewModel)
                7 -> MilkProductionScreen(viewModel)
                8 -> RecordMilkScreen(viewModel)
                9 -> NutritionScreen(viewModel)
                10 -> HealthListScreen(viewModel)
                11 -> AddHealthScreen(viewModel)
                12 -> EmployeeListScreen(viewModel)
                13 -> EmployeeDetailsScreen(viewModel)
                14 -> ExpensesScreen(viewModel)
                15 -> AddExpenseScreen(viewModel)
                16 -> SalesScreen(viewModel)
                17 -> AddSaleScreen(viewModel)
                18 -> ReportsScreen(viewModel)
                19 -> ProfileSettingsScreen(viewModel)
                20 -> AddEmployeeScreen(viewModel)
                else -> DashboardScreen(viewModel)
            }
        }

        if (activeScreen in listOf(3, 4, 18, 19)) {
            HambaaBottomNav(activeScreen = activeScreen, onNavigate = { viewModel.navigateTo(it) })
        }
    }
}

@Composable
fun HambaaAppRoot(viewModel: HambaaViewModel) {
    val activeScreen by viewModel.activeScreen.collectAsState()
    val userRole by viewModel.userRole.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        color = BgCream
    ) {
        when {
            activeScreen == 1 -> SplashScreen(viewModel)
            activeScreen == 2 -> LoginScreen(viewModel)
            userRole == UserRole.SALESMAN -> SalesmanAppRoot(viewModel)
            userRole == UserRole.WATCHMAN -> WatchmanAppRoot(viewModel)
            userRole == UserRole.CLEANER -> CleanerAppRoot(viewModel)
            userRole == UserRole.WORKER -> WorkerAppRoot(viewModel)
            else -> OwnerAppRoot(viewModel)
        }
    }
}

@Composable
fun HambaaBottomNav(activeScreen: Int, onNavigate: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = activeScreen == 3,
            onClick = { onNavigate(3) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 4,
            onClick = { onNavigate(4) },
            icon = { Icon(Icons.Default.Face, contentDescription = "Cows") },
            label = { Text("Cows", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 18,
            onClick = { onNavigate(18) },
            icon = { Icon(Icons.Default.Menu, contentDescription = "Reports") },
            label = { Text("Reports", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 19,
            onClick = { onNavigate(19) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
    }
}

@Composable
fun HambaaTopBar(title: String, subtitle: String = "", onBack: (() -> Unit)? = null, actions: @Composable (() -> Unit)? = null) {
    Surface(
        color = BgCream,
        tonalElevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                if (onBack != null) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(1.dp, BorderGreen, CircleShape)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextCharcoal, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Column {
                    Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    if (subtitle.isNotBlank()) {
                        Text(subtitle, fontSize = 11.sp, color = TextMuted)
                    }
                }
            }
            if (actions != null) {
                actions()
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 1. SPLASH SCREEN
// -----------------------------------------------------------------------------
@Composable
fun SplashScreen(viewModel: HambaaViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PrimaryForest, PrimaryForestDark, Color(0xFF113D15))
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize().padding(vertical = 32.dp)
        ) {
            Surface(
                color = SoftGreenContainer.copy(alpha = 0.2f),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
            ) {
                Text(
                    text = "AI-POWERED DAIRY OS",
                    color = Color(0xFFC8E6C9),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.size(88.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color.White, modifier = Modifier.size(48.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("HAMBAA", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text("Smart Farm. Better Future.", fontSize = 13.sp, color = Color(0xFFC8E6C9), fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(4.dp))
                Text("AI-Based Cow Shed Management System", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
            }

            Button(
                onClick = { viewModel.navigateTo(2) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text("Get Started", color = PrimaryForest, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 2. LOGIN SCREEN
// -----------------------------------------------------------------------------
@Composable
fun LoginScreen(viewModel: HambaaViewModel) {
    val showPassword by viewModel.showPassword.collectAsState()
    var usernameText by remember { mutableStateOf("owner@hambaafarm.com") }
    var passwordText by remember { mutableStateOf("FarmOwner2026") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        // Top Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(PrimaryForest, SecondaryFresh)
                    ),
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
                .padding(24.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = PrimaryForest, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("HAMBAA", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                        Text("Smart Farm. Better Future.", fontSize = 11.sp, color = Color(0xFFC8E6C9))
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text("Welcome Back!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Manage your farm with HAMBAA.", fontSize = 13.sp, color = Color(0xFFE8F5E9))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column {
                        Text("Email or Username", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(6.dp))
                        HambaaOutlinedTextField(
                            value = usernameText,
                            onValueChange = { usernameText = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Column {
                        Text("Password", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(6.dp))
                        HambaaOutlinedTextField(
                            value = passwordText,
                            onValueChange = { passwordText = it },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                                    Icon(
                                        if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Toggle password"
                                    )
                                }
                            }
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = true, onCheckedChange = {}, colors = CheckboxDefaults.colors(checkedColor = PrimaryForest))
                            Text("Remember me", fontSize = 12.sp, color = TextMuted)
                        }
                        TextButton(onClick = { viewModel.login(usernameText, passwordText) }) {
                            Text("Forgot Password?", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = PrimaryForest)
                        }
                    }

                    Button(
                        onClick = { viewModel.login(usernameText, passwordText) },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Login to Farm Dashboard", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }

                    Text(
                        "💡 Demo Logins:\n• Owner: owner@hambaafarm.com / FarmOwner2026\n• Worker: worker / worker123\n• Cleaner: cleaner / cleaner123\n• Watchman: watchman / watchman123\n• Salesman: sales / sales123",
                        fontSize = 11.sp,
                        color = TextMuted,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = SoftGreenContainer,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFC8E6C9)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(PrimaryForest),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("AI Shed Monitoring Active", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                        Text("48 cattle synced • Automated yield & health insights", fontSize = 11.sp, color = TextMuted)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 3. DASHBOARD SCREEN
// -----------------------------------------------------------------------------
@Composable
fun DashboardScreen(viewModel: HambaaViewModel) {
    val cows by viewModel.cows.collectAsState()
    val milkLogs by viewModel.milkLogs.collectAsState()
    val farmProfile by viewModel.farmProfile.collectAsState()
    val totalMilkToday = milkLogs.sumOf { it.liters } + 368.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Greeting Header
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(farmProfile.name.uppercase(), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryFresh)
                Text("Good Morning, ${farmProfile.owner}!", fontSize = 19.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = { viewModel.navigateTo(10) },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, BorderGreen, CircleShape)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Notifications, contentDescription = "Alerts", tint = TextCharcoal, modifier = Modifier.size(20.dp))
                        Badge(modifier = Modifier.align(Alignment.TopEnd)) { Text("3") }
                    }
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(PrimaryForest),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }
        }

        // 2x2 Summary KPI Cards
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DashboardKpiCard(
                modifier = Modifier.weight(1f),
                title = "Total Cows",
                value = "${44 + cows.size}",
                badge = "+2 new",
                icon = { Icon(Icons.Default.Face, contentDescription = null, tint = PrimaryForest) },
                onClick = { viewModel.navigateTo(4) }
            )
            DashboardKpiCard(
                modifier = Modifier.weight(1f),
                title = "Today's Milk",
                value = "${totalMilkToday.toInt()} L",
                badge = "+6.4%",
                icon = { Icon(Icons.Default.WaterDrop, contentDescription = null, tint = SecondaryFresh) },
                onClick = { viewModel.navigateTo(7) }
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DashboardKpiCard(
                modifier = Modifier.weight(1f),
                title = "Active Employees",
                value = "12",
                badge = "11 On Duty",
                icon = { Icon(Icons.Default.Group, contentDescription = null, tint = PrimaryForest) },
                onClick = { viewModel.navigateTo(12) }
            )
            DashboardKpiCard(
                modifier = Modifier.weight(1f),
                title = "Monthly Expenses",
                value = "৳3,290",
                badge = "Nov '26",
                icon = { Icon(Icons.Default.Payments, contentDescription = null, tint = WarningAmber) },
                onClick = { viewModel.navigateTo(14) }
            )
        }

        // Quick Actions 4-Column Strip
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Quick Actions", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                Text("Tap to record", fontSize = 11.sp, color = TextMuted)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                QuickActionTile("Add Cow", Icons.Default.Add, PrimaryForest) { viewModel.navigateTo(6) }
                QuickActionTile("Record Milk", Icons.Default.WaterDrop, SecondaryFresh) { viewModel.navigateTo(8) }
                QuickActionTile("Add Expense", Icons.Default.Payments, WarningAmber) { viewModel.navigateTo(15) }
                QuickActionTile("Health Rec.", Icons.Default.Healing, ErrorRed) { viewModel.navigateTo(11) }
            }
        }

        // Farm Modules Horizontal Strip
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.navigateTo(7) }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest)) { Text("Milk Production", fontSize = 11.sp) }
            OutlinedButton(onClick = { viewModel.navigateTo(9) }) { Text("Food & Nutrition", fontSize = 11.sp, color = TextCharcoal) }
            OutlinedButton(onClick = { viewModel.navigateTo(10) }) { Text("Health Care", fontSize = 11.sp, color = TextCharcoal) }
            OutlinedButton(onClick = { viewModel.navigateTo(16) }) { Text("Sales (৳5,213)", fontSize = 11.sp, color = TextCharcoal) }
            OutlinedButton(onClick = { viewModel.navigateTo(12) }) { Text("Employees", fontSize = 11.sp, color = TextCharcoal) }
            OutlinedButton(onClick = { viewModel.navigateTo(14) }) { Text("Expenses", fontSize = 11.sp, color = TextCharcoal) }
        }

        // Urgent Health Alert Card
        Surface(
            color = Color(0xFFFFEBEE),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFFFCDD2)),
            modifier = Modifier.fillMaxWidth().clickable { viewModel.navigateTo(10) }
        ) {
            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(38.dp).clip(RoundedCornerShape(10.dp)).background(ErrorRed),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Health Alert • Gauri (#HB-102)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB71C1C))
                    Text("Mild fever (39.6°C) detected • Follow-up due", fontSize = 11.sp, color = ErrorRed)
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = ErrorRed)
            }
        }

        // Milk Production Overview Chart Card
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth().clickable { viewModel.navigateTo(7) }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Milk Production Overview", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                        Text("Last 7 Days (Liters / Day)", fontSize = 11.sp, color = TextMuted)
                    }
                    Surface(color = SoftGreenContainer, shape = RoundedCornerShape(8.dp)) {
                        Text("Avg 398 L", color = PrimaryForest, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth().height(90.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.Bottom) {
                    val days = listOf("Mon" to 0.74f, "Tue" to 0.80f, "Wed" to 0.78f, "Thu" to 0.86f, "Fri" to 0.84f, "Sat" to 0.90f, "Sun" to 0.95f)
                    days.forEachIndexed { idx, pair ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxHeight()) {
                            Box(
                                modifier = Modifier
                                    .width(24.dp)
                                    .fillMaxHeight(pair.second)
                                    .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                    .background(if (idx == 6) PrimaryForest else SecondaryFresh)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(pair.first, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = if (idx == 6) PrimaryForest else TextMuted)
                        }
                    }
                }
            }
        }

        // Recent Farm Activities
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Recent Farm Activities", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                ActivityItem(color = SecondaryFresh, title = "Morning Milking Completed (238.0 L)", subtitle = "Logged by Mizanur Rahman • 07:15 AM")
                ActivityItem(color = WarningAmber, title = "Low Stock Warning: Protein Concentrate", subtitle = "180 kg remaining (2 days supply) • 08:30 AM")
                ActivityItem(color = PrimaryForest, title = "Bulk Milk Sale (৳494.00)", subtitle = "FreshMart Dairy Co. • Yesterday")
            }
        }
    }
}

@Composable
fun DashboardKpiCard(modifier: Modifier = Modifier, title: String, value: String, badge: String, icon: @Composable () -> Unit, onClick: () -> Unit) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, BorderGreen),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(32.dp).clip(RoundedCornerShape(10.dp)).background(SoftGreenContainer),
                    contentAlignment = Alignment.Center
                ) {
                    icon()
                }
                Text(badge, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest, modifier = Modifier.background(SoftGreenContainer, RoundedCornerShape(6.dp)).padding(horizontal = 6.dp, vertical = 2.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = TextCharcoal)
            Text(title, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = TextMuted)
        }
    }
}

@Composable
fun RowScope.QuickActionTile(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, onClick: () -> Unit) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, BorderGreen),
        modifier = Modifier.weight(1f).clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
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

@Composable
fun ActivityItem(color: Color, title: String, subtitle: String) {
    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(color).offset(y = 4.dp))
        Column {
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
            Text(subtitle, fontSize = 11.sp, color = TextMuted)
        }
    }
}

// -----------------------------------------------------------------------------
// 4. COW LIST SCREEN
// -----------------------------------------------------------------------------
@Composable
fun CowListScreen(viewModel: HambaaViewModel) {
    val cows by viewModel.cows.collectAsState()
    val cowFilter by viewModel.cowFilter.collectAsState()
    val cowSearch by viewModel.cowSearch.collectAsState()
    val emptyState by viewModel.emptyState.collectAsState()

    val filteredCows = if (emptyState) emptyList() else cows.filter { c ->
        val matchFilter = cowFilter == "All" || c.status == cowFilter
        val matchSearch = cowSearch.isBlank() || c.name.contains(cowSearch, true) || c.id.contains(cowSearch, true)
        matchFilter && matchSearch
    }

    Scaffold(
        topBar = {
            HambaaTopBar(
                title = "Cow Management",
                subtitle = "${cows.size} Registered Cattle",
                onBack = { viewModel.navigateTo(3) },
                actions = {
                    TextButton(onClick = { viewModel.setEmptyState(!emptyState) }) {
                        Text(if (emptyState) "Show Data" else "Empty QA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.navigateTo(6) },
                containerColor = PrimaryForest,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Add Cow", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = BgCream
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Search Bar
            HambaaOutlinedTextField(
                value = cowSearch,
                onValueChange = { viewModel.setCowSearch(it) },
                placeholder = { Text("Search cow name, ID or breed...", fontSize = 12.sp, color = TextMuted) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextMuted) },
                modifier = Modifier.fillMaxWidth()
            )

            // Filter Chips
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("All", "Healthy", "Due Vaccine", "Sick").forEach { filter ->
                    FilterChip(
                        selected = cowFilter == filter,
                        onClick = { viewModel.setCowFilter(filter) },
                        label = { Text(filter, fontSize = 11.sp) }
                    )
                }
            }

            if (filteredCows.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.Pets, contentDescription = null, tint = TextMuted, modifier = Modifier.size(48.dp))
                        Text("No Cows Found", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                        Text("Add your first cow to get started.", fontSize = 12.sp, color = TextMuted)
                        Button(onClick = { viewModel.navigateTo(6) }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest)) {
                            Text("Add New Cow")
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    filteredCows.forEach { cow ->
                        Surface(
                            color = Color.White,
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, BorderGreen),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setSelectedCow(cow.id)
                                    viewModel.navigateTo(5)
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
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
                                        Text("#${cow.id}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                                        CowStatusBadge(cow.status)
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text("${cow.name} • ${cow.breed}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                                    Text("${cow.dailyMilk} L/day • ${cow.weight} • Age: ${cow.age}", fontSize = 11.sp, color = TextMuted)
                                }
                                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(64.dp))
                }
            }
        }
    }
}

@Composable
fun CowStatusBadge(status: String) {
    val (color, bg) = when (status) {
        "Healthy", "Active" -> PrimaryForest to SoftGreenContainer
        "Due Vaccine" -> WarningAmber to Color(0xFFFEF3C7)
        else -> ErrorRed to Color(0xFFFFEBEE)
    }
    Surface(color = bg, shape = RoundedCornerShape(6.dp)) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(color))
            Text(status, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = color)
        }
    }
}

// -----------------------------------------------------------------------------
// 5. COW DETAILS SCREEN
// -----------------------------------------------------------------------------
@Composable
fun CowDetailsScreen(viewModel: HambaaViewModel) {
    val cows by viewModel.cows.collectAsState()
    val selectedCowId by viewModel.selectedCowId.collectAsState()
    val cow = cows.find { it.id == selectedCowId } ?: cows.firstOrNull()

    if (cow == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cow not found")
        }
        return
    }

    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete ${cow.name}?") },
            text = { Text("Are you sure you want to permanently remove #${cow.id} and all associated records?") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    viewModel.deleteCow(cow.id)
                }) {
                    Text("Delete", color = ErrorRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel", color = PrimaryForest)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        HambaaTopBar(title = "Cow Details", subtitle = "#${cow.id}", onBack = { viewModel.navigateTo(4) }, actions = {
            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRed)
            }
        })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Hero Header Card
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.size(72.dp).clip(RoundedCornerShape(20.dp)).background(SoftGreenContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Pets, contentDescription = null, tint = PrimaryForest, modifier = Modifier.size(38.dp))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    CowStatusBadge(cow.status)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(cow.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    Text("${cow.breed} • #${cow.id}", fontSize = 12.sp, color = TextMuted)
                }
            }

            // Biometric Specs Grid
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Biometric Specifications", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        SpecItem("Weight", cow.weight)
                        SpecItem("Age", cow.age)
                        SpecItem("Gender", cow.gender)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        SpecItem("Date of Birth", cow.dob)
                        SpecItem("Daily Yield", "${cow.dailyMilk} L")
                        SpecItem("Vaccination", cow.vaccine)
                    }
                }
            }

            // Milk Yield Split Card
            Surface(
                color = SoftGreenContainer,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFC8E6C9)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceAround) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Morning Shift", fontSize = 11.sp, color = PrimaryForest)
                        Text("${cow.morningMilk} L", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Evening Shift", fontSize = 11.sp, color = PrimaryForest)
                        Text("${cow.eveningMilk} L", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                    }
                }
            }

            // History Timeline
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Health & Production History", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    cow.history.forEach { h ->
                        ActivityItem(color = PrimaryForest, title = h.title, subtitle = "${h.date} • ${h.desc}")
                    }
                }
            }
        }
    }
}

@Composable
fun SpecItem(label: String, value: String) {
    Column {
        Text(label, fontSize = 11.sp, color = TextMuted)
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
    }
}

// -----------------------------------------------------------------------------
// 6. ADD NEW COW SCREEN
// -----------------------------------------------------------------------------
@Composable
fun AddCowScreen(viewModel: HambaaViewModel) {
    var cowId by remember { mutableStateOf("HB-105") }
    var cowName by remember { mutableStateOf("Kajol") }
    var cowBreed by remember { mutableStateOf("Holstein Friesian") }
    var cowWeight by remember { mutableStateOf("460") }
    var cowStatus by remember { mutableStateOf("Healthy") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        HambaaTopBar(title = "Add New Cow", subtitle = "Register cattle to herd", onBack = { viewModel.navigateTo(4) })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Column {
                        Text("Cattle Ear Tag ID *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = cowId, onValueChange = { cowId = it }, modifier = Modifier.fillMaxWidth())
                    }

                    Column {
                        Text("Cow Name *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = cowName, onValueChange = { cowName = it }, modifier = Modifier.fillMaxWidth())
                    }

                    Column {
                        Text("Breed", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = cowBreed, onValueChange = { cowBreed = it }, modifier = Modifier.fillMaxWidth())
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Weight (kg)", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                            Spacer(modifier = Modifier.height(4.dp))
                            HambaaOutlinedTextField(value = cowWeight, onValueChange = { cowWeight = it }, modifier = Modifier.fillMaxWidth())
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Health Status", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                            Spacer(modifier = Modifier.height(4.dp))
                            HambaaOutlinedTextField(value = cowStatus, onValueChange = { cowStatus = it }, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }

            Button(
                onClick = { viewModel.addCow(cowId, cowName, cowBreed, cowWeight, cowStatus, "Female") },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Save Cow", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 7. MILK PRODUCTION SCREEN
// -----------------------------------------------------------------------------
@Composable
fun MilkProductionScreen(viewModel: HambaaViewModel) {
    val milkLogs by viewModel.milkLogs.collectAsState()
    val cows by viewModel.cows.collectAsState()
    val totalMilkToday = milkLogs.sumOf { it.liters } + 368.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        HambaaTopBar(title = "Milk Production", subtitle = "Daily & shift yield tracking", onBack = { viewModel.navigateTo(3) })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Hero Today's Milk Card
            Surface(
                color = PrimaryForest,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Today's Total Milk", fontSize = 12.sp, color = Color(0xFFC8E6C9))
                        Surface(color = Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(6.dp)) {
                            Text("26 Nov 2026", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("${String.format("%.1f", totalMilkToday)} Liters", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Surface(color = Color.White.copy(alpha = 0.15f), shape = RoundedCornerShape(12.dp), modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Morning Shift", fontSize = 10.sp, color = Color(0xFFC8E6C9))
                                Text("238.0 L", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                        Surface(color = Color.White.copy(alpha = 0.15f), shape = RoundedCornerShape(12.dp), modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Evening Shift", fontSize = 10.sp, color = Color(0xFFC8E6C9))
                                Text("${String.format("%.1f", totalMilkToday - 238)} L", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                }
            }

            // Cow-Wise Production
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Cow-Wise Production", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    cows.forEach { cow ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text("${cow.name} (#${cow.id})", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                                Text("AM: ${cow.morningMilk}L • PM: ${cow.eveningMilk}L", fontSize = 10.sp, color = TextMuted)
                            }
                            Surface(color = SoftGreenContainer, shape = RoundedCornerShape(8.dp)) {
                                Text("${cow.dailyMilk} L", color = PrimaryForest, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                        }
                    }
                }
            }

            // Recent Milk Logs
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Production History", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    milkLogs.forEach { log ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text("${log.cowName} (#${log.cowId}) • ${log.shift}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                                Text("${log.date} • ${log.notes}", fontSize = 10.sp, color = TextMuted)
                            }
                            Text("+${log.liters} L", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryForest)
                        }
                    }
                }
            }

            Button(
                onClick = { viewModel.navigateTo(8) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Add Milk Record", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 8. RECORD MILK SCREEN
// -----------------------------------------------------------------------------
@Composable
fun RecordMilkScreen(viewModel: HambaaViewModel) {
    val cows by viewModel.cows.collectAsState()
    var selectedCowId by remember { mutableStateOf(cows.firstOrNull()?.id ?: "HB-101") }
    var shift by remember { mutableStateOf("Morning") }
    var litersText by remember { mutableStateOf("12.5") }
    var notesText by remember { mutableStateOf("Normal yield & clean hygiene") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        HambaaTopBar(title = "Record Milk", subtitle = "Log milking parlor yield", onBack = { viewModel.navigateTo(7) })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
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

                    Column {
                        Text("Notes", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(
                            value = notesText,
                            onValueChange = { notesText = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = false
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val liters = litersText.toDoubleOrNull() ?: 12.0
                    viewModel.recordMilk(selectedCowId, shift, liters, notesText)
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

// -----------------------------------------------------------------------------
// 9. FOOD & NUTRITION SCREEN
// -----------------------------------------------------------------------------
@Composable
fun NutritionScreen(viewModel: HambaaViewModel) {
    val feedInventory by viewModel.feedInventory.collectAsState()
    val feedingSchedule by viewModel.feedingSchedule.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        HambaaTopBar(title = "Food & Nutrition", subtitle = "Daily ration & feed inventory", onBack = { viewModel.navigateTo(3) })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Daily Overview Card
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("Daily Feeding Overview", fontSize = 11.sp, color = TextMuted)
                            Text("680 kg / day", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = TextCharcoal)
                        }
                        Surface(color = SoftGreenContainer, shape = RoundedCornerShape(8.dp)) {
                            Text("AI Ration: 96%", color = PrimaryForest, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }
                }
            }

            // Schedule
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Today's Feeding Schedule", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    feedingSchedule.forEach { s ->
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(
                                modifier = Modifier.size(24.dp).clip(CircleShape).background(if (s.done) SecondaryFresh else Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(s.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                                    Text(s.time, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                                }
                                Text(s.detail, fontSize = 11.sp, color = TextMuted)
                            }
                        }
                    }
                }
            }

            // Feed Stock Inventory
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Feed Stock Inventory", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                feedInventory.forEach { item ->
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, BorderGreen),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Column {
                                    Text(item.category, fontSize = 10.sp, color = TextMuted)
                                    Text(item.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                                }
                                CowStatusBadge(item.status)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Available: ${item.stock}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                                Text("Usage: ${item.dailyUse}", fontSize = 11.sp, color = TextMuted)
                            }
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 10. HEALTH MANAGEMENT SCREEN
// -----------------------------------------------------------------------------
@Composable
fun HealthListScreen(viewModel: HambaaViewModel) {
    val healthRecords by viewModel.healthRecords.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        HambaaTopBar(title = "Health Management", subtitle = "Veterinary & vaccination care", onBack = { viewModel.navigateTo(3) }, actions = {
            Button(onClick = { viewModel.navigateTo(11) }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest)) {
                Text("+ Log", fontSize = 11.sp)
            }
        })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // 3-Column Health Overview
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(color = Color.White, shape = RoundedCornerShape(14.dp), border = BorderStroke(1.dp, BorderGreen), modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("43", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryForest)
                        Text("Healthy", fontSize = 10.sp, color = TextMuted)
                    }
                }
                Surface(color = Color(0xFFFFEBEE), shape = RoundedCornerShape(14.dp), border = BorderStroke(1.dp, Color(0xFFFFCDD2)), modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("2", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = ErrorRed)
                        Text("Sick", fontSize = 10.sp, color = ErrorRed)
                    }
                }
                Surface(color = Color(0xFFFEF3C7), shape = RoundedCornerShape(14.dp), border = BorderStroke(1.dp, Color(0xFFFDE68A)), modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("3", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = WarningAmber)
                        Text("Vaccine Due", fontSize = 10.sp, color = WarningAmber)
                    }
                }
            }

            // Health Records List
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                healthRecords.forEach { hr ->
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, if (hr.severity == "Sick") Color(0xFFFFCDD2) else BorderGreen),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("${hr.cowName} (#${hr.cowId})", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                                CowStatusBadge(hr.severity)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(hr.condition, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                            Text("Rx: ${hr.medication} • ${hr.vet}", fontSize = 11.sp, color = TextMuted)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Logged: ${hr.date}", fontSize = 10.sp, color = TextMuted)
                                Text("Next: ${hr.nextCheckup}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                            }
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 11. ADD HEALTH RECORD SCREEN
// -----------------------------------------------------------------------------
@Composable
fun AddHealthScreen(viewModel: HambaaViewModel) {
    var condition by remember { mutableStateOf("Preventive FMD Vaccination") }
    var symptoms by remember { mutableStateOf("Normal temperature, active appetite") }
    var diagnosis by remember { mutableStateOf("Fit for seasonal immunization") }
    var treatment by remember { mutableStateOf("Subcutaneous Injection") }
    var medication by remember { mutableStateOf("Aftovaxpur 2ml") }
    var vet by remember { mutableStateOf("Dr. Mahmudul Karim") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        HambaaTopBar(title = "Add Health Record", subtitle = "Veterinary diagnosis & Rx", onBack = { viewModel.navigateTo(10) })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Column {
                        Text("Health Condition *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = condition, onValueChange = { condition = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column {
                        Text("Symptoms", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = symptoms, onValueChange = { symptoms = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column {
                        Text("Diagnosis", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = diagnosis, onValueChange = { diagnosis = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column {
                        Text("Treatment", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = treatment, onValueChange = { treatment = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column {
                        Text("Medication", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = medication, onValueChange = { medication = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column {
                        Text("Veterinarian Name", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = vet, onValueChange = { vet = it }, modifier = Modifier.fillMaxWidth())
                    }
                }
            }

            Button(
                onClick = { viewModel.addHealthRecord("HB-101", condition, symptoms, diagnosis, treatment, medication, vet) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Save Health Record", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 12. EMPLOYEE MANAGEMENT SCREEN
// -----------------------------------------------------------------------------
@Composable
fun EmployeeListScreen(viewModel: HambaaViewModel) {
    val employees by viewModel.employees.collectAsState()

    Scaffold(
        topBar = {
            HambaaTopBar(title = "Employees", subtitle = "${employees.size} Farm Staff Members", onBack = { viewModel.navigateTo(3) })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.navigateTo(20) },
                containerColor = PrimaryForest,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Add Employee", fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            employees.forEach { emp ->
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, BorderGreen),
                    modifier = Modifier.fillMaxWidth().clickable {
                        viewModel.setSelectedEmployee(emp.id)
                        viewModel.navigateTo(13)
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(14.dp)).background(PrimaryForest),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Badge, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("#${emp.id}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                                CowStatusBadge(emp.status)
                            }
                            Text(emp.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                            Text(emp.role, fontSize = 11.sp, color = TextMuted)
                            Text(emp.phone, fontSize = 11.sp, color = PrimaryForest)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

// -----------------------------------------------------------------------------
// 13. EMPLOYEE DETAILS SCREEN
// -----------------------------------------------------------------------------
@Composable
fun EmployeeDetailsScreen(viewModel: HambaaViewModel) {
    val employees by viewModel.employees.collectAsState()
    val selectedId by viewModel.selectedEmployeeId.collectAsState()
    val emp = employees.find { it.id == selectedId } ?: employees.firstOrNull()

    if (emp == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Employee not found")
        }
        return
    }

    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete ${emp.name}?") },
            text = { Text("Are you sure you want to remove this employee record?") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    viewModel.deleteEmployee(emp.id)
                }) {
                    Text("Delete", color = ErrorRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
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
    ) {
        HambaaTopBar(title = "Employee Details", subtitle = "#${emp.id}", onBack = { viewModel.navigateTo(12) })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.size(64.dp).clip(RoundedCornerShape(16.dp)).background(PrimaryForest),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Badge, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    CowStatusBadge(emp.status)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(emp.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    Text(emp.role, fontSize = 12.sp, color = TextMuted)
                }
            }

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    DetailRow("Phone Number", emp.phone)
                    DetailRow("Joining Date", emp.joined)
                    DetailRow("Monthly Salary", emp.salary)
                    DetailRow("Assigned Shift", emp.shift)
                    DetailRow("Task Completion", emp.tasksDone)
                }
            }

            // Action Buttons: Toggle Leave / Active & Delete
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { viewModel.toggleEmployeeStatus(emp.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = if (emp.status == "Active") WarningAmber else SecondaryFresh),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f).height(48.dp)
                ) {
                    Text(if (emp.status == "Active") "Mark On Leave" else "Mark Active", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { showDeleteDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f).height(48.dp)
                ) {
                    Text("Delete", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ErrorRed)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 20. ADD EMPLOYEE SCREEN
// -----------------------------------------------------------------------------
@Composable
fun AddEmployeeScreen(viewModel: HambaaViewModel) {
    var name by remember { mutableStateOf("Nazmul Hossain") }
    var role by remember { mutableStateOf("Milking Specialist") }
    var phone by remember { mutableStateOf("+880 1711-000000") }
    var salary by remember { mutableStateOf("400") }
    var shift by remember { mutableStateOf("05:30 AM – 02:30 PM") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        HambaaTopBar(title = "Add New Employee", subtitle = "Register farm staff", onBack = { viewModel.navigateTo(12) })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Column {
                        Text("Employee Name *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = name, onValueChange = { name = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column {
                        Text("Designation / Role *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = role, onValueChange = { role = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column {
                        Text("Phone Number *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = phone, onValueChange = { phone = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column {
                        Text("Monthly Salary (৳) *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = salary, onValueChange = { salary = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column {
                        Text("Assigned Shift *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = shift, onValueChange = { shift = it }, modifier = Modifier.fillMaxWidth())
                    }
                }
            }

            Button(
                onClick = { viewModel.addEmployee(name, role, phone, salary, shift) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Save Employee", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = 12.sp, color = TextMuted)
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
    }
}

// -----------------------------------------------------------------------------
// 14. EXPENSES SCREEN
// -----------------------------------------------------------------------------
@Composable
fun ExpensesScreen(viewModel: HambaaViewModel) {
    val expenses by viewModel.expenses.collectAsState()
    val totalExpenses = expenses.sumOf { it.amount } + 450.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        HambaaTopBar(title = "Expenses", subtitle = "Monthly farm expenditure", onBack = { viewModel.navigateTo(3) })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Expenses (Nov 2026)", fontSize = 12.sp, color = TextMuted)
                        Surface(color = Color(0xFFFEF3C7), shape = RoundedCornerShape(6.dp)) {
                            Text("Within Budget", color = WarningAmber, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                    Text("৳${String.format("%,.2f", totalExpenses)}", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = TextCharcoal)
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Recent Expenses", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                expenses.forEach { ex ->
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, BorderGreen),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(ex.category, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                                Text(ex.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                                Text("${ex.date} • ${ex.desc}", fontSize = 10.sp, color = TextMuted)
                            }
                            Text("-৳${ex.amount}", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = ErrorRed)
                        }
                    }
                }
            }

            Button(
                onClick = { viewModel.navigateTo(15) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Add Expense", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 15. ADD EXPENSE SCREEN
// -----------------------------------------------------------------------------
@Composable
fun AddExpenseScreen(viewModel: HambaaViewModel) {
    var title by remember { mutableStateOf("Organic Mineral Blocks (20 pcs)") }
    var category by remember { mutableStateOf("Food") }
    var amount by remember { mutableStateOf("185") }
    var desc by remember { mutableStateOf("Purchased from AgroVet Supply House") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        HambaaTopBar(title = "Add Expense", subtitle = "Record farm operational cost", onBack = { viewModel.navigateTo(14) })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Column {
                        Text("Expense Title *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = title, onValueChange = { title = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column {
                        Text("Category", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = category, onValueChange = { category = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column {
                        Text("Amount (৳) *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = amount, onValueChange = { amount = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column {
                        Text("Description", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = desc, onValueChange = { desc = it }, modifier = Modifier.fillMaxWidth(), singleLine = false)
                    }
                }
            }

            Button(
                onClick = { viewModel.addExpense(title, category, amount.toDoubleOrNull() ?: 100.0, desc) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Save Expense", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 16. SALES SCREEN
// -----------------------------------------------------------------------------
@Composable
fun SalesScreen(viewModel: HambaaViewModel) {
    val sales by viewModel.sales.collectAsState()
    val totalSales = sales.sumOf { it.total } + 5213.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        HambaaTopBar(title = "Sales & Revenue", subtitle = "Milk & dairy product sales", onBack = { viewModel.navigateTo(3) })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Surface(
                color = PrimaryForest,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Monthly Revenue", fontSize = 12.sp, color = Color(0xFFC8E6C9))
                        Surface(color = Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(6.dp)) {
                            Text("Net +৳3,210", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                        }
                    }
                    Text("৳${String.format("%,.2f", totalSales)}", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Recent Transactions", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                sales.forEach { s ->
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, BorderGreen),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(s.category, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                                Text(s.product, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                                Text("${s.customer} • ${s.qty} @ ${s.unitPrice}", fontSize = 10.sp, color = TextMuted)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("+৳${s.total}", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryForest)
                                Text(s.date, fontSize = 10.sp, color = TextMuted)
                            }
                        }
                    }
                }
            }

            Button(
                onClick = { viewModel.navigateTo(17) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Add Sale", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 17. ADD SALE SCREEN
// -----------------------------------------------------------------------------
@Composable
fun AddSaleScreen(viewModel: HambaaViewModel) {
    var product by remember { mutableStateOf("Evening Chilled Raw Milk") }
    var category by remember { mutableStateOf("Milk Sales") }
    var qtyText by remember { mutableStateOf("200") }
    var priceText by remember { mutableStateOf("1.30") }
    var customer by remember { mutableStateOf("FreshMart Dairy Co.") }

    val qty = qtyText.toDoubleOrNull() ?: 0.0
    val price = priceText.toDoubleOrNull() ?: 0.0
    val calculatedTotal = qty * price

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        HambaaTopBar(title = "Add Sale", subtitle = "Create customer invoice entry", onBack = { viewModel.navigateTo(16) })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Column {
                        Text("Product Name *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = product, onValueChange = { product = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column {
                        Text("Category", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = category, onValueChange = { category = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Quantity", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                            Spacer(modifier = Modifier.height(4.dp))
                            HambaaOutlinedTextField(value = qtyText, onValueChange = { qtyText = it }, modifier = Modifier.fillMaxWidth())
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Unit Price (৳)", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                            Spacer(modifier = Modifier.height(4.dp))
                            HambaaOutlinedTextField(value = priceText, onValueChange = { priceText = it }, modifier = Modifier.fillMaxWidth())
                        }
                    }

                    Surface(
                        color = SoftGreenContainer,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFC8E6C9)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Total Sale Price", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                            Text("৳${String.format("%.2f", calculatedTotal)}", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryForest)
                        }
                    }

                    Column {
                        Text("Customer Name *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = customer, onValueChange = { customer = it }, modifier = Modifier.fillMaxWidth())
                    }
                }
            }

            Button(
                onClick = { viewModel.addSale(product, category, qty, price, customer) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Save Sale", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 18. REPORTS & ANALYTICS SCREEN
// -----------------------------------------------------------------------------
@Composable
fun ReportsScreen(viewModel: HambaaViewModel) {
    val reportFilter by viewModel.reportFilter.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
    ) {
        HambaaTopBar(title = "Farm Reports", subtitle = "AI analytics & insights", onBack = { viewModel.navigateTo(3) })

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Filter Pills
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("This Week", "This Month", "Last 3 Months", "Yearly").forEach { rf ->
                    FilterChip(
                        selected = reportFilter == rf,
                        onClick = { viewModel.setReportFilter(rf) },
                        label = { Text(rf, fontSize = 11.sp) }
                    )
                }
            }

            // Sales vs Expense Report
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Sales vs Expense Report", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                        CowStatusBadge("+101% ROI")
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Sales Revenue", fontSize = 12.sp, color = TextMuted)
                            Text("৳8,413.00", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total Expenses", fontSize = 12.sp, color = TextMuted)
                            Text("৳3,290.00", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = WarningAmber)
                        }
                    }
                }
            }

            // Cow Growth & Health Report Cards
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, BorderGreen),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("COW GROWTH", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SecondaryFresh)
                        Text("48 Head", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = TextCharcoal)
                        Text("+4 calves born • +12 kg avg weight", fontSize = 10.sp, color = TextMuted)
                    }
                }
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, BorderGreen),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("HEALTH REPORT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                        Text("95.8%", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = TextCharcoal)
                        Text("Herd immunity • 45/48 vaccinated", fontSize = 10.sp, color = TextMuted)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 19. PROFILE & SETTINGS SCREEN
// -----------------------------------------------------------------------------
@Composable
fun ProfileSettingsScreen(viewModel: HambaaViewModel) {
    val settings by viewModel.settings.collectAsState()
    val farmProfile by viewModel.farmProfile.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showEditProfileDialog by remember { mutableStateOf(false) }

    var editName by remember { mutableStateOf(farmProfile.owner) }
    var editFarmName by remember { mutableStateOf(farmProfile.name) }
    var editEmail by remember { mutableStateOf(farmProfile.email) }
    var editLocation by remember { mutableStateOf(farmProfile.location) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout from HAMBAA?") },
            text = { Text("You will be signed out of Green Valley Dairy Farm on this device.") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    viewModel.navigateTo(2)
                }) {
                    Text("Logout", color = ErrorRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = PrimaryForest)
                }
            }
        )
    }

    if (showEditProfileDialog) {
        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = { Text("Edit Farm Profile") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Owner Name", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    HambaaOutlinedTextField(value = editName, onValueChange = { editName = it })
                    Text("Farm Name", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    HambaaOutlinedTextField(value = editFarmName, onValueChange = { editFarmName = it })
                    Text("Email", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    HambaaOutlinedTextField(value = editEmail, onValueChange = { editEmail = it })
                    Text("Location", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    HambaaOutlinedTextField(value = editLocation, onValueChange = { editLocation = it })
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.updateFarmProfile(editFarmName, editName, editEmail, editLocation)
                    showEditProfileDialog = false
                }) {
                    Text("Save", color = PrimaryForest, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) {
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
    ) {
        HambaaTopBar(title = "Profile & Settings", subtitle = "Account & app preferences")

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Owner Profile Card
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier.size(56.dp).clip(RoundedCornerShape(16.dp)).background(PrimaryForest),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(farmProfile.owner, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                        Text(farmProfile.email, fontSize = 11.sp, color = TextMuted)
                        Text(farmProfile.name, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                    }
                    Button(
                        onClick = {
                            editName = farmProfile.owner
                            editFarmName = farmProfile.name
                            editEmail = farmProfile.email
                            editLocation = farmProfile.location
                            showEditProfileDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SoftGreenContainer)
                    ) {
                        Text("Edit", fontSize = 11.sp, color = PrimaryForest, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Theme Selector Card
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Appearance & Theme", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("Dark Theme", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                            Text("Switch between Light and Dark mode", fontSize = 10.sp, color = TextMuted)
                        }
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { viewModel.toggleTheme() },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PrimaryForest)
                        )
                    }
                }
            }

            // Notification Toggles
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text("Notification Settings", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    ToggleSettingItem(
                        title = "AI Health & Fever Alerts",
                        subtitle = "Instant push alerts for sick cattle",
                        checked = settings["healthAlerts"] ?: true,
                        onCheckedChange = { viewModel.toggleSetting("healthAlerts") }
                    )
                    ToggleSettingItem(
                        title = "Low Feed Inventory Warnings",
                        subtitle = "Notify when stock drops below 25%",
                        checked = settings["lowFeedAlerts"] ?: true,
                        onCheckedChange = { viewModel.toggleSetting("lowFeedAlerts") }
                    )
                    ToggleSettingItem(
                        title = "Daily Milk Shift Summary",
                        subtitle = "Evening summary report at 08:00 PM",
                        checked = settings["dailyMilkSummary"] ?: true,
                        onCheckedChange = { viewModel.toggleSetting("dailyMilkSummary") }
                    )
                }
            }

            // Logout Button
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
}

@Composable
fun ToggleSettingItem(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
            Text(subtitle, fontSize = 10.sp, color = TextMuted)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PrimaryForest))
    }
}
