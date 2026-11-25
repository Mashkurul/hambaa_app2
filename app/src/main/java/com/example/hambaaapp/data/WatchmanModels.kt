package com.example.hambaaapp.data

data class WatchmanProfile(
    val name: String = "Karim Ahmed",
    val empId: String = "WTM-001",
    val roleTitle: String = "Watchman",
    val email: String = "watchman@hamba.com",
    val phone: String = "+880 1711-649203",
    val assignedShift: String = "Morning Gate Shift (06:00 AM – 06:00 PM)",
    val gatePost: String = "Main North Gate & Weighbridge",
    val farmName: String = "Green Valley Dairy Farm",
    val attendanceStatus: String = "On Duty", // "Not Checked In", "On Duty", "Completed Shift"
    val checkInTime: String = "05:54 AM",
    val checkOutTime: String = "-- : --"
)

data class VisitorRecord(
    val id: String,
    val name: String,
    val phone: String,
    val address: String,
    val purpose: String,
    val personToMeet: String,
    val visitorType: String, // "Business", "Supplier", "Customer", "Personal"
    val vehicleInfo: String,
    val entryDate: String,
    val entryTime: String,
    val exitTime: String,
    val status: String, // "Inside", "Exited"
    val notes: String
)

data class VehicleRecord(
    val id: String,
    val regNo: String,
    val type: String, // "Chilled Milk Tanker", "Veterinary Car", "Maintenance Van", "Heavy Feed Truck", "Green Fodder Tractor"
    val driverName: String,
    val driverPhone: String,
    val purpose: String,
    val entryTime: String,
    val exitTime: String,
    val status: String // "Inside", "Exited"
)

data class DeliveryRecord(
    val id: String,
    val supplier: String,
    val deliveryType: String,
    val vehicleNo: String,
    val arrivalTime: String,
    val purpose: String,
    val status: String // "At Gate / Loading", "Departed"
)

data class EntryExitLogItem(
    val id: String,
    val ident: String,
    val category: String, // "Visitors", "Vehicles", "Deliveries"
    val action: String, // "Entry", "Exit"
    val date: String,
    val time: String,
    val status: String
)

data class SecurityIncident(
    val id: String,
    val category: String,
    val location: String,
    val description: String,
    val priority: String, // "Low", "Medium", "High", "Critical"
    val date: String,
    val status: String // "Submitted", "Acknowledged"
)

data class SecurityInstructionItem(
    val id: String,
    val title: String,
    val desc: String
)

data class WatchmanNotification(
    val id: String,
    val type: String,
    val title: String,
    val body: String,
    val time: String,
    val unread: Boolean,
    val targetScreen: Int = 301
)

data class AttendanceHistoryItem(
    val date: String,
    val checkIn: String,
    val checkOut: String,
    val hours: String,
    val status: String
)
