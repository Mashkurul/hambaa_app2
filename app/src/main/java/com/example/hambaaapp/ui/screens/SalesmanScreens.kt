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
fun SalesmanAppRoot(viewModel: HambaaViewModel) {
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
                    401 -> SalesmanDashboardScreen(viewModel)
                    402 -> SalesmanProductsScreen(viewModel)
                    403 -> SalesmanProductDetailsScreen(viewModel)
                    404 -> SalesmanNewSaleScreen(viewModel)
                    405 -> SalesmanReceiptScreen(viewModel)
                    406 -> SalesmanCustomersScreen(viewModel)
                    407 -> SalesmanAddCustomerScreen(viewModel)
                    408 -> SalesmanCustomerDetailsScreen(viewModel)
                    409 -> SalesmanSalesHistoryScreen(viewModel)
                    410 -> SalesmanOrderDetailsScreen(viewModel)
                    411 -> SalesmanDailySummaryScreen(viewModel)
                    412 -> SalesmanPendingOrdersScreen(viewModel)
                    413 -> SalesmanNotificationsScreen(viewModel)
                    414 -> SalesmanProfileScreen(viewModel)
                    else -> SalesmanDashboardScreen(viewModel)
                }
            }

            // Bottom Navigation Bar for Salesman (Home, Products, Sales, Profile)
            if (activeScreen in listOf(401, 402, 409, 414)) {
                SalesmanBottomNav(activeScreen = activeScreen, onNavigate = { viewModel.navigateTo(it) })
            }
        }
    }
}

