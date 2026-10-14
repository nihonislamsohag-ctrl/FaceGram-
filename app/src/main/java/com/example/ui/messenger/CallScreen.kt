package com.example.ui.messenger

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ActiveCall
import com.example.data.model.CallType
import com.example.ui.theme.FaceGramBlue
import com.example.ui.theme.FaceGramDarkBg
import com.example.ui.theme.FaceGramStoryBrush

@Composable
fun CallScreen(
    call: ActiveCall,
    onEndCall: () -> Unit,
    onToggleMute: () -> Unit,
    onToggleSpeaker: () -> Unit,
    onToggleVideo: () -> Unit,
    onFlipCamera: () -> Unit,
    modifier: Modifier = Modifier
) {
    var callSeconds by remember { mutableStateOf(0) }

    LaunchedEffect(call.callId) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            callSeconds++
        }
    }

    val callTimeFormatted = remember(callSeconds) {
        val mins = callSeconds / 60
        val secs = callSeconds % 60
        "%02d:%02d".format(mins, secs)
    }

    when (call.type) {
        CallType.AUDIO -> {
            AudioCallView(
                call = call,
                timeFormatted = callTimeFormatted,
                onEndCall = onEndCall,
                onToggleMute = onToggleMute,
                onToggleSpeaker = onToggleSpeaker,
                onToggleVideo = onToggleVideo,
                modifier = modifier
            )
        }
        CallType.VIDEO -> {
            VideoCallView(
                call = call,
                timeFormatted = callTimeFormatted,
                onEndCall = onEndCall,
                onToggleMute = onToggleMute,
                onToggleVideo = onToggleVideo,
                onFlipCamera = onFlipCamera,
                modifier = modifier
            )
        }
        CallType.GROUP_VIDEO -> {
            GroupVideoCallView(
                call = call,
                timeFormatted = callTimeFormatted,
                onEndCall = onEndCall,
                onToggleMute = onToggleMute,
                onFlipCamera = onFlipCamera,
                modifier = modifier
            )
        }
    }
}

