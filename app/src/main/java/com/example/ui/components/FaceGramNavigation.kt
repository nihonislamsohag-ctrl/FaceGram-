package com.example.ui.components

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.BottomTab
import com.example.ui.theme.FaceGramBlue
import com.example.ui.theme.FaceGramDarkBg
import com.example.ui.theme.FaceGramLogoBrush

@Composable
fun FaceGramTopBar(
    onAddClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onDirectMessagesClick: () -> Unit
) {
    Surface(
        color = FaceGramDarkBg,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onAddClick,
                modifier = Modifier.testTag("top_add_button")
            ) {
                Icon(
                    imageVector = Icons.Default.AddBox,
                    contentDescription = "Create post",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.facegram_logo),
                    contentDescription = "FaceGram Logo",
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(6.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "FaceGram",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                BadgedBox(
                    badge = {
                        Badge(
                            containerColor = Color(0xFFEF4444),
                            modifier = Modifier.offset(x = (-4).dp, y = 4.dp)
                        ) {
                            Text("3", color = Color.White, fontSize = 10.sp)
                        }
                    }
                ) {
                    IconButton(
                        onClick = onNotificationsClick,
                        modifier = Modifier.testTag("top_notifications_button")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Notifications",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                BadgedBox(
                    badge = {
                        Badge(
                            containerColor = FaceGramBlue,
                            modifier = Modifier.offset(x = (-4).dp, y = 4.dp)
                        ) {
                            Text("2", color = Color.White, fontSize = 10.sp)
                        }
                    }
                ) {
                    IconButton(
                        onClick = onDirectMessagesClick,
                        modifier = Modifier.testTag("top_messages_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Direct Messages",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FaceGramBottomBar(
    currentTab: BottomTab,
    avatarResId: Int?,
    onSelectTab: (BottomTab) -> Unit
) {
    Surface(
        color = FaceGramDarkBg,
        tonalElevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home
            IconButton(
                onClick = { onSelectTab(BottomTab.HOME) },
                modifier = Modifier.testTag("bottom_tab_home")
            ) {
                Icon(
                    imageVector = if (currentTab == BottomTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home",
                    tint = if (currentTab == BottomTab.HOME) Color.White else Color(0xFF94A3B8),
                    modifier = Modifier.size(28.dp)
                )
            }

            // Search
            IconButton(
                onClick = { onSelectTab(BottomTab.SEARCH) },
                modifier = Modifier.testTag("bottom_tab_search")
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = if (currentTab == BottomTab.SEARCH) Color.White else Color(0xFF94A3B8),
                    modifier = Modifier.size(28.dp)
                )
            }

            // Reels / Video
            IconButton(
                onClick = { onSelectTab(BottomTab.REELS) },
                modifier = Modifier.testTag("bottom_tab_reels")
            ) {
                Icon(
                    imageVector = if (currentTab == BottomTab.REELS) Icons.Filled.PlayCircleFilled else Icons.Outlined.PlayCircleOutline,
                    contentDescription = "Reels",
                    tint = if (currentTab == BottomTab.REELS) Color.White else Color(0xFF94A3B8),
                    modifier = Modifier.size(28.dp)
                )
            }

            // Profile
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .border(
                        width = if (currentTab == BottomTab.PROFILE) 2.dp else 0.dp,
                        color = if (currentTab == BottomTab.PROFILE) Color.White else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { onSelectTab(BottomTab.PROFILE) }
                    .testTag("bottom_tab_profile")
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = avatarResId ?: R.drawable.avatar_niyon),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
        }
    }
}
