package com.example.hambaaapp.data

enum class UserRole {
    OWNER,
    WORKER,
    CLEANER,
    WATCHMAN,
    SALESMAN
}

data class WorkerProfile(
    val name: String = "Rahim Uddin",
    val empId: String = "EMP-104",
    val designation: String = "Cow Worker",
    val phone: String = "+880 1712-458910",
    val assignedArea: String = "North Dairy Shed A & B",
    val farmName: String = "Green Valley Dairy Farm",
    val assignedCowCount: Int = 24
)

data class CareActivity(
    val time: String,
    val title: String,
    val by: String
)

data class WorkerCow(
    val id: String,
    val name: String,
    val breed: String,
    val age: String,
    val weight: String,
    val status: String, // "Healthy", "Observation", "Sick"
    val shedBay: String,
    val todayMilk: Double,
    val morningMilk: Double,
    val eveningMilk: Double,
    val feedingNote: String,
    val ownerNote: String,
    val coatColor: String,
    val careActivity: List<CareActivity>
)

data class WorkerTask(
    val id: String,
    val title: String,
    val time: String,
    val area: String,
    val status: String, // "Pending", "In Progress", "Completed"
    val desc: String,
    val checklist: List<String>,
    val supervisorNote: String
)

data class FeedingScheduleItem(
    val id: String,
    val group: String,
    val feed: String,
    val amount: String,
    val time: String,
    val status: String
)

data class FeedingSchedulePeriod(
    val period: String,
    val timeWindow: String,
    val items: List<FeedingScheduleItem>
)

data class WorkHistoryItem(
    val id: String,
    val type: String, // "Feeding", "Shed Cleaning", "Health Reporting", "Milk Recording"
    val title: String,
    val target: String,
    val date: String,
    val time: String,
    val status: String
)

data class WorkerNotification(
    val id: String,
    val category: String,
    val title: String,
    val body: String,
    val time: String,
    val unread: Boolean,
    val targetScreen: Int = 3
)

data class HealthReportSubmission(
    val id: String,
    val cowId: String,
    val cowName: String,
    val symptoms: List<String>,
    val urgency: String,
    val notes: String,
    val date: String,
    val status: String // "Submitted", "Reviewed"
)
