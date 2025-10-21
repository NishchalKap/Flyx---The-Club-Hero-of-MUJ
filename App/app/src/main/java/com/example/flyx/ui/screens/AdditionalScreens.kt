package com.example.flyx.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flyx.data.model.*
import com.example.flyx.ui.components.*
import com.example.flyx.ui.viewmodel.EventViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: EventViewModel,
    onEventClick: (String) -> Unit,
    onClubClick: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(0) }
    val categories = listOf("All", "Events", "Clubs")
    
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            when (selectedCategory) {
                0 -> {
                    viewModel.searchEvents(searchQuery)
                    viewModel.searchClubs(searchQuery)
                }
                1 -> viewModel.searchEvents(searchQuery)
                2 -> viewModel.searchClubs(searchQuery)
            }
        } else {
            viewModel.loadEvents()
            viewModel.loadClubs()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search events, clubs...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            
            // Category Tabs
            CustomTabRow(
                tabs = categories,
                selectedTabIndex = selectedCategory,
                onTabSelected = { selectedCategory = it }
            )
            
            // Search Results
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when (selectedCategory) {
                    0 -> {
                        // Show both events and clubs
                        if (uiState.events.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Events",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            items(uiState.events) { event ->
                                EventCard(
                                    event = event,
                                    onClick = { onEventClick(event.id) },
                                    onRegisterClick = { /* Handle registration */ }
                                )
                            }
                        }
                        
                        if (uiState.clubs.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Clubs",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            items(uiState.clubs) { club ->
                                ClubCard(
                                    club = club,
                                    onClick = { onClubClick(club.id) },
                                    onJoinClick = { /* Handle join */ }
                                )
                            }
                        }
                    }
                    1 -> {
                        // Show only events
                        items(uiState.events) { event ->
                            EventCard(
                                event = event,
                                onClick = { onEventClick(event.id) },
                                onRegisterClick = { /* Handle registration */ }
                            )
                        }
                    }
                    2 -> {
                        // Show only clubs
                        items(uiState.clubs) { club ->
                            ClubCard(
                                club = club,
                                onClick = { onClubClick(club.id) },
                                onJoinClick = { /* Handle join */ }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    eventId: String,
    viewModel: EventViewModel,
    onBackClick: () -> Unit,
    onClubClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val registrationState by viewModel.registrationState.collectAsStateWithLifecycle()
    
    LaunchedEffect(eventId) {
        viewModel.loadEventById(eventId)
    }
    
    val event = uiState.selectedEvent
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (event != null) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Event Image
                if (event.imageUrls.isNotEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            // AsyncImage would go here
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Event Image")
                            }
                        }
                    }
                }
                
                // Event Info
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = event.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        EventCategoryBadge(category = event.category)
                        
                        Text(
                            text = event.description,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                
                // Event Details
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            DetailRow(
                                icon = Icons.Default.CalendarToday,
                                label = "Date",
                                value = event.date?.let { 
                                    SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(it)
                                } ?: "TBD"
                            )
                            
                            DetailRow(
                                icon = Icons.Default.Schedule,
                                label = "Time",
                                value = event.time
                            )
                            
                            DetailRow(
                                icon = Icons.Default.LocationOn,
                                label = "Venue",
                                value = event.venue
                            )
                            
                            DetailRow(
                                icon = Icons.Default.Group,
                                label = "Organizer",
                                value = event.organizer
                            )
                            
                            DetailRow(
                                icon = Icons.Default.People,
                                label = "Capacity",
                                value = "${event.registeredUsers.size}/${event.capacity}"
                            )
                            
                            if (event.price > 0) {
                                DetailRow(
                                    icon = Icons.Default.AttachMoney,
                                    label = "Price",
                                    value = "₹${event.price.toInt()}"
                                )
                            }
                        }
                    }
                }
                
                // Registration Button
                item {
                    Button(
                        onClick = { 
                            // Handle registration
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !registrationState.isRegistered
                    ) {
                        Text(
                            text = if (registrationState.isRegistered) "Already Registered" else "Register Now",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun DetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(80.dp)
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubDetailScreen(
    clubId: String,
    viewModel: EventViewModel,
    onBackClick: () -> Unit,
    onEventClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(clubId) {
        viewModel.loadClubById(clubId)
    }
    
    val club = uiState.selectedClub
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Club Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (club != null) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Club Header
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Club Logo
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(
                                        MaterialTheme.colorScheme.surfaceVariant,
                                        RoundedCornerShape(40.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Logo")
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = club.name,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Text(
                                text = club.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                StatItem(
                                    label = "Members",
                                    value = club.members.size.toString()
                                )
                                
                                StatItem(
                                    label = "Followers",
                                    value = club.followers.size.toString()
                                )
                                
                                StatItem(
                                    label = "Events",
                                    value = club.events.size.toString()
                                )
                            }
                        }
                    }
                }
                
                // Club Events
                if (club.events.isNotEmpty()) {
                    item {
                        Text(
                            text = "Upcoming Events",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    items(club.events.take(3)) { eventId ->
                        // Event card would go here
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Event: $eventId",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Click to view details",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: EventViewModel,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Picture
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(40.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Profile")
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Student Name",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = "student@university.edu",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "Computer Science • 2024",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Profile Options
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column {
                    ProfileOption(
                        icon = Icons.Default.Event,
                        title = "My Events",
                        subtitle = "View registered events",
                        onClick = { /* Handle click */ }
                    )
                    
                    ProfileOption(
                        icon = Icons.Default.Group,
                        title = "My Clubs",
                        subtitle = "Manage club memberships",
                        onClick = { /* Handle click */ }
                    )
                    
                    ProfileOption(
                        icon = Icons.Default.Settings,
                        title = "Settings",
                        subtitle = "App preferences",
                        onClick = { /* Handle click */ }
                    )
                    
                    ProfileOption(
                        icon = Icons.Default.Logout,
                        title = "Logout",
                        subtitle = "Sign out of account",
                        onClick = onLogoutClick
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Navigate",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
