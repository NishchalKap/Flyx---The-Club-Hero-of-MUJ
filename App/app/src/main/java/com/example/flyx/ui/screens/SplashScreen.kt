package com.example.flyx.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.flyx.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }
    
    // Enhanced animations
    val infiniteTransition = rememberInfiniteTransition(label = "splash")
    val logoScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logoScale"
    )
    
    val floatAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )
    
    val rotationAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    val pulseAnimation by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
        delay(3000) // Show splash for 3 seconds
        onSplashFinished()
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        UniBlue.copy(alpha = 0.9f),
                        UniPurple.copy(alpha = 0.8f),
                        UniGreen.copy(alpha = 0.7f),
                        UniGray50
                    )
                )
            )
    ) {
        // Floating decorative elements
        FloatingElement(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 50.dp, y = 100.dp + floatAnimation.dp)
                .scale(pulseAnimation),
            color = Color.White.copy(alpha = 0.2f),
            size = 80.dp
        )
        
        FloatingElement(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-60).dp, y = 150.dp + floatAnimation.dp)
                .scale(pulseAnimation * 0.8f),
            color = Color.White.copy(alpha = 0.15f),
            size = 60.dp
        )
        
        FloatingElement(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 40.dp, y = (-100).dp - floatAnimation.dp)
                .scale(pulseAnimation * 0.6f),
            color = Color.White.copy(alpha = 0.1f),
            size = 100.dp
        )
        
        FloatingElement(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-50).dp, y = (-80).dp + floatAnimation.dp)
                .scale(pulseAnimation * 0.7f),
            color = Color.White.copy(alpha = 0.12f),
            size = 70.dp
        )
        
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated logo
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.9f),
                                Color.White.copy(alpha = 0.7f)
                            )
                        ),
                        shape = CircleShape
                    )
                    .scale(logoScale),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "UC",
                    style = MaterialTheme.typography.displayLarge,
                    color = UniBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // App name with animation
            Text(
                text = "UniConnect",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Connect • Discover • Experience",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Loading indicator
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = Color.White,
                strokeWidth = 3.dp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Loading your campus experience...",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
        
        // Bottom branding
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Made with ❤️ for University Students",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
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
