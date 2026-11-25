package com.example.hambaaapp.data

data class SalesmanProfile(
    val name: String = "Rahim Ahmed",
    val empId: String = "SLM-001",
    val roleTitle: String = "Salesman",
    val email: String = "salesman@hamba.com",
    val phone: String = "+880 1714-509281",
    val assignedTerritory: String = "Savar, Uttara & Mirpur Dairy Route",
    val farmName: String = "Green Valley Dairy Farm"
)

data class DairyProduct(
    val id: String,
    val name: String,
    val category: String, // "Milk", "Ghee & Butter", "Yogurt & Curd", "Cheese & Paneer"
    val unit: String, // "Liter", "Pack", "Jar", "Cup (500g)"
    val price: Double, // in ৳ BDT
    val stock: Int,
    val status: String, // "In Stock", "Low Stock", "Out of Stock"
    val iconType: String,
    val soldToday: Int,
    val desc: String
)

data class CustomerRecord(
    val id: String,
    val name: String,
    val phone: String,
    val address: String,
    val customerType: String, // "Regular", "Shop", "Business"
    val totalOrders: Int,
    val totalPurchased: Double, // in ৳ BDT
    val notes: String
)

data class SaleOrder(
    val id: String,
    val customerId: String,
    val customerName: String,
    val customerPhone: String,
    val productName: String,
    val quantity: Int,
    val unit: String,
    val unitPrice: Double,
    val discount: Double,
    val totalAmount: Double, // in ৳ BDT
    val paymentMethod: String, // "Cash", "bKash", "Nagad"
    val status: String, // "Completed", "Pending"
    val date: String,
    val dateFull: String,
    val time: String
)

data class SalesmanNotification(
    val id: String,
    val type: String,
    val title: String,
    val body: String,
    val time: String,
    val unread: Boolean,
    val targetScreen: Int = 401
)
