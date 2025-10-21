package com.example.flyx.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flyx.data.model.*
import com.example.flyx.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UniConnectUiState())
    val uiState: StateFlow<UniConnectUiState> = _uiState.asStateFlow()

    private val _registrationState = MutableStateFlow(EventRegistrationState())
    val registrationState: StateFlow<EventRegistrationState> = _registrationState.asStateFlow()

    private val _socialState = MutableStateFlow(SocialUiState())
    val socialState: StateFlow<SocialUiState> = _socialState.asStateFlow()

    // Event Management
    fun loadEvents() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingEvents = true, eventsError = null)
            
            try {
                eventRepository.getAllEvents()
                    .collect { events ->
                        _uiState.value = _uiState.value.copy(
                            isLoadingEvents = false,
                            events = events,
                            eventsError = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingEvents = false,
                    eventsError = e.message ?: "Failed to load events"
                )
            }
        }
    }

    fun loadEventsByCategory(category: EventCategory) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingEvents = true, eventsError = null)
            
            try {
                eventRepository.getEventsByCategory(category)
                    .collect { events ->
                        _uiState.value = _uiState.value.copy(
                            isLoadingEvents = false,
                            events = events,
                            eventsError = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingEvents = false,
                    eventsError = e.message ?: "Failed to load events"
                )
            }
        }
    }

    fun loadUpcomingEvents() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingEvents = true, eventsError = null)
            
            try {
                eventRepository.getUpcomingEvents()
                    .collect { events ->
                        _uiState.value = _uiState.value.copy(
                            isLoadingEvents = false,
                            events = events,
                            eventsError = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingEvents = false,
                    eventsError = e.message ?: "Failed to load upcoming events"
                )
            }
        }
    }

    fun loadLiveEvents() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingEvents = true, eventsError = null)
            
            try {
                eventRepository.getLiveEvents()
                    .collect { events ->
                        _uiState.value = _uiState.value.copy(
                            isLoadingEvents = false,
                            events = events,
                            eventsError = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingEvents = false,
                    eventsError = e.message ?: "Failed to load live events"
                )
            }
        }
    }

    fun loadEventById(eventId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingEvent = true, eventError = null)
            
            try {
                eventRepository.getEventById(eventId)
                    .collect { event ->
                        _uiState.value = _uiState.value.copy(
                            isLoadingEvent = false,
                            selectedEvent = event,
                            eventError = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingEvent = false,
                    eventError = e.message ?: "Failed to load event"
                )
            }
        }
    }

    fun searchEvents(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingEvents = true, eventsError = null)
            
            try {
                eventRepository.searchEvents(query)
                    .collect { events ->
                        _uiState.value = _uiState.value.copy(
                            isLoadingEvents = false,
                            events = events,
                            eventsError = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingEvents = false,
                    eventsError = e.message ?: "Search failed"
                )
            }
        }
    }

    // Club Management
    fun loadClubs() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingClubs = true, clubsError = null)
            
            try {
                eventRepository.getAllClubs()
                    .collect { clubs ->
                        _uiState.value = _uiState.value.copy(
                            isLoadingClubs = false,
                            clubs = clubs,
                            clubsError = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingClubs = false,
                    clubsError = e.message ?: "Failed to load clubs"
                )
            }
        }
    }

    fun loadClubById(clubId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingClub = true, clubError = null)
            
            try {
                eventRepository.getClubById(clubId)
                    .collect { club ->
                        _uiState.value = _uiState.value.copy(
                            isLoadingClub = false,
                            selectedClub = club,
                            clubError = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingClub = false,
                    clubError = e.message ?: "Failed to load club"
                )
            }
        }
    }

    fun loadClubsByCategory(category: ClubCategory) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingClubs = true, clubsError = null)
            
            try {
                eventRepository.getClubsByCategory(category)
                    .collect { clubs ->
                        _uiState.value = _uiState.value.copy(
                            isLoadingClubs = false,
                            clubs = clubs,
                            clubsError = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingClubs = false,
                    clubsError = e.message ?: "Failed to load clubs"
                )
            }
        }
    }

    fun searchClubs(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingClubs = true, clubsError = null)
            
            try {
                eventRepository.searchClubs(query)
                    .collect { clubs ->
                        _uiState.value = _uiState.value.copy(
                            isLoadingClubs = false,
                            clubs = clubs,
                            clubsError = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingClubs = false,
                    clubsError = e.message ?: "Search failed"
                )
            }
        }
    }

    fun joinClub(userId: String, clubId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isJoiningClub = true, joinClubError = null)
            
            try {
                val success = eventRepository.joinClub(userId, clubId).getOrThrow()
                _uiState.value = _uiState.value.copy(
                    isJoiningClub = false,
                    joinClubSuccess = success,
                    joinClubError = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isJoiningClub = false,
                    joinClubError = e.message ?: "Failed to join club"
                )
            }
        }
    }

    fun followClub(userId: String, clubId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isFollowingClub = true, followClubError = null)
            
            try {
                val success = eventRepository.followClub(userId, clubId).getOrThrow()
                _uiState.value = _uiState.value.copy(
                    isFollowingClub = false,
                    followClubSuccess = success,
                    followClubError = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isFollowingClub = false,
                    followClubError = e.message ?: "Failed to follow club"
                )
            }
        }
    }

    // Event Registration
    fun registerForEvent(userId: String, eventId: String) {
        viewModelScope.launch {
            _registrationState.value = _registrationState.value.copy(isLoading = true, error = null)
            
            try {
                val registration = eventRepository.registerForEvent(userId, eventId).getOrThrow()
                _registrationState.value = _registrationState.value.copy(
                    isLoading = false,
                    registration = registration,
                    error = null
                )
            } catch (e: Exception) {
                _registrationState.value = _registrationState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Registration failed"
                )
            }
        }
    }

    fun getUserRegistrations(userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingRegistrations = true, registrationsError = null)
            
            try {
                eventRepository.getUserRegistrations(userId)
                    .collect { registrations ->
                        _uiState.value = _uiState.value.copy(
                            isLoadingRegistrations = false,
                            userRegistrations = registrations,
                            registrationsError = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingRegistrations = false,
                    registrationsError = e.message ?: "Failed to load registrations"
                )
            }
        }
    }

    // Social Features
    fun loadPosts() {
        viewModelScope.launch {
            _socialState.value = _socialState.value.copy(isLoadingPosts = true, postsError = null)
            
            try {
                eventRepository.getAllPosts()
                    .collect { posts ->
                        _socialState.value = _socialState.value.copy(
                            isLoadingPosts = false,
                            posts = posts,
                            postsError = null
                        )
                    }
            } catch (e: Exception) {
                _socialState.value = _socialState.value.copy(
                    isLoadingPosts = false,
                    postsError = e.message ?: "Failed to load posts"
                )
            }
        }
    }

    fun loadPostsByClub(clubId: String) {
        viewModelScope.launch {
            _socialState.value = _socialState.value.copy(isLoadingPosts = true, postsError = null)
            
            try {
                eventRepository.getPostsByClub(clubId)
                    .collect { posts ->
                        _socialState.value = _socialState.value.copy(
                            isLoadingPosts = false,
                            posts = posts,
                            postsError = null
                        )
                    }
            } catch (e: Exception) {
                _socialState.value = _socialState.value.copy(
                    isLoadingPosts = false,
                    postsError = e.message ?: "Failed to load club posts"
                )
            }
        }
    }

    fun loadPostsByEvent(eventId: String) {
        viewModelScope.launch {
            _socialState.value = _socialState.value.copy(isLoadingPosts = true, postsError = null)
            
            try {
                eventRepository.getPostsByEvent(eventId)
                    .collect { posts ->
                        _socialState.value = _socialState.value.copy(
                            isLoadingPosts = false,
                            posts = posts,
                            postsError = null
                        )
                    }
            } catch (e: Exception) {
                _socialState.value = _socialState.value.copy(
                    isLoadingPosts = false,
                    postsError = e.message ?: "Failed to load event posts"
                )
            }
        }
    }

    fun createPost(post: Post) {
        viewModelScope.launch {
            _socialState.value = _socialState.value.copy(isCreatingPost = true, createPostError = null)
            
            try {
                val newPost = eventRepository.createPost(post).getOrThrow()
                _socialState.value = _socialState.value.copy(
                    isCreatingPost = false,
                    createdPost = newPost,
                    createPostError = null
                )
            } catch (e: Exception) {
                _socialState.value = _socialState.value.copy(
                    isCreatingPost = false,
                    createPostError = e.message ?: "Failed to create post"
                )
            }
        }
    }

    fun likePost(postId: String, userId: String) {
        viewModelScope.launch {
            _socialState.value = _socialState.value.copy(isLikingPost = true, likePostError = null)
            
            try {
                val success = eventRepository.likePost(postId, userId).getOrThrow()
                _socialState.value = _socialState.value.copy(
                    isLikingPost = false,
                    likePostSuccess = success,
                    likePostError = null
                )
            } catch (e: Exception) {
                _socialState.value = _socialState.value.copy(
                    isLikingPost = false,
                    likePostError = e.message ?: "Failed to like post"
                )
            }
        }
    }

    fun addComment(postId: String, comment: Comment) {
        viewModelScope.launch {
            _socialState.value = _socialState.value.copy(isAddingComment = true, addCommentError = null)
            
            try {
                val newComment = eventRepository.addComment(postId, comment).getOrThrow()
                _socialState.value = _socialState.value.copy(
                    isAddingComment = false,
                    addedComment = newComment,
                    addCommentError = null
                )
            } catch (e: Exception) {
                _socialState.value = _socialState.value.copy(
                    isAddingComment = false,
                    addCommentError = e.message ?: "Failed to add comment"
                )
            }
        }
    }

    // User Management
    fun loadUserById(userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingUser = true, userError = null)
            
            try {
                eventRepository.getUserById(userId)
                    .collect { user ->
                        _uiState.value = _uiState.value.copy(
                            isLoadingUser = false,
                            currentUser = user,
                            userError = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingUser = false,
                    userError = e.message ?: "Failed to load user"
                )
            }
        }
    }

    // Error clearing functions
    fun clearEventsError() {
        _uiState.value = _uiState.value.copy(eventsError = null)
    }

    fun clearEventError() {
        _uiState.value = _uiState.value.copy(eventError = null)
    }

    fun clearClubsError() {
        _uiState.value = _uiState.value.copy(clubsError = null)
    }

    fun clearClubError() {
        _uiState.value = _uiState.value.copy(clubError = null)
    }

    fun clearRegistrationError() {
        _registrationState.value = _registrationState.value.copy(error = null)
    }

    fun clearPostsError() {
        _socialState.value = _socialState.value.copy(postsError = null)
    }

    fun clearCreatePostError() {
        _socialState.value = _socialState.value.copy(createPostError = null)
    }
}

