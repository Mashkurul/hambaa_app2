package com.example.hambaaapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hambaaapp.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HambaaViewModel : ViewModel() {

    private val _activeScreen = MutableStateFlow(1) // Start at Splash Screen (Screen 1) -> Login -> Dashboard
    val activeScreen: StateFlow<Int> = _activeScreen.asStateFlow()

    private val _userRole = MutableStateFlow(UserRole.OWNER)
    val userRole: StateFlow<UserRole> = _userRole.asStateFlow()

    // Salesman State Flows
    private val _salesmanProfile = MutableStateFlow(SalesmanProfile())
    val salesmanProfile: StateFlow<SalesmanProfile> = _salesmanProfile.asStateFlow()

    private val _dairyProducts = MutableStateFlow(
        listOf(
            DairyProduct("PRD-01", "Fresh Cow Milk", "Milk", "Liter", 90.0, 145, "In Stock", "milk", 28, "Raw, unadulterated morning farm milk chilled to 4°C."),
            DairyProduct("PRD-02", "Pasteurized Milk (1L Pouch)", "Milk", "Pack", 105.0, 80, "In Stock", "bottle", 12, "Hygienically pasteurized and homogenized whole farm milk."),
            DairyProduct("PRD-03", "Pure Farm Ghee (500g Jar)", "Ghee & Butter", "Jar", 750.0, 6, "Low Stock", "ghee", 3, "Traditional slow-simmered pure cow ghee."),
            DairyProduct("PRD-04", "Sweet Bogura-Style Curd (Doi)", "Yogurt & Curd", "Cup (500g)", 160.0, 22, "In Stock", "curd", 2, "Creamy, thick farm-cultured sweet yogurt."),
            DairyProduct("PRD-05", "Fresh Cottage Paneer (250g)", "Cheese & Paneer", "Pack", 220.0, 4, "Low Stock", "paneer", 0, "Soft, high-protein fresh farm paneer."),
            DairyProduct("PRD-06", "Cultured Farm Butter (200g)", "Ghee & Butter", "Pack", 280.0, 0, "Out of Stock", "butter", 0, "Rich unsalted table butter.")
        )
    )
    val dairyProducts: StateFlow<List<DairyProduct>> = _dairyProducts.asStateFlow()

    private val _customers = MutableStateFlow(
        listOf(
            CustomerRecord("CUS-101", "FreshMart Super Shop", "+880 1819-440219", "House 14, Road 7, Uttara Sector 4, Dhaka", "Shop", 28, 64500.0, "Daily morning delivery of 20L Fresh Milk."),
            CustomerRecord("CUS-102", "Nusrat Jahan", "+880 1711-883402", "Flat 4B, Green View Apt, Mirpur DOHS", "Regular", 19, 14250.0, "Prefers 2L Fresh Cow Milk every alternate day."),
            CustomerRecord("CUS-103", "Bengal Sweets & Bakery", "+880 1912-309844", "Savar Bazar Main Road, Dhaka", "Business", 34, 112800.0, "Bulk buyer of Fresh Cow Milk and Paneer."),
            CustomerRecord("CUS-104", "Tanvir Ahmed", "+880 1675-221904", "Block C, Bashundhara R/A, Dhaka", "Regular", 9, 7840.0, "Pays via bKash mobile banking upon delivery.")
        )
    )
    val customers: StateFlow<List<CustomerRecord>> = _customers.asStateFlow()

    private val _salesOrders = MutableStateFlow(
        listOf(
            SaleOrder("ORD-512", "CUS-101", "FreshMart Super Shop", "+880 1819-440219", "Fresh Cow Milk", 20, "Liter", 90.0, 0.0, 1800.0, "bKash", "Completed", "Today", "Nov 26, 2026", "08:20 AM"),
            SaleOrder("ORD-511", "CUS-103", "Bengal Sweets & Bakery", "+880 1912-309844", "Pure Farm Ghee (500g Jar)", 3, "Jar", 750.0, 50.0, 2200.0, "Cash", "Completed", "Today", "Nov 26, 2026", "09:05 AM"),
            SaleOrder("ORD-510", "CUS-102", "Nusrat Jahan", "+880 1711-883402", "Pasteurized Milk (1L Pouch)", 10, "Pack", 105.0, 0.0, 1050.0, "bKash", "Pending", "Today", "Nov 26, 2026", "09:30 AM"),
            SaleOrder("ORD-509", "CUS-104", "Tanvir Ahmed", "+880 1675-221904", "Sweet Bogura-Style Curd (Doi)", 5, "Cup", 160.0, 0.0, 800.0, "Cash", "Pending", "Today", "Nov 26, 2026", "09:38 AM")
        )
    )
    val salesOrders: StateFlow<List<SaleOrder>> = _salesOrders.asStateFlow()

    private val _salesmanNotifications = MutableStateFlow(
        listOf(
            SalesmanNotification("NTF-01", "Stock Availability", "Low Stock Alert: Pure Farm Ghee", "Only 6 jars remaining in chilled store.", "10 mins ago", true, 401),
            SalesmanNotification("NTF-02", "Customer Order", "New Customer Order #ORD-508", "FreshMart requested 7L Fresh Cow Milk.", "22 mins ago", true, 401)
        )
    )
    val salesmanNotifications: StateFlow<List<SalesmanNotification>> = _salesmanNotifications.asStateFlow()

    private val _selectedProductId = MutableStateFlow("PRD-01")
    val selectedProductId: StateFlow<String> = _selectedProductId.asStateFlow()

    private val _selectedCustomerId = MutableStateFlow("CUS-101")
    val selectedCustomerId: StateFlow<String> = _selectedCustomerId.asStateFlow()

    private val _selectedOrderId = MutableStateFlow("ORD-512")
    val selectedOrderId: StateFlow<String> = _selectedOrderId.asStateFlow()

    // Watchman State Flows
    private val _watchmanProfile = MutableStateFlow(WatchmanProfile())
    val watchmanProfile: StateFlow<WatchmanProfile> = _watchmanProfile.asStateFlow()

    private val _visitors = MutableStateFlow(
        listOf(
            VisitorRecord("VIS-101", "Dr. Mahmudul Karim", "+880 1817-229410", "Savar Veterinary Complex, Dhaka", "Scheduled Herd Vaccination & Checkup", "Farm Owner (Tariqul Hasan)", "Business", "Dhaka Metro-Ga 21-4890", "Nov 26, 2026", "08:45 AM", "--", "Inside", "Carrying cold-box medical kit.")
        )
    )
    val visitors: StateFlow<List<VisitorRecord>> = _visitors.asStateFlow()

    private val _vehicles = MutableStateFlow(
        listOf(
            VehicleRecord("VEH-01", "Dhaka Metro-U 14-8821", "Chilled Milk Tanker", "Mizanur Rahman", "+880 1713-402918", "Morning Bulk Milk Collection", "07:30 AM", "--", "Inside")
        )
    )
    val vehicles: StateFlow<List<VehicleRecord>> = _vehicles.asStateFlow()

    private val _deliveries = MutableStateFlow(
        listOf(
            DeliveryRecord("DEL-201", "FreshMart Dairy Logistics", "Milk Tanker Pickup", "Dhaka Metro-U 14-8821", "07:30 AM", "Morning Chilled Milk Dispatch", "At Gate / Loading")
        )
    )
    val deliveries: StateFlow<List<DeliveryRecord>> = _deliveries.asStateFlow()

    private val _entryExitLogs = MutableStateFlow(
        listOf(
            EntryExitLogItem("LOG-01", "Sazzadul Islam (VIS-103)", "Visitors", "Entry", "Nov 26, 2026", "09:25 AM", "Inside")
        )
    )
    val entryExitLogs: StateFlow<List<EntryExitLogItem>> = _entryExitLogs.asStateFlow()

    private val _securityIncidents = MutableStateFlow<List<SecurityIncident>>(emptyList())
    val securityIncidents: StateFlow<List<SecurityIncident>> = _securityIncidents.asStateFlow()

    private val _securityInstructions = MutableStateFlow(
        listOf(
            SecurityInstructionItem("INS-1", "Verify every visitor before allowing entry", "Check valid photo ID, confirm visit purpose, and call the host person.")
        )
    )
    val securityInstructions: StateFlow<List<SecurityInstructionItem>> = _securityInstructions.asStateFlow()

    private val _watchmanNotifications = MutableStateFlow(
        listOf(
            WatchmanNotification("NTF-01", "Visitor Alert", "Visitor Alert", "Doctor checked in.", "15 mins ago", true, 301)
        )
    )
    val watchmanNotifications: StateFlow<List<WatchmanNotification>> = _watchmanNotifications.asStateFlow()

    private val _attendanceHistory = MutableStateFlow(
        listOf(
            AttendanceHistoryItem("Thu, Nov 26, 2026", "05:54 AM", "Active Shift", "3h 47m (Ongoing)", "On Duty")
        )
    )
    val attendanceHistory: StateFlow<List<AttendanceHistoryItem>> = _attendanceHistory.asStateFlow()

    private val _selectedVisitorId = MutableStateFlow("VIS-101")
    val selectedVisitorId: StateFlow<String> = _selectedVisitorId.asStateFlow()

    // Cleaner State Flows
    private val _cleanerProfile = MutableStateFlow(CleanerProfile())
    val cleanerProfile: StateFlow<CleanerProfile> = _cleanerProfile.asStateFlow()

    private val _cleaningTasks = MutableStateFlow(
        listOf(
            CleaningTask("CLN-T01", "Clean Cow Shed A", "AREA-01", "Cow Shed A", "Morning", "06:00 AM", "07:30 AM", "High", "Completed", "Remove manure.", listOf("Scrape waste"), "All bays scrubbed", true)
        )
    )
    val cleaningTasks: StateFlow<List<CleaningTask>> = _cleaningTasks.asStateFlow()

    private val _cleaningAreas = MutableStateFlow(
        listOf(
            CleaningArea("AREA-01", "Cow Shed A", "Cleaned", "Today, 07:15 AM", "Nov 26, 2026 • 07:15 AM", "ZONE-A1", "shed", "Scrape alleys twice daily.", listOf("Keep walkway dry."))
        )
    )
    val cleaningAreas: StateFlow<List<CleaningArea>> = _cleaningAreas.asStateFlow()

    private val _cleanerWorkHistory = MutableStateFlow(
        listOf(
            CleanerWorkHistoryItem("HIS-101", "Remove Waste", "Waste Disposal Area", "Today", "Nov 26, 2026", "03:45 PM", "Completed", "Transferred 4 carts.")
        )
    )
    val cleanerWorkHistory: StateFlow<List<CleanerWorkHistoryItem>> = _cleanerWorkHistory.asStateFlow()

    private val _cleanerNotifications = MutableStateFlow(
        listOf(
            CleanerNotification("NTF-01", "Owner Instruction", "Owner Instruction", "Extra disinfectant needed.", "14 mins ago", true)
        )
    )
    val cleanerNotifications: StateFlow<List<CleanerNotification>> = _cleanerNotifications.asStateFlow()

    private val _cleaningReports = MutableStateFlow<List<CleaningReportSubmission>>(emptyList())
    val cleaningReports: StateFlow<List<CleaningReportSubmission>> = _cleaningReports.asStateFlow()

    private val _selectedCleanerTaskId = MutableStateFlow("CLN-T04")
    val selectedCleanerTaskId: StateFlow<String> = _selectedCleanerTaskId.asStateFlow()

    private val _selectedCleanerAreaId = MutableStateFlow("AREA-01")
    val selectedCleanerAreaId: StateFlow<String> = _selectedCleanerAreaId.asStateFlow()

    // Worker State Flows
    private val _workerProfile = MutableStateFlow(WorkerProfile())
    val workerProfile: StateFlow<WorkerProfile> = _workerProfile.asStateFlow()

    private val _workerCows = MutableStateFlow(
        listOf(
            WorkerCow("C-101", "Rani", "Holstein Friesian", "3 Yrs", "545 kg", "Healthy", "Bay 1", 24.5, 13.5, 11.0, "Napier", "Top milker", "#1B5E20", emptyList())
        )
    )
    val workerCows: StateFlow<List<WorkerCow>> = _workerCows.asStateFlow()

    private val _workerTasks = MutableStateFlow(
        listOf(WorkerTask("TSK-01", "Morning Feeding", "6:00 AM", "Shed A", "Completed", "Feed cows", emptyList(), "Note"))
    )
    val workerTasks: StateFlow<List<WorkerTask>> = _workerTasks.asStateFlow()

    private val _feedingPeriods = MutableStateFlow(
        listOf(FeedingSchedulePeriod("Morning", "06:00 AM", listOf(FeedingScheduleItem("FS-1", "Group A", "Napier", "185 kg", "06:00 AM", "Completed"))))
    )
    val feedingPeriods: StateFlow<List<FeedingSchedulePeriod>> = _feedingPeriods.asStateFlow()

    private val _workHistory = MutableStateFlow(
        listOf(WorkHistoryItem("WH-01", "Feeding", "Napier Served", "Group A", "Today", "01:35 PM", "Completed"))
    )
    val workHistory: StateFlow<List<WorkHistoryItem>> = _workHistory.asStateFlow()

    private val _workerNotifications = MutableStateFlow(
        listOf(WorkerNotification("NTF-1", "Owner Message", "Message", "Great job!", "12 mins ago", true))
    )
    val workerNotifications: StateFlow<List<WorkerNotification>> = _workerNotifications.asStateFlow()

    private val _healthReports = MutableStateFlow<List<HealthRecord>>(emptyList())
    val healthReports: StateFlow<List<HealthRecord>> = _healthReports.asStateFlow()

    private val _workerHealthSubmissions = MutableStateFlow<List<HealthReportSubmission>>(emptyList())
    val workerHealthSubmissions: StateFlow<List<HealthReportSubmission>> = _workerHealthSubmissions.asStateFlow()

    private val _selectedWorkerCowId = MutableStateFlow("C-101")
    val selectedWorkerCowId: StateFlow<String> = _selectedWorkerCowId.asStateFlow()

    private val _selectedWorkerTaskId = MutableStateFlow("TSK-04")
    val selectedWorkerTaskId: StateFlow<String> = _selectedWorkerTaskId.asStateFlow()

    // Owner State Flows
    private val _selectedCowId = MutableStateFlow("HB-101")
    val selectedCowId: StateFlow<String> = _selectedCowId.asStateFlow()

    private val _selectedEmployeeId = MutableStateFlow("EMP-01")
    val selectedEmployeeId: StateFlow<String> = _selectedEmployeeId.asStateFlow()

    private val _emptyState = MutableStateFlow(false)
    val emptyState: StateFlow<Boolean> = _emptyState.asStateFlow()

    private val _showPassword = MutableStateFlow(false)
    val showPassword: StateFlow<Boolean> = _showPassword.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _reportFilter = MutableStateFlow("This Month")
    val reportFilter: StateFlow<String> = _reportFilter.asStateFlow()

    private val _cowFilter = MutableStateFlow("All")
    val cowFilter: StateFlow<String> = _cowFilter.asStateFlow()

    private val _cowSearch = MutableStateFlow("")
    val cowSearch: StateFlow<String> = _cowSearch.asStateFlow()

    private val _settings = MutableStateFlow(
        mapOf("healthAlerts" to true, "lowFeedAlerts" to true, "dailyMilkSummary" to true, "highContrast" to false)
    )
    val settings: StateFlow<Map<String, Boolean>> = _settings.asStateFlow()

    private val _farmProfile = MutableStateFlow(FarmProfile())
    val farmProfile: StateFlow<FarmProfile> = _farmProfile.asStateFlow()

    private val _cows = MutableStateFlow(
        listOf(Cow("HB-101", "Rani", "Holstein Friesian", "3 Yrs", "Female", "540 kg", "14 Jul 2023", "Healthy", 24.5, 13.5, 11.0, "#1B5E20", "spot", "Up to date", "18 Jan 2027", emptyList()))
    )
    val cows: StateFlow<List<Cow>> = _cows.asStateFlow()

    private val _milkLogs = MutableStateFlow(
        listOf(MilkLog("ML-901", "HB-101", "Rani", "26 Nov 2026", "Morning", 13.5, "Clean parlor"))
    )
    val milkLogs: StateFlow<List<MilkLog>> = _milkLogs.asStateFlow()

    private val _feedInventory = MutableStateFlow(
        listOf(FeedItem("Green Napier Grass", "Green Fodder", "2,400 kg", 85, "Optimal", "340 kg/day"))
    )
    val feedInventory: StateFlow<List<FeedItem>> = _feedInventory.asStateFlow()

    private val _feedingSchedule = MutableStateFlow(
        listOf(FeedingSchedule("06:00 AM", "Morning Ration", "Silage", true))
    )
    val feedingSchedule: StateFlow<List<FeedingSchedule>> = _feedingSchedule.asStateFlow()

    private val _healthRecords = MutableStateFlow(
        listOf(
            HealthRecord("HR-301", "HB-102", "Gauri", "Mild Fever", "Sick", "Temp", "Mastitis", "Infusion", "Ceftiofur", "Dr. Karim", "26 Nov 2026", "28 Nov 2026")
        )
    )
    val healthRecords: StateFlow<List<HealthRecord>> = _healthRecords.asStateFlow()

    private val _employees = MutableStateFlow(
        listOf(Employee("EMP-01", "Abdul Kader", "Herdsman", "+880 1712", "Active", "12 Mar 2022", "৳480 / mo", "05:00 AM", "98%"))
    )
    val employees: StateFlow<List<Employee>> = _employees.asStateFlow()

    private val _expenses = MutableStateFlow(
        listOf(Expense("EX-401", "Feed Bulk", "Food", 1640.0, "24 Nov 2026", "Ration"))
    )
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private val _sales = MutableStateFlow(
        listOf(Sale("SL-701", "Raw Milk", "Milk Sales", "380 L", "৳1.30", 494.0, "26 Nov 2026", "FreshMart"))
    )
    val sales: StateFlow<List<Sale>> = _sales.asStateFlow()

    // Navigation & Mutator Actions
    fun navigateTo(screenId: Int) {
        _activeScreen.value = screenId
    }

    fun login(emailOrUsername: String, pass: String): Boolean {
        val clean = emailOrUsername.trim().lowercase()
        if (clean.contains("sales") || clean == "sales@hamba.com" || clean == "salesman" || pass == "sales123") {
            _userRole.value = UserRole.SALESMAN
            _activeScreen.value = 401 // Salesman Dashboard
            return true
        } else if (clean.contains("watchman") || clean == "watchman@hamba.com" || pass == "watchman123") {
            _userRole.value = UserRole.WATCHMAN
            _activeScreen.value = 301 // Watchman Dashboard
            return true
        } else if (clean.contains("cleaner") || clean == "cleaner@hamba.com" || pass == "cleaner123") {
            _userRole.value = UserRole.CLEANER
            _activeScreen.value = 201 // Cleaner Dashboard
            return true
        } else if (clean.contains("worker") || clean == "worker@hamba.com" || pass == "worker123") {
            _userRole.value = UserRole.WORKER
            _activeScreen.value = 101 // Worker Dashboard
            return true
        } else if (clean.contains("owner") || clean == "owner@hambaafarm.com" || pass == "FarmOwner2026") {
            _userRole.value = UserRole.OWNER
            _activeScreen.value = 3 // Owner Dashboard
            return true
        }
        if (pass == "sales123") {
            _userRole.value = UserRole.SALESMAN
            _activeScreen.value = 401
            return true
        }
        if (pass == "watchman123") {
            _userRole.value = UserRole.WATCHMAN
            _activeScreen.value = 301
            return true
        }
        if (pass == "cleaner123") {
            _userRole.value = UserRole.CLEANER
            _activeScreen.value = 201
            return true
        }
        if (pass == "worker123") {
            _userRole.value = UserRole.WORKER
            _activeScreen.value = 101
            return true
        }
        _userRole.value = UserRole.OWNER
        _activeScreen.value = 3
        return true
    }

    fun logout() {
        _userRole.value = UserRole.OWNER
        _activeScreen.value = 2 // Login Screen
    }

    // Salesman Actions
    fun setSelectedProductId(productId: String) {
        _selectedProductId.value = productId
    }

    fun setSelectedCustomerId(customerId: String) {
        _selectedCustomerId.value = customerId
    }

    fun setSelectedOrderId(orderId: String) {
        _selectedOrderId.value = orderId
    }

    fun createNewSale(productId: String, customerId: String, qty: Int, discount: Double, paymentMethod: String): Boolean {
        val product = _dairyProducts.value.find { it.id == productId } ?: return false
        if (qty <= 0 || qty > product.stock) return false
        val customer = _customers.value.find { it.id == customerId } ?: _customers.value.first()

        _dairyProducts.update { list ->
            list.map { p ->
                if (p.id == productId) {
                    val newStock = p.stock - qty
                    p.copy(
                        stock = newStock,
                        soldToday = p.soldToday + qty,
                        status = if (newStock <= 0) "Out of Stock" else if (newStock <= 5) "Low Stock" else "In Stock"
                    )
                } else p
            }
        }

        val subtotal = qty * product.price
        val grandTotal = subtotal - discount

        val order = SaleOrder(
            id = "ORD-" + (513..999).random(),
            customerId = customer.id,
            customerName = customer.name,
            customerPhone = customer.phone,
            productName = product.name,
            quantity = qty,
            unit = product.unit,
            unitPrice = product.price,
            discount = discount,
            totalAmount = grandTotal,
            paymentMethod = paymentMethod,
            status = "Completed",
            date = "Today",
            dateFull = "Nov 26, 2026",
            time = "Just now"
        )
        _salesOrders.update { listOf(order) + it }
        return true
    }

    fun addCustomer(name: String, phone: String, address: String, customerType: String, notes: String) {
        val cust = CustomerRecord(
            id = "CUS-" + (105..999).random(),
            name = name.ifBlank { "New Customer" },
            phone = phone.ifBlank { "+880 1700-000000" },
            address = address,
            customerType = customerType,
            totalOrders = 1,
            totalPurchased = 500.0,
            notes = notes.ifBlank { "Registered via Salesman POS" }
        )
        _customers.update { listOf(cust) + it }
    }

    fun markOrderCompleted(orderId: String) {
        _salesOrders.update { list ->
            list.map { o ->
                if (o.id == orderId) o.copy(status = "Completed") else o
            }
        }
    }

    // Watchman Actions
    fun setSelectedVisitorId(visitorId: String) {
        _selectedVisitorId.value = visitorId
    }

    fun checkInAttendance() {
        _watchmanProfile.update { it.copy(attendanceStatus = "On Duty", checkInTime = "05:54 AM") }
    }

    fun checkOutAttendance() {
        _watchmanProfile.update { it.copy(attendanceStatus = "Completed Shift", checkOutTime = "06:00 PM") }
    }

    fun registerVisitor(name: String, phone: String, address: String, purpose: String, personToMeet: String, visitorType: String, vehicleInfo: String) {
        val v = VisitorRecord(
            id = "VIS-" + (106..999).random(),
            name = name,
            phone = phone,
            address = address,
            purpose = purpose,
            personToMeet = personToMeet,
            visitorType = visitorType,
            vehicleInfo = vehicleInfo.ifBlank { "None" },
            entryDate = "Nov 26, 2026",
            entryTime = "Just now",
            exitTime = "--",
            status = "Inside",
            notes = "Registered at Main Gate."
        )
        _visitors.update { listOf(v) + it }
        val log = EntryExitLogItem(
            id = "LOG-" + (10..99).random(),
            ident = "$name (${v.id})",
            category = "Visitors",
            action = "Entry",
            date = "Nov 26, 2026",
            time = "Just now",
            status = "Inside"
        )
        _entryExitLogs.update { listOf(log) + it }
    }

    fun recordVisitorExit(visitorId: String) {
        _visitors.update { list ->
            list.map { v ->
                if (v.id == visitorId) v.copy(status = "Exited", exitTime = "Just now") else v
            }
        }
        val v = _visitors.value.find { it.id == visitorId }
        if (v != null) {
            val log = EntryExitLogItem(
                id = "LOG-" + (10..99).random(),
                ident = "${v.name} (${v.id})",
                category = "Visitors",
                action = "Exit",
                date = "Nov 26, 2026",
                time = "Just now",
                status = "Exited"
            )
            _entryExitLogs.update { listOf(log) + it }
        }
    }

    fun submitSecurityIncident(category: String, location: String, description: String, priority: String) {
        val inc = SecurityIncident(
            id = "INC-" + (100..999).random(),
            category = category,
            location = location,
            description = description,
            priority = priority,
            date = "Today, Nov 26, 2026",
            status = "Submitted"
        )
        _securityIncidents.update { listOf(inc) + it }
    }

    // Cleaner Actions
    fun setSelectedCleanerTaskId(taskId: String) {
        _selectedCleanerTaskId.value = taskId
    }

    fun setSelectedCleanerAreaId(areaId: String) {
        _selectedCleanerAreaId.value = areaId
    }

    fun updateCleaningTaskStatus(taskId: String, newStatus: String, notes: String) {
        _cleaningTasks.update { list ->
            list.map { if (it.id == taskId) it.copy(status = newStatus, notes = notes.ifBlank { it.notes }) else it }
        }
        val task = _cleaningTasks.value.find { it.id == taskId }
        if (task != null && newStatus == "Completed") {
            val history = CleanerWorkHistoryItem(
                id = "HIS-" + (200..999).random(),
                taskName = task.title,
                area = task.area,
                date = "Today",
                dateFull = "Nov 26, 2026",
                time = "Just now",
                status = "Completed",
                notes = notes.ifBlank { task.desc }
            )
            _cleanerWorkHistory.update { listOf(history) + it }
        }
    }

    fun submitCleaningReport(category: String, area: String, description: String, priority: String) {
        val report = CleaningReportSubmission(
            id = "REP-" + (100..999).random(),
            category = category,
            area = area,
            description = description,
            priority = priority,
            date = "Today, Nov 26, 2026",
            status = "Submitted"
        )
        _cleaningReports.update { listOf(report) + it }
    }

    // Worker Actions
    fun setSelectedWorkerCow(cowId: String) {
        _selectedWorkerCowId.value = cowId
    }

    fun setSelectedWorkerTaskId(taskId: String) {
        _selectedWorkerTaskId.value = taskId
    }

    fun updateTaskStatus(taskId: String, newStatus: String) {
        _workerTasks.update { list ->
            list.map { if (it.id == taskId) it.copy(status = newStatus) else it }
        }
        val task = _workerTasks.value.find { it.id == taskId }
        if (task != null && newStatus == "Completed") {
            val historyItem = WorkHistoryItem(
                id = "WH-" + (10..99).random(),
                type = "Task Completion",
                title = "Completed Task: ${task.title}",
                target = task.area,
                date = "Today",
                time = "Just now",
                status = "Completed"
            )
            _workHistory.update { listOf(historyItem) + it }
        }
    }

    fun submitHealthReport(cowId: String, symptoms: List<String>, urgency: String, notes: String) {
        val cow = _workerCows.value.find { it.id == cowId } ?: _workerCows.value.first()
        val report = HealthReportSubmission(
            id = "HR-" + (500..999).random(),
            cowId = cow.id,
            cowName = cow.name,
            symptoms = symptoms,
            urgency = urgency,
            notes = notes,
            date = "Today, 26 Nov 2026",
            status = "Submitted"
        )
        _workerHealthSubmissions.update { listOf(report) + it }
        val historyItem = WorkHistoryItem(
            id = "WH-" + (10..99).random(),
            type = "Health Reporting",
            title = "Health Problem Reported ($urgency)",
            target = "Cow ${cow.name} (${cow.id})",
            date = "Today",
            time = "Just now",
            status = "Submitted"
        )
        _workHistory.update { listOf(historyItem) + it }
    }

    fun recordWorkerMilk(cowId: String, shift: String, liters: Double) {
        _workerCows.update { list ->
            list.map { c ->
                if (c.id == cowId) {
                    val am = if (shift == "Morning") liters else c.morningMilk
                    val pm = if (shift == "Evening") liters else c.eveningMilk
                    c.copy(todayMilk = am + pm, morningMilk = am, eveningMilk = pm)
                } else c
            }
        }
        val cow = _workerCows.value.find { it.id == cowId } ?: _workerCows.value.first()
        val historyItem = WorkHistoryItem(
            id = "WH-" + (10..99).random(),
            type = "Milk Recording",
            title = "$shift Milking Logged ($liters L)",
            target = "Cow ${cow.name} (${cow.id})",
            date = "Today",
            time = "Just now",
            status = "Completed"
        )
        _workHistory.update { listOf(historyItem) + it }
    }

    // Owner Actions
    fun setSelectedCow(cowId: String) {
        _selectedCowId.value = cowId
    }

    fun setSelectedEmployee(empId: String) {
        _selectedEmployeeId.value = empId
    }

    fun setEmptyState(isEmpty: Boolean) {
        _emptyState.value = isEmpty
    }

    fun togglePasswordVisibility() {
        _showPassword.update { !it }
    }

    fun toggleTheme() {
        _isDarkTheme.update { !it }
    }

    fun updateFarmProfile(name: String, owner: String, email: String, location: String) {
        _farmProfile.value = FarmProfile(name = name, owner = owner, email = email, location = location)
    }

    fun setReportFilter(filter: String) {
        _reportFilter.value = filter
    }

    fun setCowFilter(filter: String) {
        _cowFilter.value = filter
    }

    fun setCowSearch(query: String) {
        _cowSearch.value = query
    }

    fun toggleSetting(key: String) {
        _settings.update { current ->
            current.toMutableMap().apply {
                this[key] = !(this[key] ?: true)
            }
        }
    }

    fun addCow(id: String, name: String, breed: String, weight: String, status: String, gender: String) {
        val trimmedId = id.ifBlank { "HB-${(105..999).random()}" }.trim()
        if (_cows.value.any { it.id.equals(trimmedId, ignoreCase = true) }) {
            return
        }
        val newCow = Cow(
            id = trimmedId,
            name = name.ifBlank { "Kajol" },
            breed = breed,
            age = "2 Yrs 8 Mos",
            gender = gender,
            weight = "$weight kg",
            dob = "15 Mar 2024",
            status = status,
            dailyMilk = 18.5,
            morningMilk = 10.2,
            eveningMilk = 8.3,
            color = "#1B5E20",
            spotPattern = "black-white",
            vaccine = "FMD & Anthrax (Verified)",
            nextVaccine = "20 Mar 2027",
            history = listOf(CowHistory("26 Nov 2026", "Registered in HAMBAA", "Initial onboarding health check passed"))
        )
        _cows.update { listOf(newCow) + it }
        _emptyState.value = false
        navigateTo(4)
    }

    fun deleteCow(cowId: String) {
        _cows.update { list -> list.filter { it.id != cowId } }
        navigateTo(4)
    }

    fun recordMilk(cowId: String, shift: String, liters: Double, notes: String) {
        val cow = _cows.value.find { it.id == cowId } ?: _cows.value.first()
        val log = MilkLog(
            id = "ML-" + (905..999).random(),
            cowId = cow.id,
            cowName = cow.name,
            date = "26 Nov 2026",
            shift = shift,
            liters = liters,
            notes = notes.ifBlank { "Recorded via mobile" }
        )
        _milkLogs.update { listOf(log) + it }
        navigateTo(7)
    }

    fun addExpense(title: String, category: String, amount: Double, desc: String) {
        val exp = Expense(
            id = "EX-" + (405..499).random(),
            title = title,
            category = category,
            amount = amount,
            date = "26 Nov 2026",
            desc = desc
        )
        _expenses.update { listOf(exp) + it }
        navigateTo(14)
    }

    fun addSale(product: String, category: String, qty: Double, unitPrice: Double, customer: String) {
        val total = qty * unitPrice
        val sale = Sale(
            id = "SL-" + (705..799).random(),
            product = product,
            category = category,
            qty = "$qty units",
            unitPrice = "৳${String.format("%.2f", unitPrice)}",
            total = total,
            date = "26 Nov 2026",
            customer = customer
        )
        _sales.update { listOf(sale) + it }
        navigateTo(16)
    }

    fun addHealthRecord(cowId: String, condition: String, symptoms: String, diagnosis: String, treatment: String, medication: String, vet: String) {
        val cow = _cows.value.find { it.id == cowId } ?: _cows.value.first()
        val hr = HealthRecord(
            id = "HR-" + (304..399).random(),
            cowId = cow.id,
            cowName = cow.name,
            condition = condition,
            severity = "Healthy",
            symptoms = symptoms,
            diagnosis = diagnosis,
            treatment = treatment,
            medication = medication,
            vet = vet,
            date = "26 Nov 2026",
            nextCheckup = "26 Feb 2027"
        )
        _healthRecords.update { listOf(hr) + it }
        navigateTo(10)
    }

    fun addEmployee(name: String, role: String, phone: String, salary: String, shift: String) {
        val newEmp = Employee(
            id = "EMP-" + (5..99).random(),
            name = name.ifBlank { "New Staff" },
            role = role.ifBlank { "Farm Assistant" },
            phone = phone.ifBlank { "+880 1700-000000" },
            status = "Active",
            joined = "26 Nov 2026",
            salary = "৳$salary / mo",
            shift = shift.ifBlank { "06:00 AM – 03:00 PM" },
            tasksDone = "100%"
        )
        _employees.update { listOf(newEmp) + it }
        navigateTo(12)
    }

    fun toggleEmployeeStatus(empId: String) {
        _employees.update { list ->
            list.map { emp ->
                if (emp.id == empId) {
                    emp.copy(status = if (emp.status == "Active") "On Leave" else "Active")
                } else emp
            }
        }
    }

    fun deleteEmployee(empId: String) {
        _employees.update { list -> list.filter { it.id != empId } }
        navigateTo(12)
    }
}
