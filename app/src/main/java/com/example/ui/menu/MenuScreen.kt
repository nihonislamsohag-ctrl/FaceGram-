package com.example.ui.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Send
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.model.User
import com.example.ui.theme.FaceGramBlue
import com.example.ui.theme.FaceGramDarkBg
import com.example.ui.theme.FaceGramDarkCard
import com.example.ui.theme.FaceGramDarkInput

// Feature card definition
data class MenuFeatureItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val iconTint: Color,
    val badgeText: String? = null,
    val badgeColor: Color = Color(0xFFE11D48)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    user: User,
    onBackClick: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToMessages: () -> Unit,
    onNavigateToReels: () -> Unit,
    onNavigateToFeeds: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var activeDialogId by remember { mutableStateOf<String?>(null) }
    var isSettingsExpanded by remember { mutableStateOf(false) }
    var isHelpExpanded by remember { mutableStateOf(false) }
    var showSwitchAccountDialog by remember { mutableStateOf(false) }
    var showInviteFriendsDialog by remember { mutableStateOf(false) }
    var showAddAccountDialog by remember { mutableStateOf(false) }

    // 14 feature items matching both user screenshots exactly
    val featureItems = remember {
        listOf(
            MenuFeatureItem("dashboard", "Dashboard", Icons.Default.BarChart, Color(0xFF0284C7)),
            MenuFeatureItem("messages", "Messages", Icons.Default.ChatBubble, Color(0xFF2563EB)),
            MenuFeatureItem("groups", "Groups", Icons.Default.Groups, Color(0xFF16A34A)),
            MenuFeatureItem("marketplace", "Marketplace", Icons.Default.Storefront, Color(0xFF0EA5E9)),
            MenuFeatureItem("friends", "Friends", Icons.Default.People, Color(0xFF2563EB)),
            MenuFeatureItem("reels", "Reels", Icons.Default.MovieCreation, Color(0xFFEC4899)),
            MenuFeatureItem("ai_assistant", "Fida AI", Icons.Default.AutoAwesome, Color(0xFF38BDF8), badgeText = "নতুন", badgeColor = Color(0xFFE11D48)),
            MenuFeatureItem("pages", "Pages", Icons.Default.Flag, Color(0xFFF97316)),
            MenuFeatureItem("saved", "Saved", Icons.Default.Bookmark, Color(0xFF9333EA)),
            MenuFeatureItem("memories", "Memories", Icons.Default.History, Color(0xFF06B6D4)),
            MenuFeatureItem("birthdays", "Birthdays", Icons.Default.CardGiftcard, Color(0xFF0284C7)),
            MenuFeatureItem("events", "Events", Icons.Default.CalendarMonth, Color(0xFFF43F5E)),
            MenuFeatureItem("verified", "Fida Verified", Icons.Default.Verified, Color(0xFF2563EB), badgeText = "সক্রিয় 🟢", badgeColor = Color(0xFF047857)),
            MenuFeatureItem("feeds", "Feeds", Icons.Default.DynamicFeed, Color(0xFFEA580C))
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FaceGramDarkBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("menu_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Menu",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { showSwitchAccountDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = "Switch profile",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
                IconButton(onClick = { activeDialogId = "search_menu" }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search menu",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            contentPadding = PaddingValues(bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Card 1: User Profile Header Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onNavigateToProfile)
                        .testTag("menu_profile_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box {
                            Image(
                                painter = painterResource(id = user.avatarResId ?: R.drawable.avatar_niyon),
                                contentDescription = "Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                            )
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Verified",
                                tint = Color(0xFF22C55E),
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(16.dp)
                                    .background(FaceGramDarkCard, CircleShape)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = user.fullName,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified badge",
                                    tint = FaceGramBlue,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "View your profile",
                                color = Color(0xFF94A3B8),
                                fontSize = 13.sp
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expand",
                            tint = Color(0xFF94A3B8)
                        )
                    }
                }
            }

            // Card 2: Switch Account Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showSwitchAccountDialog = true }
                        .testTag("menu_switch_account_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF2563EB)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(14.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF16A34A)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SwapHoriz,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(10.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(
                            text = "Switch account",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 15.sp,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color(0xFF94A3B8)
                        )
                    }
                }
            }

            // Card 3: Invite Friends Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showInviteFriendsDialog = true }
                        .testTag("menu_invite_friends_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "❤️",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 6.dp, end = 4.dp)
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(
                            text = "Invite friends",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 15.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Section Header: সব ফিচারসমূহ
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "সব ফিচারসমূহ",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF94A3B8),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            // 2-Column Grid of 14 Feature Cards
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (pair in featureItems.chunked(2)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            pair.forEach { feature ->
                                FeatureGridCard(
                                    item = feature,
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        when (feature.id) {
                                            "messages" -> onNavigateToMessages()
                                            "reels" -> onNavigateToReels()
                                            "feeds" -> onNavigateToFeeds()
                                            else -> activeDialogId = feature.id
                                        }
                                    }
                                )
                            }
                            if (pair.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            // Right Manager Fida Card
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { activeDialogId = "right_manager" }
                        .testTag("menu_right_manager_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🌸",
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "Right Manager Fida",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 15.sp,
                            modifier = Modifier.weight(1f)
                        )

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFE11D48)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("🌸", fontSize = 10.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Admin",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Accordion Card: Settings, Help, Add Account, Log Out
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Settings & privacy item
                        AccordionItemRow(
                            icon = Icons.Default.Settings,
                            title = "Settings & privacy",
                            isExpanded = isSettingsExpanded,
                            onToggle = { isSettingsExpanded = !isSettingsExpanded }
                        )

                        AnimatedVisibility(visible = isSettingsExpanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(FaceGramDarkInput)
                                    .padding(start = 48.dp, end = 16.dp, top = 4.dp, bottom = 8.dp)
                            ) {
                                listOf(
                                    "Privacy checkup",
                                    "Password and security",
                                    "Dark mode: On",
                                    "Language: বাংলা (Bangladesh)"
                                ).forEach { sub ->
                                    Text(
                                        text = sub,
                                        color = Color(0xFFCBD5E1),
                                        fontSize = 13.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { activeDialogId = "settings_sub" }
                                            .padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }

                        HorizontalDivider(color = Color(0xFF1E293D), thickness = 0.5.dp)

                        // Help & support item
                        AccordionItemRow(
                            icon = Icons.Default.HelpOutline,
                            title = "Help & support",
                            isExpanded = isHelpExpanded,
                            onToggle = { isHelpExpanded = !isHelpExpanded }
                        )

                        AnimatedVisibility(visible = isHelpExpanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(FaceGramDarkInput)
                                    .padding(start = 48.dp, end = 16.dp, top = 4.dp, bottom = 8.dp)
                            ) {
                                listOf(
                                    "Help Center",
                                    "Report a Problem",
                                    "Safety & Community Standards",
                                    "FaceGram Terms and Policies"
                                ).forEach { sub ->
                                    Text(
                                        text = sub,
                                        color = Color(0xFFCBD5E1),
                                        fontSize = 13.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { activeDialogId = "help_sub" }
                                            .padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }

                        HorizontalDivider(color = Color(0xFF1E293D), thickness = 0.5.dp)

                        // Add account item
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showAddAccountDialog = true }
                                .padding(horizontal = 14.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = "Add account",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                text = "Add account",
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }

                        HorizontalDivider(color = Color(0xFF1E293D), thickness = 0.5.dp)

                        // Log out item
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = onLogoutClick)
                                .padding(horizontal = 14.dp, vertical = 14.dp)
                                .testTag("menu_logout_button"),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Log out",
                                tint = Color(0xFFEF4444),
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                text = "Log out",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFEF4444),
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }

            // Footer
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "FaceFeed",
                        fontWeight = FontWeight.ExtraBold,
                        color = FaceGramBlue,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Version 2.4.0 • Made with ❤️ for Bangladesh 🇧🇩",
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    // Interactive Dialogs for each feature
    when (activeDialogId) {
        "dashboard" -> CreatorDashboardDialog(onDismiss = { activeDialogId = null })
        "groups" -> GroupsViewerDialog(onDismiss = { activeDialogId = null })
        "marketplace" -> MarketplaceViewerDialog(onDismiss = { activeDialogId = null })
        "friends" -> FriendsViewerDialog(onDismiss = { activeDialogId = null })
        "ai_assistant" -> AiAssistantDialog(onDismiss = { activeDialogId = null })
        "pages" -> PagesViewerDialog(onDismiss = { activeDialogId = null })
        "saved" -> SavedItemsViewerDialog(onDismiss = { activeDialogId = null })
        "memories" -> MemoriesViewerDialog(onDismiss = { activeDialogId = null })
        "birthdays" -> BirthdaysViewerDialog(onDismiss = { activeDialogId = null })
        "events" -> EventsViewerDialog(onDismiss = { activeDialogId = null })
        "verified" -> VerifiedStatusDialog(onDismiss = { activeDialogId = null })
        "right_manager" -> RightManagerDialog(onDismiss = { activeDialogId = null })
        "settings_sub" -> SettingsInfoDialog(title = "Settings & Privacy", onDismiss = { activeDialogId = null })
        "help_sub" -> SettingsInfoDialog(title = "Help & Support", onDismiss = { activeDialogId = null })
        "search_menu" -> SearchMenuDialog(items = featureItems, onDismiss = { activeDialogId = null })
    }

    if (showSwitchAccountDialog) {
        SwitchAccountDialog(
            currentUser = user,
            onDismiss = { showSwitchAccountDialog = false }
        )
    }

    if (showInviteFriendsDialog) {
        InviteFriendsDialog(onDismiss = { showInviteFriendsDialog = false })
    }

    if (showAddAccountDialog) {
        AddAccountDialog(onDismiss = { showAddAccountDialog = false })
    }
}

@Composable
fun FeatureGridCard(
    item: MenuFeatureItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(84.dp)
            .clickable(onClick = onClick)
            .testTag("menu_item_${item.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = item.iconTint,
                    modifier = Modifier.size(24.dp)
                )

                item.badgeText?.let { badge ->
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = item.badgeColor
                    ) {
                        Text(
                            text = badge,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Text(
                text = item.title,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun AccordionItemRow(
    icon: ImageVector,
    title: String,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    val rotation by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "rot")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.White,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Expand",
            tint = Color(0xFF94A3B8),
            modifier = Modifier.rotate(rotation)
        )
    }
}

// Dialog Implementations for all features

@Composable
fun CreatorDashboardDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Professional Dashboard", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text("Account performance in the last 30 days:", color = Color(0xFF94A3B8), fontSize = 13.sp)
                Spacer(modifier = Modifier.height(12.dp))

                listOf(
                    Pair("Total Impressions", "17.4K (+24%)"),
                    Pair("Post & Reel Reach", "8,920 (+18%)"),
                    Pair("New Followers", "+142 this week"),
                    Pair("Engagement Rate", "8.7% (Very High)")
                ).forEach { (stat, value) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(stat, color = Color.White, fontSize = 14.sp)
                        Text(value, color = Color(0xFF22C55E), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    HorizontalDivider(color = Color(0xFF1E293D))
                }
            }
        }
    }
}

