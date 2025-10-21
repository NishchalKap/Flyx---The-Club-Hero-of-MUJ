package com.example.flyx.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flyx.ui.components.*
import com.example.flyx.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()
    val infiniteTransition = rememberInfiniteTransition(label = "onboarding")
    
    val floatAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )
    
    val scaleAnimation by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    val onboardingPages = listOf(
        OnboardingPage(
            title = "Discover Events",
            description = "Find amazing events happening on your campus. From tech talks to cultural festivals, never miss out!",
            icon = Icons.Default.Event,
            color = UniBlue
        ),
        OnboardingPage(
            title = "Join Clubs",
            description = "Connect with like-minded students. Join clubs that match your interests and passions.",
            icon = Icons.Default.Group,
            color = UniPurple
        ),
        OnboardingPage(
            title = "Share Experiences",
            description = "Post about your campus life, share photos from events, and connect with your university community.",
            icon = Icons.Default.Chat,
            color = UniGreen
        ),
        OnboardingPage(
            title = "Live Updates",
            description = "Get real-time updates from ongoing events. See what's happening live on your campus right now!",
            icon = Icons.Default.LiveTv,
            color = UniOrange
        )
    )
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        UniBlue.copy(alpha = 0.1f),
                        UniPurple.copy(alpha = 0.08f),
                        UniGreen.copy(alpha = 0.06f),
                        UniGray50
                    )
                )
            )
    ) {
        // Floating decorative elements
        FloatingElement(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 30.dp, y = 80.dp + floatAnimation.dp)
                .scale(scaleAnimation),
            color = UniBlue.copy(alpha = 0.2f),
            size = 60.dp
        )
        
        FloatingElement(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-40).dp, y = 120.dp + floatAnimation.dp)
                .scale(scaleAnimation * 0.8f),
            color = UniPurple.copy(alpha = 0.2f),
            size = 80.dp
        )
        
        FloatingElement(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 20.dp, y = (-100).dp - floatAnimation.dp)
                .scale(scaleAnimation * 0.6f),
            color = UniGreen.copy(alpha = 0.2f),
            size = 100.dp
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            
            // App Logo
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(UniBlue, UniPurple)
                        ),
                        shape = CircleShape
                    )
                    .scale(scaleAnimation),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "UC",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "UniConnect",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Onboarding Pages
            HorizontalPager(
                count = onboardingPages.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPageContent(
                    page = onboardingPages[page],
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Page Indicator
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.padding(16.dp),
                activeColor = MaterialTheme.colorScheme.primary,
                inactiveColor = MaterialTheme.colorScheme.surfaceVariant
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Get Started Button
            GradientButton(
                text = if (pagerState.currentPage == onboardingPages.size - 1) "Get Started" else "Next",
                onClick = {
                    if (pagerState.currentPage == onboardingPages.size - 1) {
                        onGetStarted()
                    } else {
                        // Move to next page
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Skip Button
            TextButton(onClick = onGetStarted) {
                Text(
                    text = "Skip",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon with animation
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            page.color.copy(alpha = 0.2f),
                            page.color.copy(alpha = 0.1f)
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = page.icon,
                contentDescription = page.title,
                tint = page.color,
                modifier = Modifier.size(60.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
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
                shape = CircleShape
            )
    )
}

data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)