@Composable
fun SalesmanBottomNav(activeScreen: Int, onNavigate: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = activeScreen == 401,
            onClick = { onNavigate(401) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 402,
            onClick = { onNavigate(402) },
            icon = { Icon(Icons.Default.Inventory, contentDescription = "Products") },
            label = { Text("Products", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 409,
            onClick = { onNavigate(409) },
            icon = { Icon(Icons.Default.PointOfSale, contentDescription = "Sales") },
            label = { Text("Sales", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        NavigationBarItem(
            selected = activeScreen == 414,
            onClick = { onNavigate(414) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
    }
}

// -----------------------------------------------------------------------------
// 1. SALESMAN DASHBOARD (Screen 401)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanDashboardScreen(viewModel: HambaaViewModel) {
    val profile by viewModel.salesmanProfile.collectAsState()
    val products by viewModel.dairyProducts.collectAsState()
    val orders by viewModel.salesOrders.collectAsState()
    val notifs by viewModel.salesmanNotifications.collectAsState()

    val todaysSales = orders.filter { it.date == "Today" && it.status == "Completed" }.sumOf { it.totalAmount } + 3450.0
    val pendingOrdersCount = orders.count { it.status == "Pending" }

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
                Text("DAIRY SALES ROUTE • SLM-001", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryFresh)
                Text("Welcome, ${profile.name}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = TextCharcoal)
                Text(profile.assignedTerritory, fontSize = 11.sp, color = TextMuted)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = { viewModel.navigateTo(413) },
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

        // 4 KPI Cards in ৳ BDT
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DashboardKpiCard(modifier = Modifier.weight(1f), title = "Today's Sales", value = "৳${String.format("%,.0f", todaysSales)}", badge = "Sales", icon = { Icon(Icons.Default.Payments, contentDescription = null, tint = PrimaryForest) }, onClick = { viewModel.navigateTo(411) })
            DashboardKpiCard(modifier = Modifier.weight(1f), title = "Orders", value = "${orders.size}", badge = "Total", icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = SecondaryFresh) }, onClick = { viewModel.navigateTo(409) })
            DashboardKpiCard(modifier = Modifier.weight(1f), title = "Sold", value = "45", badge = "Units", icon = { Icon(Icons.Default.Inventory2, contentDescription = null, tint = WarningAmber) }, onClick = { viewModel.navigateTo(402) })
            DashboardKpiCard(modifier = Modifier.weight(1f), title = "Pending", value = "$pendingOrdersCount", badge = "Queue", icon = { Icon(Icons.Default.Schedule, contentDescription = null, tint = ErrorRed) }, onClick = { viewModel.navigateTo(412) })
        }

        // Quick Actions 4-Column Strip
        Column {
            Text("Salesman Quick Actions", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                WorkerQuickActionTile("New Sale", Icons.Default.AddShoppingCart, PrimaryForest) { viewModel.navigateTo(404) }
                WorkerQuickActionTile("Products", Icons.Default.Inventory, SecondaryFresh) { viewModel.navigateTo(402) }
                WorkerQuickActionTile("Customers", Icons.Default.People, WarningAmber) { viewModel.navigateTo(406) }
                WorkerQuickActionTile("Summary", Icons.Default.BarChart, ErrorRed) { viewModel.navigateTo(411) }
            }
        }

        // Low Stock Availability Alert Card
        Surface(
            color = Color(0xFFFFEBEE),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFFFCDD2)),
            modifier = Modifier.fillMaxWidth().clickable { viewModel.navigateTo(402) }
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
                    Text("Low Stock Alert: Pure Farm Ghee", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB71C1C))
                    Text("Only 6 jars remaining in chilled store. Check inventory.", fontSize = 11.sp, color = ErrorRed)
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = ErrorRed)
            }
        }

        // Recent Transactions Preview
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Recent Transactions", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    TextButton(onClick = { viewModel.navigateTo(409) }) {
                        Text("View All", fontSize = 12.sp, color = PrimaryForest, fontWeight = FontWeight.Bold)
                    }
                }
                orders.take(3).forEach { o ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable {
                            viewModel.setSelectedOrderId(o.id)
                            viewModel.navigateTo(410)
                        },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(o.customerName, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                            Text("${o.productName} • ${o.quantity} ${o.unit}", fontSize = 10.sp, color = TextMuted)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("৳${o.totalAmount}", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryForest)
                            CowStatusBadge(o.status)
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 2. PRODUCTS SCREEN (Screen 402)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanProductsScreen(viewModel: HambaaViewModel) {
    val products by viewModel.dairyProducts.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Dairy Product Catalog", subtitle = "${products.size} Farm-fresh dairy items in ৳ BDT", onBack = { viewModel.navigateTo(401) })

        products.forEach { p ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth().clickable {
                    viewModel.setSelectedProductId(p.id)
                    viewModel.navigateTo(403)
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
                        Icon(Icons.Default.Inventory, contentDescription = null, tint = PrimaryForest, modifier = Modifier.size(28.dp))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(p.category, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                            CowStatusBadge(p.status)
                        }
                        Text(p.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                        Text("Price: ৳${p.price} / ${p.unit} • Stock: ${p.stock}", fontSize = 11.sp, color = TextMuted)
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 3. PRODUCT DETAILS SCREEN (Screen 403)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanProductDetailsScreen(viewModel: HambaaViewModel) {
    val products by viewModel.dairyProducts.collectAsState()
    val selectedId by viewModel.selectedProductId.collectAsState()
    val product = products.find { it.id == selectedId } ?: products.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = product.name, subtitle = "#${product.id} • ${product.category}", onBack = { viewModel.navigateTo(402) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier.size(72.dp).clip(RoundedCornerShape(20.dp)).background(SoftGreenContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Inventory, contentDescription = null, tint = PrimaryForest, modifier = Modifier.size(38.dp))
                }
                CowStatusBadge(product.status)
                Text(product.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                Text("৳${product.price} per ${product.unit}", fontSize = 16.sp, color = PrimaryForest, fontWeight = FontWeight.ExtraBold)

                Divider(color = BorderGreen)

                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DetailRow("Available Stock", "${product.stock} ${product.unit}s")
                    DetailRow("Sold Today", "${product.soldToday} ${product.unit}s")
                    DetailRow("Product Description", product.desc)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.navigateTo(404) },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Create Sale with This Product", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 4. NEW SALE POS CHECKOUT SCREEN (Screen 404)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanNewSaleScreen(viewModel: HambaaViewModel) {
    val products by viewModel.dairyProducts.collectAsState()
    val customers by viewModel.customers.collectAsState()

    var selectedProductId by remember { mutableStateOf(products.firstOrNull()?.id ?: "PRD-01") }
    var selectedCustomerId by remember { mutableStateOf(customers.firstOrNull()?.id ?: "CUS-101") }
    var qtyText by remember { mutableStateOf("10") }
    var discountText by remember { mutableStateOf("0") }
    var paymentMethod by remember { mutableStateOf("Cash") }

    val product = products.find { it.id == selectedProductId } ?: products.first()
    val qty = qtyText.toIntOrNull() ?: 1
    val discount = discountText.toDoubleOrNull() ?: 0.0
    val subtotal = qty * product.price
    val grandTotal = subtotal - discount

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "New Sale POS Checkout", subtitle = "Calculate subtotal & grand total in ৳", onBack = { viewModel.navigateTo(401) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Column {
                    Text("Select Dairy Product *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        products.forEach { p ->
                            FilterChip(
                                selected = selectedProductId == p.id,
                                onClick = { selectedProductId = p.id },
                                label = { Text("${p.name} (৳${p.price})", fontSize = 11.sp) }
                            )
                        }
                    }
                }

                Column {
                    Text("Select Customer *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        customers.forEach { c ->
                            FilterChip(
                                selected = selectedCustomerId == c.id,
                                onClick = { selectedCustomerId = c.id },
                                label = { Text(c.name, fontSize = 11.sp) }
                            )
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Quantity (${product.unit}) *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = qtyText, onValueChange = { qtyText = it }, modifier = Modifier.fillMaxWidth())
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Discount (৳)", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                        Spacer(modifier = Modifier.height(4.dp))
                        HambaaOutlinedTextField(value = discountText, onValueChange = { discountText = it }, modifier = Modifier.fillMaxWidth())
                    }
                }

                Column {
                    Text("Payment Method *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Cash", "bKash", "Nagad").forEach { m ->
                            Button(
                                onClick = { paymentMethod = m },
                                colors = ButtonDefaults.buttonColors(containerColor = if (paymentMethod == m) PrimaryForest else Color.LightGray),
                                modifier = Modifier.weight(1f)
                            ) { Text(m, fontSize = 11.sp) }
                        }
                    }
                }

                // Calculation Card
                Surface(
                    color = SoftGreenContainer,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFC8E6C9)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Subtotal:", fontSize = 12.sp, color = TextMuted)
                            Text("৳$subtotal", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Discount:", fontSize = 12.sp, color = TextMuted)
                            Text("-৳$discount", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ErrorRed)
                        }
                        Divider(color = Color(0xFFC8E6C9))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Grand Total:", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                            Text("৳$grandTotal", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryForest)
                        }
                    }
                }

                Button(
                    onClick = {
                        val success = viewModel.createNewSale(selectedProductId, selectedCustomerId, qty, discount, paymentMethod)
                        if (success) {
                            viewModel.navigateTo(405)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Confirm Transaction & Print Receipt", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 5. SALE RECEIPT SCREEN (Screen 405)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanReceiptScreen(viewModel: HambaaViewModel) {
    val orders by viewModel.salesOrders.collectAsState()
    val latestOrder = orders.firstOrNull() ?: SaleOrder("ORD-000", "", "Customer", "", "Milk", 1, "Liter", 90.0, 0.0, 90.0, "Cash", "Completed", "Today", "Nov 26, 2026", "Just now")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(modifier = Modifier.size(52.dp).clip(CircleShape).background(SoftGreenContainer), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Receipt, contentDescription = null, tint = PrimaryForest, modifier = Modifier.size(28.dp))
                }
                Text("Sale Recorded Successfully!", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                Text("Receipt ID: ${latestOrder.id}", fontSize = 11.sp, color = TextMuted)

                Divider(color = BorderGreen)

                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    DetailRow("Customer Name", latestOrder.customerName)
                    DetailRow("Phone", latestOrder.customerPhone)
                    DetailRow("Product", "${latestOrder.productName} (${latestOrder.quantity} ${latestOrder.unit}s)")
                    DetailRow("Unit Price", "৳${latestOrder.unitPrice}")
                    DetailRow("Discount", "৳${latestOrder.discount}")
                    DetailRow("Payment Method", latestOrder.paymentMethod)
                    DetailRow("Grand Total", "৳${latestOrder.totalAmount}")
                    DetailRow("Salesman", "Rahim Ahmed (SLM-001)")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { viewModel.navigateTo(401) },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Back to Dashboard", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// -----------------------------------------------------------------------------
// 6. CUSTOMERS SCREEN (Screen 406)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanCustomersScreen(viewModel: HambaaViewModel) {
    val customers by viewModel.customers.collectAsState()

    Scaffold(
        topBar = {
            HambaaTopBar(title = "Customer Directory", subtitle = "${customers.size} Registered dairy buyers", onBack = { viewModel.navigateTo(401) })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.navigateTo(407) },
                containerColor = PrimaryForest,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.PersonAdd, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Add Customer", fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
            customers.forEach { c ->
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, BorderGreen),
                    modifier = Modifier.fillMaxWidth().clickable {
                        viewModel.setSelectedCustomerId(c.id)
                        viewModel.navigateTo(408)
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
                                Text("#${c.id}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                                CowStatusBadge(c.customerType)
                            }
                            Text(c.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                            Text("Phone: ${c.phone} • Orders: ${c.totalOrders}", fontSize = 11.sp, color = TextMuted)
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
// 7. ADD CUSTOMER SCREEN (Screen 407)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanAddCustomerScreen(viewModel: HambaaViewModel) {
    var name by remember { mutableStateOf("Shwapno Retail Outlet") }
    var phone by remember { mutableStateOf("+880 1811-992200") }
    var address by remember { mutableStateOf("Uttara Sector 13, Dhaka") }
    var customerType by remember { mutableStateOf("Shop") }
    var notes by remember { mutableStateOf("Weekly milk and ghee supplier contract.") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Add New Customer", subtitle = "Register buyer to sales route", onBack = { viewModel.navigateTo(406) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Column {
                    Text("Customer Name *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = name, onValueChange = { name = it }, modifier = Modifier.fillMaxWidth())
                }
                Column {
                    Text("Phone Number *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = phone, onValueChange = { phone = it }, modifier = Modifier.fillMaxWidth())
                }
                Column {
                    Text("Delivery Address", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = address, onValueChange = { address = it }, modifier = Modifier.fillMaxWidth())
                }
                Column {
                    Text("Customer Type *", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Regular", "Shop", "Business").forEach { t ->
                            Button(
                                onClick = { customerType = t },
                                colors = ButtonDefaults.buttonColors(containerColor = if (customerType == t) PrimaryForest else Color.LightGray),
                                modifier = Modifier.weight(1f)
                            ) { Text(t, fontSize = 11.sp) }
                        }
                    }
                }
                Column {
                    Text("Notes", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    HambaaOutlinedTextField(value = notes, onValueChange = { notes = it }, modifier = Modifier.fillMaxWidth(), singleLine = false)
                }

                Button(
                    onClick = {
                        viewModel.addCustomer(name, phone, address, customerType, notes)
                        viewModel.navigateTo(406)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Save Customer", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 8. CUSTOMER DETAILS SCREEN (Screen 408)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanCustomerDetailsScreen(viewModel: HambaaViewModel) {
    val customers by viewModel.customers.collectAsState()
    val selectedId by viewModel.selectedCustomerId.collectAsState()
    val customer = customers.find { it.id == selectedId } ?: customers.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Customer Details", subtitle = "#${customer.id} • ${customer.customerType}", onBack = { viewModel.navigateTo(406) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CowStatusBadge(customer.customerType)
                Text(customer.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                Text(customer.phone, fontSize = 13.sp, color = PrimaryForest, fontWeight = FontWeight.SemiBold)

                Divider(color = BorderGreen)

                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DetailRow("Delivery Address", customer.address)
                    DetailRow("Total Orders", "${customer.totalOrders} Orders")
                    DetailRow("Total Purchased", "৳${customer.totalPurchased}")
                    DetailRow("Buyer Notes", customer.notes)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.navigateTo(404) },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Create Sale for This Customer", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 9. SALES HISTORY SCREEN (Screen 409)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanSalesHistoryScreen(viewModel: HambaaViewModel) {
    val orders by viewModel.salesOrders.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Sales Transactions", subtitle = "${orders.size} Personal route orders", onBack = { viewModel.navigateTo(401) })

        orders.forEach { o ->
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderGreen),
                modifier = Modifier.fillMaxWidth().clickable {
                    viewModel.setSelectedOrderId(o.id)
                    viewModel.navigateTo(410)
                }
            ) {
                Row(modifier = Modifier.padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("${o.customerName} • ${o.paymentMethod}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryForest)
                        Text("${o.productName} (${o.quantity} ${o.unit}s)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                        Text("${o.date} at ${o.time}", fontSize = 10.sp, color = TextMuted)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("৳${o.totalAmount}", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryForest)
                        CowStatusBadge(o.status)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 10. ORDER DETAILS SCREEN (Screen 410)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanOrderDetailsScreen(viewModel: HambaaViewModel) {
    val orders by viewModel.salesOrders.collectAsState()
    val selectedId by viewModel.selectedOrderId.collectAsState()
    val order = orders.find { it.id == selectedId } ?: orders.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Order Details", subtitle = "#${order.id} • ${order.date}", onBack = { viewModel.navigateTo(409) })

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CowStatusBadge(order.status)
                Text(order.customerName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                Text(order.customerPhone, fontSize = 13.sp, color = PrimaryForest, fontWeight = FontWeight.SemiBold)

                Divider(color = BorderGreen)

                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DetailRow("Product", "${order.productName} (${order.quantity} ${order.unit}s)")
                    DetailRow("Unit Price", "৳${order.unitPrice}")
                    DetailRow("Discount", "৳${order.discount}")
                    DetailRow("Grand Total", "৳${order.totalAmount}")
                    DetailRow("Payment Method", order.paymentMethod)
                    DetailRow("Timestamp", "${order.dateFull} at ${order.time}")
                }

                if (order.status == "Pending") {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            viewModel.markOrderCompleted(order.id)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("Mark Order as Completed", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 11. DAILY SALES SUMMARY SCREEN (Screen 411)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanDailySummaryScreen(viewModel: HambaaViewModel) {
    val orders by viewModel.salesOrders.collectAsState()
    val todayCompleted = orders.filter { it.date == "Today" && it.status == "Completed" }
    val totalToday = todayCompleted.sumOf { it.totalAmount } + 3450.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HambaaTopBar(title = "Daily Sales Summary", subtitle = "Personal route performance in ৳", onBack = { viewModel.navigateTo(401) })

        Surface(
            color = PrimaryForest,
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Today's Total Revenue", fontSize = 12.sp, color = Color(0xFFC8E6C9))
                Text("৳${String.format("%,.0f", totalToday)}", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text("Across ${todayCompleted.size + 4} completed transactions", fontSize = 11.sp, color = Color(0xFFE8F5E9))
            }
        }

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Performance Breakdown", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                DetailRow("Cash Collected", "৳4,250")
                DetailRow("bKash & Nagad Digital", "৳4,250")
                DetailRow("Most Sold Product", "Fresh Cow Milk (35 Liters)")
                DetailRow("Average Order Value", "৳708")
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 12. PENDING ORDERS SCREEN (Screen 412)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanPendingOrdersScreen(viewModel: HambaaViewModel) {
    val orders by viewModel.salesOrders.collectAsState()
    val pending = orders.filter { it.status == "Pending" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Pending Orders", subtitle = "${pending.size} Orders awaiting delivery or payment", onBack = { viewModel.navigateTo(401) })

        if (pending.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No pending orders!", fontSize = 14.sp, color = TextMuted)
            }
        } else {
            pending.forEach { o ->
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, BorderGreen),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(o.customerName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                            CowStatusBadge(o.status)
                        }
                        Text("Item: ${o.productName} (${o.quantity} ${o.unit}s) • ৳${o.totalAmount}", fontSize = 11.sp, color = TextMuted)
                        Button(
                            onClick = { viewModel.markOrderCompleted(o.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().height(38.dp)
                        ) {
                            Text("Mark as Completed", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// 13. NOTIFICATIONS SCREEN (Screen 413)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanNotificationsScreen(viewModel: HambaaViewModel) {
    val notifs by viewModel.salesmanNotifications.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BgCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HambaaTopBar(title = "Notifications & Alerts", subtitle = "Stock alerts & customer orders", onBack = { viewModel.navigateTo(401) })

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
// 14. SALESMAN PROFILE SCREEN (Screen 414)
// -----------------------------------------------------------------------------
@Composable
fun SalesmanProfileScreen(viewModel: HambaaViewModel) {
    val profile by viewModel.salesmanProfile.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout from HAMBAA?") },
            text = { Text("Your personal sales transactions and customer records are safely synced.") },
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
        HambaaTopBar(title = "Salesman Profile", subtitle = "Route & sales credentials")

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
                CowStatusBadge("SALESMAN")
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
                DetailRow("Assigned Territory", profile.assignedTerritory)
                DetailRow("Email / Username", profile.email)
                DetailRow("Phone Number", profile.phone)
                DetailRow("Farm Name", profile.farmName)
            }
        }

        Button(
            onClick = { viewModel.navigateTo(411) },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryForest),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("View Daily Sales Summary", fontSize = 13.sp, fontWeight = FontWeight.Bold)
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