@Composable
fun GroupsViewerDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Your Groups", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(10.dp))

                val groups = listOf(
                    Pair("FaceGram Creators 🇧🇩", "14.2K members • Active now"),
                    Pair("Tech Adda Dhaka", "8.9K members • 24 posts today"),
                    Pair("Photography & Video Makers BD", "32.1K members • 5 new posts")
                )
                groups.forEach { (name, info) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Groups, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(info, color = Color(0xFF94A3B8), fontSize = 12.sp)
                        }
                    }
                    HorizontalDivider(color = Color(0xFF1E293D))
                }
            }
        }
    }
}

@Composable
fun MarketplaceViewerDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("FaceGram Marketplace", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(10.dp))

                listOf(
                    Pair("Sony Alpha A7 IV Camera", "৳ 1,85,000 • Dhaka"),
                    Pair("iPhone 15 Pro Max 256GB", "৳ 1,22,000 • Gulshan"),
                    Pair("MacBook Pro M3 14-inch", "৳ 1,95,000 • Banani")
                ).forEach { (item, price) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = Color(0xFF0EA5E9), modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Text(price, color = Color(0xFF22C55E), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Button(onClick = onDismiss, contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp), modifier = Modifier.height(30.dp)) {
                            Text("Contact", fontSize = 11.sp)
                        }
                    }
                    HorizontalDivider(color = Color(0xFF1E293D))
                }
            }
        }
    }
}

