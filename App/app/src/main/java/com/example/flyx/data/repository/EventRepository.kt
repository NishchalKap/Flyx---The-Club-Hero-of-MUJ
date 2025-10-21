package com.example.flyx.data.repository

import com.example.flyx.data.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor() {
    
    // Mock data for university club platform
    private val mockEvents = mutableListOf(
        Event(
            id = "EVT001",
            title = "TechFest 2024 - Innovation Summit",
            description = "Join us for the biggest tech event of the year! Featuring AI workshops, startup pitches, and networking opportunities.",
            date = Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L),
            time = "09:00 AM",
            location = "Main Campus",
            venue = "University Auditorium",
            price = 150.0,
            imageUrls = listOf("https://example.com/techfest1.jpg", "https://example.com/techfest2.jpg"),
            category = EventCategory.TECH,
            clubId = "CLUB001",
            organizer = "Tech Club",
            capacity = 200,
            registeredUsers = listOf("USER001", "USER002", "USER003"),
            tags = listOf("technology", "innovation", "networking"),
            requirements = "Bring your laptop and student ID",
            contactInfo = "techclub@university.edu"
        ),
        Event(
            id = "EVT002",
            title = "Cultural Night - Bollywood Dance Competition",
            description = "Showcase your dancing skills in our annual Bollywood dance competition. Prizes for winners!",
            date = Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000L),
            time = "06:00 PM",
            location = "Cultural Center",
            venue = "Open Air Theatre",
            price = 0.0,
            imageUrls = listOf("https://example.com/cultural1.jpg"),
            category = EventCategory.CULTURAL,
            clubId = "CLUB002",
            organizer = "Cultural Club",
            capacity = 100,
            registeredUsers = listOf("USER004", "USER005"),
            tags = listOf("dance", "competition", "cultural"),
            requirements = "Dance costume and registration form"
        ),
        Event(
            id = "EVT003",
            title = "Startup Pitch Competition",
            description = "Pitch your innovative business ideas to industry experts and win funding opportunities.",
            date = Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000L),
            time = "02:00 PM",
            location = "Business School",
            venue = "Conference Hall",
            price = 200.0,
            imageUrls = listOf("https://example.com/startup1.jpg"),
            category = EventCategory.ACADEMIC,
            clubId = "CLUB003",
            organizer = "Entrepreneurship Club",
            capacity = 50,
            registeredUsers = listOf("USER006"),
            tags = listOf("startup", "pitch", "funding"),
            requirements = "Business plan presentation"
        ),
        Event(
            id = "EVT004",
            title = "Football Championship Finals",
            description = "Watch the epic finale of our annual football championship. Free entry for all students!",
            date = Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000L),
            time = "04:00 PM",
            location = "Sports Complex",
            venue = "Main Football Ground",
            price = 0.0,
            imageUrls = listOf("https://example.com/football1.jpg"),
            category = EventCategory.SPORTS,
            clubId = "CLUB004",
            organizer = "Sports Club",
            capacity = 500,
            registeredUsers = listOf("USER007", "USER008", "USER009", "USER010"),
            tags = listOf("football", "championship", "sports"),
            requirements = "Student ID for entry"
        )
    )
    
    private val mockClubs = mutableListOf(
        Club(
            id = "CLUB001",
            name = "Tech Club",
            description = "Promoting technology and innovation among students",
            logoUrl = "https://example.com/techclub_logo.jpg",
            bannerUrl = "https://example.com/techclub_banner.jpg",
            category = ClubCategory.TECH,
            president = "John Doe",
            vicePresident = "Jane Smith",
            facultyAdvisor = "Dr. Tech Advisor",
            members = listOf("USER001", "USER002", "USER003"),
            followers = listOf("USER004", "USER005", "USER006"),
            events = listOf("EVT001"),
            socialMediaLinks = mapOf("instagram" -> "@techclub", "twitter" -> "@techclub")
        ),
        Club(
            id = "CLUB002",
            name = "Cultural Club",
            description = "Celebrating diversity and cultural heritage",
            logoUrl = "https://example.com/cultural_logo.jpg",
            category = ClubCategory.CULTURAL,
            president = "Alice Johnson",
            vicePresident = "Bob Wilson",
            facultyAdvisor = "Dr. Culture Advisor",
            members = listOf("USER004", "USER005"),
            followers = listOf("USER001", "USER002"),
            events = listOf("EVT002")
        )
    )
    
    private val mockUsers = mutableListOf(
        User(
            id = "USER001",
            email = "john.doe@university.edu",
            name = "John Doe",
            studentId = "STU2024001",
            department = "Computer Science",
            academicYear = "2024",
            interests = listOf("technology", "programming", "AI"),
            clubMemberships = listOf("CLUB001"),
            followingClubs = listOf("CLUB001", "CLUB003")
        ),
        User(
            id = "USER002",
            email = "jane.smith@university.edu",
            name = "Jane Smith",
            studentId = "STU2024002",
            department = "Information Technology",
            academicYear = "2024",
            interests = listOf("web development", "design"),
            clubMemberships = listOf("CLUB001"),
            followingClubs = listOf("CLUB001", "CLUB002")
        )
    )
    
    private val mockPosts = mutableListOf(
        Post(
            id = "POST001",
            userId = "USER001",
            clubId = "CLUB001",
            eventId = "EVT001",
            content = "Excited for TechFest 2024! Can't wait to see all the amazing innovations 🚀",
            imageUrls = listOf("https://example.com/post1.jpg"),
            likes = listOf("USER002", "USER003"),
            comments = listOf(
                Comment(
                    id = "COMMENT001",
                    userId = "USER002",
                    content = "Same here! Looking forward to it!",
                    likes = listOf("USER001")
                )
            ),
            isEventPost = true
        )
    )
    
    private val mockRegistrations = mutableListOf<EventRegistration>()
    
    // Event-related functions
    fun getAllEvents(): Flow<List<Event>> = flow {
        delay(500)
        emit(mockEvents.filter { it.isActive })
    }
    
    fun getEventsByCategory(category: EventCategory): Flow<List<Event>> = flow {
        delay(300)
        emit(mockEvents.filter { it.category == category && it.isActive })
    }
    
    fun getEventById(eventId: String): Flow<Event?> = flow {
        delay(300)
        emit(mockEvents.find { it.id == eventId })
    }
    
    fun getUpcomingEvents(): Flow<List<Event>> = flow {
        delay(300)
        val now = Date()
        emit(mockEvents.filter { it.date?.after(now) == true && it.isActive })
    }
    
    fun getLiveEvents(): Flow<List<Event>> = flow {
        delay(300)
        emit(mockEvents.filter { it.isLive })
    }
    
    suspend fun registerForEvent(userId: String, eventId: String): Result<EventRegistration> {
        delay(1000)
        
        val event = mockEvents.find { it.id == eventId }
        if (event == null) {
            return Result.failure(Exception("Event not found"))
        }
        
        if (!event.isActive) {
            return Result.failure(Exception("Event is not active"))
        }
        
        if (event.registeredUsers.size >= event.capacity) {
            return Result.failure(Exception("Event is full"))
        }
        
        if (event.registeredUsers.contains(userId)) {
            return Result.failure(Exception("Already registered for this event"))
        }
        
        val registration = EventRegistration(
            id = "REG${System.currentTimeMillis()}",
            userId = userId,
            eventId = eventId,
            status = RegistrationStatus.CONFIRMED,
            ticketNumber = "TKT${System.currentTimeMillis()}",
            qrCode = "QR${System.currentTimeMillis()}"
        )
        
        mockRegistrations.add(registration)
        
        // Update event registration
        val eventIndex = mockEvents.indexOfFirst { it.id == eventId }
        if (eventIndex != -1) {
            mockEvents[eventIndex] = mockEvents[eventIndex].copy(
                registeredUsers = mockEvents[eventIndex].registeredUsers + userId
            )
        }
        
        return Result.success(registration)
    }
    
    // Club-related functions
    fun getAllClubs(): Flow<List<Club>> = flow {
        delay(500)
        emit(mockClubs.filter { it.isActive })
    }
    
    fun getClubById(clubId: String): Flow<Club?> = flow {
        delay(300)
        emit(mockClubs.find { it.id == clubId })
    }
    
    fun getClubsByCategory(category: ClubCategory): Flow<List<Club>> = flow {
        delay(300)
        emit(mockClubs.filter { it.category == category && it.isActive })
    }
    
    suspend fun joinClub(userId: String, clubId: String): Result<Boolean> {
        delay(800)
        
        val club = mockClubs.find { it.id == clubId }
        if (club == null) {
            return Result.failure(Exception("Club not found"))
        }
        
        if (club.members.contains(userId)) {
            return Result.failure(Exception("Already a member"))
        }
        
        val clubIndex = mockClubs.indexOfFirst { it.id == clubId }
        if (clubIndex != -1) {
            mockClubs[clubIndex] = mockClubs[clubIndex].copy(
                members = mockClubs[clubIndex].members + userId
            )
        }
        
        return Result.success(true)
    }
    
    suspend fun followClub(userId: String, clubId: String): Result<Boolean> {
        delay(500)
        
        val club = mockClubs.find { it.id == clubId }
        if (club == null) {
            return Result.failure(Exception("Club not found"))
        }
        
        if (club.followers.contains(userId)) {
            return Result.failure(Exception("Already following"))
        }
        
        val clubIndex = mockClubs.indexOfFirst { it.id == clubId }
        if (clubIndex != -1) {
            mockClubs[clubIndex] = mockClubs[clubIndex].copy(
                followers = mockClubs[clubIndex].followers + userId
            )
        }
        
        return Result.success(true)
    }
    
    // User-related functions
    fun getUserById(userId: String): Flow<User?> = flow {
        delay(300)
        emit(mockUsers.find { it.id == userId })
    }
    
    fun getUserRegistrations(userId: String): Flow<List<EventRegistration>> = flow {
        delay(300)
        emit(mockRegistrations.filter { it.userId == userId })
    }
    
    // Post-related functions
    fun getAllPosts(): Flow<List<Post>> = flow {
        delay(500)
        emit(mockPosts.sortedByDescending { it.createdAt })
    }
    
    fun getPostsByClub(clubId: String): Flow<List<Post>> = flow {
        delay(300)
        emit(mockPosts.filter { it.clubId == clubId }.sortedByDescending { it.createdAt })
    }
    
    fun getPostsByEvent(eventId: String): Flow<List<Post>> = flow {
        delay(300)
        emit(mockPosts.filter { it.eventId == eventId }.sortedByDescending { it.createdAt })
    }
    
    suspend fun createPost(post: Post): Result<Post> {
        delay(1000)
        
        val newPost = post.copy(
            id = "POST${System.currentTimeMillis()}",
            createdAt = Date()
        )
        
        mockPosts.add(newPost)
        return Result.success(newPost)
    }
    
    suspend fun likePost(postId: String, userId: String): Result<Boolean> {
        delay(500)
        
        val postIndex = mockPosts.indexOfFirst { it.id == postId }
        if (postIndex == -1) {
            return Result.failure(Exception("Post not found"))
        }
        
        val post = mockPosts[postIndex]
        if (post.likes.contains(userId)) {
            return Result.failure(Exception("Already liked"))
        }
        
        mockPosts[postIndex] = post.copy(
            likes = post.likes + userId
        )
        
        return Result.success(true)
    }
    
    suspend fun addComment(postId: String, comment: Comment): Result<Comment> {
        delay(800)
        
        val postIndex = mockPosts.indexOfFirst { it.id == postId }
        if (postIndex == -1) {
            return Result.failure(Exception("Post not found"))
        }
        
        val newComment = comment.copy(
            id = "COMMENT${System.currentTimeMillis()}",
            createdAt = Date()
        )
        
        mockPosts[postIndex] = mockPosts[postIndex].copy(
            comments = mockPosts[postIndex].comments + newComment
        )
        
        return Result.success(newComment)
    }
    
    // Search functions
    fun searchEvents(query: String): Flow<List<Event>> = flow {
        delay(500)
        val results = mockEvents.filter { event ->
            event.title.contains(query, ignoreCase = true) ||
            event.description.contains(query, ignoreCase = true) ||
            event.tags.any { tag -> tag.contains(query, ignoreCase = true) }
        }
        emit(results)
    }
    
    fun searchClubs(query: String): Flow<List<Club>> = flow {
        delay(500)
        val results = mockClubs.filter { club ->
            club.name.contains(query, ignoreCase = true) ||
            club.description.contains(query, ignoreCase = true)
        }
        emit(results)
    }
}