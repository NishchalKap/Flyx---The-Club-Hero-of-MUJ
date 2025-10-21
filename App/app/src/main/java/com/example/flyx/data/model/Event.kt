package com.example.flyx.data.model

import java.util.Date

// Core Event Model
data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: Date? = null,
    val time: String = "",
    val location: String = "",
    val venue: String = "",
    val price: Double = 0.0,
    val imageUrls: List<String> = emptyList(),
    val category: EventCategory = EventCategory.ACADEMIC,
    val clubId: String = "",
    val organizer: String = "",
    val capacity: Int = 0,
    val registeredUsers: List<String> = emptyList(),
    val waitlist: List<String> = emptyList(),
    val isActive: Boolean = true,
    val isLive: Boolean = false,
    val tags: List<String> = emptyList(),
    val requirements: String = "",
    val contactInfo: String = "",
    val socialMediaLinks: Map<String, String> = emptyMap(),
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class EventCategory {
    ACADEMIC, CULTURAL, SPORTS, SOCIAL, TECH, WORKSHOP, COMPETITION, CONCERT, FESTIVAL
}

// Club Management Model
data class Club(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val logoUrl: String = "",
    val bannerUrl: String = "",
    val category: ClubCategory = ClubCategory.ACADEMIC,
    val president: String = "",
    val vicePresident: String = "",
    val facultyAdvisor: String = "",
    val members: List<String> = emptyList(),
    val followers: List<String> = emptyList(),
    val events: List<String> = emptyList(),
    val socialMediaLinks: Map<String, String> = emptyMap(),
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class ClubCategory {
    ACADEMIC, CULTURAL, SPORTS, TECH, SOCIAL, ENVIRONMENTAL, LITERARY, DEBATE, PHOTOGRAPHY, MUSIC, DANCE, DRAMA
}

// User Profile Model
data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val profileImageUrl: String = "",
    val studentId: String = "",
    val department: String = "",
    val academicYear: String = "",
    val phoneNumber: String = "",
    val interests: List<String> = emptyList(),
    val clubMemberships: List<String> = emptyList(),
    val followingClubs: List<String> = emptyList(),
    val registeredEvents: List<String> = emptyList(),
    val posts: List<String> = emptyList(),
    val isVerified: Boolean = false,
    val createdAt: Date = Date(),
    val lastActive: Date = Date()
)

// Social Media Post Model
data class Post(
    val id: String = "",
    val userId: String = "",
    val clubId: String? = null,
    val eventId: String? = null,
    val content: String = "",
    val imageUrls: List<String> = emptyList(),
    val videoUrl: String = "",
    val likes: List<String> = emptyList(),
    val comments: List<Comment> = emptyList(),
    val shares: Int = 0,
    val isEventPost: Boolean = false,
    val isLive: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

data class Comment(
    val id: String = "",
    val userId: String = "",
    val content: String = "",
    val likes: List<String> = emptyList(),
    val createdAt: Date = Date()
)

// Event Registration Model
data class EventRegistration(
    val id: String = "",
    val userId: String = "",
    val eventId: String = "",
    val registrationDate: Date = Date(),
    val status: RegistrationStatus = RegistrationStatus.PENDING,
    val ticketNumber: String = "",
    val qrCode: String = "",
    val checkedIn: Boolean = false,
    val checkInTime: Date? = null
)

enum class RegistrationStatus {
    PENDING, CONFIRMED, CANCELLED, WAITLISTED
}

// Notification System
data class Notification(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val message: String = "",
    val type: NotificationType = NotificationType.EVENT_REMINDER,
    val relatedId: String = "",
    val isRead: Boolean = false,
    val createdAt: Date = Date()
)

enum class NotificationType {
    EVENT_REMINDER, EVENT_CANCELLED, NEW_POST, CLUB_UPDATE, REGISTRATION_CONFIRMED, EVENT_STARTING
}

// Membership Management
data class Membership(
    val id: String = "",
    val userId: String = "",
    val clubId: String = "",
    val membershipType: MembershipType = MembershipType.BASIC,
    val startDate: Date = Date(),
    val endDate: Date? = null,
    val isActive: Boolean = true,
    val benefits: List<String> = emptyList()
)

enum class MembershipType {
    BASIC, PREMIUM, VIP, LIFETIME
}

// Live Event Updates
data class LiveUpdate(
    val id: String = "",
    val eventId: String = "",
    val userId: String = "",
    val content: String = "",
    val imageUrl: String = "",
    val timestamp: Date = Date(),
    val isOfficial: Boolean = false
)

// Analytics and Insights
data class EventAnalytics(
    val eventId: String = "",
    val totalRegistrations: Int = 0,
    val totalAttendees: Int = 0,
    val totalPosts: Int = 0,
    val totalLikes: Int = 0,
    val totalShares: Int = 0,
    val engagementRate: Double = 0.0,
    val demographics: Map<String, Int> = emptyMap()
)

// Payment Integration
data class Payment(
    val id: String = "",
    val userId: String = "",
    val eventId: String = "",
    val amount: Double = 0.0,
    val currency: String = "INR",
    val status: PaymentStatus = PaymentStatus.PENDING,
    val paymentMethod: String = "",
    val transactionId: String = "",
    val createdAt: Date = Date()
)

enum class PaymentStatus {
    PENDING, COMPLETED, FAILED, REFUNDED, CANCELLED
}