@Composable
fun FriendsViewerDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Friends (1,420)", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(10.dp))
                listOf("Maria Nur", "Sohini Sarkar", "Ramisa Rahman", "Tanvir Ahmed").forEach { friend ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.avatar_niyon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(friend, color = Color.White, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                        Text("Message", color = FaceGramBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    HorizontalDivider(color = Color(0xFF1E293D))
                }
            }
        }
    }
}

@Composable
fun AiAssistantDialog(onDismiss: () -> Unit) {
    var prompt by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("হ্যালো! আমি FaceGram AI। আপনার কি ধরনের সহায়তা প্রয়োজন? (পোস্ট ক্যাপশন, বায়ো, অথবা নতুন আইডিয়া)") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFF38BDF8))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Fida AI Assistant", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    }
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = FaceGramDarkInput,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = response,
                        color = Color.White,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(12.dp),
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Button(
                        onClick = {
                            response = "✨ সেরা ক্যাপশন আইডিয়া:\n'জীবনের প্রতিটি মুহূর্ত সুন্দর, শুধু দেখার চোখ থাকা চাই ❤️ #FaceGram #Dhaka #Vibes'"
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293D)),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text("ক্যাপশন লিখুন", fontSize = 11.sp)
                    }
                    Button(
                        onClick = {
                            response = "🚀 প্রোফাইল বায়ো:\n'Dreamer • Content Creator 🇧🇩\nSpreading positivity everywhere ✨\nWelcome to my FaceGram!'"
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293D)),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text("বায়ো তৈরি", fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = prompt,
                        onValueChange = { prompt = it },
                        placeholder = { Text("Ask AI anything...", color = Color(0xFF64748B), fontSize = 13.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        shape = RoundedCornerShape(23.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = FaceGramDarkInput,
                            unfocusedContainerColor = FaceGramDarkInput,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(onClick = {
                        if (prompt.isNotBlank()) {
                            response = "আপনার প্রশ্ন '$prompt' এর উত্তর:\nFaceGram এ দারুণ কন্টেন্ট বানানোর জন্য নিয়মিত রিলস ও স্টোরিজ পোস্ট করুন! 🚀"
                            prompt = ""
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = FaceGramBlue)
                    }
                }
            }
        }
    }
}

