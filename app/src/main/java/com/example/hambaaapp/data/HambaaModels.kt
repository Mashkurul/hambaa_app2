package com.example.hambaaapp.data

data class FarmProfile(
    val name: String = "Green Valley Dairy Farm",
    val owner: String = "Tariqul Hasan",
    val email: String = "owner@hambaafarm.com",
    val location: String = "Savar, Dhaka"
)

data class CowHistory(
    val date: String,
    val title: String,
    val desc: String
)

data class Cow(
    val id: String,
    val name: String,
    val breed: String,
    val age: String,
    val gender: String,
    val weight: String,
    val dob: String,
    val status: String, // "Healthy", "Sick", "Due Vaccine"
    val dailyMilk: Double,
    val morningMilk: Double,
    val eveningMilk: Double,
    val color: String,
    val spotPattern: String,
    val vaccine: String,
    val nextVaccine: String,
    val history: List<CowHistory>
)

data class MilkLog(
    val id: String,
    val cowId: String,
    val cowName: String,
    val date: String,
    val shift: String, // "Morning", "Evening"
    val liters: Double,
    val notes: String
)

data class FeedItem(
    val name: String,
    val category: String,
    val stock: String,
    val percent: Int,
    val status: String, // "Optimal", "Low Stock"
    val dailyUse: String
)

data class FeedingSchedule(
    val time: String,
    val title: String,
    val detail: String,
    val done: Boolean
)

data class HealthRecord(
    val id: String,
    val cowId: String,
    val cowName: String,
    val condition: String,
    val severity: String, // "Healthy", "Sick", "Due Vaccine"
    val symptoms: String,
    val diagnosis: String,
    val treatment: String,
    val medication: String,
    val vet: String,
    val date: String,
    val nextCheckup: String
)

data class Employee(
    val id: String,
    val name: String,
    val role: String,
    val phone: String,
    val status: String, // "Active", "On Leave"
    val joined: String,
    val salary: String,
    val shift: String,
    val tasksDone: String
)

data class Expense(
    val id: String,
    val title: String,
    val category: String, // "Food", "Medicine", "Employee Salary", "Equipment", "Other"
    val amount: Double,
    val date: String,
    val desc: String
)

data class Sale(
    val id: String,
    val product: String,
    val category: String, // "Milk Sales", "Other Products"
    val qty: String,
    val unitPrice: String,
    val total: Double,
    val date: String,
    val customer: String
)