// UI State classes
data class UniConnectUiState(
    // Events
    val events: List<Event> = emptyList(),
    val selectedEvent: Event? = null,
    val isLoadingEvents: Boolean = false,
    val isLoadingEvent: Boolean = false,
    val eventsError: String? = null,
    val eventError: String? = null,
    
    // Clubs
    val clubs: List<Club> = emptyList(),
    val selectedClub: Club? = null,
    val isLoadingClubs: Boolean = false,
    val isLoadingClub: Boolean = false,
    val clubsError: String? = null,
    val clubError: String? = null,
    
    // User
    val currentUser: User? = null,
    val isLoadingUser: Boolean = false,
    val userError: String? = null,
    
    // Registrations
    val userRegistrations: List<EventRegistration> = emptyList(),
    val isLoadingRegistrations: Boolean = false,
    val registrationsError: String? = null,
    
    // Club Actions
    val isJoiningClub: Boolean = false,
    val joinClubSuccess: Boolean = false,
    val joinClubError: String? = null,
    val isFollowingClub: Boolean = false,
    val followClubSuccess: Boolean = false,
    val followClubError: String? = null
)

data class EventRegistrationState(
    val isRegistered: Boolean = false,
    val registration: EventRegistration? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class SocialUiState(
    val posts: List<Post> = emptyList(),
    val isLoadingPosts: Boolean = false,
    val postsError: String? = null,
    
    val isCreatingPost: Boolean = false,
    val createdPost: Post? = null,
    val createPostError: String? = null,
    
    val isLikingPost: Boolean = false,
    val likePostSuccess: Boolean = false,
    val likePostError: String? = null,
    
    val isAddingComment: Boolean = false,
    val addedComment: Comment? = null,
    val addCommentError: String? = null
)