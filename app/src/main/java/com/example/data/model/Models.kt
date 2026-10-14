package com.example.data.model

data class User(
    val id: String = "u_niyon",
    val fullName: String = "Niyon Islam Sohag",
    val username: String = "niyonislamsohag",
    val email: String = "smd224292@gmail.com",
    val avatarResId: Int? = null,
    val gender: String = "পুরুষ",
    val country: String = "বাংলাদেশ 🇧🇩",
    val birthDate: String = "01/05/2004",
    val bio: String = "Creator • Tech & Digital Media 🚀\nWelcome to my official FaceGram profile!",
    val postsCount: Int = 3,
    val followersCount: Int = 11,
    val followingCount: Int = 32,
    val socialWhatsApp: String = "WhatsApp",
    val socialFacebook: String = "Niyon Islam Sohag",
    val profileViews30Days: Int = 17
)

data class Story(
    val id: String,
    val username: String,
    val avatarResId: Int?,
    val hasUnseen: Boolean = true,
    val isOwnStory: Boolean = false,
    val storyImageResId: Int? = null,
    val text: String = "",
    val timeAgo: String = "2h"
)

data class Post(
    val id: String,
    val userId: String,
    val username: String,
    val userDisplayName: String = "",
    val avatarResId: Int?,
    val audioTrack: String = "Original audio",
    val imageResId: Int? = null,
    val caption: String,
    val likesCount: Int,
    val commentsCount: Int,
    val sharesCount: Int = 51,
    val bookmarksCount: Int = 2613,
    val isLiked: Boolean = false,
    val isBookmarked: Boolean = false,
    val timeAgo: String = "15m"
)

data class Reel(
    val id: String,
    val username: String,
    val avatarResId: Int?,
    val audioTitle: String = "Original audio",
    val caption: String = "Happy Vibe! ✨☀️ #FaceGram #Reels",
    val imageResId: Int? = null,
    val likesCount: String = "24.5K",
    val commentsCount: String = "1,842",
    val sharesCount: String = "3.2K",
    val bookmarksCount: String = "890",
    val isLiked: Boolean = false,
    val isBookmarked: Boolean = false,
    val isFollowing: Boolean = false
)

data class Comment(
    val id: String,
    val username: String,
    val avatarResId: Int?,
    val text: String,
    val timeAgo: String = "Just now",
    val likesCount: Int = 0,
    val isLiked: Boolean = false
)

data class ExploreItem(
    val id: String,
    val imageResId: Int?,
    val title: String,
    val viewsCount: String,
    val isVideo: Boolean = true,
    val author: String = "Creator"
)

data class SuggestedUser(
    val id: String,
    val name: String,
    val username: String,
    val avatarResId: Int?,
    val mutualFriendsText: String = "1 mutual",
    val isFollowed: Boolean = false
)

enum class MessageType {
    TEXT, IMAGE, VIDEO, VOICE_NOTE
}

data class ChatMessage(
    val id: String,
    val conversationId: String,
    val senderName: String,
    val text: String? = null,
    val mediaResId: Int? = null,
    val type: MessageType = MessageType.TEXT,
    val voiceDuration: String = "0:12",
    val timestamp: String = "10:45 AM",
    val isFromMe: Boolean = false
)

data class Conversation(
    val id: String,
    val title: String,
    val isGroup: Boolean = false,
    val participants: List<String> = emptyList(),
    val avatarResId: Int?,
    val isOnline: Boolean = true,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int = 0
)

enum class CallType {
    AUDIO, VIDEO, GROUP_VIDEO
}

data class ActiveCall(
    val callId: String,
    val conversationId: String,
    val title: String,
    val avatarResId: Int?,
    val type: CallType,
    val participants: List<String> = emptyList(),
    val isMuted: Boolean = false,
    val isSpeakerOn: Boolean = false,
    val isVideoEnabled: Boolean = true,
    val isFrontCamera: Boolean = true,
    val durationSeconds: Int = 0
)