@Composable
fun PagesViewerDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Your Pages (2)", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(10.dp))
                listOf(
                    Pair("Niyon Tech Official", "48.2K followers • Technology"),
                    Pair("FaceFeed Bangladesh", "124.5K followers • Media")
                ).forEach { (page, sub) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Flag, contentDescription = null, tint = Color(0xFFF97316), modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(page, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(sub, color = Color(0xFF94A3B8), fontSize = 12.sp)
                        }
                    }
                    HorizontalDivider(color = Color(0xFF1E293D))
                }
            }
        }
    }
}

@Composable
fun SavedItemsViewerDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Saved Items (12)", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(10.dp))
                listOf("Reel by @ramisa25096", "Photo by @arishba.rahman_", "Tech updates 2026").forEach { title ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Bookmark, contentDescription = null, tint = Color(0xFF9333EA))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(title, color = Color.White, fontSize = 13.sp, modifier = Modifier.weight(1f))
                    }
                    HorizontalDivider(color = Color(0xFF1E293D))
                }
            }
        }
    }
}

@Composable
fun MemoriesViewerDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("On This Day", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text("1 year ago today:", color = Color(0xFF06B6D4), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Image(
                    painter = painterResource(id = R.drawable.feed_post),
                    contentDescription = "Memory photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Wonderful memories in Dhaka! ❤️", color = Color.White, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun BirthdaysViewerDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Birthdays Today 🎉", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(10.dp))
                listOf(
                    Pair("Maria Nur", "Celebrating 22nd birthday today! 🎂"),
                    Pair("Tanvir Ahmed", "Birthday tomorrow, October 14th")
                ).forEach { (name, note) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CardGiftcard, contentDescription = null, tint = Color(0xFF0284C7))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(note, color = Color(0xFF94A3B8), fontSize = 12.sp)
                        }
                    }
                    HorizontalDivider(color = Color(0xFF1E293D))
                }
            }
        }
    }
}