@Composable
fun AudioCallView(
    call: ActiveCall,
    timeFormatted: String,
    onEndCall: () -> Unit,
    onToggleMute: () -> Unit,
    onToggleSpeaker: () -> Unit,
    onToggleVideo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "avatarPulse"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F172A), Color(0xFF1E1B4B), Color(0xFF0B0F19))
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Text(
                    text = "FaceGram Audio Call",
                    color = FaceGramBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = call.title,
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Connected • $timeFormatted",
                    color = Color(0xFF94A3B8),
                    fontSize = 15.sp
                )
            }

            // Pulsing Avatar
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(200.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(170.dp)
                        .scale(pulseScale)
                        .clip(CircleShape)
                        .background(FaceGramBlue.copy(alpha = 0.2f))
                )
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(FaceGramStoryBrush)
                        .padding(3.dp)
                ) {
                    Image(
                        painter = painterResource(id = call.avatarResId ?: R.drawable.avatar_niyon),
                        contentDescription = "Caller",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
            }

            // Controls
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 30.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Mute Button
                    CallActionButton(
                        icon = if (call.isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                        label = if (call.isMuted) "Unmute" else "Mute",
                        isActive = call.isMuted,
                        onClick = onToggleMute,
                        testTag = "call_mute_button"
                    )

                    // Speaker Button
                    CallActionButton(
                        icon = if (call.isSpeakerOn) Icons.Default.VolumeUp else Icons.Default.VolumeDown,
                        label = "Speaker",
                        isActive = call.isSpeakerOn,
                        onClick = onToggleSpeaker,
                        testTag = "call_speaker_button"
                    )

                    // Switch to Video
                    CallActionButton(
                        icon = Icons.Default.Videocam,
                        label = "Video",
                        isActive = false,
                        onClick = onToggleVideo,
                        testTag = "call_video_toggle_button"
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))

                // End Call Red Button
                FloatingActionButton(
                    onClick = onEndCall,
                    containerColor = Color(0xFFEF4444),
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(68.dp)
                        .testTag("end_call_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.CallEnd,
                        contentDescription = "End Call",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VideoCallView(
    call: ActiveCall,
    timeFormatted: String,
    onEndCall: () -> Unit,
    onToggleMute: () -> Unit,
    onToggleVideo: () -> Unit,
    onFlipCamera: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Full screen Remote Video
        Image(
            painter = painterResource(id = call.avatarResId ?: R.drawable.reel_cover),
            contentDescription = "Remote video",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Vignette
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.6f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
        )

        // Top info bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = call.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "HD Video • $timeFormatted",
                    color = Color(0xFF22C55E),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            IconButton(onClick = onFlipCamera) {
                Icon(
                    imageVector = Icons.Default.FlipCameraAndroid,
                    contentDescription = "Flip Camera",
                    tint = Color.White
                )
            }
        }

        // Picture-in-picture Local User Camera
        Card(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 90.dp, end = 16.dp)
                .size(width = 110.dp, height = 160.dp),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.avatar_niyon),
                    contentDescription = "Local Camera",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = "You",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(topEnd = 6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        // Bottom Controls
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CallActionButton(
                icon = if (call.isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                label = if (call.isMuted) "Unmute" else "Mute",
                isActive = call.isMuted,
                onClick = onToggleMute,
                testTag = "video_call_mute"
            )

            FloatingActionButton(
                onClick = onEndCall,
                containerColor = Color(0xFFEF4444),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .size(64.dp)
                    .testTag("end_video_call_button")
            ) {
                Icon(
                    imageVector = Icons.Default.CallEnd,
                    contentDescription = "End Call",
                    modifier = Modifier.size(30.dp)
                )
            }

            CallActionButton(
                icon = if (call.isVideoEnabled) Icons.Default.Videocam else Icons.Default.VideocamOff,
                label = "Camera",
                isActive = !call.isVideoEnabled,
                onClick = onToggleVideo,
                testTag = "video_call_cam_toggle"
            )
        }
    }
}

@Composable
fun GroupVideoCallView(
    call: ActiveCall,
    timeFormatted: String,
    onEndCall: () -> Unit,
    onToggleMute: () -> Unit,
    onFlipCamera: () -> Unit,
    modifier: Modifier = Modifier
) {
    val participants = listOf(
        Pair("Niyon Islam Sohag (You)", R.drawable.avatar_niyon),
        Pair("Maria Nur", R.drawable.feed_post),
        Pair("Ramisa", R.drawable.reel_cover),
        Pair("Sohini Sarkar", R.drawable.facegram_logo)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0B0F19))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Group Call Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = call.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
                Text(
                    text = "Group Video Call • $timeFormatted (4 in call)",
                    color = Color(0xFF22C55E),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            IconButton(onClick = onFlipCamera) {
                Icon(
                    imageVector = Icons.Default.FlipCameraAndroid,
                    contentDescription = "Flip",
                    tint = Color.White
                )
            }
        }

        // 2x2 Grid of Video Call Participants
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Row 1
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GroupParticipantBox(
                    name = participants[0].first,
                    imageResId = participants[0].second,
                    isSpeaking = false,
                    modifier = Modifier.weight(1f)
                )
                GroupParticipantBox(
                    name = participants[1].first,
                    imageResId = participants[1].second,
                    isSpeaking = true,
                    modifier = Modifier.weight(1f)
                )
            }

            // Row 2
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GroupParticipantBox(
                    name = participants[2].first,
                    imageResId = participants[2].second,
                    isSpeaking = false,
                    modifier = Modifier.weight(1f)
                )
                GroupParticipantBox(
                    name = participants[3].first,
                    imageResId = participants[3].second,
                    isSpeaking = false,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Bottom Group Call Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CallActionButton(
                icon = if (call.isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                label = if (call.isMuted) "Unmute" else "Mute",
                isActive = call.isMuted,
                onClick = onToggleMute,
                testTag = "group_mute_button"
            )

            FloatingActionButton(
                onClick = onEndCall,
                containerColor = Color(0xFFEF4444),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .size(64.dp)
                    .testTag("end_group_call_button")
            ) {
                Icon(
                    imageVector = Icons.Default.CallEnd,
                    contentDescription = "Leave Call",
                    modifier = Modifier.size(30.dp)
                )
            }

            CallActionButton(
                icon = Icons.Default.PersonAdd,
                label = "Add",
                isActive = false,
                onClick = {},
                testTag = "group_add_person_button"
            )
        }
    }
}

@Composable
fun GroupParticipantBox(
    name: String,
    imageResId: Int,
    isSpeaking: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxHeight()
            .border(
                width = if (isSpeaking) 2.5.dp else 1.dp,
                color = if (isSpeaking) Color(0xFF22C55E) else Color(0xFF1E293D),
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.25f))
            )

            // Name badge
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 6.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSpeaking) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF22C55E))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = name,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun CallActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
    testTag: String = ""
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(if (isActive) Color.White else Color.White.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isActive) Color.Black else Color.White,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
