package com.example.hambaaapp.data

data class CleanerProfile(
    val name: String = "Rahim Ahmed",
    val empId: String = "CLN-001",
    val roleTitle: String = "Cleaner",
    val email: String = "cleaner@hamba.com",
    val phone: String = "+880 1715-882940",
    val shift: String = "05:30 AM – 04:30 PM",
    val farmName: String = "Green Valley Dairy Farm"
)

data class CleaningTask(
    val id: String,
    val title: String,
    val areaId: String,
    val area: String,
    val period: String, // "Morning", "Afternoon", "Evening"
    val scheduledTime: String,
    val deadline: String,
    val priority: String, // "High", "Medium", "Low"
    val status: String, // "Pending", "In Progress", "Completed"
    val desc: String,
    val instructions: List<String>,
    val notes: String,
    val photoAttached: Boolean
)

data class CleaningArea(
    val id: String,
    val name: String,
    val status: String, // "Cleaned", "In Progress", "Needs Attention"
    val lastCleaned: String,
    val lastCleanedDate: String,
    val zoneCode: String,
    val iconType: String,
    val instructions: String,
    val reminders: List<String>
)

data class CleanerWorkHistoryItem(
    val id: String,
    val taskName: String,
    val area: String,
    val date: String,
    val dateFull: String,
    val time: String,
    val status: String,
    val notes: String
)

data class CleanerNotification(
    val id: String,
    val type: String,
    val title: String,
    val body: String,
    val time: String,
    val unread: Boolean,
    val taskId: String? = null
)

data class CleaningReportSubmission(
    val id: String,
    val category: String,
    val area: String,
    val description: String,
    val priority: String,
    val date: String,
    val status: String
)