@Composable
fun EventsViewerDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Upcoming Events", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(10.dp))
                listOf(
                    Pair("Bangladesh Creator Summit 2026", "Oct 25 • ICCB Dhaka"),
                    Pair("Tech & AI Meetup 2026", "Nov 02 • Dhanmondi")
                ).forEach { (event, date) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Color(0xFFF43F5E))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(event, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(date, color = Color(0xFF94A3B8), fontSize = 11.sp)
                        }
                    }
                    HorizontalDivider(color = Color(0xFF1E293D))
                }
            }
        }
    }
}

@Composable
fun VerifiedStatusDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Verified, contentDescription = null, tint = Color(0xFF2563EB))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Fida Verified Status", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    }
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Surface(color = Color(0xFF064E3B), shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "স্ট্যাটাস: সক্রিয় (Active Verified Creator) 🟢",
                        color = Color(0xFF6EE7B7),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text("• Verified blue checkmark active on profile & reels\n• Increased account protection against impersonation\n• Direct priority support channel\n• Exclusive Creator stickers & badges", color = Color(0xFFCBD5E1), fontSize = 12.sp, lineHeight = 18.sp)
            }
        }
    }
}

@Composable
fun RightManagerDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🌸", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Right Manager Fida", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    }
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text("Content Protection & Rights Ownership", color = Color(0xFF94A3B8), fontSize = 13.sp)
                Spacer(modifier = Modifier.height(12.dp))
                listOf(
                    Pair("Original Audio Protection", "100% Protected"),
                    Pair("Video Fingerprints Registered", "12 files active"),
                    Pair("Detected Matches / Violations", "0 found"),
                    Pair("Copyright Policy Compliance", "Standard (Good standing)")
                ).forEach { (k, v) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(k, color = Color.White, fontSize = 12.sp)
                        Text(v, color = Color(0xFF22C55E), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                    HorizontalDivider(color = Color(0xFF1E293D))
                }
            }
        }
    }
}

@Composable
fun SettingsInfoDialog(title: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text("All privacy and security controls are active for your account.", color = Color(0xFFCBD5E1), fontSize = 13.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                    Text("OK")
                }
            }
        }
    }
}

@Composable
fun SearchMenuDialog(items: List<MenuFeatureItem>, onDismiss: () -> Unit) {
    var query by remember { mutableStateOf("") }
    val filtered = remember(query) {
        if (query.isBlank()) items else items.filter { it.title.contains(query, ignoreCase = true) }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Search Features", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("Search...", color = Color(0xFF94A3B8)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(modifier = Modifier.heightIn(max = 240.dp)) {
                    items(filtered) { itm ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onDismiss() }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(itm.icon, contentDescription = null, tint = itm.iconTint)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(itm.title, color = Color.White, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SwitchAccountDialog(currentUser: User, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Switch Account", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(10.dp))
                // Account 1
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDismiss() }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = currentUser.avatarResId ?: R.drawable.avatar_niyon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(currentUser.fullName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("@${currentUser.username} • Active", color = Color(0xFF22C55E), fontSize = 12.sp)
                    }
                    Icon(Icons.Default.Check, contentDescription = null, tint = FaceGramBlue)
                }

                HorizontalDivider(color = Color(0xFF1E293D))

                // Account 2
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDismiss() }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facegram_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Niyon Tech Page", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("@niyon_tech • Creator Page", color = Color(0xFF94A3B8), fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Log into another account")
                }
            }
        }
    }
}

@Composable
fun InviteFriendsDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Invite Friends ❤️", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Share FaceGram with your friends and family in Bangladesh!",
                    color = Color(0xFFCBD5E1),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    color = FaceGramDarkInput,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "https://facegram.bd/invite/niyonislamsohag",
                        color = FaceGramBlue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                    Text("Copy Link & Share")
                }
            }
        }
    }
}

@Composable
fun AddAccountDialog(onDismiss: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FaceGramDarkCard)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Add Account", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null, tint = Color.White) }
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email or Username", color = Color(0xFF94A3B8)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = Color(0xFF94A3B8)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                    Text("Log In")
                }
            }
        }
    }
}
