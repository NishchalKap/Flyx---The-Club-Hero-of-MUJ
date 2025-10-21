package com.example.flyx.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flyx.data.model.*
import com.example.flyx.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

// Enhanced Event Card Component with animations
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "scale"
    )
    
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer"
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable { 
                isPressed = true
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Event Image with gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                getCategoryColor(event.category).copy(alpha = 0.8f),
                                getCategoryColor(event.category).copy(alpha = 0.4f)
                            )
                        )
                    )
            ) {
                // Event category icon
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                ) {
                    EventCategoryBadge(
                        category = event.category,
                        modifier = Modifier
                    )
                }
                
                // Live indicator if event is live
                if (event.isLive) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .background(
                                color = UniRed,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(Color.White, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "LIVE",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                
                // Event title overlay
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = event.title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Event Description
            Text(
                text = event.description,
                style = UniConnectTypography.eventDescription,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Event Details Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Date and Location
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Date",
                            tint = getCategoryColor(event.category),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = event.date?.let { 
                                SimpleDateFormat("MMM dd", Locale.getDefault()).format(it)
                            } ?: "TBD",
                            style = UniConnectTypography.eventDate,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = getCategoryColor(event.category),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = event.venue,
                            style = UniConnectTypography.eventDate,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                
                // Price and Registration
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    if (event.price > 0) {
                        Text(
                            text = "₹${event.price.toInt()}",
                            style = UniConnectTypography.eventPrice,
                            color = getCategoryColor(event.category),
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = "FREE",
                            style = UniConnectTypography.eventPrice,
                            color = SuccessGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "${event.registeredUsers.size}/${event.capacity}",
                        style = UniConnectTypography.eventDate,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Registration Button with animation
            Button(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = getCategoryColor(event.category)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Register Now",
                    style = UniConnectTypography.buttonSecondary,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// Enhanced Club Card Component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubCard(
    club: Club,
    onClick: () -> Unit,
    onJoinClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(100),
        label = "scale"
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable { 
                isPressed = true
                onClick() 
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Club Logo with gradient background
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                getClubCategoryColor(club.category),
                                getClubCategoryColor(club.category).copy(alpha = 0.7f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = club.name.take(2).uppercase(),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Club Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = club.name,
                    style = UniConnectTypography.clubName,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = club.description,
                    style = UniConnectTypography.clubDescription,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = "Members",
                        tint = getClubCategoryColor(club.category),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${club.members.size} members",
                        style = UniConnectTypography.memberCount,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Followers",
                        tint = getClubCategoryColor(club.category),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${club.followers.size}",
                        style = UniConnectTypography.memberCount,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Join Button with animation
            Button(
                onClick = onJoinClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = getClubCategoryColor(club.category)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Join",
                    style = UniConnectTypography.buttonSecondary,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// Enhanced Event Category Badge
@Composable
fun EventCategoryBadge(
    category: EventCategory,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor) = when (category) {
        EventCategory.ACADEMIC -> AcademicColor to Color.White
        EventCategory.CULTURAL -> CulturalColor to Color.White
        EventCategory.SPORTS -> SportsColor to Color.White
        EventCategory.SOCIAL -> SocialColor to Color.White
        EventCategory.TECH -> TechColor to Color.White
        EventCategory.WORKSHOP -> WorkshopColor to Color.White
        EventCategory.COMPETITION -> UniRed to Color.White
        EventCategory.CONCERT -> UniPurple to Color.White
        EventCategory.FESTIVAL -> UniOrange to Color.White
    }
    
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = category.name,
            style = UniConnectTypography.badgeText,
            color = textColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// Enhanced Social Post Card
@Composable
fun PostCard(
    post: Post,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isLiked by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Post Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User Avatar with gradient
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(UniBlue, UniPurple)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "U", // User initial
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "User Name",
                        style = UniConnectTypography.postUsername,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(post.createdAt),
                        style = UniConnectTypography.postTime,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                IconButton(onClick = { /* More options */ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Post Content
            Text(
                text = post.content,
                style = UniConnectTypography.postContent,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            // Post Images placeholder
            if (post.imageUrls.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    UniBlue.copy(alpha = 0.3f),
                                    UniPurple.copy(alpha = 0.3f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "📸 Post Image",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 16.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Post Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { 
                        isLiked = !isLiked
                        onLikeClick()
                    }) {
                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (isLiked) UniRed else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "${post.likes.size + if (isLiked) 1 else 0}",
                        style = UniConnectTypography.postTime,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onCommentClick) {
                        Icon(
                            imageVector = Icons.Default.Comment,
                            contentDescription = "Comment",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "${post.comments.size}",
                        style = UniConnectTypography.postTime,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                IconButton(onClick = onShareClick) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// Enhanced Loading Shimmer Effect
@Composable
fun LoadingShimmer(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )
    
    Box(
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha),
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha * 0.5f),
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha)
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
    )
}

// Enhanced Custom Tab Row
@Composable
fun CustomTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                height = 3.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        style = UniConnectTypography.tabLabel,
                        color = if (selectedTabIndex == index) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
    }
}

// Helper functions for colors
@Composable
private fun getCategoryColor(category: EventCategory): Color {
    return when (category) {
        EventCategory.ACADEMIC -> AcademicColor
        EventCategory.CULTURAL -> CulturalColor
        EventCategory.SPORTS -> SportsColor
        EventCategory.SOCIAL -> SocialColor
        EventCategory.TECH -> TechColor
        EventCategory.WORKSHOP -> WorkshopColor
        EventCategory.COMPETITION -> UniRed
        EventCategory.CONCERT -> UniPurple
        EventCategory.FESTIVAL -> UniOrange
    }
}

@Composable
private fun getClubCategoryColor(category: ClubCategory): Color {
    return when (category) {
        ClubCategory.ACADEMIC -> AcademicColor
        ClubCategory.CULTURAL -> CulturalColor
        ClubCategory.SPORTS -> SportsColor
        ClubCategory.TECH -> TechColor
        ClubCategory.SOCIAL -> SocialColor
        ClubCategory.ENVIRONMENTAL -> UniGreen
        ClubCategory.LITERARY -> UniPurple
        ClubCategory.DEBATE -> UniBlue
        ClubCategory.PHOTOGRAPHY -> UniOrange
        ClubCategory.MUSIC -> UniPink
        ClubCategory.DANCE -> UniPurple
        ClubCategory.DRAMA -> UniOrange
    }
}