package com.example.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.*
import com.example.ui.auth.AuthScreen
import com.example.ui.components.*
import com.example.ui.feed.FeedScreen
import com.example.ui.menu.MenuScreen
import com.example.ui.messenger.CallScreen
import com.example.ui.messenger.ChatDetailScreen
import com.example.ui.messenger.MessengerScreen
import com.example.ui.profile.ProfileScreen
import com.example.ui.reels.ReelsScreen
import com.example.ui.search.SearchScreen
import com.example.ui.theme.FaceGramDarkBg

@Composable
fun FaceGramApp(
    viewModel: FaceGramViewModel = viewModel()
) {
    val context = LocalContext.current
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()

    val stories by viewModel.stories.collectAsStateWithLifecycle()
    val posts by viewModel.posts.collectAsStateWithLifecycle()
    val reels by viewModel.reels.collectAsStateWithLifecycle()
    val exploreItems by viewModel.exploreItems.collectAsStateWithLifecycle()
    val suggestedUsers by viewModel.suggestedUsers.collectAsStateWithLifecycle()
    val commentsMap by viewModel.comments.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    // Messenger & Calls
    val conversations by viewModel.conversations.collectAsStateWithLifecycle()
    val messagesMap by viewModel.messages.collectAsStateWithLifecycle()
    val activeCall by viewModel.activeCall.collectAsStateWithLifecycle()
    val activeConversationId by viewModel.activeConversationId.collectAsStateWithLifecycle()

    val activeStory by viewModel.activeStory.collectAsStateWithLifecycle()
    val activeCommentsPostId by viewModel.activeCommentsPostId.collectAsStateWithLifecycle()
    val showCreatePost by viewModel.showCreatePost.collectAsStateWithLifecycle()
    val showEditProfile by viewModel.showEditProfile.collectAsStateWithLifecycle()
    val showNotifications by viewModel.showNotifications.collectAsStateWithLifecycle()
    val showDirectMessages by viewModel.showDirectMessages.collectAsStateWithLifecycle()
    val showMenuScreen by viewModel.showMenuScreen.collectAsStateWithLifecycle()
    val selectedExploreItem by viewModel.selectedExploreItem.collectAsStateWithLifecycle()

    if (!isLoggedIn) {
        AuthScreen(
            onLoginSuccess = {
                viewModel.login(currentUser.email, "12345678")
                Toast.makeText(context, "FaceGram এ স্বাগতম, ${currentUser.fullName}!", Toast.LENGTH_SHORT).show()
            },
            onSignUpSuccess = { fullName, username, email, gender, country, birthDate, avatarRes ->
                viewModel.signUp(fullName, username, email, gender, country, birthDate, avatarRes)
                Toast.makeText(context, "অ্যাকাউন্ট সফলভাবে তৈরি হয়েছে!", Toast.LENGTH_SHORT).show()
            }
        )
    } else {
        // Back press handling hierarchy
        BackHandler(enabled = activeCall != null || activeConversationId != null || showDirectMessages || showMenuScreen || selectedTab != BottomTab.HOME) {
            when {
                activeCall != null -> viewModel.endCall()
                activeConversationId != null -> viewModel.closeConversation()
                showDirectMessages -> viewModel.setDirectMessagesVisible(false)
                showMenuScreen -> viewModel.setMenuScreenVisible(false)
                selectedTab != BottomTab.HOME -> viewModel.selectTab(BottomTab.HOME)
            }
        }

        // Active Call Overlay (Full screen for Audio, Video, or Group Video)
        val currentCall = activeCall
        if (currentCall != null) {
            CallScreen(
                call = currentCall,
                onEndCall = {
                    viewModel.endCall()
                    Toast.makeText(context, "কল সমাপ্ত হয়েছে", Toast.LENGTH_SHORT).show()
                },
                onToggleMute = { viewModel.toggleCallMute() },
                onToggleSpeaker = { viewModel.toggleCallSpeaker() },
                onToggleVideo = { viewModel.toggleCallVideo() },
                onFlipCamera = { viewModel.flipCamera() }
            )
        } else if (activeConversationId != null) {
            // Chat Detail View (1-on-1 or Group)
            val activeConv = conversations.find { it.id == activeConversationId }
            if (activeConv != null) {
                val currentChatMessages = messagesMap[activeConversationId].orEmpty()
                ChatDetailScreen(
                    conversation = activeConv,
                    messages = currentChatMessages,
                    onBackClick = { viewModel.closeConversation() },
                    onSendText = { text -> viewModel.sendTextMessage(activeConv.id, text) },
                    onSendMedia = { resId, isVideo -> viewModel.sendMediaMessage(activeConv.id, resId, isVideo) },
                    onSendVoice = { duration -> viewModel.sendVoiceMessage(activeConv.id, duration) },
                    onStartAudioCall = { viewModel.startAudioCall(activeConv.id) },
                    onStartVideoCall = { viewModel.startVideoCall(activeConv.id) },
                    onStartGroupVideoCall = { viewModel.startGroupVideoCall(activeConv.id) }
                )
            }
        } else if (showDirectMessages) {
            // Messenger Inbox Screen
            MessengerScreen(
                currentUsername = currentUser.username,
                conversations = conversations,
                onBackClick = { viewModel.setDirectMessagesVisible(false) },
                onConversationClick = { convId -> viewModel.openConversation(convId) },
                onStartAudioCall = { convId -> viewModel.startAudioCall(convId) },
                onStartVideoCall = { convId ->
                    val conv = conversations.find { it.id == convId }
                    if (conv?.isGroup == true) viewModel.startGroupVideoCall(convId)
                    else viewModel.startVideoCall(convId)
                }
            )
        } else if (showMenuScreen) {
            // Menu Screen containing all 14 features, settings, switch account, Right Manager from user screenshots
            MenuScreen(
                user = currentUser,
                onBackClick = { viewModel.setMenuScreenVisible(false) },
                onNavigateToProfile = {
                    viewModel.setMenuScreenVisible(false)
                    viewModel.selectTab(BottomTab.PROFILE)
                },
                onNavigateToMessages = {
                    viewModel.setMenuScreenVisible(false)
                    viewModel.setDirectMessagesVisible(true)
                },
                onNavigateToReels = {
                    viewModel.setMenuScreenVisible(false)
                    viewModel.selectTab(BottomTab.REELS)
                },
                onNavigateToFeeds = {
                    viewModel.setMenuScreenVisible(false)
                    viewModel.selectTab(BottomTab.HOME)
                },
                onLogoutClick = {
                    viewModel.setMenuScreenVisible(false)
                    viewModel.logout()
                    Toast.makeText(context, "লগ আউট সম্পন্ন হয়েছে", Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            // Standard App Shell
            Scaffold(
                containerColor = FaceGramDarkBg,
                topBar = {
                    if (selectedTab == BottomTab.HOME) {
                        FaceGramTopBar(
                            onAddClick = { viewModel.setCreatePostVisible(true) },
                            onNotificationsClick = { viewModel.setNotificationsVisible(true) },
                            onDirectMessagesClick = { viewModel.setDirectMessagesVisible(true) }
                        )
                    }
                },
                bottomBar = {
                    FaceGramBottomBar(
                        currentTab = selectedTab,
                        avatarResId = currentUser.avatarResId,
                        onSelectTab = { tab -> viewModel.selectTab(tab) }
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(FaceGramDarkBg)
                ) {
                    when (selectedTab) {
                        BottomTab.HOME -> {
                            FeedScreen(
                                stories = stories,
                                posts = posts,
                                onStoryClick = { story -> viewModel.openStory(story) },
                                onLikePost = { postId -> viewModel.toggleLikePost(postId) },
                                onBookmarkPost = { postId -> viewModel.toggleBookmarkPost(postId) },
                                onCommentsClick = { postId -> viewModel.openComments(postId) },
                                onSharePost = { post ->
                                    Toast.makeText(context, "${post.username} এর পোস্ট শেয়ার করা হয়েছে!", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }

                        BottomTab.SEARCH -> {
                            SearchScreen(
                                searchQuery = searchQuery,
                                onSearchQueryChange = { q -> viewModel.updateSearchQuery(q) },
                                exploreItems = exploreItems,
                                onItemClick = { item -> viewModel.selectExploreItem(item) }
                            )
                        }

                        BottomTab.REELS -> {
                            ReelsScreen(
                                reels = reels,
                                onLikeReel = { reelId -> viewModel.toggleLikeReel(reelId) },
                                onFollowUser = { reelId -> viewModel.toggleFollowReelUser(reelId) },
                                onBookmarkReel = { reelId -> viewModel.toggleBookmarkReel(reelId) },
                                onCommentsClick = { reelId -> viewModel.openComments(reelId) },
                                onAddReelClick = { viewModel.setCreatePostVisible(true) }
                            )
                        }

                        BottomTab.PROFILE -> {
                            ProfileScreen(
                                user = currentUser,
                                userPosts = posts.filter { it.userId == currentUser.id },
                                suggestedUsers = suggestedUsers,
                                onFollowSuggestedUser = { userId -> viewModel.toggleFollowSuggestedUser(userId) },
                                onDismissSuggestedUser = { userId -> viewModel.dismissSuggestedUser(userId) },
                                onEditProfileClick = { viewModel.setEditProfileVisible(true) },
                                onLogoutClick = {
                                    viewModel.logout()
                                    Toast.makeText(context, "লগ আউট সম্পন্ন হয়েছে", Toast.LENGTH_SHORT).show()
                                },
                                onAddPostClick = { viewModel.setCreatePostVisible(true) },
                                onOpenMenuClick = { viewModel.setMenuScreenVisible(true) }
                            )
                        }
                    }
                }
            }

            // Global Modals & Sheets
            activeStory?.let { story ->
                StoryViewerDialog(
                    story = story,
                    onClose = { viewModel.closeStory() }
                )
            }

            activeCommentsPostId?.let { postId ->
                val postComments = commentsMap[postId].orEmpty()
                CommentsBottomSheet(
                    postId = postId,
                    commentsList = postComments,
                    onAddComment = { text -> viewModel.addComment(postId, text) },
                    onDismiss = { viewModel.closeComments() }
                )
            }

            if (showCreatePost) {
                CreatePostDialog(
                    onDismiss = { viewModel.setCreatePostVisible(false) },
                    onPostCreated = { caption ->
                        viewModel.createPost(caption)
                        Toast.makeText(context, "FaceGram এ পোস্ট শেয়ার হয়েছে!", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            if (showEditProfile) {
                EditProfileDialog(
                    user = currentUser,
                    onDismiss = { viewModel.setEditProfileVisible(false) },
                    onSave = { name, uname, bio ->
                        viewModel.updateProfile(name, uname, bio, null)
                        Toast.makeText(context, "প্রোফাইল আপডেট হয়েছে!", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            if (showNotifications) {
                NotificationsBottomSheet(
                    onDismiss = { viewModel.setNotificationsVisible(false) }
                )
            }

            selectedExploreItem?.let { item ->
                ExploreMediaDialog(
                    item = item,
                    onDismiss = { viewModel.selectExploreItem(null) }
                )
            }
        }
    }
}
