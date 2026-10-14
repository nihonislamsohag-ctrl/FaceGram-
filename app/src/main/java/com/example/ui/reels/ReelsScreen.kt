package com.example.ui.reels

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Reel
import com.example.ui.theme.FaceGramBlue

@Composable
fun ReelsScreen(
    reels: List<Reel>,
    onLikeReel: (String) -> Unit,
    onFollowUser: (String) -> Unit,
    onBookmarkReel: (String) -> Unit,
    onCommentsClick: (String) -> Unit,
    onAddReelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentReelIndex by remember { mutableStateOf(0) }
    var selectedTab by remember { mutableStateOf("Reels") } // "Reels" or "Friends"

    if (reels.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text("No reels available", color = Color.White)
        }
        return
    }

    val currentReel = reels[currentReelIndex % reels.size]

    // Video playback simulation (auto progress)
    val infiniteTransition = rememberInfiniteTransition(label = "video_playback")
    val playbackProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )

    val discRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "disc"
    )

    var isPaused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(reels.size) {
                detectVerticalDragGestures { _, dragAmount ->
                    if (dragAmount < -40) {
                        // Swipe Up -> Next Reel
                        if (currentReelIndex < reels.size - 1) {
                            currentReelIndex++
                        } else {
                            currentReelIndex = 0
                        }
                    } else if (dragAmount > 40) {
                        // Swipe Down -> Prev Reel
                        if (currentReelIndex > 0) {
                            currentReelIndex--
                        } else {
                            currentReelIndex = reels.size - 1
                        }
                    }
                }
            }
            .clickable { isPaused = !isPaused }
            .testTag("reels_screen")
    ) {
        // Video Backdrop Image
        Image(
            painter = painterResource(id = currentReel.imageResId ?: R.drawable.reel_cover),
            contentDescription = "Reel video",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient Vignette for readable text
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.5f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.85f)
                        ),
                        startY = 0f,
                        endY = 1600f
                    )
                )
        )

        // Paused Indicator
        if (isPaused) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Paused",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        // Top Header (Reels | Friends tabs)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onAddReelClick,
                modifier = Modifier.testTag("add_reel_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Reel",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Reels",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedTab == "Reels") Color.White else Color.White.copy(alpha = 0.5f),
                    modifier = Modifier
                        .clickable { selectedTab = "Reels" }
                        .padding(horizontal = 8.dp)
                )
                Text(
                    text = " | ",
                    color = Color.White.copy(alpha = 0.3f),
                    fontSize = 16.sp
                )
                Text(
                    text = "Friends",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedTab == "Friends") Color.White else Color.White.copy(alpha = 0.5f),
                    modifier = Modifier
                        .clickable { selectedTab = "Friends" }
                        .padding(horizontal = 8.dp)
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Camera",
                    tint = Color.White
                )
            }
        }

        // Right Action Rail (Likes, Comments, Share, Bookmark, Audio)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Like
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onLikeReel(currentReel.id) }
                    .testTag("like_reel_${currentReel.id}")
            ) {
                Icon(
                    imageVector = if (currentReel.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (currentReel.isLiked) Color(0xFFEF4444) else Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = currentReel.likesCount,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Comment
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onCommentsClick(currentReel.id) }
                    .testTag("comment_reel_${currentReel.id}")
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChatBubbleOutline,
                    contentDescription = "Comment",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = currentReel.commentsCount,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Share / Send
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "Share",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = currentReel.sharesCount,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Bookmark
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onBookmarkReel(currentReel.id) }
            ) {
                Icon(
                    imageVector = if (currentReel.isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = if (currentReel.isBookmarked) FaceGramBlue else Color.White,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = currentReel.bookmarksCount,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // More Options
            IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = Color.White
                )
            }

            // Rotating Music Disc
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, Color.White, CircleShape)
                    .rotate(if (isPaused) 0f else discRotation),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.facegram_logo),
                    contentDescription = "Audio Disc",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Bottom Left Creator Info & Caption
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.78f)
                .padding(start = 14.dp, bottom = 40.dp)
        ) {
            // Creator info row
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = currentReel.avatarResId ?: R.drawable.reel_cover),
                    contentDescription = currentReel.username,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = currentReel.username,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.width(10.dp))

                // Follow Button
                OutlinedButton(
                    onClick = { onFollowUser(currentReel.id) },
                    modifier = Modifier.height(30.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (currentReel.isFollowing) Color.White.copy(alpha = 0.2f) else Color.Transparent,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(listOf(Color.White, Color.White))
                    )
                ) {
                    Text(
                        text = if (currentReel.isFollowing) "Following" else "Follow",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Caption
            Text(
                text = currentReel.caption,
                color = Color.White,
                fontSize = 13.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Audio ticker
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = currentReel.audioTitle,
                    color = Color.White,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Bottom Video Progress Bar
        LinearProgressIndicator(
            progress = { if (isPaused) 0.5f else playbackProgress },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(2.dp),
            color = FaceGramBlue,
            trackColor = Color.White.copy(alpha = 0.2f)
        )
    }
}
