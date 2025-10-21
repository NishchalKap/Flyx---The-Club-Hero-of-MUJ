package com.example.flyx.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flyx.data.model.*
import com.example.flyx.ui.components.*
import com.example.flyx.ui.theme.*
import com.example.flyx.ui.viewmodel.EventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: EventViewModel,
    onEventClick: (String) -> Unit,
    onClubClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val socialState by viewModel.socialState.collectAsStateWithLifecycle()
    
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Events", "Clubs", "Feed", "Live")
    
    // Enhanced animations
    val infiniteTransition = rememberInfiniteTransition(label = "hero")
    val heroScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heroScale"
    )
    
    val floatAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )
    
    LaunchedEffect(Unit) {
        viewModel.loadEvents()
        viewModel.loadClubs()
        viewModel.loadPosts()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "UniConnect",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Discover • Connect • Experience",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = onProfileClick) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = when (index) {
                                    0 -> Icons.Default.Event
                                    1 -> Icons.Default.Group
                                    2 -> Icons.Default.Home
                                    3 -> Icons.Default.LiveTv
                                    else -> Icons.Default.Home
                                },
                                contentDescription = title
                            )
                        },
                        label = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Enhanced Hero Section with animations
            item {
                EnhancedHeroSection(
                    modifier = Modifier.fillMaxWidth(),
                    scale = heroScale,
                    floatOffset = floatAnimation
                )
            }
            
            // Quick Stats with animations
            item {
                EnhancedQuickStatsSection(
                    eventsCount = uiState.events.size,
                    clubsCount = uiState.clubs.size,
                    postsCount = socialState.posts.size
                )
            }
            
            // Tab Content with enhanced animations
            when (selectedTab) {
                0 -> {
                    // Events Tab
                    item {
                        Text(
                            text = "🔥 Upcoming Events",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    
                    if (uiState.isLoadingEvents) {
                        item {
                            repeat(3) {
                                LoadingShimmer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                )
                            }
                        }
                    } else {
                        items(uiState.events.take(5)) { event ->
                            EventCard(
                                event = event,
                                onClick = { onEventClick(event.id) },
                                onRegisterClick = { 
                                    // Handle registration
                                }
                            )
                        }
                    }
                }
                
                1 -> {
                    // Clubs Tab
                    item {
                        Text(
                            text = "🏛️ Popular Clubs",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    
                    if (uiState.isLoadingClubs) {
                        item {
                            repeat(3) {
                                LoadingShimmer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                )
                            }
                        }
                    } else {
                        items(uiState.clubs.take(5)) { club ->
                            ClubCard(
                                club = club,
                                onClick = { onClubClick(club.id) },
                                onJoinClick = {
                                    // Handle join club
                                }
                            )
                        }
                    }
                }
                
                2 -> {
                    // Feed Tab
                    item {
                        Text(
                            text = "📱 Campus Feed",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    
                    if (socialState.isLoadingPosts) {
                        item {
                            repeat(3) {
                                LoadingShimmer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
                                )
                            }
                        }
                    } else {
                        items(socialState.posts.take(5)) { post ->
                            PostCard(
                                post = post,
                                onLikeClick = { 
                                    // Handle like
                                },
                                onCommentClick = { 
                                    // Handle comment
                                },
                                onShareClick = { 
                                    // Handle share
                                }
                            )
                        }
                    }
                }
                
                3 -> {
                    // Live Tab
                    item {
                        Text(
                            text = "🔴 Live Events",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    
                    item {
                        EnhancedLiveEventsSection(
                            events = uiState.events.filter { it.isLive }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EnhancedHeroSection(
    modifier: Modifier = Modifier,
    scale: Float,
    floatOffset: Float
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            UniBlue.copy(alpha = 0.9f),
                            UniPurple.copy(alpha = 0.8f),
                            UniGreen.copy(alpha = 0.6f)
                        )
                    )
                )
        ) {
            // Floating decorative elements
            FloatingElement(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 20.dp, y = 20.dp + floatOffset.dp)
                    .scale(scale),
                color = Color.White.copy(alpha = 0.2f),
                size = 60.dp
            )
            
            FloatingElement(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-20).dp, y = 40.dp + floatOffset.dp)
                    .scale(scale * 0.8f),
                color = Color.White.copy(alpha = 0.15f),
                size = 40.dp
            )
            
            FloatingElement(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 30.dp, y = (-30).dp - floatOffset.dp)
                    .scale(scale * 0.6f),
                color = Color.White.copy(alpha = 0.1f),
                size = 80.dp
            )
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🎓 Welcome to Your Campus Hub!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Discover amazing events, join clubs, and connect with your university community.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Button(
                    onClick = { /* Handle explore */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = UniBlue
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text(
                        text = "🚀 Explore Now",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun EnhancedQuickStatsSection(
    eventsCount: Int,
    clubsCount: Int,
    postsCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        EnhancedStatCard(
            title = "Events",
            count = eventsCount,
            icon = Icons.Default.Event,
            color = UniBlue,
            modifier = Modifier.weight(1f)
        )
        
        EnhancedStatCard(
            title = "Clubs",
            count = clubsCount,
            icon = Icons.Default.Group,
            color = UniPurple,
            modifier = Modifier.weight(1f)
        )
        
        EnhancedStatCard(
            title = "Posts",
            count = postsCount,
            icon = Icons.Default.Chat,
            color = UniGreen,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun EnhancedStatCard(
    title: String,
    count: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "stat")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(color, color.copy(alpha = 0.7f))
                        )
                    )
                    .scale(pulse),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun EnhancedLiveEventsSection(
    events: List<Event>,
    modifier: Modifier = Modifier
) {
    if (events.isEmpty()) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(UniRed.copy(alpha = 0.3f), UniRed.copy(alpha = 0.1f))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LiveTv,
                        contentDescription = "No Live Events",
                        tint = UniRed,
                        modifier = Modifier.size(40.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Text(
                    text = "No Live Events Right Now",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = "Check back later for live updates from ongoing events!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(events) { event ->
                EnhancedLiveEventCard(event = event)
            }
        }
    }
}

@Composable
private fun EnhancedLiveEventCard(
    event: Event,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "live")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    Card(
        modifier = modifier.width(220.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(UniRed)
                        .scale(pulse)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "🔴 LIVE",
                    style = MaterialTheme.typography.labelSmall,
                    color = UniRed,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = event.organizer,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.People,
                    contentDescription = "Viewers",
                    tint = UniRed,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${event.registeredUsers.size} watching",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun FloatingElement(
    modifier: Modifier = Modifier,
    color: Color,
    size: androidx.compose.ui.unit.Dp
) {
    Box(
        modifier = modifier
            .size(size)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(color, color.copy(alpha = 0.3f))
                ),
                shape = RoundedCornerShape(50.dp)
            )
    )
}